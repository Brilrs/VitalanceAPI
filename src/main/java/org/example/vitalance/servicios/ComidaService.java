package org.example.vitalance.servicios;

import org.example.vitalance.dtos.AlimentoComidaDTO;
import org.example.vitalance.dtos.ComidaDTO;
import org.example.vitalance.entidades.Alimento;
import org.example.vitalance.entidades.AlimentoComida;
import org.example.vitalance.entidades.Comida;
import org.example.vitalance.entidades.Paciente;
import org.example.vitalance.interfaces.IComidaService;
import org.example.vitalance.repositorios.AlimentoComidaRepository;
import org.example.vitalance.repositorios.AlimentoRepository;
import org.example.vitalance.repositorios.ComidaRepository;
import org.example.vitalance.repositorios.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComidaService implements IComidaService {

    @Autowired
    private ComidaRepository comidaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private AlimentoComidaRepository alimentoComidaRepository;

    @Override
    public List<ComidaDTO> getAllComidas() {
        List<Comida> comidas = comidaRepository.findAll();
        return comidas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ComidaDTO getComidaById(Long id) {
        Comida comida = comidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comida no encontrada con ID: " + id));
        return convertToDTO(comida);
    }

    @Override
    public ComidaDTO createComida(ComidaDTO comidaDTO) {
        Comida comida = new Comida();
        comida.setNombreComida(comidaDTO.getNombreComida());
        comida.setHoraComida(comidaDTO.getHoraComida());
        comida.setCarbohidratosTotal(comidaDTO.getCarbohidratosTotal());

        if (comidaDTO.getIdPaciente() != null) {
            Paciente paciente = pacienteRepository.findById(comidaDTO.getIdPaciente())
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + comidaDTO.getIdPaciente()));
            comida.setPaciente(paciente);
        }

        Comida savedComida = comidaRepository.save(comida);
        return convertToDTO(savedComida);
    }

    @Override
    public ComidaDTO updateComida(Long id, ComidaDTO comidaDTO) {
        Comida existingComida = comidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comida no encontrada con ID: " + id));

        existingComida.setNombreComida(comidaDTO.getNombreComida());
        existingComida.setHoraComida(comidaDTO.getHoraComida());
        existingComida.setCarbohidratosTotal(comidaDTO.getCarbohidratosTotal());

        if (comidaDTO.getIdPaciente() != null) {
            Paciente paciente = pacienteRepository.findById(comidaDTO.getIdPaciente())
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + comidaDTO.getIdPaciente()));
            existingComida.setPaciente(paciente);
        }

        Comida updatedComida = comidaRepository.save(existingComida);
        return convertToDTO(updatedComida);
    }

    @Override
    public void deleteComida(Long id) {
        if (!comidaRepository.existsById(id)) {
            throw new RuntimeException("Comida no encontrada con ID: " + id);
        }
        comidaRepository.deleteById(id);
    }

    @Override
    public List<ComidaDTO> getComidasByPaciente(Long pacienteId) {
        List<Comida> comidas = comidaRepository.findByPacienteId(pacienteId);
        return comidas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComidaDTO> getComidasByPacienteAndHora(Long pacienteId, LocalTime horaInicio, LocalTime horaFin) {
        List<Comida> comidas = comidaRepository.findByPacienteIdAndHoraBetween(pacienteId, horaInicio, horaFin);
        return comidas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComidaDTO> searchComidasByNombre(String nombre) {
        List<Comida> comidas = comidaRepository.findByNombreContainingIgnoreCase(nombre);
        return comidas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ComidaDTO addAlimentoToComida(Long comidaId, Long alimentoId, BigDecimal cantidad) {
        Comida comida = comidaRepository.findById(comidaId)
                .orElseThrow(() -> new RuntimeException("Comida no encontrada con ID: " + comidaId));
        
        Alimento alimento = alimentoRepository.findById(alimentoId)
                .orElseThrow(() -> new RuntimeException("Alimento no encontrado con ID: " + alimentoId));

        AlimentoComida alimentoComida = new AlimentoComida();
        alimentoComida.setComida(comida);
        alimentoComida.setAlimento(alimento);
        alimentoComida.setCantidad(cantidad);

        alimentoComidaRepository.save(alimentoComida);
        
        return convertToDTO(comida);
    }

    @Override
    public void removeAlimentoFromComida(Long comidaId, Long alimentoId) {
        List<AlimentoComida> alimentoComidas = alimentoComidaRepository.findByComidaId(comidaId);
        alimentoComidas.stream()
                .filter(ac -> ac.getAlimento().getIdAlimento().equals(alimentoId))
                .findFirst()
                .ifPresent(alimentoComidaRepository::delete);
    }

    private ComidaDTO convertToDTO(Comida comida) {
        ComidaDTO dto = new ComidaDTO();
        dto.setIdComida(comida.getIdComida());
        dto.setNombreComida(comida.getNombreComida());
        dto.setHoraComida(comida.getHoraComida());
        dto.setCarbohidratosTotal(comida.getCarbohidratosTotal());
        
        if (comida.getPaciente() != null) {
            dto.setIdPaciente(comida.getPaciente().getIdPaciente());
            if (comida.getPaciente().getUser() != null) {
                dto.setNombrePaciente(comida.getPaciente().getUser().getNombreUser());
            }
        }

        if (comida.getAlimentoComidas() != null) {
            List<AlimentoComidaDTO> alimentosDTO = comida.getAlimentoComidas().stream()
                    .map(this::convertAlimentoComidaToDTO)
                    .collect(Collectors.toList());
            dto.setAlimentos(alimentosDTO);
        }

        return dto;
    }

    private AlimentoComidaDTO convertAlimentoComidaToDTO(AlimentoComida alimentoComida) {
        AlimentoComidaDTO dto = new AlimentoComidaDTO();
        dto.setId(alimentoComida.getId());
        dto.setIdComida(alimentoComida.getComida().getIdComida());
        dto.setCantidad(alimentoComida.getCantidad());
        
        if (alimentoComida.getAlimento() != null) {
            dto.setIdAlimento(alimentoComida.getAlimento().getIdAlimento());
            dto.setNombreAlimento(alimentoComida.getAlimento().getNombreAlimento());
            
            // Calculate totals based on quantity
            BigDecimal factor = alimentoComida.getCantidad().divide(new BigDecimal("100"));
            dto.setCaloriasTotales(alimentoComida.getAlimento().getCaloriasPor100g().multiply(factor));
            dto.setProteinasTotales(alimentoComida.getAlimento().getProteinasPor100g().multiply(factor));
            dto.setCarbohidratosTotales(alimentoComida.getAlimento().getCarbohidratosPor100g().multiply(factor));
            dto.setGrasasTotales(alimentoComida.getAlimento().getGrasasPor100g().multiply(factor));
        }
        
        return dto;
    }
}