package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.MedicamentoDTO;
import org.example.vitalance.servicios.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/apiMedicamentos")
public class MedicamentosController {
    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping("/listarMedicamentos")
    public List<MedicamentoDTO> listarMedicamentos(){
        log.info("Iniciando lista de medicamentos");
        return medicamentoService.listar();
    }

    @PostMapping("/insertarMedicamentos")
    public ResponseEntity<MedicamentoDTO> insertarMedicamentos(@Valid @RequestBody MedicamentoDTO medicamentoDTO){
        log.info("Iniciando lista de medicamentos {}", medicamentoDTO.getIdMedicamento());
        return ResponseEntity.ok().body(medicamentoService.insertar(medicamentoDTO));
    }

    @PutMapping("/editarMedicamentos")
    public ResponseEntity<MedicamentoDTO> editarMedicamentos(@RequestBody MedicamentoDTO medicamentoDTO){
        return ResponseEntity.ok().body(medicamentoService.editar(medicamentoDTO));
    }

    @GetMapping("/ver/{idMedicamento}")
    public ResponseEntity<MedicamentoDTO> buscarPorId(@PathVariable Long idMedicamento){
        return ResponseEntity.ok().body(medicamentoService.buscarPorId(idMedicamento));
    }

    @DeleteMapping("/{idMedicamento}")
    public void eliminar(@PathVariable Long idMedicamento){
        medicamentoService.eliminar(idMedicamento);
    }
}
