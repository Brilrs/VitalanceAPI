package org.example.vitalance.servicios;

import org.example.vitalance.dtos.MedicionesDTO;
import org.example.vitalance.entidades.Mediciones;
import org.example.vitalance.entidades.Paciente;
import org.example.vitalance.entidades.User;
import org.example.vitalance.interfaces.IMediciones;
import org.example.vitalance.repositorios.MedicionesRepository;
import org.example.vitalance.repositorios.PacienteRepository;
import org.example.vitalance.repositorios.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicionesService implements IMediciones {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicionesRepository medicionesRepository;

    @Override
    public MedicionesDTO AgregarMedicion(MedicionesDTO medicionesDTO) {
        //encuentro el paciente con el id llegado en medicionesDTO
       Paciente x= pacienteRepository.findById(medicionesDTO.getIdPaciente()).orElseThrow(() -> new RuntimeException("paciente con ese id no encontrado"));
       //encuentro el user con el idUser de ese paciente
        User y= userRepository.findById(x.getUser().getIdUser()).orElseThrow(() -> new RuntimeException("user con ese id no encontrado"));





       //transformo de dto a entity
        Mediciones a= modelMapper.map(medicionesDTO, Mediciones.class);
        a.setPaciente(x); //a la medicion le asigno el paciente
        a.setUser(y);//a la medicion le asigno el usuario de ese paciente
        x.getMediciones().add(a);//Agrego la medicion para el paciente y para el usuario que tiene ese paciente
        y.getMediciones().add(a);

        pacienteRepository.save(x);
        userRepository.save(y);
        Mediciones guardado= medicionesRepository.save(a);
       return modelMapper.map(guardado, MedicionesDTO.class);
    }

    @Override
    public List<MedicionesDTO> ListarMediciones() {
        return medicionesRepository.findAll().stream().map(Mediciones->modelMapper.map(Mediciones,MedicionesDTO.class)).collect(Collectors.toList());
    }

    @Override
    public String EliminarMediciones(Long idMedicion) {
        medicionesRepository.deleteById(idMedicion);
        return "Medicion eliminada";
    }

    @Override
    public MedicionesDTO ActualizarMediciones(Long idMedicion, MedicionesDTO medicionesDTO) {

        //encontramos la medicion
        Mediciones x= medicionesRepository.findById(idMedicion).orElseThrow(() -> new RuntimeException("medicion no encontrado"));
        //encontramos al paciente y de ese paciente, el usuario enlazado
        Paciente y= pacienteRepository.findById(x.getPaciente().getIdPaciente()).orElseThrow(() -> new RuntimeException("paciente con ese id no encontrado"));
        User z= userRepository.findById(y.getUser().getIdUser()).orElseThrow(() -> new RuntimeException("user no encontrado"));


        x.setIdMedicion(idMedicion);
        x.setTipoMedicion(medicionesDTO.getTipoMedicion());
        x.setValorMedicion(medicionesDTO.getValorMedicion());
        x.setUnidadMedicion(medicionesDTO.getUnidadMedicion());
        x.setFechaMedicicion(medicionesDTO.getFechaMedicicion());
        x.setNotaMedicion(medicionesDTO.getNotaMedicion());
        x.setPaciente(y);
        x.setUser(z);

   Mediciones guardado= medicionesRepository.save(x);


        return modelMapper.map(guardado, MedicionesDTO.class);
    }

    @Override
    public List<MedicionesDTO> ListarMedicionesPaciente(Long idPaciente) {
        Paciente x= pacienteRepository.findById(idPaciente).orElseThrow(() -> new RuntimeException("paciente no encontrado"));
      return x.getMediciones().stream().map(Mediciones->modelMapper.map(Mediciones,MedicionesDTO.class)).collect(Collectors.toList());
    }
}
