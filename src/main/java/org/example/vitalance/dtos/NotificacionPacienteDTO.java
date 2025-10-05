package org.example.vitalance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class NotificacionPacienteDTO {
    private Long idNotificacion;
    private String tipoIndicador;
    private Double valor;
    private String unidad;
    private String severidad;     // CRITICA | ADVERTENCIA
    private String estado;        // ENVIADA | VISTA | COMPARTIDA
    private String mensaje;
    private String recomendacion;
    private LocalDateTime creadaEn;
    private LocalDateTime actualizadaEn;
    private PacienteDTO paciente;
    private MedicionesDTO medicion;
}
