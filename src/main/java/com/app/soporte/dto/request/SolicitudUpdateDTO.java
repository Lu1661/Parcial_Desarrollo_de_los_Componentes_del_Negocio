package com.app.soporte.dto.request;

import com.app.soporte.model.enums.EstadoSolicitud;
import com.app.soporte.model.enums.Prioridad;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para la actualización de una solicitud existente.
 */
@Schema(description = "Datos para actualizar una solicitud de soporte")
public class SolicitudUpdateDTO {

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres")
    @Schema(description = "Título resumen del problema", example = "Falla al conectar a la VPN corporativa - Actualizado")
    private String titulo;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 10, max = 1000, message = "La descripción debe tener entre 10 y 1000 caracteres")
    @Schema(description = "Detalle del incidente", example = "Se reinició el router pero persiste el fallo de DNS.")
    private String descripcion;

    @NotNull(message = "La prioridad es obligatoria")
    @Schema(description = "Nivel de prioridad", example = "URGENTE")
    private Prioridad prioridad;

    @NotNull(message = "El estado es obligatorio")
    @Schema(description = "Estado actual de la solicitud", example = "EN_PROCESO")
    private EstadoSolicitud estado;

    public SolicitudUpdateDTO() {
    }

    public SolicitudUpdateDTO(String titulo, String descripcion, Prioridad prioridad, EstadoSolicitud estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = estado;
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

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }
}
