package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.MedicamentoPacienteDTO;
import org.example.vitalance.servicios.MedicamentoPacienteService;
import org.example.vitalance.servicios.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.MedicamentoPacienteDTO;
import org.example.vitalance.interfaces.IRecordatorioService; // Nueva inyección para el scheduler
import org.example.vitalance.servicios.MedicamentoPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController // Debe ser RestController para manejar peticiones HTTP
@RequiredArgsConstructor
@RequestMapping("/apiMedicamentopaciente")
public class MedicamentoPacienteController {

    @Autowired
    private MedicamentoPacienteService medicamentoPacienteService;

    // Nuevo: Inyectamos el servicio que tiene la lógica del scheduler
    // Se inyecta aunque no se use directamente aquí, para documentar la relación
    // y para posibles llamadas futuras (ej. forzar programación).
    @Autowired
    private IRecordatorioService recordatorioService;


    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR','PACIENTE')")
    @GetMapping("/listarMP")
    public List<MedicamentoPacienteDTO> listarMP(){
        log.info("Iniciando lista de MedicamentoPaciente");
        return medicamentoPacienteService.listar();
    }

    // --- PUNTO CLAVE PARA US-009 ---

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR','PACIENTE')")
    @PostMapping("/insertarMP")
    public ResponseEntity<MedicamentoPacienteDTO> insertarMP(@Valid @RequestBody MedicamentoPacienteDTO medicamentoPacienteDTO){
        // Usamos el ID de Medicamento para el log, asumiendo que el DTO tiene getIdMedicamento()
        log.info("Iniciando inserción de MedicamentoPaciente con ID de Medicamento: {}", medicamentoPacienteDTO.getIdMedicamento());

        // 1. Persistir la programación (incluyendo las horas programadas en el DTO)
        MedicamentoPacienteDTO guardadoDTO = medicamentoPacienteService.insertar(medicamentoPacienteDTO);

        // 2. Activación del Recordatorio (Automática):
        // Como el RecordatorioService usa un @Scheduled que consulta la BD cada minuto,
        // no necesitamos hacer una llamada explícita aquí. La persistencia es suficiente.
        log.info("Programación guardada exitosamente. El scheduler global la detectará automáticamente.");

        return ResponseEntity.ok(guardadoDTO);
    }
    // --------------------------------


    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR')")
    @PutMapping("/editarMP")
    public ResponseEntity<MedicamentoPacienteDTO> editarMP(@RequestBody MedicamentoPacienteDTO medicamentoPacienteDTO){
        // El scheduler global gestionará los cambios de estado (activo/inactivo) en la siguiente pasada.
        return ResponseEntity.ok(medicamentoPacienteService.editar(medicamentoPacienteDTO));
    }


    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR','PACIENTE')")
    @GetMapping("/ver/{idMedicamentoPaciente}")
    public ResponseEntity<MedicamentoPacienteDTO> buscarPorId(@PathVariable Long idMedicamentoPaciente){
        return  ResponseEntity.ok(medicamentoPacienteService.buscarPorId(idMedicamentoPaciente));
    }


    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR')")
    @DeleteMapping("/{idMedicamentoPaciente}")
    public void eliminar(@PathVariable Long idMedicamentoPaciente){
        medicamentoPacienteService.eliminar(idMedicamentoPaciente);
    }
}
