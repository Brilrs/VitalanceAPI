package org.example.vitalance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlertaGlucosaDTO {
    private Long idPaciente;       // lo enlazas con paciente
    private Double valorGlucosa;// Ej: 45 mg/dL
    private Double umbralGlucosa;  // Ej: < 70 mg/dL (hipoglucemia)
    private String nivelAlerta;        // "hipoglucemia", "hiperglucemia"
    private String recomendacion;// "Tomar azúcar de acción rápida"
    private Boolean notificado; // true si se envió notificación
    private Boolean compartidoConMedico; // true si el paciente presionó "Compartir"
    private LocalDateTime fechaNotificacion;// Para controlar la regla de 30 min


}
