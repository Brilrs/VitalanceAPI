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
public class PrediccionDTO {

    private Long idPrediccion;
    private String tipoPrediccion;
    private Double probabilidadPrediccion;
    private String promptPrediccion;
    private String respuestaTextoPrediccion;
    private PacienteDTO paciente;
}
