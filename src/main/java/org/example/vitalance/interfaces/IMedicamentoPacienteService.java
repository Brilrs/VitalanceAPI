package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.MedicamentoPacienteDTO;

import java.util.List;

public interface IMedicamentoPacienteService {
    public List<MedicamentoPacienteDTO> listar();
    public MedicamentoPacienteDTO insertar(MedicamentoPacienteDTO medicamentoPacienteDTO);
    public MedicamentoPacienteDTO editar(MedicamentoPacienteDTO medicamentoPacienteDTO);
    public MedicamentoPacienteDTO buscarPorId(Long idMedicamentoPaciente);
    public void eliminar(Long idMedicamentoPaciente);
}
