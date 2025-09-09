package org.example.vitalance.Servicios;

import org.example.vitalance.Repositorios.PacienteRepository;
import org.example.vitalance.Repositorios.UsuarioRepository;
import org.example.vitalance.dtos.PacienteDTO;
import org.example.vitalance.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;
@Autowired
private UsuarioRepository usuarioRepository;


public List<PacienteDTO> ObtenerPacientes(){
    return pacienteRepository.findAll();
}



 public PacienteDTO AgregarPaciente(PacienteDTO pacienteDTO){
    UserDTO userDTO = null;
for( UserDTO u: usuarioRepository.findAll() ){
    if(u.getIdUser()==pacienteDTO.getUser().getIdUser()){
        userDTO = u;
    }
}
pacienteDTO.setUser(userDTO);
    return pacienteRepository.save(pacienteDTO);
 }







 public String EliminarPaciente(int id){
     pacienteRepository.deleteById(id);
    return "Eliminado el paciente con id: "+id;
 }

 public String actualizarPaciente(PacienteDTO pacienteDTO, int id){
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
