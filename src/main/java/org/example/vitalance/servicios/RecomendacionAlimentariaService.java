package org.example.vitalance.servicios;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.*;
import org.example.vitalance.entidades.Alimento;
import org.example.vitalance.entidades.Mediciones;
import org.example.vitalance.entidades.Paciente;
import org.example.vitalance.interfaces.IRecomendacionAlimentariaService;
import org.example.vitalance.repositorios.AlimentoRepository;
import org.example.vitalance.repositorios.MedicionesRepository;
import org.example.vitalance.repositorios.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecomendacionAlimentariaService implements IRecomendacionAlimentariaService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicionesRepository medicionesRepository;

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private GeminiService geminiService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public RecomendacionAlimentariaDTO generarRecomendaciones(RecomendacionRequestDTO request) {
        log.info("Generando recomendaciones para paciente ID: {}, tipo comida: {}",
                request.getIdPaciente(), request.getTipoComida());

        // 1. Validar y obtener paciente
        Paciente paciente = validarYObtenerPaciente(request.getIdPaciente());

        // 2. Obtener última medición
        Mediciones ultimaMedicion = obtenerUltimaMedicion(paciente);

        // 3. Validar antigüedad de medición
        validarAntiguedadMedicion(ultimaMedicion);

        // 4. Validar tipo de comida
        validarTipoComida(request.getTipoComida());

        // 5. Construir prompt contextualizado
        String prompt = construirPrompt(paciente, ultimaMedicion, request);

        // 6. Llamar a Gemini
        String respuestaIA = llamarGemini(prompt);

        // 7. Procesar respuesta de IA
        RecomendacionAlimentariaDTO resultado = procesarRespuestaIA(
                respuestaIA, paciente, ultimaMedicion, request, prompt);

        log.info("Recomendaciones generadas exitosamente para paciente ID: {}",
                request.getIdPaciente());

        return resultado;
    }

    @Override
    public List<RecomendacionAlimentariaDTO> obtenerHistorial(Long idPaciente) {
        // TODO: Implementar persistencia de recomendaciones si se requiere
        log.warn("Historial de recomendaciones no implementado aún");
        return new ArrayList<>();
    }

    @Override
    public boolean validarDatosSuficientes(Long idPaciente) {
        try {
            Paciente paciente = pacienteRepository.findById(idPaciente)
                    .orElse(null);

            if (paciente == null) return false;

            // Verificar datos esenciales
            boolean tieneTipoDiabetes = paciente.getTipoDiabetesPaciente() != null &&
                    !paciente.getTipoDiabetesPaciente().trim().isEmpty();

            boolean tieneDatosAntropometricos = paciente.getPesoDiagnosticoPaciente() != null &&
                    paciente.getEstaturaDiagnosticoPaciente() != null;

            // Verificar que tenga al menos una medición
            List<Mediciones> mediciones = medicionesRepository.findByPaciente(paciente);
            boolean tieneMediciones = !mediciones.isEmpty();

            return tieneTipoDiabetes && tieneDatosAntropometricos && tieneMediciones;

        } catch (Exception e) {
            log.error("Error validando datos del paciente {}: {}", idPaciente, e.getMessage());
            return false;
        }
    }

    // ==================== MÉTODOS PRIVADOS ====================

    private Paciente validarYObtenerPaciente(Long idPaciente) {
        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new RuntimeException("REC-001: Paciente no encontrado con ID: " + idPaciente));

        // Validar que está activo
        if (!Boolean.TRUE.equals(paciente.getActivoPaciente())) {
            throw new RuntimeException("REC-001: Paciente está inactivo");
        }

        // Validar datos completos
        if (paciente.getTipoDiabetesPaciente() == null || paciente.getTipoDiabetesPaciente().trim().isEmpty()) {
            throw new RuntimeException("REC-002: Tipo de diabetes no especificado");
        }

        if (paciente.getPesoDiagnosticoPaciente() == null || paciente.getEstaturaDiagnosticoPaciente() == null) {
            throw new RuntimeException("REC-002: Datos antropométricos incompletos (peso o estatura)");
        }

        return paciente;
    }

    private Mediciones obtenerUltimaMedicion(Paciente paciente) {
        List<Mediciones> mediciones = medicionesRepository.findByPaciente(paciente);

        if (mediciones.isEmpty()) {
            throw new RuntimeException("REC-003: No hay mediciones registradas para este paciente");
        }

        // Obtener la medición más reciente de glucosa
        return mediciones.stream()
                .filter(m -> "GLUCOSA".equalsIgnoreCase(m.getTipoMedicion()) ||
                        "Glucosa".equalsIgnoreCase(m.getTipoMedicion()))
                .max(Comparator.comparing(Mediciones::getFechaMedicicion))
                .orElseThrow(() -> new RuntimeException("REC-003: No hay mediciones de glucosa registradas"));
    }

    private void validarAntiguedadMedicion(Mediciones medicion) {
        LocalDate fechaMedicion = medicion.getFechaMedicicion();
        LocalDate hoy = LocalDate.now();
        long diasDesde = ChronoUnit.DAYS.between(fechaMedicion, hoy);

        if (diasDesde > 7) {
            throw new RuntimeException(
                    String.format("REC-004: La última medición es de hace %d días. " +
                            "Por favor registra una medición reciente", diasDesde));
        }
    }

    private void validarTipoComida(String tipoComida) {
        List<String> tiposValidos = Arrays.asList("DESAYUNO", "ALMUERZO", "CENA", "SNACK");

        if (tipoComida == null || !tiposValidos.contains(tipoComida.toUpperCase())) {
            throw new RuntimeException("REC-005: Tipo de comida inválido. " +
                    "Debe ser: DESAYUNO, ALMUERZO, CENA o SNACK");
        }
    }

    private String construirPrompt(Paciente paciente, Mediciones medicion, RecomendacionRequestDTO request) {
        Double imc = calcularIMC(paciente.getPesoDiagnosticoPaciente(),
                paciente.getEstaturaDiagnosticoPaciente());

        String restricciones = request.getRestriccionesDieteticas() != null ?
                request.getRestriccionesDieteticas() : "ninguna";

        return String.format("""
                Eres un nutricionista especializado en diabetes que debe proporcionar recomendaciones alimenticias específicas.
                
                DATOS DEL PACIENTE:
                - Tipo de diabetes: %s
                - Glucosa actual: %.0f mg/dL
                - Peso: %.1f kg
                - Estatura: %.2f m
                - IMC: %.1f
                - Rango objetivo de glucosa: %.0f-%.0f mg/dL
                
                CONTEXTO DE LA COMIDA:
                - Tipo de comida: %s
                - Hora planeada: %s
                - Restricciones dietéticas: %s
                
                TAREA:
                Proporciona recomendaciones específicas de alimentos para esta comida, considerando el nivel actual de glucosa.
                
                IMPORTANTE: Responde ÚNICAMENTE con un objeto JSON válido, sin texto adicional antes o después.
                
                Formato de respuesta (JSON):
                {
                  "recomendaciones": [
                    {
                      "nombre": "nombre del alimento",
                      "porcion": "cantidad específica (ej: 1 taza, 150g)",
                      "razon": "por qué es bueno para este paciente"
                    }
                  ],
                  "advertencias": [
                    "lista de advertencias importantes"
                  ],
                  "alimentosEvitar": [
                    "lista de alimentos que debe evitar"
                  ],
                  "consejosAdicionales": [
                    "consejos generales para esta comida"
                  ]
                }
                
                Proporciona entre 5 y 7 alimentos recomendados. Sé específico y práctico.
                """,
                paciente.getTipoDiabetesPaciente(),
                medicion.getValorMedicion(),
                paciente.getPesoDiagnosticoPaciente(),
                paciente.getEstaturaDiagnosticoPaciente(),
                imc,
                paciente.getGlucosaMinimaPaciente() != null ? paciente.getGlucosaMinimaPaciente() : 70.0,
                paciente.getGlucosaMaximaPaciente() != null ? paciente.getGlucosaMaximaPaciente() : 140.0,
                request.getTipoComida(),
                request.getHoraPlaneada() != null ? request.getHoraPlaneada().toString() : "no especificada",
                restricciones
        );
    }

    private String llamarGemini(String prompt) {
        try {
            log.debug("Llamando a Gemini con prompt de {} caracteres", prompt.length());
            String respuesta = geminiService.generarTexto(prompt);
            log.debug("Respuesta recibida de Gemini: {} caracteres", respuesta.length());
            return respuesta;
        } catch (Exception e) {
            log.error("Error al llamar a Gemini: {}", e.getMessage(), e);
            throw new RuntimeException("REC-006: Error al generar recomendaciones con IA: " + e.getMessage());
        }
    }

    private RecomendacionAlimentariaDTO procesarRespuestaIA(
            String respuestaIA,
            Paciente paciente,
            Mediciones medicion,
            RecomendacionRequestDTO request,
            String prompt) {

        RecomendacionAlimentariaDTO resultado = new RecomendacionAlimentariaDTO();

        try {
            // Limpiar respuesta (remover markdown si existe)
            String jsonLimpio = limpiarRespuestaJSON(respuestaIA);

            // Parsear JSON
            JsonNode rootNode = objectMapper.readTree(jsonLimpio);

            // Construir DTO base
            resultado.setPacienteId(paciente.getIdPaciente());
            resultado.setNombrePaciente(paciente.getUser().getNombreUser() + " " +
                    paciente.getUser().getApellidoUser());
            resultado.setTipoComida(request.getTipoComida());
            resultado.setGlucosaActual(medicion.getValorMedicion());
            resultado.setFechaMedicion(medicion.getFechaMedicicion().atStartOfDay());
            resultado.setDiasDesdeMedicion((int) ChronoUnit.DAYS.between(
                    medicion.getFechaMedicicion(), LocalDate.now()));
            resultado.setFechaGeneracion(LocalDateTime.now());
            resultado.setPromptUtilizado(prompt);
            resultado.setRespuestaIA(respuestaIA);

            // Determinar nivel de glucosa
            resultado.determinarNivelGlucosa();

            // Procesar recomendaciones de alimentos
            List<AlimentoRecomendadoDTO> recomendaciones = procesarRecomendacionesAlimentos(rootNode);
            resultado.setRecomendaciones(recomendaciones);

            // Calcular resumen nutricional
            ResumenNutricionalDTO resumen = calcularResumen(recomendaciones, medicion.getValorMedicion());
            resultado.setResumen(resumen);

            // Extraer advertencias
            if (rootNode.has("advertencias")) {
                List<String> advertencias = new ArrayList<>();
                rootNode.get("advertencias").forEach(node -> advertencias.add(node.asText()));
                resultado.setAdvertencias(advertencias);
            }

            // Extraer alimentos a evitar
            if (rootNode.has("alimentosEvitar")) {
                List<String> evitar = new ArrayList<>();
                rootNode.get("alimentosEvitar").forEach(node -> evitar.add(node.asText()));
                resultado.setAlimentosEvitar(evitar);
            }

            // Extraer consejos adicionales
            if (rootNode.has("consejosAdicionales")) {
                List<String> consejos = new ArrayList<>();
                rootNode.get("consejosAdicionales").forEach(node -> consejos.add(node.asText()));
                resultado.setConsejosAdicionales(consejos);
            }

            // Agregar advertencias automáticas según nivel de glucosa
            agregarAdvertenciasAutomaticas(resultado);

            return resultado;

        } catch (Exception e) {
            log.error("Error parseando respuesta de IA: {}", e.getMessage(), e);
            log.error("Respuesta recibida: {}", respuestaIA);
            throw new RuntimeException("REC-007: Error al procesar respuesta de IA: " + e.getMessage());
        }
    }

    private String limpiarRespuestaJSON(String respuesta) {
        // Remover markdown code blocks si existen
        String limpio = respuesta.trim();
        limpio = limpio.replaceAll("```json\\s*", "");
        limpio = limpio.replaceAll("```\\s*", "");
        limpio = limpio.trim();
        return limpio;
    }

    private List<AlimentoRecomendadoDTO> procesarRecomendacionesAlimentos(JsonNode rootNode) {
        List<AlimentoRecomendadoDTO> recomendaciones = new ArrayList<>();

        if (!rootNode.has("recomendaciones")) {
            log.warn("No se encontró campo 'recomendaciones' en respuesta");
            return recomendaciones;
        }

        JsonNode recArray = rootNode.get("recomendaciones");
        int orden = 1;

        for (JsonNode rec : recArray) {
            AlimentoRecomendadoDTO dto = new AlimentoRecomendadoDTO();

            String nombreAlimento = rec.has("nombre") ? rec.get("nombre").asText() : "Alimento desconocido";
            dto.setNombre(nombreAlimento);
            dto.setPorcionSugerida(rec.has("porcion") ? rec.get("porcion").asText() : "1 porción");
            dto.setRazonRecomendacion(rec.has("razon") ? rec.get("razon").asText() : "Recomendado para tu dieta");
            dto.setOrdenPrioridad(orden++);

            // Intentar buscar el alimento en la BD para obtener datos nutricionales
            buscarDatosNutricionalesEnBD(dto, nombreAlimento);

            recomendaciones.add(dto);
        }

        return recomendaciones;
    }

    private void buscarDatosNutricionalesEnBD(AlimentoRecomendadoDTO dto, String nombreAlimento) {
        try {
            // Buscar alimentos que contengan el nombre (búsqueda flexible)
            List<Alimento> alimentosEncontrados = alimentoRepository.findAll().stream()
                    .filter(a -> a.getNombre() != null &&
                            a.getNombre().toLowerCase().contains(nombreAlimento.toLowerCase()))
                    .limit(1)
                    .collect(Collectors.toList());

            if (!alimentosEncontrados.isEmpty()) {
                Alimento alimento = alimentosEncontrados.get(0);
                dto.setAlimentoId(alimento.getId());
                dto.setDescripcion(alimento.getDescripcion());
                dto.setCarbohidratos(alimento.getCarbohidrato());
                dto.setCalorias(alimento.getCalorias());
                dto.setIndiceGlucemico(alimento.getIndiceGlucemico());
                dto.calcularCategoriaIG();
            } else {
                // Valores por defecto si no se encuentra
                dto.setCarbohidratos(0.0);
                dto.setCalorias(0.0);
                dto.setIndiceGlucemico(null);
                dto.setCategoriaIG("DESCONOCIDO");
            }
        } catch (Exception e) {
            log.warn("Error buscando datos nutricionales para '{}': {}", nombreAlimento, e.getMessage());
        }
    }

    private ResumenNutricionalDTO calcularResumen(List<AlimentoRecomendadoDTO> recomendaciones, Double glucosaActual) {
        ResumenNutricionalDTO resumen = new ResumenNutricionalDTO();

        double totalCarbs = recomendaciones.stream()
                .mapToDouble(r -> r.getCarbohidratos() != null ? r.getCarbohidratos() : 0.0)
                .sum();

        double totalCals = recomendaciones.stream()
                .mapToDouble(r -> r.getCalorias() != null ? r.getCalorias() : 0.0)
                .sum();

        double promedioIG = recomendaciones.stream()
                .filter(r -> r.getIndiceGlucemico() != null)
                .mapToDouble(AlimentoRecomendadoDTO::getIndiceGlucemico)
                .average()
                .orElse(0.0);

        resumen.setTotalCarbohidratos(totalCarbs);
        resumen.setTotalCalorias(totalCals);
        resumen.setPromedioIndiceGlucemico(promedioIG);

        // Calcular evaluación
        resumen.calcularEvaluacion(glucosaActual);

        // Recomendación horaria
        if (glucosaActual < 70) {
            resumen.setRecomendacionHoraria("Consumir INMEDIATAMENTE para elevar glucosa");
        } else if (glucosaActual > 180) {
            resumen.setRecomendacionHoraria("Esperar a que glucosa baje antes de consumir");
        } else {
            resumen.setRecomendacionHoraria("Puedes consumir en los próximos 30-60 minutos");
        }

        return resumen;
    }

    private void agregarAdvertenciasAutomaticas(RecomendacionAlimentariaDTO resultado) {
        List<String> advertencias = resultado.getAdvertencias();
        if (advertencias == null) {
            advertencias = new ArrayList<>();
        }

        Double glucosa = resultado.getGlucosaActual();

        // Advertencias por glucosa baja
        if (glucosa < 70) {
            advertencias.add("⚠️ GLUCOSA BAJA: Consume alimentos de acción rápida primero");
            advertencias.add("Mide tu glucosa nuevamente en 15 minutos después de comer");
            resultado.setEsRecomendacionEmergencia(true);
        }

        // Advertencias por glucosa alta
        if (glucosa > 180) {
            advertencias.add("⚠️ GLUCOSA ALTA: Evita carbohidratos simples");
            advertencias.add("Considera posponer esta comida hasta que tu glucosa baje");
            advertencias.add("Consulta con tu médico si persiste elevada");
            resultado.setEsRecomendacionEmergencia(true);
        }

        // Advertencias por datos antiguos
        if (resultado.getDiasDesdeMedicion() > 3) {
            advertencias.add("⚠️ Estas recomendaciones se basan en una medición de hace " +
                    resultado.getDiasDesdeMedicion() + " días");
            advertencias.add("Registra una medición actual para recomendaciones más precisas");
        }

        // Advertencia general
        advertencias.add("Siempre mide tu glucosa 2 horas después de comer");

        resultado.setAdvertencias(advertencias);
    }

    private Double calcularIMC(Double peso, Double estatura) {
        if (peso == null || estatura == null || estatura == 0) {
            return 0.0;
        }
        return peso / (estatura * estatura);
    }
}