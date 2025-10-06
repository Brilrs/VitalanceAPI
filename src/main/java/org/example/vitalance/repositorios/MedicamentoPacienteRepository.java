package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.MedicamentoPaciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface MedicamentoPacienteRepository extends JpaRepository<MedicamentoPaciente, Long> {
    /**
     * BUSQUEDA PARA US-009 (Recordatorios de Medicación):
     * Busca programaciones de medicamentos que incluyan una hora específica
     * en su lista de horas programadas (horasProgramadas) y que estén activas
     * (estadoMedicamento = true).
     *
     * @param hora La hora actual (LocalTime) a verificar.
     * @return Una lista de programaciones que deben ser notificadas ahora.
     */
    List<MedicamentoPaciente> findByHorasProgramadasContainingAndEstadoMedicamentoTrue(LocalTime hora);
}
