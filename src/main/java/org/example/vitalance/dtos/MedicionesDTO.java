package org.example.vitalance.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.websocket.Decoder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.vitalance.entidades.Mediciones;
import org.example.vitalance.entidades.Paciente;
import org.example.vitalance.entidades.User;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter//generar getter y setter

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

    private Integer pacienteId;
    private Long userId;
    private Integer dispositivoId;
    private Integer datosCrudoDispositivoId;


}
