package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //este metodo lo genera spring automaticamente
    List<User>findByActivoUserTrue();//filtrar solo los que no estan eliminados

    //PARA LOGIN EN SEGURIDAD:
    Optional<User> findByCorreoUser(String correoUser);
}
