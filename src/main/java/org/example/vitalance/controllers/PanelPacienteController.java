package org.example.vitalance.controllers;

import org.example.vitalance.dtos.PanelPacienteDTO;
import org.example.vitalance.servicios.PanelPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apiPanelPaciente")
public class PanelPacienteController {

    @Autowired
    private PanelPacienteService panelPacienteService;

    @GetMapping("/{idPaciente}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<PanelPacienteDTO> obtenerPanel(@PathVariable Long idPaciente) {
        return ResponseEntity.ok(panelPacienteService.obtenerPanelConsolidado(idPaciente));
    }
}
