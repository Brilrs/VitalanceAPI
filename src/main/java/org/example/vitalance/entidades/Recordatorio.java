package org.example.vitalance.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDateTime fechaCreacionRecordatorio;
    private LocalTime horaProgramadaRecordatorio;

    //US 09
    private LocalDateTime ultimoEnvioAt;            // último push enviado
    private Short reintentos = 0;                   // número de reintentos realizados

    //Relacion con la tabla Paciente
    @ManyToOne
    @JoinColumn(name="idPaciente",nullable = false)
    @JsonBackReference("recordatorio_paciente")
    private Paciente paciente;


}
