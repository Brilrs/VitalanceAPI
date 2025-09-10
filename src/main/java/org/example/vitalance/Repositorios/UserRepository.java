package org.example.vitalance.Repositorios;

import org.example.vitalance.dtos.UserDTO;
import org.example.vitalance.entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
