package org.example.vitalance.servicios;

import org.example.vitalance.dtos.MedicionesDTO;
import org.example.vitalance.entidades.Mediciones;
import org.example.vitalance.entidades.Paciente;
import org.example.vitalance.entidades.User;
import org.example.vitalance.interfaces.IMedicionesService;
import org.example.vitalance.repositorios.MedicionesRepository;
import org.example.vitalance.repositorios.PacienteRepository;
import org.example.vitalance.repositorios.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.vitalance.entidades.*;
import org.example.vitalance.repositorios.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicionesService implements IMedicionesService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicionesRepository medicionesRepository;
    @Autowired
    private UmbralRepository umbralRepository;
    @Autowired
    private PacienteDoctorRepository pacienteDoctorRepository;
    @Autowired
    private AlertaRepository alertaRepository;
    @Autowired
    private DoctorRepository doctorRepository;


    @Override
    public List<MedicionesDTO>listar(){
        return medicionesRepository.findAll()
                .stream()
                .map(m->modelMapper.map(m, MedicionesDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MedicionesDTO insertar(MedicionesDTO medicionesDTO) {
        // 1) Persistir la medición
        Mediciones entidad = modelMapper.map(medicionesDTO, Mediciones.class);

        // asegurar referencias a Paciente y User (IDs vienen dentro del DTO)
        Paciente paciente = pacienteRepository.findById(medicionesDTO.getPaciente().getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        User autor = userRepository.findById(medicionesDTO.getCreatedBy().getIdUser())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        entidad.setPaciente(paciente);
        entidad.setCreatedBy(autor);

        Mediciones guardada = medicionesRepository.save(entidad);

        // 2) Buscar los doctores asignados al paciente y sus umbrales activos del tipo
        List<PacienteDoctor> vinculos = pacienteDoctorRepository.findAll().stream()
                .filter(v -> v.getPaciente().getIdPaciente().equals(paciente.getIdPaciente()))
                .filter(v -> Boolean.TRUE.equals(v.getActivoPacienteDoctor()))
                .toList();

        for (PacienteDoctor vinc : vinculos) {
            Doctor doc = vinc.getDoctor();
            List<Umbral> umbrales = umbralRepository
                    .findByDoctorAndTipoIndicadorIgnoreCaseAndActivoTrue(doc, guardada.getTipoMedicion());
            for (Umbral u : umbrales) {
                boolean supera = false;
                if (u.getMinimo()!=null && guardada.getValorMedicion() < u.getMinimo()) supera = true;
                if (u.getMaximo()!=null && guardada.getValorMedicion() > u.getMaximo()) supera = true;

                if (supera) {
                    Alerta alerta = new Alerta();
                    alerta.setTipoIndicador(guardada.getTipoMedicion());
                    alerta.setValor(guardada.getValorMedicion());
                    alerta.setUnidad(guardada.getUnidadMedicion());
                    alerta.setSeveridad("CRITICA");       // por defecto cuando supera umbral
                    alerta.setEstado("PENDIENTE");
                    alerta.setPaciente(paciente);
                    alerta.setDoctor(doc);
                    alerta.setMedicion(guardada);
                    alertaRepository.save(alerta);
                }
            }
        }
        return modelMapper.map(guardada, MedicionesDTO.class);
    }

    @Override
    public MedicionesDTO editar(MedicionesDTO medicionesDto){
        return medicionesRepository.findById(medicionesDto.getIdMedicion()).
          map(existing->{
              Mediciones medicionesEntidad=modelMapper.map(medicionesDto,Mediciones.class);
              //Aseguramos que paciente y createdBy se refresquen desde la BD
              Mediciones guardado=medicionesRepository.save(medicionesEntidad);
              return modelMapper.map(guardado,MedicionesDTO.class);
          }).orElseThrow(()->new RuntimeException("No se encontro el medicion"));
    }

    @Override
    public MedicionesDTO buscarPorId(Long id){
        return medicionesRepository.findById(id).map((element)->modelMapper.map(element,MedicionesDTO.class))
        .orElseThrow(()->new RuntimeException("Medicion no encontrado"));
    }

    @Override
    public void eliminar(Long id){
        if(!medicionesRepository.existsById(id)){
            throw new RuntimeException("No se encontro el medicion");
        }
        medicionesRepository.deleteById(id);
    }

}
