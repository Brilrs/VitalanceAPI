package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.AlimentoDTO;
import org.example.vitalance.dtos.ComidaDTO;
import org.example.vitalance.entidades.Comida;
import org.example.vitalance.interfaces.IComidaService;
import org.example.vitalance.servicios.ComidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/apiComida")
public class ComidaController {

    private final IComidaService comidaService;

    @GetMapping("/listar")
    public List<ComidaDTO> listar() {
        log.info("Listando comidas");
        return comidaService.listar();
    }

    @PostMapping("/insertar")
    public ResponseEntity<ComidaDTO>insertar(@Valid @RequestBody ComidaDTO dto){
        log.info("Insertando comida {}", dto.getNombreComida());
        return ResponseEntity.ok(comidaService.insertar(dto));
    }

    @PutMapping("/editar")
    public ResponseEntity<ComidaDTO>editar(@RequestBody ComidaDTO dto){
        return ResponseEntity.ok(comidaService.editar(dto));
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<ComidaDTO>ver(@PathVariable Long id){
        return ResponseEntity.ok(comidaService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        comidaService.eliminar(id); }
}