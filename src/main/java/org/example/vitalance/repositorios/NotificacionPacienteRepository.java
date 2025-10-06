package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.NotificacionPaciente;
import org.example.vitalance.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificacionPacienteRepository extends JpaRepository<NotificacionPaciente, Long> {
    List<NotificacionPaciente> findByPacienteOrderByCreadaEnDesc(Paciente paciente);

    Optional<NotificacionPaciente> findFirstByPacienteAndTipoIndicadorAndCreadaEnAfterOrderByCreadaEnDesc(
            Paciente paciente, String tipoIndicador, LocalDateTime after);
}
