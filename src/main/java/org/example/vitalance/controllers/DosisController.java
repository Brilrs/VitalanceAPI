package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.DosisDTO;
import org.example.vitalance.dtos.SolicitudConfirmacionDosisDTO;
import org.example.vitalance.interfaces.IDosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dosis")
public class DosisController {

    @Autowired
    private IDosisService dosisService;

    /**
     * US-016: Endpoint para que el paciente confirme o posponga una toma de medicación.
     * Recibe el ID de la Dosis, la acción ("TOMADA" o "POSPONER") y la hora real de confirmación.
     * @param requestDTO DTO con los detalles de la confirmación.
     * @return Respuesta HTTP de éxito.
     */
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','PACIENTE')")
    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarToma(@Valid @RequestBody SolicitudConfirmacionDosisDTO requestDTO) {
        log.info("Recibida solicitud de confirmación de Dosis ID: {} con acción: {}",
                requestDTO.getIdDosis(), requestDTO.getAccion());

        // La lógica de US-016 (marcar como TOMADA) está en el DosisService.
        dosisService.confirmarToma(requestDTO);

        return ResponseEntity.ok("Confirmación de dosis procesada exitosamente.");
    }

    /**
     * US-016 Complemento: Permite listar el historial de dosis de un paciente.
     * Se usa para mostrar el registro de tomas y omisiones.
     * Nota: El ID del paciente idealmente se obtendría del token de autenticación.
     * @param idPaciente ID del paciente para filtrar el historial.
     * @return Lista de DosisDTO (historial de tomas y omisiones).
     */
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCTOR','PACIENTE')")
    @GetMapping("/historial/paciente/{idPaciente}")
    public ResponseEntity<List<DosisDTO>> listarHistorialPorPaciente(@PathVariable Long idPaciente) {
        log.info("Buscando historial de dosis para Paciente ID: {}", idPaciente);

        // Nota: Este método debe ser implementado en IDosisService/DosisService.
        // Asumimos la existencia de un método para listar el historial.
        List<DosisDTO> historial = dosisService.listarHistorialPorPaciente(idPaciente);

        return ResponseEntity.ok(historial);
    }
}
