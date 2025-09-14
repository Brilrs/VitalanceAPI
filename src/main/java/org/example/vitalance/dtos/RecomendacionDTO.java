package org.example.vitalance.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.vitalance.entidades.Paciente;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecomendacionDTO {
    private Long idRecomendacion;
    private String tipoRecomendacion;
    private String contenidoRecomendacion;
    private Double confianzaRecomendacion;
    private Boolean accionTomadaRecomendacion;

    private PacienteDTO paciente;
}
