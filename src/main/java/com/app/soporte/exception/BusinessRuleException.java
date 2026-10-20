package com.app.soporte.exception;

/**
 * Excepción lanzada cuando se infringe una regla de negocio del sistema (HTTP 400).
 */
public class BusinessRuleException extends RuntimeException {

    public BusinessRuleException(String message) {
        super(message);
    }
}

