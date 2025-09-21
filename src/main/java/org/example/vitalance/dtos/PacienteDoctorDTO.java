package org.example.vitalance.dtos;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.vitalance.entidades.Doctor;
import org.example.vitalance.entidades.Paciente;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDoctorDTO {
    private Long idPacienteDoctor;
    private Boolean ActivoPacienteDoctor;
    private LocalDate fechaAsignacionPacienteDoctor;
    //Solo lo basico, no toda la entidad ni user
    //por que modelmapper explota por que trata de mapear toda la jerarquia
    //NO incluir los DTO completos de Paciente y Doctor, porque esos a su vez traen UserDTO, y ahí se forma la recursión.
    private Long idPaciente;
    private Long idDoctor;
}
