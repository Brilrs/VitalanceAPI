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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="MedicamentoPaciente")
public class MedicamentoPaciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedicamentoPaciente;
    private String frecuenciaMedicamento;
    private LocalDate fechaInicioMedicamento;
    private LocalDate fechaFinMedicamento;
    private Boolean estadoMedicamento;

    //RELACION CON MEDICAMENTO FK
    @ManyToOne
    @JoinColumn(name="idMedicamento",nullable = false)
    private Medicamento medicamento;

    //RELACION CON PACIENTE FK
    @ManyToOne
    @JoinColumn(name="idPaciente",nullable = false)
    private Paciente paciente;
}
