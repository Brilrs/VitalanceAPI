package org.example.vitalance.entidades;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Recomendaciones")
public class Recomendacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRecomendacion;
    private String tipoRecomendacion;
    @Column(name = "contenidoRecomendacion",nullable = true, columnDefinition = "TEXT")
    private String contenidoRecomendacion;
    private Double confianzaRecomendacion;
    private Boolean accionTomadaRecomendacion;
    //relacion con la tabla Paciente-
    @ManyToOne
    @JoinColumn(name="idPaciente",nullable = false)//el FK vive aqui
    @JsonBackReference("recomendacion_paciente")//se usa el mismo nombre de referencia para ambos lados
    private Paciente paciente;
}
