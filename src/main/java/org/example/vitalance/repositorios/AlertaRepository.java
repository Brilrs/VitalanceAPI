package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findByDoctorOrderBySeveridadDescCreadaEnDesc(Doctor doctor);
    List<Alerta> findByDoctorAndEstadoOrderBySeveridadDescCreadaEnDesc(Doctor doctor, String estado);

    @Query("SELECT a FROM Alerta a WHERE a.doctor = :doctor AND a.creadaEn BETWEEN :ini AND :fin ORDER BY a.severidad DESC, a.creadaEn DESC")
    List<Alerta> findResumenDiario(@Param("doctor") Doctor doctor,
                                   @Param("ini") LocalDateTime ini,
                                   @Param("fin") LocalDateTime fin);

    List<Alerta> findByEstadoAndSeveridadAndCreadaEnBefore(String estado, String severidad, LocalDateTime limite);

    List<Alerta> findByEstadoAndSeveridadAndCreadaEnAfter(String estado, String severidad, LocalDateTime creadaEnAfter);
}
