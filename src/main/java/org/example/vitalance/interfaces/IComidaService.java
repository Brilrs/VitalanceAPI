package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.ComidaDTO;

import java.util.List;

public interface IComidaService {
    public List<ComidaDTO> listar();
    public ComidaDTO insertar (ComidaDTO comidaDTO);
    public ComidaDTO editar (ComidaDTO comidaDTO);
    public ComidaDTO buscarPorId(Long idComida);
    public void eliminar (Long idComida);
}
