package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.AlertaGlucosaDTO;
import org.example.vitalance.dtos.RecordatorioDTO;
import org.example.vitalance.dtos.AccionRecordatorioDTO;

import java.util.List;

public interface IRecordatorioService {
    public List<RecordatorioDTO> listar();
    public RecordatorioDTO insertar(RecordatorioDTO recordatorio);
    public RecordatorioDTO editar(RecordatorioDTO recordatorio);
    public RecordatorioDTO buscarPorId(Long id);
    public void eliminar(Long id);

    //US 09
    List<RecordatorioDTO> dispararPendientes(); // devuelve los que deben notificarse ahora
    void accionar(Long idRecordatorio, AccionRecordatorioDTO accion);

}
