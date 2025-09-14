package org.example.vitalance.repositorios;

import org.example.vitalance.dtos.PacienteDTO;
import org.example.vitalance.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    PacienteDTO findByIdPaciente(Long idPaciente);
}
