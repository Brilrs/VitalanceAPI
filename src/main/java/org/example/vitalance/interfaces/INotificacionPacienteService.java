package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.NotificacionPacienteDTO;

import java.util.List;

public interface INotificacionPacienteService {
    List<NotificacionPacienteDTO> listarPorPaciente(Long idPaciente);
    NotificacionPacienteDTO compartirConMedico(Long idNotificacion);
    void evaluarYCrearParaMedicion(Long idPaciente, Long idMedicion, String tipoIndicador,
                                   double valor, String unidad);
}
