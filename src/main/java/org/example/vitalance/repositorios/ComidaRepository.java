package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.Comida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComidaRepository extends JpaRepository<Comida, Long> {
}
