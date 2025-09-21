package org.example.vitalance.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor

@Table(name="Predicciones")
public class Prediccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrediccion;
    private String tipoPrediccion;
    private Double probabilidadPrediccion;

    @Column(name = "promptPrediccion",nullable = true, columnDefinition = "TEXT")
    private String promptPrediccion;
    @Column(name = "respuestaTextoPrediccion",nullable = true, columnDefinition = "TEXT")
    private String respuestaTextoPrediccion;
    //RELACION CON LA TABLA PACIENTE-
    @ManyToOne
    @JoinColumn(name="idPaciente",nullable = false)
    @JsonBackReference("prediccion_paciente")
    private Paciente paciente;
}
