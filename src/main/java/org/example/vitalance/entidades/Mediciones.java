package org.example.vitalance.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Mediciones")
public class Mediciones {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idMedicion;
    private String tipoMedicion;
    private Double valorMedicion;
    private String unidadMedicion;
    private LocalDate fechaMedicion;
    private String notaMedicion;

    //Relacion con la tabla Paciente-Dueño de la relacion
    @ManyToOne
    @JoinColumn(name="idPaciente",nullable = false)//FK en Medicion
    @JsonBackReference("medicion_paciente")
    private Paciente paciente;

    //Relacion con la tabla User
    @ManyToOne
    @JoinColumn(name="idUser",nullable = false)//FK en medicion
    @JsonBackReference("medicion_user")
    private User user;
}
