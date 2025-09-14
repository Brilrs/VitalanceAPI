package org.example.vitalance.controllers;

import org.example.vitalance.dtos.PacienteDTO;
import org.example.vitalance.servicios.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiPaciente")
public class PacienteController {


   @Autowired
    private PacienteService pacienteService;



   @GetMapping("/listarPacientes")
    public List<PacienteDTO> listar(){
       return pacienteService.listar();
   }


   @PostMapping("/insertarPaciente")
    public PacienteDTO insertar(@RequestBody PacienteDTO pacienteDTO){
       return pacienteService.insertar(pacienteDTO);
   }
@PutMapping("/actualizaPaciente/{id}")
    public PacienteDTO actualizar(@RequestBody PacienteDTO pacienteDTO, @PathVariable Long id){
       return pacienteService.editar(id,pacienteDTO);
}
@GetMapping("/BuscaPacientePorID/{id}")
    public PacienteDTO buscaPacientePorID(@PathVariable Long id){
       return pacienteService.buscarPorId(id);
}
@DeleteMapping("/Elimina/{id}")
    public void eliminar(@PathVariable Long id){
       pacienteService.eliminar(id);
}


}
