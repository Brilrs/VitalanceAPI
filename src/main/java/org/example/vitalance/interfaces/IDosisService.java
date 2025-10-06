package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.DosisDTO;
import org.example.vitalance.dtos.SolicitudConfirmacionDosisDTO;
import org.example.vitalance.entidades.Dosis;

import java.time.LocalDateTime;
import java.util.List;

public interface IDosisService {

    /**
     * US-016: Procesa la confirmación de la toma de medicación enviada por el paciente.
     * Actualiza el estado de la Dosis a 'TOMADA' y registra la hora real.
     * @param requestDTO Contiene el ID de la Dosis y la acción (ej. TOMADA).
     */
    void confirmarToma(SolicitudConfirmacionDosisDTO requestDTO);

    /**
     * US-009 / US-024: Utilizado por el RecordatorioService (@Scheduled) para generar
     * un registro PENDIENTE de Dosis antes de enviar la notificación.
     * Esto asegura que haya un registro de historial antes de la acción del paciente.
     * @param idMedicamentoPaciente ID de la programación activa.
     * @param horaProgramada La hora exacta que se debe registrar.
     * @return Dosis creada o null si ya existía.
     */
    Dosis crearDosisPendiente(Long idMedicamentoPaciente, LocalDateTime horaProgramada);

    /**
     * Lista el historial de dosis de un paciente ordenado descendentemente por hora programada.
     * @param idPaciente ID del paciente.
     * @return Lista de DosisDTO.
     */
    List<DosisDTO> listarHistorialPorPaciente(Long idPaciente);
}