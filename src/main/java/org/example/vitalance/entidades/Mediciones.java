package org.example.vitalance.entidades;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.vitalance.dtos.PacienteDTO;
import org.example.vitalance.dtos.UserDTO;

import java.util.Date;

@Entity
@Getter
@Setter//generar getter y setter

@NoArgsConstructor @AllArgsConstructor//generar constructor con y sin argumentos
@Table(name = "Mediciones")
public class Mediciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tipoMedicion;
    private double valor;
    private String unidad;
    private Date fechaMedicion;
    private String origen;
    private String notasValidacion;


    //relacion muchos a uno con usuario
    @ManyToOne
    @JoinColumn(name = "User_Id")
    @JsonBackReference
    private User usuario;


    @ManyToOne
    @JoinColumn(name = "Paciente_Id")
    @JsonBackReference
    private Paciente paciente;


}