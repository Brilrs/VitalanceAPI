package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.AlertaGlucosaDTO;
import org.example.vitalance.dtos.RecordatorioDTO;

import java.util.List;

public interface IRecordatorioService {
    public List<RecordatorioDTO> listar();
    public RecordatorioDTO insertar(RecordatorioDTO recordatorio);
    public RecordatorioDTO editar(RecordatorioDTO recordatorio);
    public RecordatorioDTO buscarPorId(Long id);
    public void eliminar(Long id);
    public List<RecordatorioDTO> filtrarPorPaciente(String filtro);


}
