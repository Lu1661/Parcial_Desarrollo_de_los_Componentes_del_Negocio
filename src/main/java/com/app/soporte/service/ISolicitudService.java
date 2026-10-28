package com.app.soporte.service;

import com.app.soporte.dto.request.AsignarTecnicoDTO;
import com.app.soporte.dto.request.SolicitudCreateDTO;
import com.app.soporte.dto.request.SolicitudUpdateDTO;
import com.app.soporte.dto.response.SolicitudResponseDTO;
import com.app.soporte.model.enums.EstadoSolicitud;

import java.util.List;

/**
 * Interfaz de la capa de servicio para la gestión de solicitudes.
 * Cumple con el Principio de Segregación de Interfaces y Arquitectura en Capas.
 */
public interface ISolicitudService {

    SolicitudResponseDTO crearSolicitud(SolicitudCreateDTO createDTO);

    SolicitudResponseDTO obtenerPorId(Long id);

    List<SolicitudResponseDTO> listarTodas();

    List<SolicitudResponseDTO> listarPorEstado(EstadoSolicitud estado);

    SolicitudResponseDTO actualizarSolicitud(Long id, SolicitudUpdateDTO updateDTO);

    SolicitudResponseDTO asignarTecnico(Long id, AsignarTecnicoDTO tecnicoDTO);

    void eliminarSolicitud(Long id);
}

