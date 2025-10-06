package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.PrediccionDTO;
import org.example.vitalance.servicios.PrediccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/apiPrediccion")
public class PrediccionController {
    @Autowired
    private PrediccionService prediccionService;

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR')")
    @GetMapping("/listarPrediccion")
    public List<PrediccionDTO> listarPrediccion() {
        return prediccionService.listar();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','PACIENTE')")
    @PostMapping("/insertarPrediccion")
    public ResponseEntity<PrediccionDTO>insertarPrediccion(@Valid @RequestBody PrediccionDTO prediccionDto) {
        return ResponseEntity.ok(prediccionService.insertar(prediccionDto));
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','PACIENTE')")
    @PutMapping("/editarPrediccion")
    public ResponseEntity<PrediccionDTO>editarPrediccion(@RequestBody PrediccionDTO prediccionDTO) {
        return ResponseEntity.ok(prediccionService.editar(prediccionDTO));
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','PACIENTE','DOCTOR')")
    @GetMapping("/ver/{id}")
    public ResponseEntity<PrediccionDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(prediccionService.buscarPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        prediccionService.eliminar(id);
    }
}
