package org.example.vitalance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlimentoRecomendadoDTO {

    private Long alimentoId;
    private String nombre;
    private String descripcion;
    private String porcionSugerida;
    private Double carbohidratos;
    private Double calorias;
    private Double indiceGlucemico;
    private String categoriaIG; // "BAJO" (<55), "MEDIO" (55-70), "ALTO" (>70)
    private String razonRecomendacion;
    private Integer ordenPrioridad; // 1 = más recomendado

    // Método auxiliar para determinar categoría IG
    public void calcularCategoriaIG() {
        if (indiceGlucemico == null) {
            this.categoriaIG = "DESCONOCIDO";
            return;
        }

        if (indiceGlucemico < 55) {
            this.categoriaIG = "BAJO";
        } else if (indiceGlucemico <= 70) {
            this.categoriaIG = "MEDIO";
        } else {
            this.categoriaIG = "ALTO";
        }
    }
}