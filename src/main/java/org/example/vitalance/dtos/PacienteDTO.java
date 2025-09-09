package org.example.vitalance.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    //relacion de uno a uno con usuario
@OneToOne
@JoinColumn(name = "User_id")
@JsonBackReference
private UserDTO user;









    @OneToMany
            //un paciente tiene muchas mediciones, mapeado con atributo "paciente" en clase MedicionesDTO
            (fetch = FetchType.LAZY,mappedBy = "paciente", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MedicionesDTO> mediciones = new ArrayList<>();


    public PacienteDTO(int id, double estatura, Date fechaCreacion, Date fechaDiagnostico, double glucosaMaxima, double glucosaMinima, String numeroHistoriaClinica, double peso, String tipoDiabetes, UserDTO user) {
        this.id = id;
        this.estatura = estatura;
        this.fechaCreacion = fechaCreacion;
        this.fechaDiagnostico = fechaDiagnostico;
        this.glucosaMaxima = glucosaMaxima;
        this.glucosaMinima = glucosaMinima;
        this.numeroHistoriaClinica = numeroHistoriaClinica;
        this.peso = peso;
        this.tipoDiabetes = tipoDiabetes;
        this.user = user;
    }
}
