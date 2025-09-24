package org.example.vitalance.servicios;


import org.example.vitalance.dtos.AccionRecordatorioDTO;
import org.example.vitalance.dtos.AlertaGlucosaDTO;
import org.example.vitalance.dtos.RecordatorioDTO;
import org.example.vitalance.entidades.Recordatorio;
import org.example.vitalance.interfaces.IRecordatorioService;
import org.example.vitalance.repositorios.RecordatorioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

    // ====== US 09 ======
    @Override
    public List<RecordatorioDTO> dispararPendientes() {
        LocalTime ahoraHora = LocalTime.now();
        LocalDateTime haceDiez = LocalDateTime.now().minusMinutes(10);

        List<Recordatorio> due = recordatorioRepository.findDue(ahoraHora, haceDiez);

        // Marcar envío (primer o segundo aviso) y devolverlos para que el frontend notifique
        for (Recordatorio r : due) {
            r.setUltimoEnvioAt(LocalDateTime.now());
            r.setReintentos((r.getReintentos() == null) ? 1 : (short)(r.getReintentos() + 1));
        }
        recordatorioRepository.saveAll(due);

        return due.stream().map(e -> modelMapper.map(e, RecordatorioDTO.class)).collect(Collectors.toList());
    }

    // ====== Acción del usuario (Tomado / Posponer) ======
    @Override
    public void accionar(Long idRecordatorio, AccionRecordatorioDTO accion) {
        Recordatorio r = recordatorioRepository.findById(idRecordatorio)
                .orElseThrow(() -> new RuntimeException("Recordatorio no encontrado"));

        switch (accion.getAccion()) {
            case TOMADO -> {
                // Confirmación: “Tomado” → cerramos el ciclo de avisos
                r.setReintentos((short)2); // evita más notificaciones hoy
                // podrías registrar aquí “toma confirmada” en tu módulo de adherencia
            }
            case POSPONER -> {
                int minutos = (accion.getMinutos() == null || accion.getMinutos() <= 0) ? 10 : accion.getMinutos();
                // Reprogramar sólo la hora de hoy
                r.setHoraProgramadaRecordatorio(LocalTime.now().plusMinutes(minutos));
                r.setUltimoEnvioAt(null);
                r.setReintentos((short)0);
            }
        }
        recordatorioRepository.save(r);
    }

}
