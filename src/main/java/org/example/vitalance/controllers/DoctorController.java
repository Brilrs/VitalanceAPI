package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.DoctorDTO;
import org.example.vitalance.servicios.DoctorService;
import org.example.vitalance.servicios.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/apiDoctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR')")
    @GetMapping("/listarDoctor")
    public List<DoctorDTO> listarDoctor() {
        log.info("Listado de doctorers");
        return doctorService.listar();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    @PostMapping("/insertarDoctor")
    public ResponseEntity<DoctorDTO>insertarDoctor(@Valid @RequestBody DoctorDTO doctorDto) {
        log.info("Registrado el doctor en la clinica {}", doctorDto.getClinicaDoctor());
        return ResponseEntity.ok(doctorService.insertar(doctorDto));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR')")
    @PutMapping("/editarDoctor")
    public ResponseEntity<DoctorDTO>editarDoctor(@RequestBody DoctorDTO doctorDTO){
        return ResponseEntity.ok(doctorService.editar(doctorDTO));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR','PACIENTE')")
    @GetMapping("/ver/{id}")
    public ResponseEntity<DoctorDTO>buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(doctorService.buscarPorId(id));
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        doctorService.eliminar(id);
    }

}
