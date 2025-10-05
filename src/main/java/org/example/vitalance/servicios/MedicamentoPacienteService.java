package org.example.vitalance.servicios;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.MedicamentoPacienteDTO;
import org.example.vitalance.entidades.MedicamentoPaciente;
import org.example.vitalance.interfaces.IMedicamentoPacienteService;
import org.example.vitalance.repositorios.MedicamentoPacienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MedicamentoPacienteService implements IMedicamentoPacienteService {
    @Autowired
    private MedicamentoPacienteRepository medicamentoPacienteRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MedicamentoPacienteDTO> listar() {
        return medicamentoPacienteRepository.findAll().stream().map(medicamentoPaciente -> modelMapper.map(medicamentoPaciente,MedicamentoPacienteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MedicamentoPacienteDTO insertar(MedicamentoPacienteDTO medicamentoPacienteDTO) {
        MedicamentoPaciente mpentity = modelMapper.map(medicamentoPacienteDTO, MedicamentoPaciente.class);
        MedicamentoPaciente mpguardado = medicamentoPacienteRepository.save(mpentity);
        return modelMapper.map(mpguardado,MedicamentoPacienteDTO.class);
    }

    @Override
    public MedicamentoPacienteDTO editar(MedicamentoPacienteDTO medicamentoPacienteDTO) {
        return medicamentoPacienteRepository.findById(medicamentoPacienteDTO.getIdMedicamento()).map(existing->{
            MedicamentoPaciente mpentity = modelMapper.map(medicamentoPacienteDTO,MedicamentoPaciente.class);
            MedicamentoPaciente mpguardado = medicamentoPacienteRepository.save(mpentity);
            return modelMapper.map(mpguardado,MedicamentoPacienteDTO.class);
        }).orElseThrow(()->new RuntimeException("MedicamentoPaciente no encontrado"));
    }

    @Override
    public MedicamentoPacienteDTO buscarPorId(Long idMedicamentoPaciente) {
        return medicamentoPacienteRepository.findById(idMedicamentoPaciente).map((element)->modelMapper.map(element,MedicamentoPacienteDTO.class))
                .orElseThrow(()->new RuntimeException("MedicamentoPaciente no encontrado"));
    }

    @Override
    public void eliminar(Long idMedicamentoPaciente) {
        medicamentoPacienteRepository.deleteById(idMedicamentoPaciente);
    }
}
