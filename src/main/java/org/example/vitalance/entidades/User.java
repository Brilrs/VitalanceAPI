package org.example.vitalance.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    private String correoUser;
    private String passwordUser;
    private String emailUser;
    private String nombreUser;
    private String apellidoUser;
    private String telefonoUser;
    private String generoUser;
    private LocalDate fechaNacimientoUser;
    private LocalDateTime fechaRegistroUser;
    private Boolean activoUser;

    @ManyToOne
    @JoinColumn(name="idRole")
    private Role role;

}
