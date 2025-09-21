package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.MedicionesDTO;
import org.example.vitalance.dtos.UserDTO;

import java.util.List;

public interface IMedicionesService {
    public List<MedicionesDTO> listar();
    public MedicionesDTO insertar(MedicionesDTO mediciones);
    public MedicionesDTO editar(MedicionesDTO mediciones);
    public MedicionesDTO buscarPorId(Long id);
    public void eliminar(Long id);
}
