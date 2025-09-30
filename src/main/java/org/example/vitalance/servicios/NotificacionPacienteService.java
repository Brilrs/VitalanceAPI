package org.example.vitalance.servicios;

import org.example.vitalance.dtos.NotificacionPacienteDTO;
import org.example.vitalance.entidades.*;
import org.example.vitalance.interfaces.INotificacionPacienteService;
import org.example.vitalance.repositorios.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificacionPacienteService implements INotificacionPacienteService {

    @Autowired private NotificacionPacienteRepository notifRepo;
    @Autowired private PacienteRepository pacienteRepository;
    @Autowired private MedicionesRepository medicionesRepository;
    @Autowired private UmbralRepository umbralRepository;
    @Autowired private PacienteDoctorRepository pacienteDoctorRepository;
    @Autowired private AlertaRepository alertaRepository;
    @Autowired private ModelMapper modelMapper;

    // “cerca del rango seguro” = |valor - umbral| <= nearMargin
    @Value("${app.notifications.nearMargin:10}")
    private double nearMargin;

    // antirebote
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

        // Garantiza que el/los médico(s) reciban Alerta si no existía (p. ej. caso ADVERTENCIA).
        Paciente paciente = n.getPaciente();
        Mediciones med = n.getMedicion();

        List<PacienteDoctor> vinculos = pacienteDoctorRepository.findAll().stream()
                .filter(v -> v.getPaciente().getIdPaciente().equals(paciente.getIdPaciente()))
                .filter(v -> Boolean.TRUE.equals(v.getActivoPacienteDoctor()))
                .toList();

        for (PacienteDoctor vinc : vinculos) {
            // ¿Existe ya alerta de este doctor por esta medición?
            boolean existe = alertaRepository.findAll().stream()
                    .anyMatch(a -> a.getMedicion().getIdMedicion().equals(med.getIdMedicion())
                            && a.getDoctor().getIdDoctor().equals(vinc.getDoctor().getIdDoctor()));
            if (!existe) {
                Alerta a = new Alerta();
                a.setTipoIndicador(med.getTipoMedicion());
                a.setValor(med.getValorMedicion());
                a.setUnidad(med.getUnidadMedicion());
                a.setSeveridad("NO_CRITICA"); // compartir desde el paciente
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

        // Antirebote 30 min
        LocalDateTime after = LocalDateTime.now().minusMinutes(cooldownMinutes);
        boolean bloqueado = notifRepo.findFirstByPacienteAndTipoIndicadorAndCreadaEnAfterOrderByCreadaEnDesc(
                p, tipoIndicador, after).isPresent();
        if (bloqueado) return;

        // Tomamos el primer umbral activo del/los médico(s) del paciente para ese indicador
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
        if (umbralElegido == null) return; // sin umbral no notificamos

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
        n.setMensaje(String.format("Glucosa %.0f %s %s de rango (%s)",
                valor, unidad, (fuera ? "fuera" : "cerca"), rango));

        // Recomendación específica para hipoglucemia
        if (min != null && valor < min) {
            n.setRecomendacion("Tomar azúcar de acción rápida");
        }
        notifRepo.save(n);
    }
}
