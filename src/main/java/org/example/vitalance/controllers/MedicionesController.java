package org.example.vitalance.controllers;

import org.example.vitalance.dtos.MedicionesDTO;
import org.example.vitalance.servicios.MedicionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiMediciones")
public class MedicionesController {
@Autowired
private MedicionesService medicionesService;
@PostMapping("/AgregaMedicion")
    public MedicionesDTO AgregaMedicion(@RequestBody MedicionesDTO medicionesDTO){
    return medicionesService.AgregarMedicion(medicionesDTO);
}
@GetMapping("/ListaMediciones")
    public List<MedicionesDTO> ListaMediciones(){
    return medicionesService.ListarMediciones();
}

@DeleteMapping("/EliminaMedicion/{id}")
    public String EliminarMedicion(@PathVariable Long id){
    return medicionesService.EliminarMediciones(id);
}


@PutMapping("/ActualizarMedicion/{id}")
    public MedicionesDTO ActualizarMedicion(@PathVariable Long id, @RequestBody MedicionesDTO medicionesDTO){
    return medicionesService.ActualizarMediciones(id,medicionesDTO);
}
@GetMapping("/OtenerMedicionesDePaciente/{id}")
    public List<MedicionesDTO> ListarMedicionesDePaciente(@PathVariable Long id){
    return medicionesService.ListarMedicionesPaciente(id);
}




}
