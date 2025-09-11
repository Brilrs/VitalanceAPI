package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.MedicionesDTO;
import org.example.vitalance.dtos.PacienteDTO;

import java.util.List;

public interface IMedicionesService {
    public List<MedicionesDTO> ObtenerMediciones();
    public MedicionesDTO AgregarMedicion(MedicionesDTO medicionesDTO);
    public String EliminarMedicion(int id);
    public  MedicionesDTO actualizarMedicion(MedicionesDTO medicion, int id);
}
