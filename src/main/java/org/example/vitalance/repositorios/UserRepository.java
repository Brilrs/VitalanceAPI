package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
