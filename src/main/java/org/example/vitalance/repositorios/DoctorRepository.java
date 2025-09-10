package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
