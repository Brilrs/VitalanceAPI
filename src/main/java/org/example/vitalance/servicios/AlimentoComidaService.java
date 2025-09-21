package org.example.vitalance.servicios;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.AlimentoComidaDTO;
import org.example.vitalance.entidades.AlimentoComida;
import org.example.vitalance.interfaces.IAlimentoComidaService;
import org.example.vitalance.repositorios.AlimentoComidaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class AlimentoComidaService implements IAlimentoComidaService {
    @Autowired
    private AlimentoComidaRepository alimentoComidaRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AlimentoComidaDTO> listar() {
        return alimentoComidaRepository.findAll().stream().map(alimentoComida -> modelMapper.map(alimentoComida,AlimentoComidaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AlimentoComidaDTO insertar(AlimentoComidaDTO alimentoComidaDTO) {
        AlimentoComida alimentocomidaentity = modelMapper.map(alimentoComidaDTO,AlimentoComida.class);
        AlimentoComida alimentocomidaguardado =  alimentoComidaRepository.save(alimentocomidaentity);
        return modelMapper.map(alimentocomidaguardado,AlimentoComidaDTO.class);
    }

    @Override
    public AlimentoComidaDTO editar(AlimentoComidaDTO alimentoComidaDTO) {
        return alimentoComidaRepository.findById(alimentoComidaDTO.getIdAlimentoComida()).map(existing->{
            AlimentoComida alimentocomidaentity = modelMapper.map(alimentoComidaDTO,AlimentoComida.class);
            AlimentoComida alimentocomidaguardado =  alimentoComidaRepository.save(alimentocomidaentity);
            return modelMapper.map(alimentocomidaguardado,AlimentoComidaDTO.class);
        }).orElseThrow(()->new RuntimeException("No encontrado"));
    }

    @Override
    public AlimentoComidaDTO buscarPorId(Long idAlimentoComida) {
        return alimentoComidaRepository.findById(idAlimentoComida).map((element)->modelMapper.map(element,AlimentoComidaDTO.class))
                .orElse(null);
    }

    @Override
    public void eliminar(Long idAlimentoComida) {
        alimentoComidaRepository.deleteById(idAlimentoComida);
    }
}
