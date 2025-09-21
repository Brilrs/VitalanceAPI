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
    private LocalDate fechaMedicicion;
    private String notaMedicion;

    //Relacion con la tabla Paciente-Dueño de la relacion
    @ManyToOne
    @JoinColumn(name="idPaciente",nullable = false)//FK de paciente en Medicion
    @JsonBackReference("medicion_paciente")
    private Paciente paciente;//el paciente al que le pertenece la medicion




    //Relacion con el USUARIO que registro la medicion
    @ManyToOne
    @JoinColumn(name="created_by_user_id",nullable = false)//FK de user  en medicion
    @JsonBackReference("medicion_user")
    private User createdBy; //el usuario que digito la medicion

}
