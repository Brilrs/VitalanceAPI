package org.example.vitalance.servicios;

import lombok.RequiredArgsConstructor;
import org.example.vitalance.dtos.AlimentoDTO;
import org.example.vitalance.entidades.Alimento;
import org.example.vitalance.repositorios.AlimentoRepository;
import org.example.vitalance.interfaces.IAlimentoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlimentoService implements IAlimentoService {

    private final AlimentoRepository alimentoRepository;
    private final ModelMapper modelMapper;

    private AlimentoDTO toDto(Alimento e) { return modelMapper.map(e, AlimentoDTO.class); }
    private Alimento toEntity(AlimentoDTO d) { return modelMapper.map(d, Alimento.class); }

    @Override
    public List<AlimentoDTO> listar() {
        return alimentoRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public AlimentoDTO insertar(AlimentoDTO dto) {
        Alimento saved = alimentoRepository.save(toEntity(dto));
        return toDto(saved);
    }

    @Override
    public AlimentoDTO editar(AlimentoDTO dto) {
        Alimento updated = alimentoRepository.save(toEntity(dto));
        return toDto(updated);
    }

    @Override
    public AlimentoDTO buscarPorId(Long id) {
        return alimentoRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Alimento no encontrado: " + id));
    }

    @Override
    public void eliminar(Long id) {
        alimentoRepository.deleteById(id);
    }
}