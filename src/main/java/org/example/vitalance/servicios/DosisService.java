package org.example.vitalance.servicios;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.DosisDTO;
import org.example.vitalance.dtos.SolicitudConfirmacionDosisDTO;
import org.example.vitalance.entidades.Dosis;
import org.example.vitalance.entidades.EstadoDosis;
import org.example.vitalance.entidades.MedicamentoPaciente;
import org.example.vitalance.interfaces.IDosisService;
import org.example.vitalance.repositorios.DosisRepository;
import org.example.vitalance.repositorios.MedicamentoPacienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DosisService implements IDosisService {

    private final DosisRepository dosisRepository;
    private final MedicamentoPacienteRepository mpRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void confirmarToma(SolicitudConfirmacionDosisDTO requestDTO) {
        Dosis dosis = dosisRepository.findById(requestDTO.getIdDosis())
                .orElseThrow(() -> new RuntimeException("Dosis no encontrada para confirmar."));

        if ("TOMADA".equalsIgnoreCase(requestDTO.getAccion())) {
            dosis.setEstadoDosis(EstadoDosis.TOMADA);
            dosis.setConfirmadaEn(requestDTO.getHoraConfirmacion());
            dosisRepository.save(dosis);
            log.info("Dosis ID {} marcada como TOMADA a las {}", dosis.getIdDosis(), requestDTO.getHoraConfirmacion());
        } else if ("POSPONER".equalsIgnoreCase(requestDTO.getAccion())) {
            log.info("Dosis ID {} pospuesta. El RecordatorioService gestionará el segundo aviso.", dosis.getIdDosis());
        } else {
            throw new IllegalArgumentException("Acción de dosis no válida: " + requestDTO.getAccion());
        }
    }

    @Override
    @Transactional
    public Dosis crearDosisPendiente(Long idMedicamentoPaciente, LocalDateTime horaProgramada) {
        MedicamentoPaciente mp = mpRepository.findById(idMedicamentoPaciente)
                .orElseThrow(() -> new RuntimeException("Programación de medicamento no encontrada."));

        Dosis existente = dosisRepository.findByMedicamentoPacienteAndHoraProgramada(mp, horaProgramada).orElse(null);
        if (existente != null) {
            log.debug("Dosis ya existente (ID={}) para {} a las {}. No se recrea.", existente.getIdDosis(), mp.getMedicamento().getNombreMedicamento(), horaProgramada);
            return null;
        }

        Dosis nueva = new Dosis();
        nueva.setMedicamentoPaciente(mp);
        nueva.setHoraProgramada(horaProgramada);
        nueva.setEstadoDosis(EstadoDosis.PENDIENTE);
        dosisRepository.save(nueva);
        log.info("Dosis PENDIENTE creada para {} a las {}. ID Dosis: {}", mp.getMedicamento().getNombreMedicamento(), horaProgramada, nueva.getIdDosis());
        return nueva;
    }

    @Override
    public List<DosisDTO> listarHistorialPorPaciente(Long idPaciente) {
        List<Dosis> dosisList = dosisRepository.findByMedicamentoPaciente_Paciente_IdPacienteOrderByHoraProgramadaDesc(idPaciente);
        return dosisList.stream()
                .map(dosis -> {
                    DosisDTO dto = modelMapper.map(dosis, DosisDTO.class);
                    dto.setIdMedicamentoPaciente(dosis.getMedicamentoPaciente().getIdMedicamentoPaciente());
                    dto.setNombreMedicamento(dosis.getMedicamentoPaciente().getMedicamento().getNombreMedicamento());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}