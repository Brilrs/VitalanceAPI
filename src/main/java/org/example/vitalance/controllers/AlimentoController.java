package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.AlimentoDTO;
import org.example.vitalance.interfaces.IAlimentoService;
import org.example.vitalance.servicios.AlimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//asdasd
@Slf4j
@RestController
@RequestMapping("/apiAlimento")
public class    AlimentoController {
    @Autowired
    private AlimentoService alimentoService;

    @GetMapping("/listarAlimento")
    public List<AlimentoDTO> listarAlimentos(){
        log.info("Iniciando lista de alimentos");
        return alimentoService.listar();
    }

    @PostMapping("/insertarAlimento")
    public ResponseEntity<AlimentoDTO> insertarAlimento(@Valid @RequestBody AlimentoDTO alimento){
        log.info("Iniciando alimento {}",alimento.getNombre());
        return ResponseEntity.ok(alimentoService.insertar(alimento));
    }

    @PutMapping("/editarAlimento")
    public ResponseEntity<AlimentoDTO> editarAlimento(@RequestBody AlimentoDTO alimento){
        return ResponseEntity.ok(alimentoService.editar(alimento));
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<AlimentoDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(alimentoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        alimentoService.eliminar(id);
    }
}