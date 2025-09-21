package org.example.vitalance.dtos;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.vitalance.entidades.User;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDTO {

    private Long idPaciente;
    private String numeroHistoriaClinicaPaciente;
    private String tipoDiabetesPaciente;
    private LocalDate fechaDiagnosticoPaciente;
    private Double estaturaDiagnosticoPaciente;
    private Double pesoDiagnosticoPaciente;
    private Double glucosaMinimaPaciente;
    private Double glucosaMaximaPaciente;
    private LocalDate fechaCreacionPaciente;
    private Boolean activoPaciente;
    private UserDTO user;

}
