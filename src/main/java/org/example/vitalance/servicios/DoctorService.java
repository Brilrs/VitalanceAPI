package org.example.vitalance.servicios;

import org.example.vitalance.dtos.DoctorDTO;
import org.example.vitalance.entidades.Doctor;
import org.example.vitalance.entidades.User;
import org.example.vitalance.interfaces.IDoctorService;
import org.example.vitalance.repositorios.DoctorRepository;
import org.example.vitalance.repositorios.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService implements IDoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<DoctorDTO>listar(){
        return doctorRepository.findByActivoDoctorTrue().stream().map(doctor -> modelMapper.map(doctor, DoctorDTO.class)).collect(Collectors.toList());
        /*
        return doctorRepository.findAll().stream().map(doctor -> modelMapper.map(doctor, DoctorDTO.class)).collect(Collectors.toList());*/
    }
    @Override
    public DoctorDTO insertar(DoctorDTO doctorDto){
        Doctor doctorEntidad=modelMapper.map(doctorDto, Doctor.class);
        Doctor guardado=doctorRepository.save(doctorEntidad);

        //traer user desde la base de datos
        User user =userRepository.findById(doctorDto.getUser().getIdUser())
                .orElseThrow(()->new RuntimeException("No se encontro el user"));
        doctorEntidad.setUser(user);

        return modelMapper.map(guardado,DoctorDTO.class);
    }
    @Override
    public DoctorDTO editar(DoctorDTO doctorDto){
        return doctorRepository.findById(doctorDto.getIdDoctor()).
                map(existing->{
                    Doctor doctorEntidad=modelMapper.map(doctorDto, Doctor.class);
                    Doctor guardado=doctorRepository.save(doctorEntidad);
                    return modelMapper.map(guardado,DoctorDTO.class);

                }).orElseThrow(()->new RuntimeException("Doctor con id: "+doctorDto.getIdDoctor()+" no encontrado"));
    }
    @Override
    public DoctorDTO buscarPorId(Long id){
        return doctorRepository.findById(id).map((element)->modelMapper.map(element,DoctorDTO.class))
                .orElseThrow(()->new RuntimeException("Doctor con Id: "+id+" no encontrado"));
    }
    @Override
    public void eliminar(Long id){
        Doctor doctor=doctorRepository.findById(id).orElseThrow(()->new RuntimeException("Doctor con id: "+id+" no encontrado"));
        //Borrado Logico - user queda intacto
        doctor.setActivoDoctor(false);
        doctorRepository.save(doctor);

        /*
        if(!doctorRepository.existsById(id)){
            throw new RuntimeException("Doctor con id: "+id+" no encontrado");
        }
        doctorRepository.deleteById(id);
        */
    }
}
