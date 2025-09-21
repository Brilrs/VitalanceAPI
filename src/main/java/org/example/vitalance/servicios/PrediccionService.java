package org.example.vitalance.servicios;

import org.example.vitalance.dtos.PrediccionDTO;
import org.example.vitalance.entidades.Prediccion;
import org.example.vitalance.interfaces.IPrediccionServicie;
import org.example.vitalance.repositorios.PrediccionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrediccionService implements IPrediccionServicie {
    @Autowired
    private PrediccionRepository prediccionRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GeminiService geminiService;

    @Override
    public List<PrediccionDTO> listar() {
        return prediccionRepository.findAll().stream()
                .map(p -> modelMapper.map(p, PrediccionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PrediccionDTO insertar(PrediccionDTO prediccionDto) {
        Prediccion prediccionEntidad = modelMapper.map(prediccionDto, Prediccion.class);

        try {
            // Llamada a Gemini con el prompt
            String textoGenerado = geminiService.generarTexto(prediccionEntidad.getPromptPrediccion());

            prediccionEntidad.setRespuestaTextoPrediccion(textoGenerado);
            prediccionEntidad.setTipoPrediccion("texto generado");
            prediccionEntidad.setProbabilidadPrediccion(Math.random()); // o cálculo real

        } catch (Exception e) {
            throw new RuntimeException("Error procesando respuesta de Gemini: " + e.getMessage(), e);
        }

        Prediccion guardado = prediccionRepository.save(prediccionEntidad);
        return modelMapper.map(guardado, PrediccionDTO.class);
    }

    @Override
    public PrediccionDTO editar(PrediccionDTO prediccionDto) {
        return prediccionRepository.findById(prediccionDto.getIdPrediccion()).map(existing->{
            Prediccion prediccionEntidad=modelMapper.map(prediccionDto, Prediccion.class);
            Prediccion guardado=prediccionRepository.save(prediccionEntidad);
            return modelMapper.map(guardado, PrediccionDTO.class);
        }).orElseThrow(()->new RuntimeException("Prediccion no encontrado"));
    }

    @Override
    public PrediccionDTO buscarPorId(Long id) {
        return prediccionRepository.findById(id).map((element)->modelMapper.map(element, PrediccionDTO.class))
                .orElseThrow(()->new RuntimeException("Prediccion no encontrado"));
    }

    @Override
    public void eliminar(Long id){
        if(!prediccionRepository.existsById(id)){
            throw new RuntimeException("Prediccion no encontrado");
        }
        prediccionRepository.deleteById(id);
    }
}
