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
public class MedicionesDTO {
    private Long idMedicion;
    private String tipoMedicion;
    private Double valorMedicion;
    private String unidadMedicion;
    private LocalDate fechaMedicicion;
    private String notaMedicion;
    private PacienteDTO paciente;
    private UserDTO createdBy;//autor de la medicion

}
