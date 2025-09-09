package org.example.vitalance.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.vitalance.entidades.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;
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




    //relacion de uno a uno con paciente
    @ManyToOne(fetch = FetchType.LAZY) //muchos libros para un autor
    @JoinColumn(name = "Role_id") // en postgress, un libro tiene un atributo id_autor que tiene el id de un autor
    @JsonBackReference
    private RoleDTO rol;




    @OneToOne(mappedBy="user", cascade= CascadeType.ALL)
    @JsonManagedReference
    private PacienteDTO paciente;


//relacion uno a muchos con mediciones
    @OneToMany
            //un usuario tiene muchas mediciones, mapeado con atributo "usuario" en clase MedicionesDTO
   (fetch = FetchType.LAZY,mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MedicionesDTO> mediciones = new ArrayList<>();

}
