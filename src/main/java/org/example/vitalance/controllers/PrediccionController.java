package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.PrediccionDTO;
import org.example.vitalance.servicios.PrediccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/apiPrediccion")
public class PrediccionController {
    @Autowired
    private PrediccionService prediccionService;

    @GetMapping("/listarPrediccion")
    public List<PrediccionDTO> listarPrediccion() {
        return prediccionService.listar();
    }
    @PostMapping("/insertarPrediccion")
    public ResponseEntity<PrediccionDTO>insertarPrediccion(@Valid @RequestBody PrediccionDTO prediccionDto) {
        return ResponseEntity.ok(prediccionService.insertar(prediccionDto));
    }
    @PutMapping("/editarPrediccion")
    public ResponseEntity<PrediccionDTO>editarPrediccion(@RequestBody PrediccionDTO prediccionDTO) {
        return ResponseEntity.ok(prediccionService.editar(prediccionDTO));
    }
    @GetMapping("/ver/{id}")
    public ResponseEntity<PrediccionDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(prediccionService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        prediccionService.eliminar(id);
    }
}
