package org.example.vitalance.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity @Table(name="Alertas")
@NoArgsConstructor @AllArgsConstructor
public class Alerta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlerta;

    @Column(length = 60, nullable = false)
    private String tipoIndicador;      // debe coincidir con Mediciones.tipoMedicion
    private Double valor;              // valor medido
    @Column(length = 20) private String unidad;

    @Column(length = 12, nullable = false)
    private String severidad;          // "CRITICA" | "NO_CRITICA"

    @Column(length = 16, nullable = false)
    private String estado;             // "PENDIENTE" | "REVISADA" | "AUTO_ARCHIVADA"

    private LocalDateTime creadaEn = LocalDateTime.now();
    private LocalDateTime revisadaEn;

    // vínculos
    @ManyToOne @JoinColumn(name="idPaciente", nullable=false)
    @JsonBackReference("alerta_paciente")
    private Paciente paciente;

    @ManyToOne @JoinColumn(name="idDoctor", nullable=false)
    @JsonBackReference("alerta_doctor")
    private Doctor doctor;

    // para acceso directo al detalle de la medición que la generó
    @ManyToOne @JoinColumn(name="idMedicion", nullable=false)
    private Mediciones medicion;
}
