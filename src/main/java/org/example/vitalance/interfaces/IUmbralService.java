package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.UmbralDTO;
import java.util.List;

public interface IUmbralService {
    UmbralDTO crear(UmbralDTO dto);
    UmbralDTO actualizar(UmbralDTO dto);
    void activar(Long id, boolean activo);
    List<UmbralDTO> listarPorDoctor(Long idDoctor);
}
