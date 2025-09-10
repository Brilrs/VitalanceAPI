package org.example.vitalance.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.vitalance.dtos.UserDTO;
import org.example.vitalance.servicios.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/apiUser")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/listarUser")
    public List<UserDTO> listarUser() {
        log.info("Listando users");
        return userService.listar(); //llama al metodo de intefaces
    }
    @PostMapping("/insertarUser")
    public ResponseEntity<UserDTO> insertarUser(@Valid @RequestBody UserDTO userDto) {
        log.info("Registrado usuario {}", userDto.getNombreUser());
        return ResponseEntity.ok(userService.insertar(userDto));
    }

    @PutMapping("/editarUser")
    public ResponseEntity<UserDTO>editarUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.editar(userDTO));
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<UserDTO>buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(userService.buscarPorId(id));
    }
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        userService.eliminar(id);
    }
}
