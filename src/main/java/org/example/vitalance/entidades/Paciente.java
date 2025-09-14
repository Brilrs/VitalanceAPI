package org.example.vitalance.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaciente;
    private String numeroHistoriaClinicaPaciente;
    private String tipoDiabetesPaciente;
    private LocalDate fechaDiagnosticoPaciente;
    private Double estaturaDiagnosticoPaciente;
    private Double pesoDiagnosticoPaciente;
    private Double glucosaMinimaPaciente;
    private Double glucosaMaximaPaciente;
    private LocalDate fechaCreacionPaciente;

    //relacion con la tabla USER-PACIENTE es el dueño de la Relacion por que tiene un FK de User
    @OneToOne
    @JoinColumn(name="idUser",nullable=false)
    @JsonManagedReference("paciente_user")
    private User user;

    //Relacion con la tabla Comida
    @OneToMany(mappedBy="paciente",cascade=CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference("paciente_comida")
    private List<Comida> comida=new ArrayList<>();

    //relacion con la tabla pacienteDoctor,relacion bidireccional
    @OneToMany(mappedBy = "paciente",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<PacienteDoctor> pacienteDoctores=new HashSet<>();

    //Relacion con la tabla Prediccion
    @OneToMany(mappedBy="paciente",cascade=CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference("prediccion_paciente")
    private List<Prediccion> predicciones=new ArrayList<>();

    //Relacion con la tabla Recomendacion
    @OneToMany(mappedBy="paciente",cascade=CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference("recomendacion_paciente")
    private List<Recomendacion> recomendaciones=new ArrayList<>();

    //Relacion con la tabla Mediciones
    @OneToMany(mappedBy = "paciente",cascade =CascadeType.ALL,orphanRemoval = true )
    @JsonManagedReference("medicion_paciente")
    private List<Mediciones>mediciones=new ArrayList<>();

    //Relacion con la tabla Recordatorio
    @OneToMany(mappedBy = "paciente",cascade=CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference("recordatorio_paciente")
    private List<Recordatorio>recordatorios=new ArrayList<>();

    //relacion con la tabla MedicamentoPaciente
    @OneToMany(mappedBy = "paciente",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<MedicamentoPaciente>medicamentoPaciente=new HashSet<>();


}
