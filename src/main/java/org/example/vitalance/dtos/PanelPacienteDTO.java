package org.example.vitalance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PanelPacienteDTO {
    private Long idPaciente;
    private String nombreCompleto;
    private String historiaClinica;
    private List<AlertaDTO> alertas;
    private List<MedicionesDTO> registrosGlucosa;
}
