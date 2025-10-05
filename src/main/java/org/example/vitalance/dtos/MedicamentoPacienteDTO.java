package org.example.vitalance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicamentoPacienteDTO {
    private Long idMedicamento;
    private String frecuenciaMedicamento;
    private LocalDate fechaInicioMedicamento;
    private LocalDate fechaFinMedicamento;
    private Boolean estadoMedicamento;
    private MedicamentoDTO medicamento;
    private PacienteDTO paciente;
}
