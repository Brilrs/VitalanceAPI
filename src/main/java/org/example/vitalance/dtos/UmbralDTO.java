package org.example.vitalance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UmbralDTO {
    private Long idUmbral;
    private String tipoIndicador;
    private Double minimo;
    private Double maximo;
    private String unidad;
    private Boolean activo;
    private DoctorDTO doctor; // dueño del umbral
}
