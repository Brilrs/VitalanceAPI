package org.example.vitalance.repositorios;

import org.example.vitalance.dtos.PacienteDTO;
import org.example.vitalance.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findByActivoPacienteTrue();

}
