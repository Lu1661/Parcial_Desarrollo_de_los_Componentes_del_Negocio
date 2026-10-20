package com.app.soporte.model;

/**
 * Superclase abstracta Persona aplicando Abstracción y Encapsulamiento (Criterio 1).
 */
public abstract class Persona {

    private Long id;
    private String nombre;
    private String email;
    private String telefono;

    public Persona() {
    }

    public Persona(Long id, String nombre, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
