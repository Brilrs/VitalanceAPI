package org.example.vitalance.Repositorios;

import org.example.vitalance.dtos.MedicionesDTO;
import org.example.vitalance.entidades.Mediciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicionesRepository  extends JpaRepository<MedicionesDTO,Integer> {
}
