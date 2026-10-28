package com.app.soporte.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para asignar un técnico especialista a una solicitud.
 */
@Schema(description = "Datos para asignar un técnico a una solicitud de soporte")
public class AsignarTecnicoDTO {

    @NotBlank(message = "El nombre del técnico es obligatorio")
    @Schema(description = "Nombre completo del técnico", example = "Ing. Ana Paredes")
    private String nombre;

    @NotBlank(message = "El correo del técnico es obligatorio")
    @Schema(description = "Correo del técnico", example = "ana.paredes@soporte.com")
    private String email;

    @NotBlank(message = "El teléfono del técnico es obligatorio")
    @Schema(description = "Teléfono de contacto del técnico", example = "+51 912345678")
    private String telefono;

    @NotBlank(message = "La especialidad del técnico es obligatoria")
    @Schema(description = "Área de especialización", example = "Redes y Telecomunicaciones")
    private String especialidad;

    public AsignarTecnicoDTO() {
    }

    public AsignarTecnicoDTO(String nombre, String email, String telefono, String especialidad) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.especialidad = especialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
