package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.ComidaDTO;
import org.example.vitalance.entidades.Comida;
import org.example.vitalance.servicios.ComidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/apiComida")
public class ComidaController {
    @Autowired
    private ComidaService comidaService;

    @GetMapping("/listarComida")
    public List<ComidaDTO> listarComida(){
        log.info("ComidaController listarComida");
        return comidaService.listar();
    }

    @PostMapping("/insertarComida")
    public ResponseEntity<ComidaDTO> insertarComida(@Valid @RequestBody ComidaDTO comidaDTO) {
        log.info("Iniciando Comida {}", comidaDTO.getNombreComida());
        return ResponseEntity.ok(comidaService.insertar(comidaDTO));
    }

    @PutMapping("/editarComida")
    public ResponseEntity<ComidaDTO> editarComida(@RequestBody ComidaDTO comidaDTO) {
        return ResponseEntity.ok(comidaService.editar(comidaDTO));
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<ComidaDTO> buscarPorId(@PathVariable Long idComida){
        return ResponseEntity.ok(comidaService.buscarPorId(idComida));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long idComida){
        comidaService.eliminar(idComida);
    }
}
