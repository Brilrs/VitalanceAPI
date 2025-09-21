package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.MedicionesDTO;
import org.example.vitalance.dtos.PacienteDTO;
import org.example.vitalance.servicios.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/apiPaciente")
public class PacienteController {

   @Autowired
    private PacienteService pacienteService;

   @GetMapping("/listarPaciente")
    public List<PacienteDTO> listarPaciente(){
       log.info("Listando pacientes");
       return pacienteService.listar();
   }
   @PostMapping("/insertarPaciente")
    public ResponseEntity<PacienteDTO>insertarPaciente(@Valid @RequestBody PacienteDTO pacienteDto){
       log.info("Insertando paciente, fecha creacion {}",pacienteDto.getFechaCreacionPaciente());
       return ResponseEntity.ok(pacienteService.insertar(pacienteDto));
   }

   @PutMapping("/editarPaciente")
    public ResponseEntity<PacienteDTO> editarPaciente(@RequestBody PacienteDTO pacienteDTO){
       return ResponseEntity.ok(pacienteService.editar(pacienteDTO));
   }
   @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
       pacienteService.eliminar(id);
   }

   @GetMapping("/ver/{id}")
    public ResponseEntity<PacienteDTO>buscarPorId(@PathVariable Long id){
       return ResponseEntity.ok(pacienteService.buscarPorId(id));
   }

}
