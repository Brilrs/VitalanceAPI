package org.example.vitalance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RegisterUserDTO {
    //SOLO PARA REGISTRAR Y CREAR USUARIOS
    private String correoUser;
    private String passwordUser;   // contraseña solo en registro
    private String nombreUser;
    private String apellidoUser;
    private String telefonoUser;
    private String generoUser;
    private LocalDate fechaNacimientoUser;
    private Long idRole;  // para asignar rol en el registro
}
