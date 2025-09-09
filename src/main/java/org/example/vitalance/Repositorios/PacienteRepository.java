package org.example.vitalance.Repositorios;

import org.example.vitalance.dtos.PacienteDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteDTO,Integer> {
}
