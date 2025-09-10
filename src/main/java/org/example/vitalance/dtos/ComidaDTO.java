package org.example.vitalance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComidaDTO {
    private Long idComida;
    private String nombreComida;
    private LocalTime horaComida;
    private BigDecimal carbohidratosTotal;
    private Long idPaciente;
    private String nombrePaciente;
    private List<AlimentoComidaDTO> alimentos;
}