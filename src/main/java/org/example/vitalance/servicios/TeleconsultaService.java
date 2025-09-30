package org.example.vitalance.servicios;

import org.example.vitalance.dtos.TeleconsultaDTO;
import org.example.vitalance.interfaces.ITeleconsultaService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TeleconsultaService implements ITeleconsultaService {

    private final List<TeleconsultaDTO> teleconsultas = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<TeleconsultaDTO> listar() {
        return teleconsultas;
    }

    @Override
    public TeleconsultaDTO solicitar(TeleconsultaDTO dto) {
        // Simular que solo hay disponibilidad en los próximos 14 días
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime limite = ahora.plusDays(14);

        if (dto.getFechaHora().isAfter(limite)) {
            dto.setIdTeleconsulta(idGenerator.getAndIncrement());
            dto.setConfirmada(false);
            dto.setListaEspera(true); // no hay disponibilidad
            teleconsultas.add(dto);
            return dto;
        }

        // Caso positivo: confirmada
        dto.setIdTeleconsulta(idGenerator.getAndIncrement());
        dto.setConfirmada(true);
        dto.setListaEspera(false);
        teleconsultas.add(dto);

        return dto;
    }

    @Override
    public TeleconsultaDTO buscarPorId(Long id) {
        return teleconsultas.stream()
                .filter(t -> t.getIdTeleconsulta().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Teleconsulta con ID " + id + " no encontrada"));
    }

    @Override
    public void cancelar(Long id) {
        teleconsultas.removeIf(t -> t.getIdTeleconsulta().equals(id));
    }
}
