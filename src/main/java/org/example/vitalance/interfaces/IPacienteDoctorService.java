package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.PacienteDoctorDTO;

import java.util.List;

public interface IPacienteDoctorService {
    public List<PacienteDoctorDTO> listar();
    public PacienteDoctorDTO insertar(PacienteDoctorDTO pacienteDoctor);
    public PacienteDoctorDTO editar(PacienteDoctorDTO pacienteDoctor);
    public PacienteDoctorDTO buscarPorId(Long id);

    //eliminado fisico
    void eliminar(Long id);
    //public void eliminar(Long id);
}