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
public class TeleconsultaDTO {
    private Long idTeleconsulta;    // autogenerado en memoria
    private Long idPaciente;        // referencia al paciente
    private Long idDoctor;          // referencia al doctor
    private LocalDateTime fechaHora; // fecha y hora de la teleconsulta
    private Boolean confirmada;     // true = confirmada, false = pendiente
    private Boolean listaEspera;    // true si está en lista de espera
}
