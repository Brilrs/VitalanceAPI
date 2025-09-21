package org.example.vitalance.servicios;

import org.example.vitalance.dtos.PacienteDTO;
import org.example.vitalance.entidades.Paciente;
import org.example.vitalance.entidades.User;
import org.example.vitalance.interfaces.IPacienteService;
import org.example.vitalance.repositorios.DoctorRepository;
import org.example.vitalance.repositorios.PacienteRepository;
import org.example.vitalance.repositorios.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService implements IPacienteService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
@Autowired
private PacienteRepository pacienteRepository;

    @Override
    public List<PacienteDTO> listar() {
        return pacienteRepository.findByActivoPacienteTrue().stream().map(p -> modelMapper.map(p, PacienteDTO.class)).collect(Collectors.toList());
        /*
        return pacienteRepository.findAll().stream().map(paciente->modelMapper.map(paciente,PacienteDTO.class)).collect(Collectors.toList());*/
    }

    @Override
    public PacienteDTO insertar(PacienteDTO pacienteDto) { //debe existir un usuario para crear un paciente
       Paciente pacienteEntidad=modelMapper.map(pacienteDto, Paciente.class);
       //traer el user real desde la BD
        User user=userRepository.findById(pacienteDto.getUser().getIdUser())
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
        pacienteEntidad.setUser(user);

        Paciente guardado=pacienteRepository.save(pacienteEntidad);
        return modelMapper.map(guardado,PacienteDTO.class);
    }

    @Override
    public PacienteDTO editar( PacienteDTO pacienteDto) {
        return pacienteRepository.findById(pacienteDto.getIdPaciente()).
                map(existing -> {
                    Paciente pacienteEntidad = modelMapper.map(pacienteDto, Paciente.class);
                    Paciente guardado = pacienteRepository.save(pacienteEntidad);
                    return modelMapper.map(guardado, PacienteDTO.class);
                }).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    @Override
    public PacienteDTO buscarPorId(Long id) {
        return pacienteRepository.findById(id).map((element)->modelMapper.map(element,PacienteDTO.class)).orElseThrow(()->new RuntimeException("Paciente no encontrado"));
    }

    @Override
    public void eliminar(Long id) {
        Paciente paciente=pacienteRepository.findById(id).orElseThrow(()->new RuntimeException("Paciente no encontrado"));
        //borrado logico
        paciente.setActivoPaciente(false);
        pacienteRepository.save(paciente);

        /*
        Paciente paciente=pacienteRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Paciente no encontrado"));
        System.out.println("eliminando paciente"+paciente.getIdPaciente());
        paciente.setUser(null); //solo si quieres romper la FK antes de eliminar
        pacienteRepository.save(paciente);//guardar
        //ahora si eliminar
        pacienteRepository.delete(paciente);*/
    }
}
