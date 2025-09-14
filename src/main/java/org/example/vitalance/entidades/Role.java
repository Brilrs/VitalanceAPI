package org.example.vitalance.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    @Column(unique = true) //no puedes tener dos roles con el mismo nombre
    private String nombreRole;//columna nombre_role

    //Relacion OneToMany con User
    @OneToMany(mappedBy="role",cascade = CascadeType.ALL)//LADO INVERSO POR QUE USA mappedBy
    //mappedBy="role" significa que la relacion esta mapeada en la propiedad role de Users
    @JsonManagedReference("role_users")//evita ciclos infinitos en Json
    //@JsonManagedReference y @JsonBackReference evita el loop infinito al serializar a JSON
    //Role->muestra su lista de Users
    //User->no vuelve a mostrar el Role otra vez
    //CascadeType.ALL :Si borras un Role,se borran tambien sus Users
    private List<User> users=new ArrayList<>();
}
