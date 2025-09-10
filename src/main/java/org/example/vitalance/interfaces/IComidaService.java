package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.ComidaDTO;

import java.time.LocalTime;
import java.util.List;

public interface IComidaService {
    List<ComidaDTO> getAllComidas();
    ComidaDTO getComidaById(Long id);
    ComidaDTO createComida(ComidaDTO comidaDTO);
    ComidaDTO updateComida(Long id, ComidaDTO comidaDTO);
    void deleteComida(Long id);
    List<ComidaDTO> getComidasByPaciente(Long pacienteId);
    List<ComidaDTO> getComidasByPacienteAndHora(Long pacienteId, LocalTime horaInicio, LocalTime horaFin);
    List<ComidaDTO> searchComidasByNombre(String nombre);
    ComidaDTO addAlimentoToComida(Long comidaId, Long alimentoId, java.math.BigDecimal cantidad);
    void removeAlimentoFromComida(Long comidaId, Long alimentoId);
}