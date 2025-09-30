package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.TeleconsultaDTO;
import org.example.vitalance.interfaces.ITeleconsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/Teleconsulta")
public class TeleconsultaController {

    @Autowired
    private ITeleconsultaService teleconsultaService;

    @GetMapping("/listar")
    public List<TeleconsultaDTO> listar() {
        log.info("Listando teleconsultas");
        return teleconsultaService.listar();
    }

    @PostMapping("/solicitar")
    public ResponseEntity<TeleconsultaDTO> solicitar(@Valid @RequestBody TeleconsultaDTO dto) {
        log.info("Solicitando teleconsulta para paciente {} con doctor {} en fecha {}",
                dto.getIdPaciente(), dto.getIdDoctor(), dto.getFechaHora());
        return ResponseEntity.ok(teleconsultaService.solicitar(dto));
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<TeleconsultaDTO> ver(@PathVariable Long id) {
        return ResponseEntity.ok(teleconsultaService.buscarPorId(id));
    }

    @DeleteMapping("/cancelar/{id}")
    public void cancelar(@PathVariable Long id) {
        teleconsultaService.cancelar(id);
    }
}
