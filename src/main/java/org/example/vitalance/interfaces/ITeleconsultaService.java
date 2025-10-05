package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.TeleconsultaDTO;

import java.util.List;

public interface ITeleconsultaService {
    List<TeleconsultaDTO> listar();
    TeleconsultaDTO solicitar(TeleconsultaDTO dto);
    TeleconsultaDTO buscarPorId(Long id);
    void cancelar(Long id);
}
