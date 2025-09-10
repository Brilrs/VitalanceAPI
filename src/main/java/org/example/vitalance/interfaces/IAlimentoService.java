package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.AlimentoDTO;
import java.util.List;

public interface IAlimentoService {
    List<AlimentoDTO> listar();
    AlimentoDTO insertar(AlimentoDTO dto);
    AlimentoDTO editar(AlimentoDTO dto);
    AlimentoDTO buscarPorId(Long id);
    void eliminar(Long id);
}