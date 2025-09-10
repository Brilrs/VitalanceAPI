package org.example.vitalance.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="comidas")
public class Comida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComida;

    @Column(nullable = false)
    private String nombreComida;
    
    private LocalTime horaComida;
    
    private BigDecimal carbohidratosTotal;
    
    @ManyToOne
    @JoinColumn(name="idPaciente")
    private Paciente paciente;
    
    @OneToMany(mappedBy = "comida", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AlimentoComida> alimentoComidas;
}