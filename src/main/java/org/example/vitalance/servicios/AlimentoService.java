package org.example.vitalance.servicios;

import lombok.RequiredArgsConstructor;
import org.example.vitalance.dtos.AlimentoDTO;
import org.example.vitalance.entidades.Alimento;
import org.example.vitalance.repositorios.AlimentoRepository;
import org.example.vitalance.interfaces.IAlimentoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlimentoService implements IAlimentoService {
    @Autowired
    private AlimentoRepository alimentoRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AlimentoDTO> listar(){
        return alimentoRepository.findAll().stream().map(alimento -> modelMapper.map(alimento,AlimentoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AlimentoDTO insertar(AlimentoDTO alimento){
        Alimento alimentoEntity = modelMapper.map(alimento, Alimento.class);
        Alimento alimentoguardado = alimentoRepository.save(alimentoEntity);
        return modelMapper.map(alimentoguardado,AlimentoDTO.class);
    }

    @Override
    public AlimentoDTO editar(AlimentoDTO alimento){
        return alimentoRepository.findById(alimento.getId()).map(existing->{
            Alimento alimentoEntity = modelMapper.map(alimento, Alimento.class);
            Alimento alimentoguardado = alimentoRepository.save(alimentoEntity);
            return modelMapper.map(alimentoguardado,AlimentoDTO.class);
        }).orElseThrow(()->new RuntimeException("Alimento no encontrado"));
    }

    @Override
    public AlimentoDTO buscarPorId(Long id){
        return alimentoRepository.findById(id).map((element)->modelMapper.map(element,AlimentoDTO.class))
                .orElseThrow(() -> new RuntimeException("Alimento no encontrado"));
    }

    @Override
    public void eliminar(Long id){
        alimentoRepository.deleteById(id);
    }

}
