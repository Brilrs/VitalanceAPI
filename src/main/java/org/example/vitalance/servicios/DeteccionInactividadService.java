package org.example.vitalance.servicios;

import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.entidades.Paciente;
import org.example.vitalance.entidades.Recordatorio;
import org.example.vitalance.repositorios.MedicionesRepository;
import org.example.vitalance.repositorios.PacienteRepository;
import org.example.vitalance.repositorios.RecordatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DeteccionInactividadService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicionesRepository medicionesRepository;

    @Autowired
    private RecordatorioRepository recordatorioRepository;

    // Job que se ejecuta todos los días a las 8:00 AM
    @Scheduled(cron = "0 0 8 * * *")
    @Transactional
    public void detectarPacientesInactivos() {
        log.info("========== INICIANDO JOB DE DETECCIÓN DE INACTIVIDAD ==========");

        try {
            int pacientesRevisados = 0;
            int alertasGeneradas = 0;

            // Obtener todos los pacientes activos con diabetes Tipo 1
            List<Paciente> pacientesActivos = pacienteRepository.findByActivoPacienteTrue();

            for (Paciente paciente : pacientesActivos) {
                // Filtrar solo Tipo 1
                if (!"Tipo 1".equalsIgnoreCase(paciente.getTipoDiabetesPaciente())) {
                    continue;
                }

                pacientesRevisados++;

                // Obtener última fecha de medición
                Optional<LocalDate> ultimaMedicion = medicionesRepository
                        .findUltimaMedicionFecha(paciente.getIdPaciente());

                if (ultimaMedicion.isEmpty()) {
                    // Paciente nunca ha registrado mediciones
                    log.warn("Paciente {} nunca ha registrado mediciones",
                            paciente.getIdPaciente());
                    continue;
                }

                // Calcular días sin medición
                long diasSinMedicion = ChronoUnit.DAYS.between(
                        ultimaMedicion.get(),
                        LocalDate.now()
                );

                // Si lleva más de 7 días sin mediciones
                if (diasSinMedicion > 7) {
                    // Verificar si ya existe alerta pendiente
                    boolean alertaExiste = recordatorioRepository
                            .existsByPaciente_IdPacienteAndTipoRecordatorioAndEstadoRecordatorio(
                                    paciente.getIdPaciente(),
                                    "PACIENTE_INACTIVO",
                                    true
                            );

                    if (alertaExiste) {
                        log.info("Alerta ya existente para paciente {}",
                                paciente.getIdPaciente());
                        continue;
                    }

                    // Crear nuevo recordatorio
                    Recordatorio recordatorio = new Recordatorio();
                    recordatorio.setTipoRecordatorio("PACIENTE_INACTIVO");
                    recordatorio.setEstadoRecordatorio(true); // Pendiente
                    recordatorio.setFechaCreacionRecordatorio(LocalDateTime.now());
                    recordatorio.setPaciente(paciente);

                    recordatorioRepository.save(recordatorio);
                    alertasGeneradas++;

                    log.info("Alerta generada para paciente {}: {} días sin mediciones (última: {})",
                            paciente.getIdPaciente(),
                            diasSinMedicion,
                            ultimaMedicion.get()
                    );
                }
            }

            log.info("========== JOB FINALIZADO ==========");
            log.info("Pacientes Tipo 1 revisados: {}", pacientesRevisados);
            log.info("Alertas de inactividad generadas: {}", alertasGeneradas);

        } catch (Exception e) {
            log.error("Error en job de detección de inactividad", e);
            // No lanzar excepción para no detener el scheduler
        }
    }

    // Método público para consulta manual
    public List<PacienteInactivoDTO> obtenerPacientesInactivos(int diasUmbral) {
        List<PacienteInactivoDTO> resultados = new ArrayList<>();

        List<Paciente> pacientesActivos = pacienteRepository.findByActivoPacienteTrue();

        for (Paciente paciente : pacientesActivos) {
            if (!"Tipo 1".equalsIgnoreCase(paciente.getTipoDiabetesPaciente())) {
                continue;
            }

            Optional<LocalDate> ultimaMedicion = medicionesRepository
                    .findUltimaMedicionFecha(paciente.getIdPaciente());

            if (ultimaMedicion.isEmpty()) {
                continue;
            }

            long diasSinMedicion = ChronoUnit.DAYS.between(
                    ultimaMedicion.get(),
                    LocalDate.now()
            );

            if (diasSinMedicion > diasUmbral) {
                PacienteInactivoDTO dto = new PacienteInactivoDTO();
                dto.setIdPaciente(paciente.getIdPaciente());
                dto.setNombreCompleto(
                        paciente.getUser().getNombreUser() + " " +
                                paciente.getUser().getApellidoUser()
                );
                dto.setNumeroHistoriaClinica(paciente.getNumeroHistoriaClinicaPaciente());
                dto.setDiasSinMedicion((int) diasSinMedicion);
                dto.setUltimaMedicion(ultimaMedicion.get());

                resultados.add(dto);
            }
        }

        return resultados;
    }

    // DTO interno para respuesta
    @lombok.Data
    public static class PacienteInactivoDTO {
        private Long idPaciente;
        private String nombreCompleto;
        private String numeroHistoriaClinica;
        private Integer diasSinMedicion;
        private LocalDate ultimaMedicion;
    }
}