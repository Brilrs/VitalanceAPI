package org.example.vitalance.controllers;

import org.example.vitalance.Servicios.PacienteService;
import org.example.vitalance.dtos.PacienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/PacienteRestController")
public class PacienteRestController {

    @Autowired
    private PacienteService pacienteService;

    //ver
    @GetMapping("/obtenPacientes")
    public List<PacienteDTO> getPacientes(){
       return pacienteService.ObtenerPacientes();
    }

    //agregar

    @PostMapping("/AgregaPaciente")
    public PacienteDTO addPaciente(@RequestBody PacienteDTO pacienteDTO){
        return pacienteService.AgregarPaciente(pacienteDTO);
    }

    //eliminar
    @DeleteMapping("/EliminaPaciente/{id}")
    public String eliminarPaciente(@PathVariable int id){
        return pacienteService.EliminarPaciente(id);
    }


    //actualizar

@PutMapping("/ActualizaPaciente/{id}")
    public PacienteDTO ActualizaPaciente(@PathVariable int id, @RequestBody PacienteDTO pacienteDTO){
        return pacienteService.actualizarPaciente(pacienteDTO,id);
}

}
