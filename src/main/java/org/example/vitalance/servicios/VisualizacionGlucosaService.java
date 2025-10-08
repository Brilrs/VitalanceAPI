package org.example.vitalance.servicios;

import org.example.vitalance.dtos.VisualizacionGlucosaDTO;
import org.example.vitalance.entidades.Mediciones;
import org.example.vitalance.interfaces.IVisualizacionGlucosaService;
import org.example.vitalance.repositorios.MedicionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisualizacionGlucosaService implements IVisualizacionGlucosaService {

    @Autowired
    private MedicionesRepository medicionesRepository;

    @Override
    public List<VisualizacionGlucosaDTO> visualizarGlucosa(Long idPaciente, LocalDate desde, LocalDate hasta) {
        // Obtener todas las mediciones y filtrar por paciente + rango de fechas
        List<Mediciones> mediciones = medicionesRepository.findAll().stream()
                .filter(m -> m.getPaciente().getIdPaciente().equals(idPaciente))
                .filter(m -> m.getFechaMedicicion() != null &&
                        (m.getFechaMedicicion().isEqual(desde) || m.getFechaMedicicion().isAfter(desde)) &&
                        (m.getFechaMedicicion().isEqual(hasta) || m.getFechaMedicicion().isBefore(hasta)))
                .filter(m -> m.getTipoMedicion().equalsIgnoreCase("glucosa"))
                .collect(Collectors.toList());

        return mediciones.stream()
                .map(m -> new VisualizacionGlucosaDTO(
                        m.getFechaMedicicion(),
                        m.getValorMedicion(),
                        m.getUnidadMedicion(),
                        m.getValorMedicion() > 180, // pico
                        m.getValorMedicion() < 70   // valle
                ))
                .collect(Collectors.toList());
    }
}
