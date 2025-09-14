package org.example.vitalance.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private String correoUser;
    private String nombreUser;
    private String apellidoUser;
    private String telefonoUser;
    private String generoUser;
    private LocalDate fechaNacimientoUser;
    private LocalDateTime fechaRegistroUser;
    private Boolean activoUser;

    //@ManyToOne(cascade = CascadeType.ALL)//cascada
    //@JoinColumn(name="idRole")
    //private Role role;

    //Relacion ManyToOne con Role-BIEN HECHA
    @ManyToOne
    @JoinColumn(name="idRole",nullable=false) //FK con Users-DUEÑO DE LA RELACION POR QUE TIENE JOIN COLUMN
    @JsonBackReference("role_users")
    private Role role;

    //Relacion con la tabla Doctor-BIEN HECHA
    //@OneToOne(cascade=CascadeType.ALL)
    //@JoinColumn(name="idDoctor")
    //@JsonBackReference //indica relacion en el lado secundario
    @OneToOne(mappedBy = "user")//lado inverso
    @JsonManagedReference("doctor_user") //lado principal que si se serializa-("doctor_user:lo definimos,debe ser unico e igual en ambos lados")
    private Doctor doctor;

    //Relacion con la tabla Paciente-Lado INVERSO con Paciente
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonBackReference("paciente_user")
    private Paciente paciente;

    //Relacion con la tabla Mediciones
    @OneToMany(mappedBy = "user",cascade =CascadeType.ALL,orphanRemoval = true )
    @JsonManagedReference("medicion_user")
    private List<Mediciones> mediciones=new ArrayList<>();
}
