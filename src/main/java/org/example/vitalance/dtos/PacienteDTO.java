package org.example.vitalance.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.vitalance.entidades.Paciente;
import org.example.vitalance.entidades.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity//clase de tipo entidad, requiere como minimo el id y su notacion
@Getter
@Setter//generar getter y setter
@NoArgsConstructor //generar constructor con y sin argumentos

public class PacienteDTO {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String numeroHistoriaClinica,tipoDiabetes;
private Date fechaDiagnostico,fechaCreacion;
private double estatura,peso,glucosaMinima,glucosaMaxima;

private Long userId;

}
