package org.example.vitalance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AccionRecordatorioDTO {
    public enum Accion { TOMADO, POSPONER }
    private Accion accion;
    private Integer minutos; // solo requerido si accion = POSPONER

}
