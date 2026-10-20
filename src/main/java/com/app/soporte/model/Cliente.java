package com.app.soporte.model;

/**
 * Entidad Cliente que hereda de Persona.
 */
public class Cliente extends Persona {

    private String empresa;
    private String departamento;

    public Cliente() {
        super();
    }

    public Cliente(Long id, String nombre, String email, String telefono, String empresa, String departamento) {
        super(id, nombre, email, telefono);
        this.empresa = empresa;
        this.departamento = departamento;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
