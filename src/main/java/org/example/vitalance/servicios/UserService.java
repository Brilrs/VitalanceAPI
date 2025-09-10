package org.example.vitalance.servicios;

import org.example.vitalance.dtos.UserDTO;
import org.example.vitalance.entidades.User;
import org.example.vitalance.interfaces.IUserService;
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
    private ModelMapper modelMapper;

    @Override
    public List<UserDTO> listar(){
        return userRepository.findAll().stream().map(user->modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO insertar(UserDTO userDto){
        User userEntidad=modelMapper.map(userDto,User.class);
        User guardado=userRepository.save(userEntidad);
        return modelMapper.map(guardado,UserDTO.class);
    }

    @Override
    public UserDTO editar(UserDTO userDto){
        return userRepository.findById(userDto.getIdUser()).
                map(existing->{
                    User userEntidad=modelMapper.map(userDto,User.class);
                    User guardado=userRepository.save(userEntidad);
                    return modelMapper.map(guardado,UserDTO.class);
                }).orElseThrow(()->new RuntimeException("Role con ID:"+userDto.getIdUser()+" no encontrado"));
    }
    @Override
    public UserDTO buscarPorId(Long id){
        return userRepository.findById(id).map((element)->modelMapper.map(element,UserDTO.class))
                .orElseThrow(()->new RuntimeException("Usuario con ID:"+id+" no encontrado"));
    }

    @Override
    public void eliminar(Long id){
        if(!userRepository.existsById(id)){
            throw new RuntimeException("Role con id"+id+"no encontrado");
        }
        userRepository.deleteById(id);
    }

}
