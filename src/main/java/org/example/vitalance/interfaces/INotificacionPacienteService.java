package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.NotificacionPacienteDTO;

import java.util.List;

public interface INotificacionPacienteService {
    List<NotificacionPacienteDTO> listarPorPaciente(Long idPaciente);

    NotificacionPacienteDTO compartirConMedico(Long idNotificacion);

    void evaluarYCrearParaMedicion(Long idPaciente, Long idMedicion, String tipoIndicador,
                                   double valor, String unidad);

    // --- NUEVOS MÉTODOS PARA US-009 (Recordatorios de Medicación) ---

    /**
     * Envía la notificación inicial de recordatorio al paciente, disparada por el scheduler.
     * (Escenario Positivo US-009)
     *
     * @param recordatorioId    El ID del registro de Recordatorio.
     * @param idPaciente        ID del paciente destino.
     * @param medicamentoNombre Nombre del medicamento.
     */
    void enviarNotificacion(Long recordatorioId, Long idPaciente, String medicamentoNombre);

    /**
     * Envía un aviso de refuerzo si la toma no ha sido confirmada a tiempo.
     * (Escenario Negativo US-009: 10 minutos después)
     *
     * @param recordatorioId El ID del registro de Recordatorio.
     * @param idPaciente     ID del paciente destino.
     * @param mensaje        Mensaje personalizado (e.g., "Segundo Aviso: Confirme su toma").
     */
    void enviarSegundoAviso(Long recordatorioId, Long idPaciente, String mensaje);

    /**
     * US-024: Crea y envía una Alerta al médico(s) cuando una dosis es marcada como OMITIDA.
     * @param dosisId ID de la Dosis que fue omitida.
     */
    void crearAlertaPorOmision(Long dosisId);

}
