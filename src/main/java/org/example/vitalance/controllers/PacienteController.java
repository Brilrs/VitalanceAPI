package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.AlertaGlucosaDTO;
import org.example.vitalance.dtos.MedicionesDTO;
import org.example.vitalance.dtos.PacienteDTO;
import org.example.vitalance.dtos.PacienteNivelesDeGlucosaDTO;
import org.example.vitalance.servicios.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.vitalance.servicios.DeteccionInactividadService;

import java.time.LocalDate;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/apiPaciente")
public class PacienteController {

   @Autowired
    private PacienteService pacienteService;
   @Autowired
   private DeteccionInactividadService deteccionInactividadService;

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

   @PostMapping("/{id}/alertaGlucosa")
    public ResponseEntity<AlertaGlucosaDTO>procesarAlertaGlucosa(
            @PathVariable Long id,
            @RequestBody AlertaGlucosaDTO dto){
       dto.setIdPaciente(id);
       AlertaGlucosaDTO respuesta = pacienteService.procesarAlerta(dto);
       return ResponseEntity.ok(respuesta);

   }
   @GetMapping("/MostrarNivelesDeGlucosaDePacienteResumido/{idpaciente}/{fecha}")
    public List<PacienteNivelesDeGlucosaDTO>NivelGlucosaPacienteResumido(@PathVariable long idpaciente, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha){
       return pacienteService.NivelesDeGlucosa(idpaciente,fecha);
   }

    @GetMapping("/inactivos")
    public ResponseEntity<List<DeteccionInactividadService.PacienteInactivoDTO>>
    obtenerPacientesInactivos(
            @RequestParam(name = "diasSinMedicion", defaultValue = "7") int diasUmbral
    ) {

        if (diasUmbral < 1) {
            throw new IllegalArgumentException("El umbral de días debe ser mayor a 0");
        }

        List<DeteccionInactividadService.PacienteInactivoDTO> pacientes =
                deteccionInactividadService.obtenerPacientesInactivos(diasUmbral);

        return ResponseEntity.ok(pacientes);
    }



}
