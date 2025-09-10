package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.AlimentoDTO;
import org.example.vitalance.interfaces.IAlimentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/apiAlimento")
public class AlimentoController {

    private final IAlimentoService alimentoService;

    @GetMapping("/listar")
    public List<AlimentoDTO> listar() {
        log.info("Listando alimentos");
        return alimentoService.listar();
    }

    @PostMapping("/insertar")
    public ResponseEntity<AlimentoDTO> insertar(@Valid @RequestBody AlimentoDTO dto) {
        log.info("Insertando alimento {}", dto.getDescripcion());
        return ResponseEntity.ok(alimentoService.insertar(dto));
    }

    @PutMapping("/editar")
    public ResponseEntity<AlimentoDTO> editar(@RequestBody AlimentoDTO dto) {
        return ResponseEntity.ok(alimentoService.editar(dto));
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<AlimentoDTO> ver(@PathVariable Long id) {
        return ResponseEntity.ok(alimentoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        alimentoService.eliminar(id);
    }
}