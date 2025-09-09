package org.example.vitalance.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.websocket.Decoder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Entity
@Getter
@Setter//generar getter y setter
@AllArgsConstructor
@NoArgsConstructor //generar constructor con y sin argumentos

public class MedicionesDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tipoMedicion;
    private double valor;
    private String unidad;
    private Date fechaMedicion;
    private String origen;
    private String notasValidacion;


//relacion muchos a uno con usuarioDTO
    @ManyToOne
    @JoinColumn(name = "User_Id")
    @JsonBackReference
    private UserDTO usuario;


    @ManyToOne
    @JoinColumn(name = "Paciente_Id")
    @JsonBackReference
    private PacienteDTO paciente;





}
