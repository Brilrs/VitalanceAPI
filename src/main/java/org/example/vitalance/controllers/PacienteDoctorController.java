package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.PacienteDoctorDTO;
import org.example.vitalance.servicios.PacienteDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@RestController
@RequestMapping("/apiPacienteDoctor")
public class PacienteDoctorController {
    @Autowired
    private PacienteDoctorService pacienteDoctorService;
    @GetMapping("/listarPD")
    public List<PacienteDoctorDTO> listarPacienteDoctor(){
        return pacienteDoctorService.listar();//llama al metodo de la interface
    }
    @PostMapping("/insertarPD")
    public ResponseEntity<PacienteDoctorDTO>insertarPacienteDoctor(@Valid @RequestBody PacienteDoctorDTO pdDto){
        return ResponseEntity.ok(pacienteDoctorService.insertar(pdDto));
    }
    @PutMapping("editarPD")
    public ResponseEntity<PacienteDoctorDTO>editarPacienteDoctor(@RequestBody PacienteDoctorDTO pdDto){
        return ResponseEntity.ok(pacienteDoctorService.editar(pdDto));
    }
    @GetMapping("/ver/{id}")
    public ResponseEntity<PacienteDoctorDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(pacienteDoctorService.buscarPorId(id));
    }
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        pacienteDoctorService.eliminar(id);
    }

}
