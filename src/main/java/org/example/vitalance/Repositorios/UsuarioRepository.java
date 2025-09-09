package org.example.vitalance.Repositorios;

import org.example.vitalance.dtos.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UserDTO, Integer> {
}
