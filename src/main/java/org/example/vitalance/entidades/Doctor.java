package org.example.vitalance.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Doctores")
public class Doctor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idDoctor;

    private String numeroColegiaturaDoctor;
    private String especialidadDoctor;
    private String clinicaDoctor;
    private LocalDateTime fechaRegistroDocto;

    //relacion con la tabla USERS-Doctor es el dueño de la relacion(el que tiene @JoinColumn)
    @OneToOne
    @JoinColumn(name="idUser",nullable=false)
    @JsonBackReference("doctor_user")//lado de atras que se ignora al serializar(evita el bucle)
    private User user;

    //BIEN HECHA
    //relacion con la tabla pacienteDoctor, relacion bidireccional
    //para acceder a los vinculos desde el otro lado
    @OneToMany(mappedBy = "doctor",cascade=CascadeType.ALL, orphanRemoval = true)
    private Set<PacienteDoctor>pacienteDoctores= new HashSet<>();

}
