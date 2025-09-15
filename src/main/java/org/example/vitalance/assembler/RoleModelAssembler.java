package org.example.vitalance.assembler;

import org.example.vitalance.controllers.RoleController;
import org.example.vitalance.dtos.RoleDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RoleModelAssembler implements RepresentationModelAssembler<RoleDTO, EntityModel<RoleDTO>> {

    @Override
    public EntityModel<RoleDTO> toModel(RoleDTO dto) {
        return EntityModel.of(dto,
                // self: este recurso en particular
                linkTo(methodOn(RoleController.class).buscarPorId(dto.getIdRole())).withSelfRel(),
                // acción de edición
                linkTo(methodOn(RoleController.class).editarRol(dto)).withRel("update"),
                // acción de borrado
                linkTo(RoleController.class)
                        .slash(dto.getIdRole())
                        .withRel("delete"),
                // link para la colección
                linkTo(methodOn(RoleController.class).listaRol()).withRel("roles")
        );
    }
}
