package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.Paciente;
import org.example.vitalance.entidades.Recordatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface RecordatorioRepository extends JpaRepository<Recordatorio, Long> {
    //usando metodos derivados (JPA)
    boolean existsByPaciente_IdPacienteAndFechaCreacionRecordatorioAfter(Long idPaciente, LocalDateTime fecha);

}
