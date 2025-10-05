package org.example.vitalance.servicios;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.RecordatorioDTO;
import org.example.vitalance.entidades.Dosis;
import org.example.vitalance.entidades.EstadoDosis;
import org.example.vitalance.entidades.MedicamentoPaciente;
import org.example.vitalance.entidades.Recordatorio;
import org.example.vitalance.interfaces.IDosisService;
import org.example.vitalance.interfaces.INotificacionPacienteService;
import org.example.vitalance.interfaces.IRecordatorioService;
import org.example.vitalance.repositorios.DosisRepository;
import org.example.vitalance.repositorios.MedicamentoPacienteRepository;
import org.example.vitalance.repositorios.RecordatorioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordatorioService implements IRecordatorioService {

    private final RecordatorioRepository recordatorioRepository;
    private final MedicamentoPacienteRepository mpRepository;
    private final INotificacionPacienteService notificacionService;
    private final IDosisService dosisService;
    private final ModelMapper modelMapper;
    private final DosisRepository dosisRepository;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Override
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void verificarYEnviarRecordatorios() {
        LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        LocalDate today = LocalDate.now();

        List<MedicamentoPaciente> programacionesActivas = mpRepository.findAll().stream()
                .filter(mp -> Boolean.TRUE.equals(mp.getEstadoMedicamento()))
                .toList();

        for (MedicamentoPaciente mp : programacionesActivas) {
            if (!esDiaDeToma(mp.getFrecuenciaMedicamento(), today.getDayOfWeek())) continue;
            if (mp.getFechaInicioMedicamento() != null && today.isBefore(mp.getFechaInicioMedicamento())) continue;
            if (mp.getFechaFinMedicamento() != null && today.isAfter(mp.getFechaFinMedicamento())) continue;
            if (mp.getHorasProgramadas() == null || mp.getHorasProgramadas().isEmpty()) continue;

            boolean tocaAhora = mp.getHorasProgramadas().stream()
                    .anyMatch(h -> h.truncatedTo(ChronoUnit.MINUTES).equals(now));
            if (!tocaAhora) continue;

            LocalDateTime horaToma = LocalDateTime.of(today, now);
            Dosis dosisCreada = dosisService.crearDosisPendiente(mp.getIdMedicamentoPaciente(), horaToma);
            if (dosisCreada != null) {
                notificacionService.enviarNotificacion(dosisCreada.getIdDosis(),
                        mp.getPaciente().getIdPaciente(),
                        mp.getMedicamento().getNombreMedicamento());
                enviarSegundoAviso(dosisCreada.getIdDosis(), mp.getPaciente().getIdPaciente(), mp.getMedicamento().getNombreMedicamento());
            }
        }
    }

    @Scheduled(cron = "0 30 * * * ?")
    @Transactional
    public void verificarYMarcarDosisOmitidas() {
        LocalDateTime horaCorte = LocalDateTime.now().minusMinutes(30);
        List<Dosis> dosisOmitidas = dosisRepository.findByEstadoDosisAndHoraProgramadaBefore(EstadoDosis.PENDIENTE, horaCorte);
        for (Dosis dosis : dosisOmitidas) {
            dosis.setEstadoDosis(EstadoDosis.OMITIDA);
            dosisRepository.save(dosis);
            log.warn("Dosis ID {} marcada automáticamente como OMITIDA.", dosis.getIdDosis());
        }
    }

    @Override
    public void enviarSegundoAviso(Long dosisId, Long idPaciente, String nombreMedicamento) {
        scheduler.schedule(() -> {
            dosisRepository.findById(dosisId).ifPresent(d -> {
                if (d.getEstadoDosis() == EstadoDosis.PENDIENTE) {
                    String mensaje = String.format("Segundo Aviso: Por favor, confirme la toma de %s. Pasaron 10 minutos.", nombreMedicamento);
                    notificacionService.enviarSegundoAviso(dosisId, idPaciente, mensaje);
                    log.warn("Segundo aviso enviado para Dosis ID {} (aún PENDIENTE)", dosisId);
                } else {
                    log.info("Segundo aviso omitido; Dosis ID {} ya no está PENDIENTE (estado actual: {}).", dosisId, d.getEstadoDosis());
                }
            });
        }, 10, TimeUnit.MINUTES);
    }

    private boolean esDiaDeToma(String frecuencia, DayOfWeek todayDay) {
        if (frecuencia == null || frecuencia.isBlank()) return true;
        String upper = frecuencia.toUpperCase();
        if (upper.contains("DIARIA")) return true;
        String diaEsp = switch (todayDay) {
            case MONDAY -> "LUNES"; case TUESDAY -> "MARTES"; case WEDNESDAY -> "MIERCOLES";
            case THURSDAY -> "JUEVES"; case FRIDAY -> "VIERNES"; case SATURDAY -> "SABADO"; case SUNDAY -> "DOMINGO"; };
        return upper.contains(diaEsp);
    }

    @Override
    public List<RecordatorioDTO> listar() {
        return recordatorioRepository.findAll().stream()
                .map(r -> modelMapper.map(r, RecordatorioDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RecordatorioDTO insertar(RecordatorioDTO recordatorioDto) {
        Recordatorio entity = modelMapper.map(recordatorioDto, Recordatorio.class);
        Recordatorio saved = recordatorioRepository.save(entity);
        return modelMapper.map(saved, RecordatorioDTO.class);
    }

    @Override
    public RecordatorioDTO editar(RecordatorioDTO recordatorioDto) {
        return recordatorioRepository.findById(recordatorioDto.getIdRecordatorio())
                .map(existing -> {
                    Recordatorio entity = modelMapper.map(recordatorioDto, Recordatorio.class);
                    Recordatorio saved = recordatorioRepository.save(entity);
                    return modelMapper.map(saved, RecordatorioDTO.class);
                }).orElseThrow(() -> new RuntimeException("Recordatorio no encontrado"));
    }

    @Override
    public RecordatorioDTO buscarPorId(Long id) {
        return recordatorioRepository.findById(id)
                .map(r -> modelMapper.map(r, RecordatorioDTO.class))
                .orElseThrow(() -> new RuntimeException("Recordatorio no encontrado"));
    }

    @Override
    public void eliminar(Long id) {
        if (!recordatorioRepository.existsById(id)) throw new RuntimeException("Recordatorio no encontrado");
        recordatorioRepository.deleteById(id);
    }

    @Override
    public List<RecordatorioDTO> filtrarPorPaciente(String filtro) {
        if (filtro == null || filtro.isBlank()) throw new IllegalArgumentException("El parámetro de búsqueda es requerido");
        return recordatorioRepository.findAll().stream()
                .map(r -> modelMapper.map(r, RecordatorioDTO.class))
                .collect(Collectors.toList());
    }
}
