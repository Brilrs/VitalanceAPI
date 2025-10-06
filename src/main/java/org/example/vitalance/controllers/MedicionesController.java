package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.MedicionesDTO;
import org.example.vitalance.servicios.MedicionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/apiMediciones")
public class MedicionesController {
    @Autowired
    private MedicionesService medicionesService;

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR')")
    @GetMapping("/listarMedicion")
    public List<MedicionesDTO> listarMediciones() {
        return medicionesService.listar();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','PACIENTE','DOCTOR')")
    @PostMapping("/insertarMedicion")
    public ResponseEntity<MedicionesDTO> insertarMedicion(@Valid @RequestBody MedicionesDTO medicionesDto) {
        return ResponseEntity.ok(medicionesService.insertar(medicionesDto));
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR')")
    @PutMapping("/editarMedicion")
    public ResponseEntity<MedicionesDTO>editarMedicion(@RequestBody MedicionesDTO medicionesDTO) {
        return ResponseEntity.ok(medicionesService.editar(medicionesDTO));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','PACIENTE','DOCTOR')")
    @GetMapping("/ver/{id}")
    public ResponseEntity<MedicionesDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicionesService.buscarPorId(id));
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        medicionesService.eliminar(id);
    }

}
