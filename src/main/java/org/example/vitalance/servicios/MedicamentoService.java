package org.example.vitalance.servicios;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.MedicamentoDTO;
import org.example.vitalance.entidades.Medicamento;
import org.example.vitalance.interfaces.IMedicamentoService;
import org.example.vitalance.repositorios.MedicamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicamentoService implements IMedicamentoService {
    @Autowired
    private MedicamentoRepository medicamentoRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MedicamentoDTO> listar() {
        return medicamentoRepository.findAll().stream().map(medicamento -> modelMapper.map(medicamento,MedicamentoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MedicamentoDTO insertar(MedicamentoDTO medicamentoDTO) {
        Medicamento medicamentoentity =  modelMapper.map(medicamentoDTO, Medicamento.class);
        Medicamento medicamentoguardado = medicamentoRepository.save(medicamentoentity);
        return modelMapper.map(medicamentoguardado,MedicamentoDTO.class);
    }

    @Override
    public MedicamentoDTO editar(MedicamentoDTO medicamentoDTO) {
        return medicamentoRepository.findById(medicamentoDTO.getIdMedicamento()).map(existing->{
            Medicamento medicamentoentity =  modelMapper.map(medicamentoDTO,Medicamento.class);
            Medicamento medicamentoguardado = medicamentoRepository.save(medicamentoentity);
            return  modelMapper.map(medicamentoguardado,MedicamentoDTO.class);
        }).orElseThrow(()->new RuntimeException("Medicamento no encontrado"));
    }

    @Override
    public MedicamentoDTO buscarPorId(Long idMedicamento) {
        return medicamentoRepository.findById(idMedicamento).map((element)->modelMapper.map(element,MedicamentoDTO.class))
                .orElseThrow(()->new RuntimeException("Medicamento no encontrado"));
    }

    @Override
    public void eliminar(Long idMedicamento) {
        medicamentoRepository.deleteById(idMedicamento);
    }
}
