package org.example.vitalance.interfaces;

import org.example.vitalance.dtos.RecomendacionAlimentariaDTO;
import org.example.vitalance.dtos.RecomendacionRequestDTO;

import java.util.List;

public interface IRecomendacionAlimentariaService {

    /**
     * Genera recomendaciones alimenticias personalizadas para un paciente
     * @param request Datos de la solicitud (paciente, tipo de comida, etc.)
     * @return DTO con recomendaciones completas
     * @throws RuntimeException si hay datos insuficientes o errores
     */
    RecomendacionAlimentariaDTO generarRecomendaciones(RecomendacionRequestDTO request);

    /**
     * Obtiene el historial de recomendaciones de un paciente
     * @param idPaciente ID del paciente
     * @return Lista de recomendaciones previas
     */
    List<RecomendacionAlimentariaDTO> obtenerHistorial(Long idPaciente);

    /**
     * Valida si un paciente tiene datos suficientes para generar recomendaciones
     * @param idPaciente ID del paciente
     * @return true si tiene datos completos
     */
    boolean validarDatosSuficientes(Long idPaciente);
}