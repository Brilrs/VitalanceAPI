package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.AlimentoComidaDTO;
import org.example.vitalance.entidades.AlimentoComida;

import java.util.List;

public interface IAlimentoComidaService {
    public List<AlimentoComidaDTO> listar();
    public AlimentoComidaDTO insertar(AlimentoComidaDTO alimentoComidaDTO);
    public AlimentoComidaDTO editar(AlimentoComidaDTO alimentoComidaDTO);
    public AlimentoComidaDTO buscarPorId(Long idAlimentocomida);
    public void eliminar(Long idAlimentocomida);
}
