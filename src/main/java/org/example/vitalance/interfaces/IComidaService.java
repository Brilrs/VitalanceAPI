package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.ComidaDTO;

import java.util.List;

public interface IComidaService {
    List<ComidaDTO> listar();
    ComidaDTO insertar(ComidaDTO dto);
    ComidaDTO editar(ComidaDTO dto);
    ComidaDTO buscarPorId(Long id);
    void eliminar(Long id);
}
