package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.*;

import java.time.LocalDate;
import java.util.List;

public interface IPacienteService {
    public List<PacienteDTO> listar();
    public PacienteDTO insertar(PacienteDTO paciente);
    public PacienteDTO editar(PacienteDTO paciente);
    public PacienteDTO buscarPorId(Long id);
    public void eliminar(Long id);

    //HU-035
    AlertaGlucosaDTO procesarAlerta(AlertaGlucosaDTO dto);
//US-028
    public List<PacienteNivelesDeGlucosaDTO> NivelesDeGlucosa(long idPacienteAbuscar, LocalDate fechaDeMedicion);

}
