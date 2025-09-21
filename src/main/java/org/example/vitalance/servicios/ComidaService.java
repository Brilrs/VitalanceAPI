package org.example.vitalance.servicios;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.ComidaDTO;
import org.example.vitalance.entidades.Comida;
import org.example.vitalance.interfaces.IComidaService;
import org.example.vitalance.repositorios.ComidaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor

public class ComidaService implements IComidaService {
    @Autowired
    private ComidaRepository comidaRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ComidaDTO> listar(){
        return comidaRepository.findAll().stream().map(comida -> modelMapper.map(comida,ComidaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ComidaDTO insertar(ComidaDTO comidaDTO) {
        Comida comidaEntity = modelMapper.map(comidaDTO, Comida.class);
        Comida comidaguardada = comidaRepository.save(comidaEntity);
        return modelMapper.map(comidaguardada, ComidaDTO.class);
    }

    @Override
    public ComidaDTO editar(ComidaDTO comidaDTO) {
        return comidaRepository.findById(comidaDTO.getIdComida()).map(existing->{
            Comida comidaEntity = modelMapper.map(comidaDTO, Comida.class);
            Comida comidaguardada = comidaRepository.save(comidaEntity);
            return modelMapper.map(comidaguardada, ComidaDTO.class);
        }).orElseThrow(()-> new RuntimeException("Comida no encontrada"));
    }

    @Override
    public ComidaDTO buscarPorId(Long idComida) {
        return comidaRepository.findById(idComida).map((element)->modelMapper.map(element,ComidaDTO.class))
                .orElseThrow(()-> new RuntimeException("Comida no encontrada"));
    }

    @Override
    public void eliminar(Long idComida) {
        comidaRepository.deleteById(idComida);
    }
}
