package org.example.vitalance.controllers;

import org.example.vitalance.dtos.UmbralDTO;
import org.example.vitalance.servicios.UmbralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/apiUmbrales")
public class UmbralController {
    @Autowired private UmbralService umbralService;

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR')")
    @PostMapping
    public ResponseEntity<UmbralDTO> crear(@RequestBody UmbralDTO dto){
        return ResponseEntity.ok(umbralService.crear(dto));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR')")
    @PutMapping
    public ResponseEntity<UmbralDTO> actualizar(@RequestBody UmbralDTO dto){
        return ResponseEntity.ok(umbralService.actualizar(dto));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR')")
    @PatchMapping("/{id}/activo/{flag}")
    public void activar(@PathVariable Long id, @PathVariable boolean flag){
        umbralService.activar(id, flag);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR')")
    @GetMapping("/doctor/{idDoctor}")
    public ResponseEntity<List<UmbralDTO>> listar(@PathVariable Long idDoctor){
        return ResponseEntity.ok(umbralService.listarPorDoctor(idDoctor));
    }
}
//ccs
