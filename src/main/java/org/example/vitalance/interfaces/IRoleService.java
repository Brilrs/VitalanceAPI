package org.example.vitalance.interfaces;


import org.example.vitalance.dtos.RoleDTO;
import org.example.vitalance.entidades.Role;

import java.util.List;

public interface IRoleService {
    List<RoleDTO>listar();
    RoleDTO insertar(RoleDTO role);
    public RoleDTO editar(RoleDTO role);
    RoleDTO buscarPorId(Long id);
    public void eliminar(Long id);

}
