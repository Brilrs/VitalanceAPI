package org.example.vitalance.servicios;

import lombok.RequiredArgsConstructor;
import org.example.vitalance.dtos.ComidaDTO;
import org.example.vitalance.entidades.Comida;
import org.example.vitalance.interfaces.IComidaService;
import org.example.vitalance.repositorios.ComidaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComidaService implements IComidaService {

    private final ComidaRepository comidaRepository;
    private final ModelMapper modelMapper; // lo dejamos por consistencia, aunque abajo mapeamos a mano

    private ComidaDTO toDto(Comida e) { return modelMapper.map(e, ComidaDTO.class); }
    private Comida toEntity(ComidaDTO d) { return modelMapper.map(d, Comida.class); }
    // --- CRUD ---
    @Override
    public List<ComidaDTO> listar() { return comidaRepository.findAll().stream().map(this::toDto).toList(); }

    @Override
    public ComidaDTO insertar(ComidaDTO dto) {
        Comida saved = comidaRepository.save(toEntity(dto));
        return toDto(saved);
    }

    @Override
    public ComidaDTO editar(ComidaDTO dto) {
        // Con JPA, si dto trae id existente, save() hace update
        Comida updated = comidaRepository.save(toEntity(dto));
        return toDto(updated);
    }

    @Override
    public ComidaDTO buscarPorId(Long id) {
        return comidaRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Comida no encontrada: " + id));
    }

    @Override
    public void eliminar(Long id) {
        comidaRepository.deleteById(id);
    }
}