package org.example.vitalance.servicios;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.NotificacionPacienteDTO;
import org.example.vitalance.entidades.*;
import org.example.vitalance.interfaces.INotificacionPacienteService;
import org.example.vitalance.repositorios.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionPacienteService implements INotificacionPacienteService {

    private final NotificacionPacienteRepository notifRepo;
    private final PacienteRepository pacienteRepository;
    private final MedicionesRepository medicionesRepository;
    private final UmbralRepository umbralRepository;
    private final PacienteDoctorRepository pacienteDoctorRepository;
    private final AlertaRepository alertaRepository;
    private final DosisRepository dosisRepository;
    private final ModelMapper modelMapper;

    @Value("${app.notifications.nearMargin:10}")
    private double nearMargin;

    @Value("${app.notifications.cooldownMinutes:30}")
    private long cooldownMinutes;

    @Override
    public List<NotificacionPacienteDTO> listarPorPaciente(Long idPaciente) {
        Paciente p = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        return notifRepo.findByPacienteOrderByCreadaEnDesc(p)
                .stream().map(n -> modelMapper.map(n, NotificacionPacienteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public NotificacionPacienteDTO compartirConMedico(Long idNotificacion) {
        NotificacionPaciente n = notifRepo.findById(idNotificacion)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        n.setEstado("COMPARTIDA");
        n.setActualizadaEn(LocalDateTime.now());
        notifRepo.save(n);

        Paciente paciente = n.getPaciente();
        Mediciones med = n.getMedicion();

        List<PacienteDoctor> vinculos = pacienteDoctorRepository.findAll().stream()
                .filter(v -> v.getPaciente().getIdPaciente().equals(paciente.getIdPaciente()))
                .filter(v -> Boolean.TRUE.equals(v.getActivoPacienteDoctor()))
                .toList();

        for (PacienteDoctor vinc : vinculos) {
            boolean existe = alertaRepository.findAll().stream()
                    .anyMatch(a -> a.getMedicion().getIdMedicion().equals(med.getIdMedicion())
                            && a.getDoctor().getIdDoctor().equals(vinc.getDoctor().getIdDoctor()));
            if (!existe) {
                Alerta a = new Alerta();
                a.setTipoIndicador(med.getTipoMedicion());
                a.setValor(med.getValorMedicion());
                a.setUnidad(med.getUnidadMedicion());
                a.setSeveridad("NO_CRITICA");
                a.setEstado("PENDIENTE");
                a.setPaciente(paciente);
                a.setDoctor(vinc.getDoctor());
                a.setMedicion(med);
                alertaRepository.save(a);
            }
        }
        return modelMapper.map(n, NotificacionPacienteDTO.class);
    }

    @Override
    public void evaluarYCrearParaMedicion(Long idPaciente, Long idMedicion, String tipoIndicador,
                                          double valor, String unidad) {
        Paciente p = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        Mediciones m = medicionesRepository.findById(idMedicion)
                .orElseThrow(() -> new RuntimeException("Medición no encontrada"));

        LocalDateTime after = LocalDateTime.now().minusMinutes(cooldownMinutes);
        boolean bloqueado = notifRepo.findFirstByPacienteAndTipoIndicadorAndCreadaEnAfterOrderByCreadaEnDesc(
                p, tipoIndicador, after).isPresent();
        if (bloqueado) return;

        List<PacienteDoctor> vinculos = pacienteDoctorRepository.findAll().stream()
                .filter(v -> v.getPaciente().getIdPaciente().equals(idPaciente))
                .filter(v -> Boolean.TRUE.equals(v.getActivoPacienteDoctor()))
                .toList();

        Umbral umbralElegido = null;
        for (PacienteDoctor vinc : vinculos) {
            List<Umbral> umbrales = umbralRepository
                    .findByDoctorAndTipoIndicadorIgnoreCaseAndActivoTrue(vinc.getDoctor(), tipoIndicador);
            if (!umbrales.isEmpty()) { umbralElegido = umbrales.get(0); break; }
        }
        if (umbralElegido == null) return;

        Double min = umbralElegido.getMinimo();
        Double max = umbralElegido.getMaximo();

        boolean fuera = (min != null && valor < min) || (max != null && valor > max);
        boolean cerca = false;
        if (!fuera) {
            if (min != null && Math.abs(valor - min) <= nearMargin) cerca = true;
            if (max != null && Math.abs(valor - max) <= nearMargin) cerca = true;
        }
        if (!fuera && !cerca) return;

        NotificacionPaciente n = new NotificacionPaciente();
        n.setPaciente(p);
        n.setMedicion(m);
        n.setTipoIndicador(tipoIndicador);
        n.setValor(valor);
        n.setUnidad(unidad);
        n.setSeveridad(fuera ? "CRITICA" : "ADVERTENCIA");

        String rango = String.format("%s–%s", min != null ? min.intValue() : "–", max != null ? max.intValue() : "–");
        n.setMensaje(String.format("Glucosa %.0f %s %s de rango (%s)", valor, unidad, (fuera ? "fuera" : "cerca"), rango));
        if (min != null && valor < min) {
            n.setRecomendacion("Tomar azúcar de acción rápida");
        }
        notifRepo.save(n);
    }

    @Override
    public void enviarNotificacion(Long recordatorioId, Long idPaciente, String medicamentoNombre) {
        log.info("--- NOTIFICACIÓN INICIAL ENVIADA (US-009) ---");
        log.info("Recordatorio/Dosis ID: {}", recordatorioId);
        log.info("Paciente ID: {}", idPaciente);
        log.info("Medicamento: {}", medicamentoNombre);
        log.info("Mensaje: ¡Es hora de tomar su dosis de {}!", medicamentoNombre);
        log.info("Opciones: [Tomado, Posponer]");
        log.info("---------------------------------------------");
    }

    @Override
    public void enviarSegundoAviso(Long recordatorioId, Long idPaciente, String mensaje) {
        log.warn("--- SEGUNDO AVISO ENVIADO (US-009 Negativo) ---");
        log.warn("Dosis ID: {}", recordatorioId);
        log.warn("Paciente ID: {}", idPaciente);
        log.warn("Mensaje: {}", mensaje);
        log.warn("-----------------------------------------------");
    }

    @Override
    @Transactional
    public void crearAlertaPorOmision(Long dosisId) {
        Dosis dosis = dosisRepository.findById(dosisId)
                .orElseThrow(() -> new RuntimeException("Dosis omitida no encontrada."));

        if (dosis.getEstadoDosis() != EstadoDosis.OMITIDA) {
            log.warn("Intento de crear alerta para dosis ID {} que no está OMITIDA (Estado: {}).", dosisId, dosis.getEstadoDosis());
            return;
        }

        Paciente paciente = dosis.getMedicamentoPaciente().getPaciente();
        Medicamento medicamento = dosis.getMedicamentoPaciente().getMedicamento();

        List<PacienteDoctor> vinculos = pacienteDoctorRepository.findAll().stream()
                .filter(v -> v.getPaciente().getIdPaciente().equals(paciente.getIdPaciente()))
                .filter(v -> Boolean.TRUE.equals(v.getActivoPacienteDoctor()))
                .toList();

        if (vinculos.isEmpty()) {
            log.warn("Dosis omitida ID {} por Paciente ID {} sin médicos vinculados.", dosisId, paciente.getIdPaciente());
            return;
        }

        for (PacienteDoctor vinc : vinculos) {
            boolean existeAlertaPrevia = alertaRepository.findByTipoIndicadorAndPacienteAndDoctor(
                            "OMISION_MEDICACION", paciente, vinc.getDoctor()).stream()
                    .anyMatch(a -> a.getSeveridad().contains(dosisId.toString()));
            if (existeAlertaPrevia) continue;

            Alerta alerta = new Alerta();
            alerta.setTipoIndicador("OMISION_MEDICACION");
            alerta.setSeveridad("CRITICA");
            alerta.setEstado("PENDIENTE");
            alerta.setPaciente(paciente);
            alerta.setDoctor(vinc.getDoctor());
            alerta.setValor(0.0);
            alerta.setUnidad("DOSIS");
            String mensajeAlerta = String.format("CRÍTICA: Dosis de %s OMITIDA. Hora programada: %s. Dosis ID: %d.",
                    medicamento.getNombreMedicamento(),
                    dosis.getHoraProgramada().toLocalTime().truncatedTo(ChronoUnit.MINUTES),
                    dosisId);
            alerta.setSeveridad(mensajeAlerta);
            alertaRepository.save(alerta);
            log.info("Alerta CRÍTICA generada para Médico ID {} por omisión de dosis ID {}.", vinc.getDoctor().getIdDoctor(), dosisId);
        }
    }
}
