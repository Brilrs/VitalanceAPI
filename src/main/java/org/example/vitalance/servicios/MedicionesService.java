package org.example.vitalance.servicios;

import org.example.vitalance.dtos.MedicionesDTO;
import org.example.vitalance.entidades.Mediciones;
import org.example.vitalance.entidades.Paciente;
import org.example.vitalance.entidades.User;
import org.example.vitalance.interfaces.IMedicionesService;
import org.example.vitalance.repositorios.MedicionesRepository;
import org.example.vitalance.repositorios.PacienteRepository;
import org.example.vitalance.repositorios.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicionesService implements IMedicionesService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicionesRepository medicionesRepository;

    @Override
    public List<MedicionesDTO>listar(){
        return medicionesRepository.findAll()
                .stream()
                .map(m->modelMapper.map(m, MedicionesDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MedicionesDTO insertar(MedicionesDTO medicionesDto){
        Mediciones medicionesEntidad=modelMapper.map(medicionesDto, Mediciones.class);
        //traer el paciente real desde la BD(dueño de la medicion)
        Paciente paciente=pacienteRepository.findById(medicionesDto.getPaciente().getIdPaciente())
                .orElseThrow(()->new RuntimeException("No se encontro el paciente"));
        medicionesEntidad.setPaciente(paciente);
        //Traer el usuario real desde la BD(autor que registro la medicion)
        User autor = userRepository.findById(medicionesDto.getCreatedBy().getIdUser())
                .orElseThrow(() -> new RuntimeException("No se encontró el autor"));
        medicionesEntidad.setCreatedBy(autor);

        Mediciones guardado=medicionesRepository.save(medicionesEntidad);
        return modelMapper.map(guardado,MedicionesDTO.class);

    }

    @Override
    public MedicionesDTO editar(MedicionesDTO medicionesDto){
        return medicionesRepository.findById(medicionesDto.getIdMedicion()).
          map(existing->{
              Mediciones medicionesEntidad=modelMapper.map(medicionesDto,Mediciones.class);
              //Aseguramos que paciente y createdBy se refresquen desde la BD
              Mediciones guardado=medicionesRepository.save(medicionesEntidad);
              return modelMapper.map(guardado,MedicionesDTO.class);
          }).orElseThrow(()->new RuntimeException("No se encontro el medicion"));
    }

    @Override
    public MedicionesDTO buscarPorId(Long id){
        return medicionesRepository.findById(id).map((element)->modelMapper.map(element,MedicionesDTO.class))
        .orElseThrow(()->new RuntimeException("Medicion no encontrado"));
    }

    @Override
    public void eliminar(Long id){
        if(!medicionesRepository.existsById(id)){
            throw new RuntimeException("No se encontro el medicion");
        }
        medicionesRepository.deleteById(id);
    }

}
