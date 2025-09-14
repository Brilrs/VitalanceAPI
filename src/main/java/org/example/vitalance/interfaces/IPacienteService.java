package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.DoctorDTO;
import org.example.vitalance.dtos.PacienteDTO;

import java.util.List;

public interface IPacienteService {
    List<PacienteDTO> listar();
    PacienteDTO insertar(PacienteDTO pacienteDTO);
    public PacienteDTO editar(long IdPaciente,PacienteDTO pacienteDTO);
    PacienteDTO buscarPorId(Long id);
    public void eliminar(Long id);
}
