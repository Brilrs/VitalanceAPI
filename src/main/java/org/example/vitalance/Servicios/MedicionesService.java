package org.example.vitalance.Servicios;

import org.example.vitalance.Repositorios.MedicionesRepository;
import org.example.vitalance.Repositorios.PacienteRepository;
import org.example.vitalance.dtos.MedicionesDTO;
import org.example.vitalance.dtos.PacienteDTO;
import org.example.vitalance.entidades.Mediciones;
import org.example.vitalance.entidades.Paciente;
import org.example.vitalance.interfaces.IMedicionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicionesService implements IMedicionesService {
    @Autowired
    private MedicionesRepository medicionesRepository;
@Autowired
private PacienteRepository pacienteRepository;

    @Override
    public List<MedicionesDTO> ObtenerMediciones() {
        return medicionesRepository.findAll().stream().map(medicion -> {
            MedicionesDTO dto = new MedicionesDTO();
            dto.setId(medicion.getId());
            dto.setTipoMedicion(medicion.getTipoMedicion());
            dto.setValor(medicion.getValor());
            dto.setUnidad(medicion.getUnidad());
            dto.setFechaMedicion(medicion.getFechaMedicion());
            dto.setOrigen(medicion.getOrigen());
            dto.setNotasValidacion(medicion.getNotasValidacion());

            if (medicion.getPaciente() != null) {
                dto.setPacienteId(medicion.getPaciente().getId());
                if (medicion.getPaciente().getUser() != null) {
                    dto.setUserId(medicion.getPaciente().getUser().getIdUser());
                }
            }
            dto.setDispositivoId(null);
            dto.setDatosCrudoDispositivoId(null);

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public MedicionesDTO AgregarMedicion(MedicionesDTO medicionesDTO) {
        // Buscar paciente
        Paciente paciente = pacienteRepository.findById(medicionesDTO.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        // Crear la medición
        Mediciones medicion = new Mediciones();
        medicion.setTipoMedicion(medicionesDTO.getTipoMedicion());
        medicion.setValor(medicionesDTO.getValor());
        medicion.setUnidad(medicionesDTO.getUnidad());
        medicion.setFechaMedicion(medicionesDTO.getFechaMedicion());
        medicion.setOrigen(medicionesDTO.getOrigen());
        medicion.setNotasValidacion(medicionesDTO.getNotasValidacion());

        // Relación con paciente y usuario
        medicion.setPaciente(paciente);
        medicion.setUsuario(paciente.getUser());

        // Guardar
        Mediciones guardada = medicionesRepository.save(medicion);

        // Retornar DTO con IDs
        MedicionesDTO dto = new MedicionesDTO();
        dto.setId(guardada.getId());
        dto.setTipoMedicion(guardada.getTipoMedicion());
        dto.setValor(guardada.getValor());
        dto.setUnidad(guardada.getUnidad());
        dto.setFechaMedicion(guardada.getFechaMedicion());
        dto.setOrigen(guardada.getOrigen());
        dto.setNotasValidacion(guardada.getNotasValidacion());

        dto.setPacienteId(paciente.getId());
        dto.setUserId(paciente.getUser().getIdUser());
        dto.setDispositivoId(null);
        dto.setDatosCrudoDispositivoId(null);

        return dto;
    }

    @Override
    public String EliminarMedicion(int id) {
        if (!medicionesRepository.existsById(id)) {
            throw new RuntimeException("Medición con id " + id + " no encontrada");
        }
        medicionesRepository.deleteById(id);
        return "Medición eliminada";
    }

    @Override
    public MedicionesDTO actualizarMedicion(MedicionesDTO medicionDTO, int id) {
        return medicionesRepository.findById(id).map(existing -> {
            existing.setTipoMedicion(medicionDTO.getTipoMedicion());
            existing.setValor(medicionDTO.getValor());
            existing.setUnidad(medicionDTO.getUnidad());
            existing.setFechaMedicion(medicionDTO.getFechaMedicion());
            existing.setOrigen(medicionDTO.getOrigen());
            existing.setNotasValidacion(medicionDTO.getNotasValidacion());

            Mediciones guardada = medicionesRepository.save(existing);

            MedicionesDTO dto = new MedicionesDTO();
            dto.setId(guardada.getId());
            dto.setTipoMedicion(guardada.getTipoMedicion());
            dto.setValor(guardada.getValor());
            dto.setUnidad(guardada.getUnidad());
            dto.setFechaMedicion(guardada.getFechaMedicion());
            dto.setOrigen(guardada.getOrigen());
            dto.setNotasValidacion(guardada.getNotasValidacion());

            if (guardada.getPaciente() != null) {
                dto.setPacienteId(guardada.getPaciente().getId());
                if (guardada.getPaciente().getUser() != null) {
                    dto.setUserId(guardada.getPaciente().getUser().getIdUser());
                }
            }
            dto.setDispositivoId(null);
            dto.setDatosCrudoDispositivoId(null);

            return dto;
        }).orElseThrow(() -> new RuntimeException("Medición con ID:" + id + " no encontrada"));
    }
}
