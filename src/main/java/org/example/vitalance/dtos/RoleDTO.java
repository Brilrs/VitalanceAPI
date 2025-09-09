package org.example.vitalance.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity//clase de tipo entidad, requiere como minimo el id y su notacion
@Getter
@Setter//generar getter y setter
@AllArgsConstructor
@NoArgsConstructor //generar constructor con y sin argumentos

public class RoleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRole;
    private String nombreRole;

    public int getIdRole() {
        return idRole;
    }
    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }
    public String getNombreRole() {
        return nombreRole;
    }
    public void setNombreRole(String nombreRole) {
        this.nombreRole = nombreRole;
    }
}
