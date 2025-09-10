package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.UserDTO;
import org.example.vitalance.entidades.User;

import java.util.List;

public interface IUserService {
    List<UserDTO> listar();
    UserDTO insertar(UserDTO user);
    public UserDTO editar(UserDTO user);
    UserDTO buscarPorId(Long id);
    public void eliminar(Long id);

}
