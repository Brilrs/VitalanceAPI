package org.example.vitalance.servicios;

import org.example.vitalance.dtos.AlertaDTO;
import org.example.vitalance.dtos.MedicionesDTO;
import org.example.vitalance.dtos.PanelPacienteDTO;
import org.example.vitalance.entidades.Alerta;
import org.example.vitalance.entidades.Mediciones;
import org.example.vitalance.entidades.Paciente;
import org.example.vitalance.interfaces.IPanelPacienteService;
import org.example.vitalance.repositorios.AlertaRepository;
import org.example.vitalance.repositorios.MedicionesRepository;
import org.example.vitalance.repositorios.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PanelPacienteService implements IPanelPacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private AlertaRepository alertaRepository;
    @Autowired
    private MedicionesRepository medicionesRepository;

    @Override
    public PanelPacienteDTO obtenerPanelConsolidado(Long idPaciente) {
        // Buscar el paciente entre los activos
        Paciente paciente = pacienteRepository.findByActivoPacienteTrue()
                .stream()
                .filter(p -> p.getIdPaciente().equals(idPaciente))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado o inactivo"));

        // Obtener alertas del paciente (sin modificar el repo)
        List<AlertaDTO> alertas = alertaRepository.findAll().stream()
                .filter(a -> a.getPaciente().getIdPaciente().equals(idPaciente))
                .map(a -> new AlertaDTO(
                        a.getIdAlerta(),
                        a.getTipoIndicador(),
                        a.getValor(),
                        a.getUnidad(),
                        a.getSeveridad(),
                        a.getEstado(),
                        a.getCreadaEn(),
                        a.getRevisadaEn(),
                        null,
                        null,
                        null
                ))
                .collect(Collectors.toList());

        // Obtener mediciones (solo glucosa)
        List<MedicionesDTO> registros = medicionesRepository.findAll().stream()
                .filter(m -> m.getPaciente().getIdPaciente().equals(idPaciente))
                .filter(m -> m.getTipoMedicion().equalsIgnoreCase("glucosa"))
                .map(m -> new MedicionesDTO(
                        m.getIdMedicion(),
                        m.getTipoMedicion(),
                        m.getValorMedicion(),
                        m.getUnidadMedicion(),
                        m.getFechaMedicicion(),
                        m.getNotaMedicion(),
                        null,
                        null
                ))
                .collect(Collectors.toList());

        // Construir el panel
        return new PanelPacienteDTO(
                paciente.getIdPaciente(),
                "Paciente #" + paciente.getIdPaciente(),
                "Historia no detallada",
                alertas,
                registros
        );
    }
}
