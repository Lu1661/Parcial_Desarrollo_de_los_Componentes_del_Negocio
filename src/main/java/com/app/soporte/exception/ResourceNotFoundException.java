package com.app.soporte.exception;

/**
 * Excepción lanzada cuando no se encuentra un recurso solicitado (HTTP 404).
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

