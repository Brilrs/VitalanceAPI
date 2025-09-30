package org.example.vitalance.dtos;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AlertaDTO {
    private Long idAlerta;
    private String tipoIndicador;
    private Double valor;
    private String unidad;
    private String severidad;   // CRITICA | NO_CRITICA
    private String estado;      // PENDIENTE | REVISADA | AUTO_ARCHIVADA
    private LocalDateTime creadaEn;
    private LocalDateTime revisadaEn;
    private PacienteDTO paciente;
    private DoctorDTO doctor;
    private MedicionesDTO medicion;
}
