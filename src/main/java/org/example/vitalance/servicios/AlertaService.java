package org.example.vitalance.servicios;

import org.example.vitalance.dtos.AlertaDTO;
import org.example.vitalance.entidades.*;
import org.example.vitalance.interfaces.IAlertaService;
import org.example.vitalance.repositorios.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertaService implements IAlertaService {
    @Autowired private AlertaRepository alertaRepository;
    @Autowired private DoctorRepository doctorRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public List<AlertaDTO> listarPorDoctor(Long idDoctor, String estado) {
        Doctor doc = doctorRepository.findById(idDoctor)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));
        List<Alerta> list = (estado==null || estado.isBlank())
                ? alertaRepository.findByDoctorOrderBySeveridadDescCreadaEnDesc(doc)
                : alertaRepository.findByDoctorAndEstadoOrderBySeveridadDescCreadaEnDesc(doc, estado.toUpperCase());
        return list.stream().map(a -> modelMapper.map(a, AlertaDTO.class)).collect(Collectors.toList());
    }

    @Override
    public AlertaDTO marcarRevisada(Long idAlerta) {
        Alerta a = alertaRepository.findById(idAlerta)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));
        a.setEstado("REVISADA");
        a.setRevisadaEn(LocalDateTime.now());
        return modelMapper.map(alertaRepository.save(a), AlertaDTO.class);
    }

    @Override
    public List<AlertaDTO> resumenDiario(Long idDoctor, LocalDate dia) {
        Doctor doc = doctorRepository.findById(idDoctor)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));
        LocalDateTime ini = dia.atStartOfDay();
        LocalDateTime fin = dia.plusDays(1).atStartOfDay().minusNanos(1);
        return alertaRepository.findResumenDiario(doc, ini, fin).stream()
                .map(a -> modelMapper.map(a, AlertaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public int autoArchivarNoCriticas24h() {
        LocalDateTime limite = LocalDateTime.now().minusHours(24);
        List<Alerta> pendientes = alertaRepository
                .findByEstadoAndSeveridadAndCreadaEnBefore("PENDIENTE","NO_CRITICA", limite);
        pendientes.forEach(a -> a.setEstado("AUTO_ARCHIVADA"));
        alertaRepository.saveAll(pendientes);
        return pendientes.size();
    }
}
