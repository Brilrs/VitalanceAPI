package org.example.vitalance.servicios;

import org.example.vitalance.dtos.PacienteDoctorDTO;
import org.example.vitalance.entidades.Doctor;
import org.example.vitalance.entidades.Paciente;
import org.example.vitalance.entidades.PacienteDoctor;
import org.example.vitalance.interfaces.IPacienteDoctorService;
import org.example.vitalance.repositorios.DoctorRepository;
import org.example.vitalance.repositorios.PacienteDoctorRepository;
import org.example.vitalance.repositorios.PacienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteDoctorService implements IPacienteDoctorService {
    @Autowired
    private PacienteDoctorRepository pacienteDoctorRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    //Mapear la entidad ->DTO y de DTO->Eentidad (solo cuando insertar/actualizas)
    //convertir entidad a DTO
    private PacienteDoctorDTO toDto(PacienteDoctor entity) {
        PacienteDoctorDTO dto=new PacienteDoctorDTO();
        dto.setIdPacienteDoctor(entity.getIdPacienteDoctor());
        dto.setActivoPacienteDoctor(entity.getActivoPacienteDoctor());
        dto.setFechaAsignacionPacienteDoctor(entity.getFechaAsignacionPacienteDoctor());
        dto.setIdPaciente(entity.getPaciente().getIdPaciente());
        dto.setIdDoctor(entity.getDoctor().getIdDoctor());
        return dto;
    }
    @Override
    public List<PacienteDoctorDTO>listar(){
        return pacienteDoctorRepository.findAll().
                stream().
                map(this::toDto) //ya no usamos ModelMapper
                .collect(Collectors.toList());
    }

    @Override
    public PacienteDoctorDTO insertar(PacienteDoctorDTO pdDto){
        //modelMapper intenta mapear idDoctor y idPaciente hacia los objetos Doctor y Paciente completos y eso genera error
        //PacienteDoctor pdEntidad=modelMapper.map(pdDto, PacienteDoctor.class);
        //mapeo manualmente sin usar ModelMapper para la entidad
        PacienteDoctor pdEntidad=new PacienteDoctor();
        pdEntidad.setActivoPacienteDoctor(pdDto.getActivoPacienteDoctor());
        pdEntidad.setFechaAsignacionPacienteDoctor(pdDto.getFechaAsignacionPacienteDoctor());


        //traer doctor desde la db
        Doctor doctor=doctorRepository.findById(pdDto.getIdDoctor())
                .orElseThrow(()-> new RuntimeException("Doctor no encontrado"));
        pdEntidad.setDoctor(doctor);
        //traer paciente real desde la bd
        Paciente paciente =pacienteRepository.findById(pdDto.getIdPaciente())
                .orElseThrow(()-> new RuntimeException("Paciente no encontrado"));
        pdEntidad.setPaciente(paciente);

        PacienteDoctor guardado=pacienteDoctorRepository.save(pdEntidad);
        return toDto(guardado); //devolvemos el DTO manual
    }
    @Override
    public PacienteDoctorDTO editar(PacienteDoctorDTO pdDto){
        return pacienteDoctorRepository.findById(pdDto.getIdPacienteDoctor())
                .map(existing-> {
                    //actualizar los campos que si pueden cambiar
                    existing.setActivoPacienteDoctor(pdDto.getActivoPacienteDoctor());
                    existing.setFechaAsignacionPacienteDoctor(pdDto.getFechaAsignacionPacienteDoctor());
                    //si cambian doctor o paciente:
                    Doctor doctor=doctorRepository.findById(pdDto.getIdDoctor())
                            .orElseThrow(()-> new RuntimeException("Doctor no encontrado"));
                    Paciente paciente =pacienteRepository.findById(pdDto.getIdPaciente())
                            .orElseThrow(()-> new RuntimeException("Paciente no encontrado"));
                    existing.setDoctor(doctor);
                    existing.setPaciente(paciente);

                    //PacienteDoctor pdEntidad = modelMapper.map(pdDto, PacienteDoctor.class);
                    PacienteDoctor guardado = pacienteDoctorRepository.save(existing);
                    return toDto(guardado);
                }).orElseThrow(()-> new RuntimeException("Registro  no encontrado"));
    }
    @Override
    public PacienteDoctorDTO buscarPorId(Long id){
        return pacienteDoctorRepository.findById(id)
                .map(this::toDto) //mapeo manual
        .orElseThrow(()-> new RuntimeException("Registro  no encontrado con ese ID"));
    }
    //eliminado fisico
    @Override
    public void eliminar(Long id){
        if(!pacienteDoctorRepository.existsById(id)){
            throw new RuntimeException("No se encontro el registro");
        }
        pacienteDoctorRepository.deleteById(id);
    }
}
