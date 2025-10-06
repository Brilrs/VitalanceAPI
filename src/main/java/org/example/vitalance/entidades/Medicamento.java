package org.example.vitalance.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Medicamentos")
public class Medicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedicamento;
    private String nombreMedicamento;
    private String tipoMedicamento;
    private Integer unidadMedicamento;
    private String dosisMedicamento;
    private LocalDate fechaMedicamento;

    //relacion con la tabla MedicamentoComida
    @OneToMany(mappedBy = "medicamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MedicamentoPaciente> medicamentoPaciente = new HashSet<>();
}
//odi