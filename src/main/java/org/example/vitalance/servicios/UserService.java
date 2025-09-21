package org.example.vitalance.servicios;

import org.example.vitalance.dtos.UserDTO;
import org.example.vitalance.entidades.Role;
import org.example.vitalance.entidades.User;
import org.example.vitalance.interfaces.IUserService;
import org.example.vitalance.repositorios.PacienteRepository;
import org.example.vitalance.repositorios.RoleRepository;
import org.example.vitalance.repositorios.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public List<UserDTO> listar(){
        return userRepository.findByActivoUserTrue().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        /*
        return userRepository.findAll().stream()
                .map(user -> modelMapper
                        .map(user, UserDTO.class))
                .collect(Collectors.toList());*/
    }
    @Override
    public UserDTO insertar(UserDTO userDto){
        User userEntidad=modelMapper.map(userDto, User.class);
        //traer el role real desde la BD
        Role role=roleRepository.findById(userDto.getRole().getIdRole())
                .orElseThrow(()-> new RuntimeException("No se encontro el role"));
        userEntidad.setRole(role);

        User guardado=userRepository.save(userEntidad);
        //org.example.vitalance.entidades.Users userEntidad=modelMapper.map(userDto, org.example.vitalance.entidades.Users.class);
        //org.example.vitalance.entidades.Users guardado=userRepository.save(userEntidad);
        return modelMapper.map(guardado, UserDTO.class);
    }

    @Override
    public UserDTO editar(UserDTO userDto){
        return userRepository.findById(userDto.getIdUser()).
                map(existing->{
                    User userEntidad=modelMapper.map(userDto, User.class);
                    User guardado=userRepository.save(userEntidad);
                    return modelMapper.map(guardado, UserDTO.class);
                }).orElseThrow(()->new RuntimeException("Usuario con ID:"+userDto.getIdUser()+" no encontrado"));
    }
    @Override
    public UserDTO buscarPorId(Long id){
        return userRepository.findById(id).map((element)->modelMapper.map(element, UserDTO.class))
                .orElseThrow(()->new RuntimeException("Usuario con ID:"+id+" no encontrado"));
    }

    //Borrado FISICO(delete real)->si quieres que desaparezca de la BD
    //tienes que borrar en orden todo lo que dependa de ese user->se eliminaria el historial de medicios(y esto no es correcto en un sistema de salud
    //Entonces, se usa borrado LOGICO
    @Override
    public void eliminar(Long id){
        //eliminar usuario sin tocar al role
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("Usuario con ID:"+id+" no encontrado"));

        user.setActivoUser(false);//solo se desactiva al usuario
        userRepository.save(user);
        //modificar el listar para que solo salgan los activos
    }

}
