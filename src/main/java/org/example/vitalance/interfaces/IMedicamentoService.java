package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.MedicamentoDTO;

import java.util.List;

public interface IMedicamentoService {
    public List<MedicamentoDTO> listar();
    public MedicamentoDTO insertar(MedicamentoDTO medicamentoDTO);
    public MedicamentoDTO editar(MedicamentoDTO medicamentoDTO);
    public MedicamentoDTO buscarPorId(Long idMedicamento);
    public void eliminar(Long idMedicamento);
}
