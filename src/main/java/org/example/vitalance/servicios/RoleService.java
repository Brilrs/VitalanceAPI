package org.example.vitalance.servicios;


import org.example.vitalance.dtos.RoleDTO;
import org.example.vitalance.entidades.Role;
import org.example.vitalance.interfaces.IRoleService;
import org.example.vitalance.repositorios.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
//implementa a interface
public class RoleService implements IRoleService {
    //llama al repositorio para acceder a la base de datos
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<RoleDTO> listar(){
        return roleRepository.findAll().stream().map(role->modelMapper.map(role,RoleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO insertar(RoleDTO roleDto) {
        Role roleEntidad=modelMapper.map(roleDto, Role.class);
        Role guardado=roleRepository.save(roleEntidad);
        return modelMapper.map(guardado,RoleDTO.class);
    }

    @Override
    public RoleDTO editar(RoleDTO roleDto) {
        return roleRepository.findById(roleDto.getIdRole()).map(existing->{
            Role roleEntidad=modelMapper.map(roleDto,Role.class);
            Role guardado=roleRepository.save(roleEntidad);
            return modelMapper.map(guardado,RoleDTO.class);
        }).orElseThrow(()->new RuntimeException("Role con ID "+roleDto.getIdRole()+" no encontrado"));
    }

    @Override
    public RoleDTO buscarPorId(Long id){
        return roleRepository.findById(id).map((element)->modelMapper.map(element,RoleDTO.class))
                .orElseThrow(()->new RuntimeException("Role con ID "+id+" no encontrado"));
    }

    @Override
    public void eliminar(Long id){
        if(!roleRepository.existsById(id)){
            throw new RuntimeException("Role con ID "+id+" no encontrado");
        }
        roleRepository.deleteById(id);
    }
}
