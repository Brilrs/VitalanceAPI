package org.example.vitalance.Servicios;

import org.example.vitalance.Repositorios.PacienteRepository;
import org.example.vitalance.Repositorios.UserRepository;
import org.example.vitalance.dtos.PacienteDTO;
import org.example.vitalance.dtos.RoleDTO;
import org.example.vitalance.dtos.UserDTO;
import org.example.vitalance.entidades.Paciente;
import org.example.vitalance.entidades.Role;
import org.example.vitalance.entidades.User;
import org.example.vitalance.interfaces.IPacienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService implements IPacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PacienteDTO> ObtenerPacientes() {
        return pacienteRepository.findAll().stream().map(paciente->modelMapper.map(paciente,PacienteDTO.class))
                .collect(Collectors.toList());


    }

    @Override
    public PacienteDTO AgregarPaciente(PacienteDTO pacienteDTO) {//Agregar nuevo paciente siempre que tenga el id de un usuario para hacerlo
        // Buscar usuario existente
        User usuario = userRepository.findById(pacienteDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Paciente paciente = new Paciente();
        paciente.setNumeroHistoriaClinica(pacienteDTO.getNumeroHistoriaClinica());
        paciente.setTipoDiabetes(pacienteDTO.getTipoDiabetes());
        paciente.setFechaDiagnostico(pacienteDTO.getFechaDiagnostico());
        paciente.setFechaCreacion(pacienteDTO.getFechaCreacion());
        paciente.setEstatura(pacienteDTO.getEstatura());
        paciente.setPeso(pacienteDTO.getPeso());
        paciente.setGlucosaMinima(pacienteDTO.getGlucosaMinima());
        paciente.setGlucosaMaxima(pacienteDTO.getGlucosaMaxima());

        // Asociar el usuario encontrado
        paciente.setUser(usuario);

        // Guardar en la base de datos
        Paciente pacienteGuardado = pacienteRepository.save(paciente);

        // Construir el DTO de respuesta manualmente
        PacienteDTO dto = new PacienteDTO();
        dto.setId(pacienteGuardado.getId());
        dto.setNumeroHistoriaClinica(pacienteGuardado.getNumeroHistoriaClinica());
        dto.setTipoDiabetes(pacienteGuardado.getTipoDiabetes());
        dto.setFechaDiagnostico(pacienteGuardado.getFechaDiagnostico());
        dto.setFechaCreacion(pacienteGuardado.getFechaCreacion());
        dto.setEstatura(pacienteGuardado.getEstatura());
        dto.setPeso(pacienteGuardado.getPeso());
        dto.setGlucosaMinima(pacienteGuardado.getGlucosaMinima());
        dto.setGlucosaMaxima(pacienteGuardado.getGlucosaMaxima());


        dto.setUserId(usuario.getIdUser());

        return dto;
    }

    @Override
    public String EliminarPaciente(int id) {
        if(!pacienteRepository.existsById(id)){
            throw new RuntimeException("paciente con id"+id+"no encontrado");
        }
       pacienteRepository.deleteById(id);
        return "Paciente Eliminado";
    }

    @Override
    public PacienteDTO actualizarPaciente(PacienteDTO pacienteDTO, int id) {
        return pacienteRepository.findById(pacienteDTO.getId()).
                map(existing->{
                    Paciente userEntidad=modelMapper.map(pacienteDTO,Paciente.class);
                    Paciente guardado=pacienteRepository.save(userEntidad);
                    return modelMapper.map(guardado,PacienteDTO.class);
                }).orElseThrow(()->new RuntimeException("paciente con ID:"+pacienteDTO.getId()+" no encontrado"));
    }

}
