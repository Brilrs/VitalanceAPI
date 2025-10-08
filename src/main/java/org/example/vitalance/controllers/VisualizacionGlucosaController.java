package org.example.vitalance.controllers;

import org.example.vitalance.dtos.VisualizacionGlucosaDTO;
import org.example.vitalance.servicios.VisualizacionGlucosaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/apiVisualizacionGlucosa")
public class VisualizacionGlucosaController {

    @Autowired
    private VisualizacionGlucosaService visualizacionGlucosaService;

    @GetMapping("/{idPaciente}")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<List<VisualizacionGlucosaDTO>> visualizar(
            @PathVariable Long idPaciente,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        return ResponseEntity.ok(visualizacionGlucosaService.visualizarGlucosa(idPaciente, desde, hasta));
    }
}
