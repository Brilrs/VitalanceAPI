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


    public PacienteDTO(int id, String numeroHistoriaClinica, String tipoDiabetes, Date fechaDiagnostico, double estatura, double peso, double glucosaMinima, double glucosaMaxima, Date fechaCreacion, UserDTO user) {
        this.id = id;
        this.numeroHistoriaClinica = numeroHistoriaClinica;
        this.tipoDiabetes = tipoDiabetes;
        this.fechaDiagnostico = fechaDiagnostico;
        this.estatura = estatura;
        this.peso = peso;
        this.glucosaMinima = glucosaMinima;
        this.glucosaMaxima = glucosaMaxima;
        this.fechaCreacion = fechaCreacion;
        this.user = user;
    }
}
