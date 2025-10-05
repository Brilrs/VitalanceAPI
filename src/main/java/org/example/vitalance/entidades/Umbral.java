package org.example.vitalance.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name="Umbrales")
@NoArgsConstructor @AllArgsConstructor
public class Umbral {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUmbral;

    // ej: "GLUCOSA", "PRESION_SISTOLICA", "PRESION_DIASTOLICA"
    @Column(length = 60, nullable = false)
    private String tipoIndicador;

    private Double minimo;   // puede ser null si solo se valida máximo
    private Double maximo;   // puede ser null si solo se valida mínimo

    @Column(length = 20, nullable = false)
    private String unidad;   // ej: "mg/dL", "mmHg"

    private Boolean activo = true;

    // Médico dueño del umbral
    @ManyToOne
    @JoinColumn(name="idDoctor", nullable = false)
    @JsonBackReference("umbral_doctor")
    private Doctor doctor;
}
