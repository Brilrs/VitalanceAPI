package org.example.vitalance.entidades;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="AlimentoComida",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idAlimento","idComida"})
})
public class AlimentoComida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlimentoComida;

    private Double cantidadAlimentoComida;

    //Relacion con la tabla Alimento-BIEN
    @ManyToOne
    @JoinColumn(name="idAlimento",nullable = false)
    private Alimento alimento;
    //Relacion con la tabla Comida-BIEN
    @ManyToOne
    @JoinColumn(name="idComida",nullable = false)
    private Comida comida;

}
