package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.Mediciones;
import org.example.vitalance.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface MedicionesRepository extends JpaRepository<Mediciones, Long> {
    List<Mediciones> findByPaciente(Paciente paciente);

    List<Mediciones> findAllByPaciente(Paciente paciente);

    @Query("SELECT MAX(m.fechaMedicicion) FROM Mediciones m WHERE m.paciente.idPaciente = :idPaciente")
    Optional<LocalDate> findUltimaMedicionFecha(@Param("idPaciente") Long idPaciente);
}
