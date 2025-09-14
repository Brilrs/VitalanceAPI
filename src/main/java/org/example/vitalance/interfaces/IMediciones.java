package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.MedicionesDTO;

import java.util.List;

public interface IMediciones {
public MedicionesDTO AgregarMedicion(MedicionesDTO medicionesDTO);
public List<MedicionesDTO> ListarMediciones();
public String EliminarMediciones(Long idMedicion);
public MedicionesDTO ActualizarMediciones(Long idMedicion, MedicionesDTO medicionesDTO);
public List<MedicionesDTO> ListarMedicionesPaciente(Long idPaciente);
}
