package org.example.vitalance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.vitalance.entidades.User;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
    private Long idDoctor;
    private String numeroColegiaturaDoctor;
    private String especialidadDoctor;
    private String clinicaDoctor;
    private LocalDateTime fechaRegistroDocto;
    private User user;
}
