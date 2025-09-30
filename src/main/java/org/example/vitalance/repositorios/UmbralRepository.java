package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.Doctor;
import org.example.vitalance.entidades.Umbral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UmbralRepository extends JpaRepository<Umbral, Long> {
    List<Umbral> findByDoctorAndActivoTrue(Doctor doctor);
    List<Umbral> findByDoctorAndTipoIndicadorIgnoreCaseAndActivoTrue(Doctor doctor, String tipoIndicador);
}
