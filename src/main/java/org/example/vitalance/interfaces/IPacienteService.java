package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.PacienteDTO;

import java.util.List;

public interface IPacienteService {

    public List<PacienteDTO> ObtenerPacientes();
    public PacienteDTO AgregarPaciente(PacienteDTO paciente);
    public String EliminarPaciente(int id);
    public String actualizarPaciente(PacienteDTO paciente, int id);

}
