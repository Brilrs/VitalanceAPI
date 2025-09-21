package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
}
