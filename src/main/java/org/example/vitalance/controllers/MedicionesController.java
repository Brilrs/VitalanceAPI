package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.MedicionesDTO;
import org.example.vitalance.servicios.MedicionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/apiMediciones")
public class MedicionesController {
    @Autowired
    private MedicionesService medicionesService;

    @GetMapping("/listarMedicion")
    public List<MedicionesDTO> listarMediciones() {
        return medicionesService.listar();
    }

    @PostMapping("/insertarMedicion")
    public ResponseEntity<MedicionesDTO> insertarMedicion(@Valid @RequestBody MedicionesDTO medicionesDto) {
        return ResponseEntity.ok(medicionesService.insertar(medicionesDto));
    }
    @PutMapping("/editarMedicion")
    public ResponseEntity<MedicionesDTO>editarMedicion(@RequestBody MedicionesDTO medicionesDTO) {
        return ResponseEntity.ok(medicionesService.editar(medicionesDTO));
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<MedicionesDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicionesService.buscarPorId(id));
    }
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        medicionesService.eliminar(id);
    }

}
