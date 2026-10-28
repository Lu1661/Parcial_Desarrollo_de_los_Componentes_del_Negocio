package com.app.soporte.model;

import com.app.soporte.model.enums.EstadoSolicitud;
import com.app.soporte.model.enums.Prioridad;

import java.time.LocalDateTime;

/**
 * Entidad principal Solicitud en Java estándar.
 */
public class Solicitud {

    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private EstadoSolicitud estado;
    private Prioridad prioridad;
    private Cliente cliente;
    private Tecnico tecnicoAsignado;

    public Solicitud() {
    }

    public Solicitud(Long id, String titulo, String descripcion, LocalDateTime fechaCreacion,
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
