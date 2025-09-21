package org.example.vitalance.servicios;


import org.example.vitalance.dtos.RecordatorioDTO;
import org.example.vitalance.entidades.Recordatorio;
import org.example.vitalance.interfaces.IRecordatorioService;
import org.example.vitalance.repositorios.RecordatorioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecordatorioService implements IRecordatorioService {
    @Autowired
    private RecordatorioRepository recordatorioRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<RecordatorioDTO> listar(){
        return recordatorioRepository.findAll().stream()
                .map(recordatorio -> modelMapper.map(recordatorio,RecordatorioDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RecordatorioDTO insertar(RecordatorioDTO recordatorioDto) {
        Recordatorio recordatorioEntidad=modelMapper.map(recordatorioDto,Recordatorio.class);
        Recordatorio guardado=recordatorioRepository.save(recordatorioEntidad);
        return modelMapper.map(guardado,RecordatorioDTO.class);
    }

    @Override
    public RecordatorioDTO editar(RecordatorioDTO recordatorioDto) {
        return recordatorioRepository.findById(recordatorioDto.getIdRecordatorio()).map(existing->{
            Recordatorio recordatorioEntidad=modelMapper.map(recordatorioDto,Recordatorio.class);
            Recordatorio guardado=recordatorioRepository.save(recordatorioEntidad);
            return modelMapper.map(guardado,RecordatorioDTO.class);
        }).orElseThrow(()->new RuntimeException("Recordatorio no encontrado"));
    }

    @Override
    public RecordatorioDTO buscarPorId(Long id) {
        return recordatorioRepository.findById(id).map((element)->modelMapper.map(element,RecordatorioDTO.class))
                .orElseThrow(()->new RuntimeException("Recordatorio no encontrado"));
    }

    @Override
    public void eliminar(Long id) {
        if(!recordatorioRepository.existsById(id)){
            throw new RuntimeException("Recordatorio no encontrado");
        }
        recordatorioRepository.deleteById(id);
    }
}
