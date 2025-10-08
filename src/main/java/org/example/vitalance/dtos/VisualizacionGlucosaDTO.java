package org.example.vitalance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisualizacionGlucosaDTO {
    private LocalDate fecha;
    private Double valor;
    private String unidad;
    private Boolean pico;
    private Boolean valle;
}
