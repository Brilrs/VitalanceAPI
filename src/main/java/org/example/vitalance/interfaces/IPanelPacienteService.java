package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.PanelPacienteDTO;

public interface IPanelPacienteService {
    PanelPacienteDTO obtenerPanelConsolidado(Long idPaciente);
}
