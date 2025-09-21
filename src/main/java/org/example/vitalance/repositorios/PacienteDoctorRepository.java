package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.PacienteDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteDoctorRepository extends JpaRepository<PacienteDoctor, Long> {
}
