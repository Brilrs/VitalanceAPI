package org.example.vitalance.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime; // <--- Importado para manejar las horas específicas
import java.util.List; // <--- Importado para la lista de horas

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

    // CAMPO CLAVE AGREGADO para la programación de recordatorios (US-009)
    // Usamos @ElementCollection para almacenar una colección de valores primitivos (LocalTime)
    // en una tabla separada, mapeando una relación uno a muchos simple.
    @ElementCollection
    @CollectionTable(name = "medicamento_paciente_horas", joinColumns = @JoinColumn(name = "idMedicamentoPaciente"))
    @Column(name = "hora_programada")
    private List<LocalTime> horasProgramadas;


    //RELACION CON MEDICAMENTO FK
    @ManyToOne
    @JoinColumn(name="idMedicamento",nullable = false)
    private Medicamento medicamento;

    //RELACION CON PACIENTE FK
    @ManyToOne
    @JoinColumn(name="idPaciente",nullable = false)
    private Paciente paciente;
}
