package org.example.vitalance.Servicios;

import org.example.vitalance.Repositorios.PacienteRepository;
import org.example.vitalance.Repositorios.UserRepository;
import org.example.vitalance.dtos.PacienteDTO;
import org.example.vitalance.dtos.RoleDTO;
import org.example.vitalance.dtos.UserDTO;
import org.example.vitalance.entidades.Paciente;
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
    private ModelMapper modelMapper;

    @Override
    public List<PacienteDTO> ObtenerPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public PacienteDTO AgregarPaciente(PacienteDTO paciente) {

        return pacienteRepository.save(paciente);
    }

    @Override
    public String EliminarPaciente(int id) {
        pacienteRepository.deleteById(id);
        return "Eliminado el paciente con id: "+id;
    }

    @Override
    public String actualizarPaciente(PacienteDTO pacienteDTO, int id) {
        for(PacienteDTO paciente : pacienteRepository.findAll()){
            if(paciente.getId()==id){
                paciente.setId(paciente.getId());
                paciente.setNumeroHistoriaClinica(pacienteDTO.getNumeroHistoriaClinica());
                paciente.setTipoDiabetes(pacienteDTO.getTipoDiabetes());
                paciente.setFechaDiagnostico(pacienteDTO.getFechaDiagnostico());
                paciente.setEstatura(pacienteDTO.getEstatura());
                paciente.setPeso(pacienteDTO.getPeso());
                paciente.setGlucosaMinima(pacienteDTO.getGlucosaMinima());
                paciente.setGlucosaMaxima(pacienteDTO.getGlucosaMaxima());
                paciente.setFechaCreacion(paciente.getFechaCreacion());
                pacienteRepository.save(paciente);
            }
        }

        return "El paciente con id: "+id+" fue actualizado";
    }

}
