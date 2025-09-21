package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.DoctorDTO;
import org.example.vitalance.dtos.PacienteDTO;

import java.util.List;

public interface IPacienteService {
    public List<PacienteDTO> listar();
    public PacienteDTO insertar(PacienteDTO paciente);
    public PacienteDTO editar(PacienteDTO paciente);
    public PacienteDTO buscarPorId(Long id);
    public void eliminar(Long id);
}
