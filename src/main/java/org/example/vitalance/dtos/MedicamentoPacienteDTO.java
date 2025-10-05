package org.example.vitalance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import java.time.LocalTime; // <--- NUEVO
import java.util.List; // <--- NUEVO

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicamentoPacienteDTO {
    private Long idMedicamentoPaciente; // <-- ID de la relación (para ediciones)
    private Long idMedicamento; // <-- Cambio: Sólo el ID, no el DTO completo
    private Long idPaciente; // <-- Cambio: Sólo el ID, no el DTO completo
    private String frecuenciaMedicamento;
    private LocalDate fechaInicioMedicamento;
    private LocalDate fechaFinMedicamento;
    private Boolean estadoMedicamento;

    // CAMPO CLAVE PARA US-009
    private List<LocalTime> horasProgramadas;
}
