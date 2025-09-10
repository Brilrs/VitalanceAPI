package org.example.vitalance.dtos;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlimentoDTO {
    private Long id;
    private Integer nombre; // o String si corregimos
    private String descripcion;
    private BigDecimal carbohidrato;
    private BigDecimal calorias;
    private BigDecimal indiceGlucemico;
}