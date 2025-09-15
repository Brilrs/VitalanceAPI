package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.assembler.RoleModelAssembler;
import org.example.vitalance.dtos.RoleDTO;
import org.example.vitalance.servicios.RoleService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Slf4j
@RestController
@RequestMapping("/apiRol")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final RoleModelAssembler assembler;

    @GetMapping("/listarRol")
    public CollectionModel<EntityModel<RoleDTO>> listaRol() {
        log.info("Listando roles");
        List<EntityModel<RoleDTO>> content = roleService.listar()
                .stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(
                content,
                linkTo(methodOn(RoleController.class).listaRol()).withSelfRel(),
                linkTo(methodOn(RoleController.class).insertarRol(null)).withRel("create")
        );
    }

    @PostMapping("/insertarRol")
    public ResponseEntity<EntityModel<RoleDTO>> insertarRol(@Valid @RequestBody RoleDTO roleDto) {
        RoleDTO saved = roleService.insertar(roleDto);
        return ResponseEntity
                .created(linkTo(methodOn(RoleController.class).buscarPorId(saved.getIdRole())).toUri())
                .body(assembler.toModel(saved));
    }

    @PutMapping("/editarRol")
    public ResponseEntity<EntityModel<RoleDTO>> editarRol(@RequestBody RoleDTO roleDTO) {
        RoleDTO updated = roleService.editar(roleDTO);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @GetMapping("/ver/{id}")
    public EntityModel<RoleDTO> buscarPorId(@PathVariable Long id) {
        return assembler.toModel(roleService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        roleService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}