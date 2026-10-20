package com.app.soporte.repository;

import com.app.soporte.model.Solicitud;
import com.app.soporte.model.enums.EstadoSolicitud;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones de persistencia para las solicitudes.
 * Cumple con el Principio de Inversión de Dependencias (IoC) y Abstracción.
 */
public interface ISolicitudRepository {

    Solicitud save(Solicitud solicitud);

    Optional<Solicitud> findById(Long id);

    List<Solicitud> findAll();

    List<Solicitud> findByEstado(EstadoSolicitud estado);

    boolean deleteById(Long id);

    boolean existsById(Long id);
}

