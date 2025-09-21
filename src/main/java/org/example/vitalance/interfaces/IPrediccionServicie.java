package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.PrediccionDTO;

import java.util.List;

public interface IPrediccionServicie {
    public List<PrediccionDTO> listar();
    public PrediccionDTO insertar(PrediccionDTO prediccion);
    public PrediccionDTO editar(PrediccionDTO prediccion);
    public PrediccionDTO buscarPorId(Long id);

    void eliminar(Long id);
}
