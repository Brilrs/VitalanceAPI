package org.example.vitalance.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.vitalance.entidades.Paciente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordatorioDTO {

    private Long idRecordatorio;
    private String tipoRecordatorio;
    private Boolean estadoRecordatorio;
    private LocalDateTime fechaCreacionRecordatorio;
    private LocalTime horaProgramadaRecordatorio;
    private PacienteDTO paciente;
}
