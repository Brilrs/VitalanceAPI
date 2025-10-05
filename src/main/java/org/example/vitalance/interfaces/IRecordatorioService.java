package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.AlertaGlucosaDTO;
import org.example.vitalance.dtos.RecordatorioDTO;

import java.util.List;

public interface IRecordatorioService {
    public List<RecordatorioDTO> listar();
    public RecordatorioDTO insertar(RecordatorioDTO recordatorio);
    public RecordatorioDTO editar(RecordatorioDTO recordatorio);
    public RecordatorioDTO buscarPorId(Long id);
    public void eliminar(Long id);
    public List<RecordatorioDTO> filtrarPorPaciente(String filtro);

    // --- NUEVOS MÉTODOS PARA US-009 (Recordatorios) ---

    /**
     * Tarea programada principal que se ejecuta cada minuto para verificar
     * si hay recordatorios pendientes de envío. (Escenario Positivo US-009)
     */
    void verificarYEnviarRecordatorios();

    /**
     * Envía un aviso de refuerzo si la toma no ha sido confirmada a tiempo.
     * (Escenario Negativo US-009: 10 minutos después)
     * @param dosisId El ID del registro de Recordatorio a revisar.
     * @param idPaciente El ID del paciente para personalizar el mensaje.
     * @param nombreMedicamento Nombre del medicamento para el mensaje.
     */
    void enviarSegundoAviso(Long dosisId, Long idPaciente, String nombreMedicamento);

}
