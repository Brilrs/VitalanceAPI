package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.Prediccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrediccionRepository extends JpaRepository<Prediccion, Long> {
}
