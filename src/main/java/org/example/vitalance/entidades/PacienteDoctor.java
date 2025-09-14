package org.example.vitalance.entidades;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="PacienteDoctor",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idPaciente","idDoctor"})
}) //evita duplicados(que el mismo paciente se asigne dos veces al mismo doctor)
public class PacienteDoctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPacienteDoctor;

    private Boolean ActivoPacienteDoctor;
    private LocalDate fechaAsignacionPacienteDoctor;

    //RELACION CON LA TABLA PACIENTE-BIEN
    @ManyToOne
    @JoinColumn(name="idPaciente",nullable=false)
    private Paciente paciente;

    //RELACION CON LA TABLA DOCTOR-BIEN
    @ManyToOne
    @JoinColumn(name="idDoctor",nullable = false)
    private Doctor doctor;
}
