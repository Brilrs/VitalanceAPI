package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.Comida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ComidaRepository extends JpaRepository<Comida, Long> {
    
    @Query("SELECT c FROM Comida c WHERE c.paciente.idPaciente = ?1")
    List<Comida> findByPacienteId(Long pacienteId);
    
    @Query("SELECT c FROM Comida c WHERE c.paciente.idPaciente = ?1 AND c.horaComida BETWEEN ?2 AND ?3")
    List<Comida> findByPacienteIdAndHoraBetween(Long pacienteId, LocalTime horaInicio, LocalTime horaFin);
    
    @Query("SELECT c FROM Comida c WHERE LOWER(c.nombreComida) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Comida> findByNombreContainingIgnoreCase(String nombre);
}