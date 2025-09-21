package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.Recordatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordatorioRepository extends JpaRepository<Recordatorio, Long> {

}
