package org.example.vitalance.servicios;

import org.example.vitalance.dtos.UmbralDTO;
import org.example.vitalance.entidades.Doctor;
import org.example.vitalance.entidades.Umbral;
import org.example.vitalance.interfaces.IUmbralService;
import org.example.vitalance.repositorios.DoctorRepository;
import org.example.vitalance.repositorios.UmbralRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmbralService implements IUmbralService {
    @Autowired private UmbralRepository umbralRepository;
    @Autowired private DoctorRepository doctorRepository;
    @Autowired private ModelMapper modelMapper;

    private void validarRango(UmbralDTO dto){
        if (dto.getMinimo()==null && dto.getMaximo()==null)
            throw new RuntimeException("Debe definir mínimo o máximo.");
        if (dto.getMinimo()!=null && dto.getMaximo()!=null && dto.getMinimo()>dto.getMaximo())
            throw new RuntimeException("Valores incoherentes: mínimo > máximo.");
    }

    @Override
    public UmbralDTO crear(UmbralDTO dto) {
        validarRango(dto);
        Doctor doc = doctorRepository.findById(dto.getDoctor().getIdDoctor())
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));
        Umbral entity = modelMapper.map(dto, Umbral.class);
        entity.setDoctor(doc);
        entity.setActivo(true);
        return modelMapper.map(umbralRepository.save(entity), UmbralDTO.class);
    }

    @Override
    public UmbralDTO actualizar(UmbralDTO dto) {
        validarRango(dto);
        Umbral exist = umbralRepository.findById(dto.getIdUmbral())
                .orElseThrow(() -> new RuntimeException("Umbral no encontrado"));
        exist.setTipoIndicador(dto.getTipoIndicador());
        exist.setMinimo(dto.getMinimo());
        exist.setMaximo(dto.getMaximo());
        exist.setUnidad(dto.getUnidad());
        exist.setActivo(dto.getActivo()!=null ? dto.getActivo() : exist.getActivo());
        return modelMapper.map(umbralRepository.save(exist), UmbralDTO.class);
    }

    @Override
    public void activar(Long id, boolean activo) {
        Umbral u = umbralRepository.findById(id).orElseThrow(() -> new RuntimeException("Umbral no encontrado"));
        u.setActivo(activo);
        umbralRepository.save(u);
    }

    @Override
    public List<UmbralDTO> listarPorDoctor(Long idDoctor) {
        Doctor doc = doctorRepository.findById(idDoctor)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));
        return umbralRepository.findByDoctorAndActivoTrue(doc).stream()
                .map(e -> modelMapper.map(e, UmbralDTO.class))
                .collect(Collectors.toList());
    }
}
