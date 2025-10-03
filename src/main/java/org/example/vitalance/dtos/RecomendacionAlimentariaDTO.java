package org.example.vitalance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecomendacionAlimentariaDTO {

    private Long idRecomendacion;
    private Long pacienteId;
    private String nombrePaciente;
    private String tipoComida;

    // Datos de contexto
    private Double glucosaActual;
    private String nivelGlucosa; // "BAJO" (<70), "NORMAL" (70-140), "ALTO" (>140), "MUY_ALTO" (>180)
    private LocalDateTime fechaMedicion;
    private Integer diasDesdeMedicion;

    // Recomendaciones
    private List<AlimentoRecomendadoDTO> recomendaciones = new ArrayList<>();
    private ResumenNutricionalDTO resumen;

    // Advertencias y restricciones
    private List<String> advertencias = new ArrayList<>();
    private List<String> alimentosEvitar = new ArrayList<>();
    private List<String> consejosAdicionales = new ArrayList<>();

    // Información técnica (opcional, no mostrar al paciente)
    private String promptUtilizado;
    private String respuestaIA;

    // Metadata
    private LocalDateTime fechaGeneracion;
    private Boolean esRecomendacionEmergencia; // true si glucosa muy alta o muy baja

    // Método auxiliar para determinar nivel de glucosa
    public void determinarNivelGlucosa() {
        if (glucosaActual == null) {
            this.nivelGlucosa = "DESCONOCIDO";
            return;
        }

        if (glucosaActual < 70) {
            this.nivelGlucosa = "BAJO";
            this.esRecomendacionEmergencia = true;
        } else if (glucosaActual >= 70 && glucosaActual <= 140) {
            this.nivelGlucosa = "NORMAL";
            this.esRecomendacionEmergencia = false;
        } else if (glucosaActual > 140 && glucosaActual <= 180) {
            this.nivelGlucosa = "ALTO";
            this.esRecomendacionEmergencia = false;
        } else {
            this.nivelGlucosa = "MUY_ALTO";
            this.esRecomendacionEmergencia = true;
        }
    }
}