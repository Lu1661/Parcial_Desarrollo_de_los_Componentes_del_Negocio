package com.app.soporte.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Estructura estándar para respuestas de error de la API.
 */
@Schema(description = "Estructura de error devuelta por la API")
public class ErrorResponse {

    @Schema(description = "Indica que la operación falló", example = "false")
    private boolean success = false;

    @Schema(description = "Código de estado HTTP", example = "404")
    private int status;

    @Schema(description = "Nombre o tipo del error", example = "NOT_FOUND")
    private String error;

    @Schema(description = "Mensaje explicativo del error", example = "No se encontró la solicitud con ID: 99")
    private String message;

    @Schema(description = "Ruta de la petición donde ocurrió el error", example = "/api/v1/solicitudes/99")
    private String path;

    @Schema(description = "Detalle de errores de validación por campo (si aplica)")
    private Map<String, String> validationErrors;

    @Schema(description = "Fecha y hora en que ocurrió el error")
    private LocalDateTime timestamp;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(int status, String error, String message, String path, Map<String, String> validationErrors) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.validationErrors = validationErrors;
        this.timestamp = LocalDateTime.now();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
