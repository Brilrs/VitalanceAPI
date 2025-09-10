package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.DoctorDTO;

import java.util.List;

public interface IDoctorService {
    List<DoctorDTO> listar();
    DoctorDTO insertar(DoctorDTO doctor);
    public DoctorDTO editar(DoctorDTO doctor);
    DoctorDTO buscarPorId(Long id);
    public void eliminar(Long id);
}
