package org.example.vitalance.controllers;

import org.example.vitalance.dtos.ComidaDTO;
import org.example.vitalance.interfaces.IComidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/comidas")
@CrossOrigin(origins = "*")
public class ComidaController {

    @Autowired
    private IComidaService comidaService;

    @GetMapping
    public ResponseEntity<List<ComidaDTO>> getAllComidas() {
        List<ComidaDTO> comidas = comidaService.getAllComidas();
        return ResponseEntity.ok(comidas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComidaDTO> getComidaById(@PathVariable Long id) {
        ComidaDTO comida = comidaService.getComidaById(id);
        return ResponseEntity.ok(comida);
    }

    @PostMapping
    public ResponseEntity<ComidaDTO> createComida(@RequestBody ComidaDTO comidaDTO) {
        ComidaDTO createdComida = comidaService.createComida(comidaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComida);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComidaDTO> updateComida(@PathVariable Long id, @RequestBody ComidaDTO comidaDTO) {
        ComidaDTO updatedComida = comidaService.updateComida(id, comidaDTO);
        return ResponseEntity.ok(updatedComida);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComida(@PathVariable Long id) {
        comidaService.deleteComida(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ComidaDTO>> getComidasByPaciente(@PathVariable Long pacienteId) {
        List<ComidaDTO> comidas = comidaService.getComidasByPaciente(pacienteId);
        return ResponseEntity.ok(comidas);
    }

    @GetMapping("/paciente/{pacienteId}/hora")
    public ResponseEntity<List<ComidaDTO>> getComidasByPacienteAndHora(
            @PathVariable Long pacienteId,
            @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime horaInicio,
            @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime horaFin) {
        List<ComidaDTO> comidas = comidaService.getComidasByPacienteAndHora(pacienteId, horaInicio, horaFin);
        return ResponseEntity.ok(comidas);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ComidaDTO>> searchComidasByNombre(@RequestParam String nombre) {
        List<ComidaDTO> comidas = comidaService.searchComidasByNombre(nombre);
        return ResponseEntity.ok(comidas);
    }

    @PostMapping("/{comidaId}/alimentos/{alimentoId}")
    public ResponseEntity<ComidaDTO> addAlimentoToComida(
            @PathVariable Long comidaId,
            @PathVariable Long alimentoId,
            @RequestParam BigDecimal cantidad) {
        ComidaDTO comida = comidaService.addAlimentoToComida(comidaId, alimentoId, cantidad);
        return ResponseEntity.ok(comida);
    }

    @DeleteMapping("/{comidaId}/alimentos/{alimentoId}")
    public ResponseEntity<Void> removeAlimentoFromComida(
            @PathVariable Long comidaId,
            @PathVariable Long alimentoId) {
        comidaService.removeAlimentoFromComida(comidaId, alimentoId);
        return ResponseEntity.noContent().build();
    }
}