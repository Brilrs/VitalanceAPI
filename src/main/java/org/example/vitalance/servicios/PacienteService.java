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
        return pacienteRepository.findAll().stream().map(Paciente->modelMapper.map(Paciente,PacienteDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PacienteDTO insertar(PacienteDTO pacienteDTO) { //debe existir un usuario para crear un paciente
       User x= userRepository.findById(pacienteDTO.getUser().getIdUser()).orElseThrow(() -> new RuntimeException("Usuario con ese id no encontrado"));
       Paciente a= modelMapper.map(pacienteDTO,Paciente.class);
       a.setUser(x);
       Paciente guardado= pacienteRepository.save(a);
       return modelMapper.map(guardado,PacienteDTO.class);
    }

    @Override
    public PacienteDTO editar(long IdPaciente, PacienteDTO pacienteDTO) {
        //localiza al paciente con ese id pasado
        Paciente x=  pacienteRepository.findById(IdPaciente).orElseThrow(() -> new RuntimeException("paciente con ese id no encontrado"));
        //LOCALIZA AL USUARIO DE ESE pacienteDTO pasado
        User y=userRepository.findById(pacienteDTO.getUser().getIdUser()).orElseThrow(() -> new RuntimeException("Usuario del paciente con ese id no encontrado"));


        //guarda los nuevos valores para cada atributo de el paciente encontrado
        x.setUser(y);
        x.setIdPaciente(IdPaciente);
        x.setNumeroHistoriaClinicaPaciente(pacienteDTO.getNumeroHistoriaClinicaPaciente());
        x.setTipoDiabetesPaciente(pacienteDTO.getTipoDiabetesPaciente());
        x.setFechaDiagnosticoPaciente(pacienteDTO.getFechaDiagnosticoPaciente());
        x.setEstaturaDiagnosticoPaciente(pacienteDTO.getEstaturaDiagnosticoPaciente());
        x.setPesoDiagnosticoPaciente(pacienteDTO.getPesoDiagnosticoPaciente());
        x.setGlucosaMinimaPaciente(pacienteDTO.getGlucosaMinimaPaciente());
        x.setGlucosaMaximaPaciente(pacienteDTO.getGlucosaMaximaPaciente());
        x.setFechaCreacionPaciente(pacienteDTO.getFechaCreacionPaciente());
      Paciente g= pacienteRepository.save(x);
        return modelMapper.map(g,PacienteDTO.class);
    }

    @Override
    public PacienteDTO buscarPorId(Long id) {
        return modelMapper.map(pacienteRepository.findById(id),PacienteDTO.class);
    }

    @Override
    public void eliminar(Long id) {
pacienteRepository.deleteById(id);
    }
}
