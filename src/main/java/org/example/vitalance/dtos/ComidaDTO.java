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
public class ComidaDTO {
    private Long idComida;
    private String nombreComida;
    private LocalDateTime horaComida;
    private Double carbohidratoTolal;
    private PacienteDTO paciente;
}
