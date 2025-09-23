package org.example.vitalance.servicios;

import org.example.vitalance.dtos.AlertaGlucosaDTO;
import org.example.vitalance.dtos.PacienteDTO;
import org.example.vitalance.dtos.PacienteNivelesDeGlucosaDTO;
import org.example.vitalance.entidades.Mediciones;
import org.example.vitalance.entidades.Paciente;
import org.example.vitalance.entidades.Recordatorio;
import org.example.vitalance.entidades.User;
import org.example.vitalance.interfaces.IPacienteService;
import org.example.vitalance.repositorios.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService implements IPacienteService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecordatorioRepository recordatorioRepository;

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicionesRepository medicionesRepository;
    @Override
    public List<PacienteDTO> listar() {
        return pacienteRepository.findByActivoPacienteTrue().stream().map(p -> modelMapper.map(p, PacienteDTO.class)).collect(Collectors.toList());
        /*
        return pacienteRepository.findAll().stream().map(paciente->modelMapper.map(paciente,PacienteDTO.class)).collect(Collectors.toList());*/
    }

    @Override
    public PacienteDTO insertar(PacienteDTO pacienteDto) { //debe existir un usuario para crear un paciente
        Paciente pacienteEntidad = modelMapper.map(pacienteDto, Paciente.class);
        //traer el user real desde la BD
        User user = userRepository.findById(pacienteDto.getUser().getIdUser())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        pacienteEntidad.setUser(user);

        Paciente guardado = pacienteRepository.save(pacienteEntidad);
        return modelMapper.map(guardado, PacienteDTO.class);
    }

    @Override
    public PacienteDTO editar(PacienteDTO pacienteDto) {
        return pacienteRepository.findById(pacienteDto.getIdPaciente()).
                map(existing -> {
                    Paciente pacienteEntidad = modelMapper.map(pacienteDto, Paciente.class);
                    Paciente guardado = pacienteRepository.save(pacienteEntidad);
                    return modelMapper.map(guardado, PacienteDTO.class);
                }).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    @Override
    public PacienteDTO buscarPorId(Long id) {
        return pacienteRepository.findById(id).map((element) -> modelMapper.map(element, PacienteDTO.class)).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    @Override
    public void eliminar(Long id) {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        //borrado logico
        paciente.setActivoPaciente(false);
        pacienteRepository.save(paciente);

        /*
        Paciente paciente=pacienteRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Paciente no encontrado"));
        System.out.println("eliminando paciente"+paciente.getIdPaciente());
        paciente.setUser(null); //solo si quieres romper la FK antes de eliminar
        pacienteRepository.save(paciente);//guardar
        //ahora si eliminar
        pacienteRepository.delete(paciente);*/
    }

    @Override
    public AlertaGlucosaDTO procesarAlerta(AlertaGlucosaDTO dto) {
        //buscar paciente
        Paciente paciente = pacienteRepository.findById(dto.getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        LocalDateTime ahora = LocalDateTime.now();

        // Validar bloqueo de 30 min (si ya hubo una alerta reciente)
        boolean existeAlertaReciente = recordatorioRepository.existsByPaciente_IdPacienteAndFechaCreacionRecordatorioAfter(
                dto.getIdPaciente(),
                ahora.minusMinutes(30)
        );
        if (existeAlertaReciente) {
            dto.setNotificado(false);
            dto.setRecomendacion(null);
            dto.setNivelAlerta("sin alerta (bloqueado por regla de 30min)");
            dto.setFechaNotificacion(ahora);
            return dto;
        }

        // Determinar tipo de alerta
        if (dto.getValorGlucosa() <= 70) {
            dto.setNivelAlerta("Hipoglucemia");
            dto.setRecomendacion("Tomar azúcar de acción rápida");
        } else if (dto.getValorGlucosa() >= 250) {
            dto.setNivelAlerta("Hiperglucemia");
            dto.setRecomendacion("Consultar con el médico lo antes posible");
        } else {
            dto.setNivelAlerta("Normal");
            dto.setRecomendacion("Mantener hábitos saludables");
        }

        //Guarda en el Recordatorio en la BD para ver la logica de los 30 minutos
        Recordatorio recordatorio = new Recordatorio();

        recordatorio.setPaciente(paciente);
        recordatorio.setFechaCreacionRecordatorio(ahora);
        recordatorio.setTipoRecordatorio("ALERTA_GLUCOSA");
        recordatorio.setEstadoRecordatorio(true);
        recordatorioRepository.save(recordatorio);

        // Acá podrías registrar en historial del paciente si tienes campo para eso

        return dto;

    }

    @Override
    public List<PacienteNivelesDeGlucosaDTO> NivelesDeGlucosa(long idPacienteAbuscar, LocalDate fechaDeMedicion) {
        //verificamos que el paciente exista
        Paciente x = pacienteRepository.findById(idPacienteAbuscar).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        //obtenemos todas las mediciones de ese paciente identificado
        List<Mediciones> medicionesDePaciente = medicionesRepository.findAllByPaciente(x);

        //lista personalizada de nivelesdeglucosadelpaciente que se mostrara al final
        List<PacienteNivelesDeGlucosaDTO>listapers=new ArrayList<>();


        //recorremos la lista para encontrar las mediciones de ese paciente identificado, que cumplen con la fecha entregada
        for (Mediciones m : medicionesDePaciente) {
            if (m.getFechaMedicicion().equals(fechaDeMedicion)) {
                PacienteNivelesDeGlucosaDTO dto = new PacienteNivelesDeGlucosaDTO();
                dto.setIdPaciente(x.getIdPaciente());
                dto.setFechaMedicicion(fechaDeMedicion);
                dto.setEstaturaDiagnosticoPaciente(x.getEstaturaDiagnosticoPaciente());
                dto.setPesoDiagnosticoPaciente(x.getPesoDiagnosticoPaciente());
                dto.setValorMedicion(m.getValorMedicion());
                dto.setNotaMedicion(m.getNotaMedicion());
                dto.setUnidadMedicion(m.getUnidadMedicion());

                listapers.add(dto); // se añade a la lista personalizada, considere que un paciente puede tener mas de un registro en una fecha, por eso hice que se agregase a una lista
            }

        }



        return listapers;
    }
}
