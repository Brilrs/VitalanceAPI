package org.example.vitalance.security.dtos;

public class AuthRequestDTO {
    //private String username;
    private String correo;
    private String password;
    // getters and setters


    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    //public String getUsername() {
      //  return username;
    //}

    //public void setUsername(String username) {
      //  this.username = username;
    //}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}