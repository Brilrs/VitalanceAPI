package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.RoleDTO;
import org.example.vitalance.entidades.Role;
import org.example.vitalance.servicios.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/apiRol")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    @GetMapping("/listarRol")
    public List<RoleDTO> listaRol(){
        log.info("Lista de roles");
        return roleService.listar(); //llama al metodo de interface
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    @PostMapping("/insertarRol")
    public ResponseEntity<RoleDTO> insertarRol(@Valid @RequestBody RoleDTO roleDto){
        log.info("Registrando rol {}",roleDto.getNombreRole());
        return ResponseEntity.ok(roleService.insertar(roleDto));
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    @PutMapping("/editarRol")
    public ResponseEntity<RoleDTO>editarRol(@RequestBody RoleDTO roleDTO){
        return ResponseEntity.ok(roleService.editar(roleDTO));
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    @GetMapping("/ver/{id}")
    public ResponseEntity<RoleDTO>buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(roleService.buscarPorId(id));
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        roleService.eliminar(id);
    }
}
