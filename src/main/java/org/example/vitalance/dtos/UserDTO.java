package org.example.vitalance.dtos;

import org.example.vitalance.entidades.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDTO {
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
    private Role rol;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }

    public Boolean getActivoUser() {
        return activoUser;
    }

    public void setActivoUser(Boolean activoUser) {
        this.activoUser = activoUser;
    }

    public LocalDateTime getFechaRegistroUser() {
        return fechaRegistroUser;
    }

    public void setFechaRegistroUser(LocalDateTime fechaRegistroUser) {
        this.fechaRegistroUser = fechaRegistroUser;
    }

    public LocalDate getFechaNacimientoUser() {
        return fechaNacimientoUser;
    }

    public void setFechaNacimientoUser(LocalDate fechaNacimientoUser) {
        this.fechaNacimientoUser = fechaNacimientoUser;
    }

    public String getGeneroUser() {
        return generoUser;
    }

    public void setGeneroUser(String generoUser) {
        this.generoUser = generoUser;
    }

    public String getTelefonoUser() {
        return telefonoUser;
    }

    public void setTelefonoUser(String telefonoUser) {
        this.telefonoUser = telefonoUser;
    }

    public String getApellidoUser() {
        return apellidoUser;
    }

    public void setApellidoUser(String apellidoUser) {
        this.apellidoUser = apellidoUser;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getCorreoUser() {
        return correoUser;
    }

    public void setCorreoUser(String correoUser) {
        this.correoUser = correoUser;
    }
}
