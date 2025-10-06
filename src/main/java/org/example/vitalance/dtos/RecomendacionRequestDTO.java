package org.example.vitalance.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecomendacionRequestDTO {

    @NotNull(message = "El ID del paciente es requerido")
    private Long idPaciente;

    @NotNull(message = "El tipo de comida es requerido")
    private String tipoComida; // "DESAYUNO", "ALMUERZO", "CENA", "SNACK"

    private LocalDateTime horaPlaneada;

    // Opcional: preferencias o restricciones adicionales
    private String restriccionesDieteticas; // "vegetariano", "vegano", "sin_gluten", etc.
}