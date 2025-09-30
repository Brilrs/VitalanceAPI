package org.example.vitalance.controllers;

import org.example.vitalance.dtos.AlertaDTO;
import org.example.vitalance.servicios.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/apiAlertas")
public class AlertaController {
    @Autowired private AlertaService alertaService;

    // Lista (críticas primero por orden del repo)
    @GetMapping("/doctor/{idDoctor}")
    public ResponseEntity<List<AlertaDTO>> listar(@PathVariable Long idDoctor,
                                                  @RequestParam(required = false) String estado){
        return ResponseEntity.ok(alertaService.listarPorDoctor(idDoctor, estado));
    }

    // Marcar como "revisada"
    @PostMapping("/{id}/revisar")
    public ResponseEntity<AlertaDTO> revisar(@PathVariable Long id){
        return ResponseEntity.ok(alertaService.marcarRevisada(id));
    }

    // Resumen diario (para la HU)
    @GetMapping("/doctor/{idDoctor}/resumen")
    public ResponseEntity<List<AlertaDTO>> resumen(@PathVariable Long idDoctor,
                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dia){
        return ResponseEntity.ok(alertaService.resumenDiario(idDoctor, dia));
    }

    // Caso negativo HU-034: auto-archivar no críticas >24h sin revisar
    @PostMapping("/auto-archivar")
    public ResponseEntity<String> autoArchivar(){
        int n = alertaService.autoArchivarNoCriticas24h();
        return ResponseEntity.ok("Auto-archivadas: " + n);
    }
}
