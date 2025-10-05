package org.example.vitalance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.vitalance.entidades.EstadoDosis;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DosisDTO {

    private Long idDosis;

    // Detalles de la Programación
    private Long idMedicamentoPaciente;
    private String nombreMedicamento;

    // Detalles del Evento
    private LocalDateTime horaProgramada;
    private EstadoDosis estadoDosis; // Enum ahora
    private LocalDateTime confirmadaEn; // Hora real de la toma
}