package org.example.vitalance.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Alimentos")
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // PK

    private String nombre; // en tu DER está como int, aunque lo lógico sería String
    private String descripcion;
    private Double  carbohidrato;
    private Long calorias;
    private BigDecimal indiceGlucemico;

    //relacion con la tabla AlimentoComida,relacion bidireccional
    @OneToMany(mappedBy = "alimento")
    private Set<AlimentoComida> alimentoComida=new HashSet<>();
}