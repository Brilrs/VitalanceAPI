package org.example.vitalance.controllers;

import org.example.vitalance.dtos.NotificacionPacienteDTO;
import org.example.vitalance.servicios.NotificacionPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/apiNotificacionesPaciente")
public class NotificacionPacienteController {

    @Autowired private NotificacionPacienteService service;

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','PACIENTE')")
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<NotificacionPacienteDTO>> listar(@PathVariable Long idPaciente){
        return ResponseEntity.ok(service.listarPorPaciente(idPaciente));
    }

    // Botón: "Compartir con mi médico"
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','PACIENTE')")
    @PostMapping("/{idNotificacion}/compartir")
    public ResponseEntity<NotificacionPacienteDTO> compartir(@PathVariable Long idNotificacion){
        return ResponseEntity.ok(service.compartirConMedico(idNotificacion));
    }
}
