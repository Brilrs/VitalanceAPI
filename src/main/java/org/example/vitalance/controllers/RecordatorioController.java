package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.AlertaGlucosaDTO;
import org.example.vitalance.dtos.RecordatorioDTO;
import org.example.vitalance.servicios.RecordatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/apiRecordatorio")
public class RecordatorioController {
    @Autowired
    private RecordatorioService recordatorioService;

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR')")
    @GetMapping("/listarRecordatorio")
    public List<RecordatorioDTO> listarRecordatorio(){
        return recordatorioService.listar();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR','PACIENTE')")
    @PostMapping("/insertarRecordatorio")
    public ResponseEntity<RecordatorioDTO> insertarRecordatorio(@Valid @RequestBody RecordatorioDTO recordatorioDto){
        return ResponseEntity.ok(recordatorioService.insertar(recordatorioDto));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR','PACIENTE')")
    @PutMapping("/editarRecordatorio")
    public ResponseEntity<RecordatorioDTO>editarRecordatorio(@RequestBody RecordatorioDTO recordatorioDTO){
        return ResponseEntity.ok(recordatorioService.editar(recordatorioDTO));
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR','PACIENTE')")
    @GetMapping("/ver/{id}")
    public ResponseEntity<RecordatorioDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(recordatorioService.buscarPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','PACIENTE')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        recordatorioService.eliminar(id);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR')")
    @GetMapping("/filtrar")
    public ResponseEntity<?> filtrarPorPaciente(@RequestParam(name = "paciente") String filtro) {
        log.info("Filtrando alertas con parámetro: {}", filtro);

        List<RecordatorioDTO> resultados = recordatorioService.filtrarPorPaciente(filtro);

        // Si no hay resultados, retornar mensaje informativo
        if (resultados.isEmpty()) {
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "No se encontraron alertas para el paciente especificado");
            respuesta.put("resultados", resultados);
            return ResponseEntity.ok(respuesta);
        }

        return ResponseEntity.ok(resultados);
    }

}
