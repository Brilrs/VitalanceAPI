package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
}