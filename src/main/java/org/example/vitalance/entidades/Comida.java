package org.example.vitalance.entidades;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Comida")
public class Comida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComida;
    private String nombreComida;
    private LocalDateTime horaComida;
    private Double carbohidratoTolal;

    //Relacion ManyToOne con Paciente-dueño de la relacion
    @ManyToOne
    @JoinColumn(name="idPaciente",nullable = false)//FK en comidas
    @JsonBackReference("paciente_comida")
    private Paciente paciente;

    //relacion con alimentoComida-BIEN
    //relacion con la tabla AlimentoComida,relacion bidireccional
    @OneToMany(mappedBy = "comida",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<AlimentoComida> alimentoComida=new HashSet<>();

}
