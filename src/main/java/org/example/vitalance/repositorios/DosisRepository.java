package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.Dosis;
import org.example.vitalance.entidades.EstadoDosis;
import org.example.vitalance.entidades.MedicamentoPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DosisRepository extends JpaRepository<Dosis, Long> {

    /**
     * Busca una dosis pendiente específica para una programación de medicamento
     * y hora determinada. Es útil para evitar duplicados.
     */
    Optional<Dosis> findByMedicamentoPacienteAndHoraProgramada(
            MedicamentoPaciente medicamentoPaciente,
            LocalDateTime horaProgramada
    );

    /**
     * Metodo clave para la US-024 (Marcar como Omitida):
     * Busca todas las dosis que están en estado "PENDIENTE" y cuya
     * hora programada ya pasó hace un tiempo específico (ej. 20 minutos).
     */
    List<Dosis> findByEstadoDosisAndHoraProgramadaBefore(
            EstadoDosis estadoDosis,
            LocalDateTime horaCorte
    );

    List<Dosis> findByMedicamentoPaciente_Paciente_IdPacienteOrderByHoraProgramadaDesc(Long idPaciente);
}