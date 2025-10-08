package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.VisualizacionGlucosaDTO;

import java.time.LocalDate;
import java.util.List;

public interface IVisualizacionGlucosaService {
    List<VisualizacionGlucosaDTO> visualizarGlucosa(Long idPaciente, LocalDate desde, LocalDate hasta);
}
