package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.Recordatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecordatorioRepository extends JpaRepository<Recordatorio, Long> {

    boolean existsByPaciente_IdPacienteAndFechaCreacionRecordatorioAfter(
            Long idPaciente, LocalDateTime fecha);

    @Query("SELECT r FROM Recordatorio r JOIN r.paciente p JOIN p.user u " +
            "WHERE LOWER(u.nombreUser) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
            "OR LOWER(u.apellidoUser) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
            "OR LOWER(p.numeroHistoriaClinicaPaciente) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
            "ORDER BY r.fechaCreacionRecordatorio DESC")
    List<Recordatorio> findByPacienteFiltro(@Param("filtro") String filtro);
}