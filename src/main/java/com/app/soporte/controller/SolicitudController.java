package com.app.soporte.controller;

import com.app.soporte.dto.request.AsignarTecnicoDTO;
import com.app.soporte.dto.request.SolicitudCreateDTO;
import com.app.soporte.dto.request.SolicitudUpdateDTO;
import com.app.soporte.dto.response.ApiResponse;
import com.app.soporte.dto.response.SolicitudResponseDTO;
import com.app.soporte.model.enums.EstadoSolicitud;
import com.app.soporte.service.ISolicitudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador RESTful para gestionar las solicitudes de soporte técnico.
 */
@RestController
@RequestMapping("/api/v1/solicitudes")
@Tag(name = "Solicitudes de Soporte", description = "Endpoints para el registro, consulta, actualización, asignación y eliminación de solicitudes")
public class SolicitudController {

    private final ISolicitudService solicitudService;

    public SolicitudController(ISolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @PostMapping
    @Operation(summary = "Registrar una nueva solicitud de soporte", description = "Crea una solicitud con estado inicial ABIERTO validando los datos del cliente y problema.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Solicitud creada con éxito"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<ApiResponse<SolicitudResponseDTO>> crearSolicitud(
            @Valid @RequestBody SolicitudCreateDTO createDTO) {

        SolicitudResponseDTO nuevaSolicitud = solicitudService.crearSolicitud(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Solicitud de soporte creada exitosamente", nuevaSolicitud));
    }

    @GetMapping
    @Operation(summary = "Listar todas las solicitudes", description = "Permite listar todas las solicitudes o filtrarlas opcionalmente por estado.")
    public ResponseEntity<ApiResponse<List<SolicitudResponseDTO>>> listarSolicitudes(
            @Parameter(description = "Filtro opcional por estado") @RequestParam(required = false) EstadoSolicitud estado) {

        List<SolicitudResponseDTO> lista = (estado != null)
                ? solicitudService.listarPorEstado(estado)
                : solicitudService.listarTodas();

        return ResponseEntity.ok(ApiResponse.success("Solicitudes consultadas exitosamente", lista));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar solicitud por ID", description = "Obtiene los detalles de una solicitud específica según su identificador único.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Solicitud encontrada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    public ResponseEntity<ApiResponse<SolicitudResponseDTO>> obtenerPorId(
            @Parameter(description = "Identificador de la solicitud", example = "1") @PathVariable Long id) {

        SolicitudResponseDTO solicitud = solicitudService.obtenerPorId(id);
        return ResponseEntity.ok(ApiResponse.success("Solicitud obtenida exitosamente", solicitud));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar información de una solicitud", description = "Actualiza el título, descripción, prioridad y estado de una solicitud existente.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Solicitud actualizada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos o regla de negocio no cumplida"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    public ResponseEntity<ApiResponse<SolicitudResponseDTO>> actualizarSolicitud(
            @PathVariable Long id,
            @Valid @RequestBody SolicitudUpdateDTO updateDTO) {

        SolicitudResponseDTO actualizada = solicitudService.actualizarSolicitud(id, updateDTO);
        return ResponseEntity.ok(ApiResponse.success("Solicitud actualizada exitosamente", actualizada));
    }

    @PatchMapping("/{id}/asignar-tecnico")
    @Operation(summary = "Asignar técnico a la solicitud", description = "Asigna un especialista técnico a la solicitud y cambia su estado a ASIGNADO.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Técnico asignado con éxito"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solicitud cerrada o datos incorrectos"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    public ResponseEntity<ApiResponse<SolicitudResponseDTO>> asignarTecnico(
            @PathVariable Long id,
            @Valid @RequestBody AsignarTecnicoDTO tecnicoDTO) {

        SolicitudResponseDTO actualizada = solicitudService.asignarTecnico(id, tecnicoDTO);
        return ResponseEntity.ok(ApiResponse.success("Técnico asignado exitosamente a la solicitud", actualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar solicitud", description = "Elimina permanentemente una solicitud del sistema si cumple las condiciones de negocio.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Solicitud eliminada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "No se puede eliminar en el estado actual"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    public ResponseEntity<ApiResponse<Void>> eliminarSolicitud(@PathVariable Long id) {
        solicitudService.eliminarSolicitud(id);
        return ResponseEntity.ok(ApiResponse.success("Solicitud eliminada exitosamente del sistema"));
    }
}
