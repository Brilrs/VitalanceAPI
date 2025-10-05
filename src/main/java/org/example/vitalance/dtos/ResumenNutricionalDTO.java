package org.example.vitalance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumenNutricionalDTO {

    private Double totalCarbohidratos;
    private Double totalCalorias;
    private Double promedioIndiceGlucemico;
    private String evaluacionGeneral; // "EXCELENTE", "BUENA", "MODERADA", "PRECAUCIÓN"
    private String recomendacionHoraria; // "Ideal consumir en los próximos 30 minutos"

    // Calcular evaluación basada en carbohidratos y nivel de glucosa actual
    public void calcularEvaluacion(Double glucosaActual) {
        if (glucosaActual == null) {
            this.evaluacionGeneral = "NO_EVALUABLE";
            return;
        }

        // Lógica simple de evaluación
        if (glucosaActual < 70) {
            // Hipoglucemia: necesita carbohidratos rápidos
            if (totalCarbohidratos >= 15 && totalCarbohidratos <= 30) {
                this.evaluacionGeneral = "EXCELENTE";
            } else if (totalCarbohidratos < 15) {
                this.evaluacionGeneral = "INSUFICIENTE";
            } else {
                this.evaluacionGeneral = "PRECAUCIÓN";
            }
        } else if (glucosaActual >= 70 && glucosaActual <= 140) {
            // Rango normal
            if (totalCarbohidratos <= 60 && promedioIndiceGlucemico < 55) {
                this.evaluacionGeneral = "EXCELENTE";
            } else if (totalCarbohidratos <= 75) {
                this.evaluacionGeneral = "BUENA";
            } else {
                this.evaluacionGeneral = "MODERADA";
            }
        } else {
            // Hiperglucemia
            if (totalCarbohidratos <= 30 && promedioIndiceGlucemico < 55) {
                this.evaluacionGeneral = "BUENA";
            } else {
                this.evaluacionGeneral = "PRECAUCIÓN";
            }
        }
    }
}