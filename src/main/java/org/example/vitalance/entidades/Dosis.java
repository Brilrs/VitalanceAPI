package org.example.vitalance.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Dosis")
public class Dosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDosis;

    // Relación con la programación original (FK a MedicamentoPaciente)
    // Esto vincula la dosis a la prescripción y al paciente/medicamento.
    @ManyToOne
    @JoinColumn(name = "idMedicamentoPaciente", nullable = false)
    private MedicamentoPaciente medicamentoPaciente;

    // Momento exacto en que estaba programada esta toma (Fecha y Hora)
    private LocalDateTime horaProgramada;

    // Estado de la dosis: PENDIENTE, TOMADA, OMITIDA
    // Es clave para la US-016 y US-024
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoDosis estadoDosis; // PENDIENTE, TOMADA, OMITIDA

    // Momento en que el paciente confirmó la toma (null si no la ha tomado)
    private LocalDateTime confirmadaEn;

    // Opcional: Puede ser útil para registrar si hubo un aviso
    // private Boolean segundoAvisoEnviado = false;

    // Opcional: Campo para rastrear la hora real en que se tomó (si es diferente a confirmadaEn)
    // private LocalTime horaRealToma;
}