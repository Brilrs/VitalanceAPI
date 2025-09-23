package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.Mediciones;
import org.example.vitalance.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicionesRepository extends JpaRepository<Mediciones, Long> {
    List<Mediciones> findByPaciente(Paciente paciente);

    List<Mediciones> findAllByPaciente(Paciente paciente);
}
