package org.example.vitalance.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alimentos")
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // PK

    @Column(nullable = false)
    private Integer nombre; // en tu DER está como int, aunque lo lógico sería String

    @Column(length = 100, nullable = false)
    private String descripcion;

    @Column(precision = 8, scale = 2, nullable = false)
    private BigDecimal carbohidrato;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal calorias;

    @Column(precision = 6, scale = 2, nullable = false)
    private BigDecimal indiceGlucemico;
}