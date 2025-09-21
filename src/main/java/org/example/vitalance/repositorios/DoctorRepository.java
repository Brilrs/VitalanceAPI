package org.example.vitalance.repositorios;

import org.example.vitalance.entidades.Doctor;
import org.example.vitalance.entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByActivoDoctorTrue();
}
