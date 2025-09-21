package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.Mediciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicionesRepository extends JpaRepository<Mediciones, Long> {
}
