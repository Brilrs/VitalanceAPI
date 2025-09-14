package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.UserDTO;

import java.util.List;

public interface IUserService {
    public List<UserDTO> listar();
    public UserDTO insertar(UserDTO user);
    public UserDTO editar(UserDTO user);
    public UserDTO buscarPorId(Long id);
    public void eliminar(Long id);

}
