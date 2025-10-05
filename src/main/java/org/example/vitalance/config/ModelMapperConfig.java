package org.example.vitalance.config;

import org.example.vitalance.dtos.PacienteDTO;
import org.example.vitalance.dtos.UserDTO;
import org.example.vitalance.entidades.Paciente;
import org.example.vitalance.entidades.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    // Codigo del profesor:
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
/*
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Romper ciclo Paciente -> SecurityUser
        mapper.createTypeMap(PacienteDTO.class, Paciente.class,

                        mapper.getConfiguration().copy().setImplicitMappingEnabled(false))
                .addMappings(m -> m.skip(Paciente::setUser));

        // Romper ciclo SecurityUser -> Paciente
        mapper.createTypeMap(UserDTO.class, SecurityUser.class, mapper.getConfiguration().copy().setImplicitMappingEnabled(false))
                .addMappings(m -> m.skip(SecurityUser::setPaciente));

        return mapper;

        */


