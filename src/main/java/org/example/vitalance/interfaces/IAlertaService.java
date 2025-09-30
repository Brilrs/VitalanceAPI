package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.AlertaDTO;

import java.time.LocalDate;
import java.util.List;

public interface IAlertaService {
    List<AlertaDTO> listarPorDoctor(Long idDoctor, String estado);
    AlertaDTO marcarRevisada(Long idAlerta);
    List<AlertaDTO> resumenDiario(Long idDoctor, LocalDate dia);
    int autoArchivarNoCriticas24h(); // utilidad para HU negativa
}
