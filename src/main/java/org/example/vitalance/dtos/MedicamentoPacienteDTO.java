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
    private String nombreMedicamento;
    private String tipoMedicamento;
    private Integer unidadMedicamento;
    private String dosisMedicamento;
    private LocalDate fechaMedicamento;
    private MedicamentoDTO medicamento;
    private PacienteDTO paciente;
}
