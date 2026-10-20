package com.app.soporte.service;

import com.app.soporte.dto.request.AsignarTecnicoDTO;
import com.app.soporte.dto.request.SolicitudCreateDTO;
import com.app.soporte.dto.request.SolicitudUpdateDTO;
import com.app.soporte.dto.response.SolicitudResponseDTO;
import com.app.soporte.exception.BusinessRuleException;
import com.app.soporte.exception.ResourceNotFoundException;
import com.app.soporte.model.Cliente;
import com.app.soporte.model.Solicitud;
import com.app.soporte.model.Tecnico;
import com.app.soporte.model.enums.EstadoSolicitud;
import com.app.soporte.repository.ISolicitudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementación de la lógica de negocio para la gestión de solicitudes.
 */
@Service
public class SolicitudServiceImpl implements ISolicitudService {

    private final ISolicitudRepository repository;
    private final AtomicLong personaIdSequence = new AtomicLong(10);

    public SolicitudServiceImpl(ISolicitudRepository repository) {
        this.repository = repository;
    }

    @Override
    public SolicitudResponseDTO crearSolicitud(SolicitudCreateDTO dto) {
        Cliente cliente = new Cliente(
                personaIdSequence.incrementAndGet(),
                dto.getClienteNombre(),
                dto.getClienteEmail(),
                dto.getClienteTelefono(),
                dto.getClienteEmpresa(),
                dto.getClienteDepartamento()
        );

        Solicitud nuevaSolicitud = new Solicitud(
                null,
                dto.getTitulo(),
                dto.getDescripcion(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                EstadoSolicitud.REGISTRADA,
                dto.getPrioridad(),
                cliente,
                null
        );

        Solicitud guardada = repository.save(nuevaSolicitud);
        return mapToResponseDTO(guardada);
    }

    @Override
    public SolicitudResponseDTO obtenerPorId(Long id) {
        Solicitud solicitud = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la solicitud con ID: " + id));
        return mapToResponseDTO(solicitud);
    }

    @Override
    public List<SolicitudResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public List<SolicitudResponseDTO> listarPorEstado(EstadoSolicitud estado) {
        return repository.findByEstado(estado).stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public SolicitudResponseDTO actualizarSolicitud(Long id, SolicitudUpdateDTO dto) {
        Solicitud existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la solicitud con ID: " + id));

        if (existente.getEstado() == EstadoSolicitud.CANCELADA) {
            throw new BusinessRuleException("No se puede modificar una solicitud que ha sido CANCELADA");
        }

        existente.setTitulo(dto.getTitulo());
        existente.setDescripcion(dto.getDescripcion());
        existente.setPrioridad(dto.getPrioridad());
        existente.setEstado(dto.getEstado());
        existente.setFechaActualizacion(LocalDateTime.now());

        Solicitud actualizada = repository.save(existente);
        return mapToResponseDTO(actualizada);
    }

    @Override
    public SolicitudResponseDTO asignarTecnico(Long id, AsignarTecnicoDTO dto) {
        Solicitud existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la solicitud con ID: " + id));

        if (existente.getEstado() == EstadoSolicitud.CANCELADA || existente.getEstado() == EstadoSolicitud.RESUELTA) {
            throw new BusinessRuleException("No se puede asignar un técnico a una solicitud en estado: " + existente.getEstado());
        }

        Tecnico tecnico = new Tecnico(
                personaIdSequence.incrementAndGet(),
                dto.getNombre(),
                dto.getEmail(),
                dto.getTelefono(),
                dto.getEspecialidad(),
                false
        );

        existente.setTecnicoAsignado(tecnico);
        existente.setEstado(EstadoSolicitud.EN_ATENCION);
        existente.setFechaActualizacion(LocalDateTime.now());

        Solicitud actualizada = repository.save(existente);
        return mapToResponseDTO(actualizada);
    }

    @Override
    public void eliminarSolicitud(Long id) {
        Solicitud existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la solicitud con ID: " + id));

        if (existente.getEstado() == EstadoSolicitud.EN_ATENCION) {
            throw new BusinessRuleException("No se puede eliminar una solicitud que se encuentra EN_ATENCION. Debe cancelarse primero.");
        }

        repository.deleteById(id);
    }

    private SolicitudResponseDTO mapToResponseDTO(Solicitud solicitud) {
        return new SolicitudResponseDTO(
                solicitud.getId(),
                solicitud.getTitulo(),
                solicitud.getDescripcion(),
                solicitud.getFechaCreacion(),
                solicitud.getFechaActualizacion(),
                solicitud.getEstado(),
                solicitud.getPrioridad(),
                solicitud.getCliente(),
                solicitud.getTecnicoAsignado()
        );
    }
}
