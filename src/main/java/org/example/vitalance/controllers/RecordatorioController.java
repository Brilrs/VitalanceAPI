package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.RecordatorioDTO;
import org.example.vitalance.servicios.RecordatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/apiRecordatorio")
public class RecordatorioController {
    @Autowired
    private RecordatorioService recordatorioService;

    @GetMapping("/listarRecordatorio")
    public List<RecordatorioDTO> listarRecordatorio(){
        return recordatorioService.listar();
    }

    @PostMapping("/insertarRecordatorio")
    public ResponseEntity<RecordatorioDTO> insertarRecordatorio(@Valid @RequestBody RecordatorioDTO recordatorioDto){
        return ResponseEntity.ok(recordatorioService.insertar(recordatorioDto));
    }

    @PutMapping("/editarRecordatorio")
    public ResponseEntity<RecordatorioDTO>editarRecordatorio(@RequestBody RecordatorioDTO recordatorioDTO){
        return ResponseEntity.ok(recordatorioService.editar(recordatorioDTO));
    }
    @GetMapping("/ver/{id}")
    public ResponseEntity<RecordatorioDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(recordatorioService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        recordatorioService.eliminar(id);
    }

}
