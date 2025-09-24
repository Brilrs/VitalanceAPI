package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.Paciente;
import org.example.vitalance.entidades.Recordatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface RecordatorioRepository extends JpaRepository<Recordatorio, Long> {
    //usando metodos derivados (JPA)
    boolean existsByPaciente_IdPacienteAndFechaCreacionRecordatorioAfter(Long idPaciente, LocalDateTime fecha);

    //US 09
    @Query("""
     SELECT r
       FROM Recordatorio r
      WHERE r.estadoRecordatorio = TRUE
        AND r.horaProgramadaRecordatorio <= :horaActual
        AND (r.ultimoEnvioAt IS NULL OR
             (r.reintentos = 1 AND r.ultimoEnvioAt <= :haceDiezMin))
        AND (r.reintentos IS NULL OR r.reintentos < 2)
  """)
    List<Recordatorio> findDue(LocalTime horaActual, LocalDateTime haceDiezMin);

}
