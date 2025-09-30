package org.example.vitalance.dtos;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlimentoDTO {
    private Long id;
    private String nombre; // o String si corregimos
    private String descripcion;
    private Double carbohidrato;
    private Double calorias;
    private Double indiceGlucemico;
}
