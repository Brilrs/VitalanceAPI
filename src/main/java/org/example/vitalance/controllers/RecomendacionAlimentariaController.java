package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.RecomendacionAlimentariaDTO;
import org.example.vitalance.dtos.RecomendacionRequestDTO;
import org.example.vitalance.servicios.RecomendacionAlimentariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/apiRecomendaciones")
public class RecomendacionAlimentariaController {

    @Autowired
    private RecomendacionAlimentariaService recomendacionService;

    /**
     * Endpoint principal: Generar recomendaciones alimenticias
     * POST /apiRecomendaciones/generar
     */
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','PACIENTE')")
    @PostMapping("/generar")
    public ResponseEntity<?> generarRecomendaciones(
            @Valid @RequestBody RecomendacionRequestDTO request) {

        log.info("Solicitud de recomendaciones - Paciente: {}, Tipo: {}",
                request.getIdPaciente(), request.getTipoComida());

        try {
            // Generar recomendaciones
            RecomendacionAlimentariaDTO resultado = recomendacionService.generarRecomendaciones(request);

            log.info("Recomendaciones generadas exitosamente para paciente: {}",
                    request.getIdPaciente());

            return ResponseEntity.ok(resultado);

        } catch (RuntimeException e) {
            log.error("Error generando recomendaciones: {}", e.getMessage());

            // Extraer código de error si existe
            String mensaje = e.getMessage();
            String codigo = "REC-999"; // código genérico
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

            if (mensaje.startsWith("REC-001")) {
                codigo = "REC-001";
                status = HttpStatus.NOT_FOUND;
            } else if (mensaje.startsWith("REC-002") || mensaje.startsWith("REC-003") ||
                    mensaje.startsWith("REC-004") || mensaje.startsWith("REC-005")) {
                codigo = mensaje.substring(0, 7);
                status = HttpStatus.BAD_REQUEST;
            } else if (mensaje.startsWith("REC-006") || mensaje.startsWith("REC-007")) {
                codigo = mensaje.substring(0, 7);
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("codigo", codigo);
            errorResponse.put("mensaje", mensaje);
            errorResponse.put("timestamp", java.time.LocalDateTime.now());

            return ResponseEntity.status(status).body(errorResponse);
        }
    }

    /**
     * Endpoint: Validar si un paciente tiene datos suficientes
     * GET /apiRecomendaciones/validar/{idPaciente}
     */
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR','PACIENTE')")
    @GetMapping("/validar/{idPaciente}")
    public ResponseEntity<Map<String, Object>> validarDatosPaciente(
            @PathVariable Long idPaciente) {

        log.info("Validando datos del paciente: {}", idPaciente);

        Map<String, Object> respuesta = new HashMap<>();

        try {
            boolean datosSuficientes = recomendacionService.validarDatosSuficientes(idPaciente);

            respuesta.put("idPaciente", idPaciente);
            respuesta.put("datosSuficientes", datosSuficientes);

            if (!datosSuficientes) {
                respuesta.put("mensaje", "El paciente no tiene datos suficientes para generar recomendaciones");
                respuesta.put("accionSugerida", "Completar perfil y registrar mediciones de glucosa");
            } else {
                respuesta.put("mensaje", "El paciente tiene datos completos");
                respuesta.put("puedeGenerarRecomendaciones", true);
            }

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            log.error("Error validando datos del paciente {}: {}", idPaciente, e.getMessage());

            respuesta.put("error", true);
            respuesta.put("mensaje", "Error al validar datos del paciente");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    /**
     * Endpoint: Obtener historial de recomendaciones
     * GET /apiRecomendaciones/historial/{idPaciente}
     */
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR','PACIENTE')")
    @GetMapping("/historial/{idPaciente}")
    public ResponseEntity<List<RecomendacionAlimentariaDTO>> obtenerHistorial(
            @PathVariable Long idPaciente) {

        log.info("Obteniendo historial de recomendaciones para paciente: {}", idPaciente);

        try {
            List<RecomendacionAlimentariaDTO> historial =
                    recomendacionService.obtenerHistorial(idPaciente);

            return ResponseEntity.ok(historial);

        } catch (Exception e) {
            log.error("Error obteniendo historial: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Endpoint de ayuda: Listar tipos de comida válidos
     * GET /apiRecomendaciones/tiposComida
     */
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','PACIENTE')")
    @GetMapping("/tiposComida")
    public ResponseEntity<Map<String, Object>> listarTiposComida() {

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("tiposComida", List.of("DESAYUNO", "ALMUERZO", "CENA", "SNACK"));
        respuesta.put("descripcion", Map.of(
                "DESAYUNO", "Primera comida del día (6:00 - 10:00)",
                "ALMUERZO", "Comida principal del día (12:00 - 15:00)",
                "CENA", "Última comida del día (18:00 - 21:00)",
                "SNACK", "Refrigerio entre comidas"
        ));

        return ResponseEntity.ok(respuesta);
    }

    /**
     * Endpoint de health check específico
     * GET /apiRecomendaciones/health
     */
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','PACIENTE')")
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("servicio", "Recomendaciones Alimentarias");
        health.put("timestamp", java.time.LocalDateTime.now().toString());

        return ResponseEntity.ok(health);
    }
}