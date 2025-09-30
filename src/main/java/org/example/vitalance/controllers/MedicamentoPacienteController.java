package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.MedicamentoPacienteDTO;
import org.example.vitalance.servicios.MedicamentoPacienteService;
import org.example.vitalance.servicios.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@RequestMapping("/apiMedicamentopaciente")
public class MedicamentoPacienteController {
    @Autowired
    private MedicamentoPacienteService medicamentoPacienteService;

    @GetMapping("/listarMP")
    public List<MedicamentoPacienteDTO> listarMP(){
        log.info("Iniciando lista de MedicamentoPaciente");
        return medicamentoPacienteService.listar();
    }

    @PostMapping("/insertarMP")
    public ResponseEntity<MedicamentoPacienteDTO> insertarMP(@Valid @RequestBody MedicamentoPacienteDTO medicamentoPacienteDTO){
        log.info("Iniciando lista de MedicamentoPaciente {}", medicamentoPacienteDTO.getMedicamento());
        return ResponseEntity.ok(medicamentoPacienteService.insertar(medicamentoPacienteDTO));
    }

    @PutMapping("/editarMP")
    public ResponseEntity<MedicamentoPacienteDTO> editarMP(@RequestBody MedicamentoPacienteDTO medicamentoPacienteDTO){
        return ResponseEntity.ok(medicamentoPacienteService.editar(medicamentoPacienteDTO));
    }

    @GetMapping("/ver/{idMedicamentoPaciente}")
    public ResponseEntity<MedicamentoPacienteDTO> buscarPorId(@PathVariable Long idMedicamentoPaciente){
        return  ResponseEntity.ok(medicamentoPacienteService.buscarPorId(idMedicamentoPaciente));
    }

    @DeleteMapping("/{idMedicamentoPaciente}")
    public void eliminar(@PathVariable Long idMedicamentoPaciente){
        medicamentoPacienteService.eliminar(idMedicamentoPaciente);
    }
}
