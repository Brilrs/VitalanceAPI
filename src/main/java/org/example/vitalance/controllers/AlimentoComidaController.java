package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.AlimentoComidaDTO;
import org.example.vitalance.repositorios.AlimentoComidaRepository;
import org.example.vitalance.servicios.AlimentoComidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@RequestMapping("/apiAlimentoComida")
public class AlimentoComidaController {
    @Autowired
    private AlimentoComidaService alimentoComidaService;

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR','PACIENTE')")
    @GetMapping("/listarAC")
    public List<AlimentoComidaDTO> listarAC(){
        log.info("Iniciando lista de alimentos y comidas");
        return alimentoComidaService.listar();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR')")
    @PostMapping("/insertarAC")
    public ResponseEntity<AlimentoComidaDTO> insertarAC(@Valid @RequestBody AlimentoComidaDTO alimentoComidaDTO){
        log.info("Iniciando alimento de comidas {}", alimentoComidaDTO.getCantidadAlimentoComida());
        return ResponseEntity.ok(alimentoComidaService.insertar(alimentoComidaDTO));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR')")
    @PutMapping("/editarAC")
    public ResponseEntity<AlimentoComidaDTO> editarAC(@RequestBody AlimentoComidaDTO alimentoComidaDTO){
        return ResponseEntity.ok(alimentoComidaService.editar(alimentoComidaDTO));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR','PACIENTE')")
    @GetMapping("/ver/{idAlimentoComida}")
    public ResponseEntity<AlimentoComidaDTO> buscarPorId(@PathVariable Long idAlimentoComida){
        return ResponseEntity.ok(alimentoComidaService.buscarPorId(idAlimentoComida));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR')")
    @DeleteMapping("/{idAlimentoComida}")
    public void eliminar(@PathVariable Long idAlimentoComida){
        alimentoComidaService.eliminar(idAlimentoComida);
    }

}
