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
public class PacienteNivelesDeGlucosaDTO { //Se conectara con la medicion para obtener la fecha de medicion, el paciente para su id y con el id los datos del paciente
    //-----------------------------------------------variables obtenidas del paciente
    private long IdPaciente;
    private Double estaturaDiagnosticoPaciente;
    private Double pesoDiagnosticoPaciente;

    //-----------------------------------------------variables obtenidas de medicion
    private LocalDate fechaMedicicion;
    private Double valorMedicion;
    private String unidadMedicion;
    private String notaMedicion;
}
