package org.example.vitalance.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Recordatorios")
public class Recordatorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRecordatorio;
    private String tipoRecordatorio;
    private Boolean estadoRecordatorio;
    private LocalDate fechaCreacionRecordatorio;
    private LocalTime horaProgramadaRecordatorio;

    //Relacion con la tabla Paciente
    @ManyToOne
    @JoinColumn(name="idPaciente",nullable = false)
    @JsonBackReference("recordatorio_paciente")
    private Paciente paciente;

}
