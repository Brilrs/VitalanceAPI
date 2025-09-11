package org.example.vitalance.controllers;

import org.example.vitalance.Repositorios.MedicionesRepository;
import org.example.vitalance.Servicios.MedicionesService;
import org.example.vitalance.Servicios.PacienteService;
import org.example.vitalance.dtos.MedicionesDTO;
import org.example.vitalance.dtos.PacienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/MedicionesRestController")
public class MedicionesRestController {
    @Autowired
    private MedicionesService medicionesService;

    //ver
    @GetMapping("/obtenMediciones")
    public List<MedicionesDTO> getPacientes(){
        return medicionesService.ObtenerMediciones();
    }

    //agregar

    @PostMapping("/AgregaMedicion")
    public MedicionesDTO addMedicion(@RequestBody MedicionesDTO medicionesDTO){
        return medicionesService.AgregarMedicion(medicionesDTO);
    }

    //eliminar
    @DeleteMapping("/EliminaMedicion/{id}")
    public String eliminarPaciente(@PathVariable int id){
        return medicionesService.EliminarMedicion(id);
    }


    //actualizar

    @PutMapping("/ActualizaMedicion/{id}")
    public MedicionesDTO ActualizaPaciente(@PathVariable int id, @RequestBody MedicionesDTO medicionesDTO){
        return medicionesService.actualizarMedicion(medicionesDTO,id);
    }
}
