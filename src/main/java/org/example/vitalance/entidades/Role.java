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
    //Relacion OneToMany con SecurityUser
    @OneToMany(mappedBy="role")//LADO INVERSO POR QUE USA mappedBy
    @JsonManagedReference("role_users")//evita ciclos infinitos en Json
    private List<User> users=new ArrayList<>();

    //@JsonManagedReference y @JsonBackReference evita el loop infinito al serializar a JSON
    //Role->muestra su lista de Users
    //SecurityUser->no vuelve a mostrar el Role otra vez
    //CascadeType.ALL :Si borras un Role,se borran tambien sus Users
    //mappedBy="role" significa que la relacion esta mapeada en la propiedad role de Users

    //Cualquier operacion sobre la entidad padre se propagara a las entidades hijas
    // cascade = CascadeType.ALL
}
