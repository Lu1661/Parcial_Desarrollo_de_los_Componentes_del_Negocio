package com.app.soporte.dto.response;

import com.app.soporte.model.Cliente;
import com.app.soporte.model.Tecnico;
import com.app.soporte.model.enums.EstadoSolicitud;
import com.app.soporte.model.enums.Prioridad;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * DTO de respuesta con el detalle de la solicitud.
 */
@Schema(description = "Respuesta detallada de una solicitud de soporte técnico")
public class SolicitudResponseDTO {

    @Schema(description = "ID único de la solicitud", example = "1")
    private Long id;

    @Schema(description = "Título de la solicitud", example = "Falla al conectar a la VPN corporativa")
    private String titulo;

    @Schema(description = "Descripción del problema reportado")
    private String descripcion;

    @Schema(description = "Fecha y hora de registro")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Fecha y hora de última actualización")
    private LocalDateTime fechaActualizacion;

    @Schema(description = "Estado actual de la solicitud", example = "REGISTRADA")
    private EstadoSolicitud estado;

    @Schema(description = "Nivel de prioridad", example = "ALTA")
    private Prioridad prioridad;

    @Schema(description = "Información del cliente solicitante")
    private Cliente cliente;

    @Schema(description = "Información del técnico asignado (si lo hubiere)")
    private Tecnico tecnicoAsignado;

    public SolicitudResponseDTO() {
    }

    public SolicitudResponseDTO(Long id, String titulo, String descripcion, LocalDateTime fechaCreacion,
                                LocalDateTime fechaActualizacion, EstadoSolicitud estado, Prioridad prioridad,
                                Cliente cliente, Tecnico tecnicoAsignado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.cliente = cliente;
        this.tecnicoAsignado = tecnicoAsignado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tecnico getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    public void setTecnicoAsignado(Tecnico tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
    }
}
