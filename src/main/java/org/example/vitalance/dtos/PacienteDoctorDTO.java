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

    private PacienteDTO paciente;
    private DoctorDTO doctor;
}
