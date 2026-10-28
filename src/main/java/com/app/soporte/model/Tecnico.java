package com.app.soporte.model;

/**
 * Entidad Técnico que hereda de Persona.
 */
public class Tecnico extends Persona {

    private String especialidad;
    private boolean disponible = true;

    public Tecnico() {
        super();
    }

    public Tecnico(Long id, String nombre, String email, String telefono, String especialidad, boolean disponible) {
        super(id, nombre, email, telefono);
        this.especialidad = especialidad;
        this.disponible = disponible;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
