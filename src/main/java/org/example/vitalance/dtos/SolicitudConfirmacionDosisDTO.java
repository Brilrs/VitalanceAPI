package org.example.vitalance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudConfirmacionDosisDTO {

    // ID de la dosis que el paciente está confirmando.
    // Este ID debe haber sido enviado previamente en la notificación (US-009).
    private Long idDosis;

    // Acción del paciente: "TOMADA" o "POSPONER" (aunque posponer es más de US-009,
    // lo incluimos para tener un punto de acción central).
    private String accion;

    // La hora real en que el paciente confirmó la toma.
    // Esto es crucial para medir la adherencia real (US-016).
    private LocalDateTime horaConfirmacion;

    // Podrías añadir un campo para el paciente si la seguridad lo requiere,
    // pero idealmente se obtiene del token de autenticación:
    // private Long idPaciente;

}
