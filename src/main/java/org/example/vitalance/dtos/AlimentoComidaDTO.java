package org.example.vitalance.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.vitalance.entidades.Alimento;
import org.example.vitalance.entidades.Comida;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlimentoComidaDTO {
    private Long idAlimentoComida;
    private Double cantidadAlimentoComida;
    private AlimentoDTO alimento;
    private ComidaDTO comida;
}
