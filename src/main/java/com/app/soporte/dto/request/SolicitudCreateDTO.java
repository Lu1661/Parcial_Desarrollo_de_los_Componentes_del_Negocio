package com.app.soporte.dto.request;

import com.app.soporte.model.enums.Prioridad;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para la creación de una nueva solicitud de soporte técnico.
 */
@Schema(description = "Datos requeridos para registrar una nueva solicitud de soporte técnico")
public class SolicitudCreateDTO {

    @NotBlank(message = "El título de la solicitud es obligatorio")
    @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres")
    @Schema(description = "Título resumen del problema", example = "Falla al conectar a la VPN corporativa")
    private String titulo;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 10, max = 1000, message = "La descripción debe tener entre 10 y 1000 caracteres")
    @Schema(description = "Detalle del incidente", example = "No se puede establecer conexión al servidor principal tras la actualización.")
    private String descripcion;

    @NotNull(message = "La prioridad es obligatoria (BAJA, MEDIA, ALTA, URGENTE)")
    @Schema(description = "Nivel de prioridad", example = "ALTA")
    private Prioridad prioridad;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Schema(description = "Nombre completo del cliente", example = "Carlos Mendoza")
    private String clienteNombre;

    @NotBlank(message = "El correo electrónico del cliente es obligatorio")
    @Email(message = "El correo del cliente debe tener un formato válido")
    @Schema(description = "Correo del cliente", example = "carlos.mendoza@empresa.com")
    private String clienteEmail;

    @NotBlank(message = "El teléfono del cliente es obligatorio")
    @Schema(description = "Teléfono de contacto", example = "+51 987654321")
    private String clienteTelefono;

    @NotBlank(message = "La empresa del cliente es obligatoria")
    @Schema(description = "Empresa o razón social", example = "Tech Solutions S.A.C.")
    private String clienteEmpresa;

    @NotBlank(message = "El departamento es obligatorio")
    @Schema(description = "Área o departamento del cliente", example = "Contabilidad")
    private String clienteDepartamento;

    public SolicitudCreateDTO() {
    }

    public SolicitudCreateDTO(String titulo, String descripcion, Prioridad prioridad, String clienteNombre,
                              String clienteEmail, String clienteTelefono, String clienteEmpresa, String clienteDepartamento) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.clienteNombre = clienteNombre;
        this.clienteEmail = clienteEmail;
        this.clienteTelefono = clienteTelefono;
        this.clienteEmpresa = clienteEmpresa;
        this.clienteDepartamento = clienteDepartamento;
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

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }

    public String getClienteTelefono() {
        return clienteTelefono;
    }

    public void setClienteTelefono(String clienteTelefono) {
        this.clienteTelefono = clienteTelefono;
    }

    public String getClienteEmpresa() {
        return clienteEmpresa;
    }

    public void setClienteEmpresa(String clienteEmpresa) {
        this.clienteEmpresa = clienteEmpresa;
    }

    public String getClienteDepartamento() {
        return clienteDepartamento;
    }

    public void setClienteDepartamento(String clienteDepartamento) {
        this.clienteDepartamento = clienteDepartamento;
    }
}
