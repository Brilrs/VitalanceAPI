package org.example.vitalance.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="doctores")
public class Doctor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idDoctor;

    private String numeroColegiaturaDoctor;
    private String especialidadDoctor;
    private String clinicaDoctor;
    private LocalDateTime fechaRegistroDocto;

    @OneToOne
    @JoinColumn(name="idUser")
    private User user;
}
