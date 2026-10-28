package com.app.soporte.service;

import com.app.soporte.dto.request.AsignarTecnicoDTO;
import com.app.soporte.dto.request.SolicitudCreateDTO;
import com.app.soporte.dto.response.SolicitudResponseDTO;
import com.app.soporte.exception.BusinessRuleException;
import com.app.soporte.exception.ResourceNotFoundException;
import com.app.soporte.model.enums.EstadoSolicitud;
import com.app.soporte.model.enums.Prioridad;
import com.app.soporte.repository.InMemorySolicitudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias automatizadas con JUnit 5 (equivalente a pytest en Python).
 * Valida el comportamiento de la capa de servicio y las reglas de negocio del sistema.
 */
class SolicitudServiceTest {

    private SolicitudServiceImpl solicitudService;
    private InMemorySolicitudRepository repository;

    @BeforeEach
    void setUp() {
        // Inicialización limpia antes de cada prueba (similar a fixtures en pytest)
        repository = new InMemorySolicitudRepository();
        solicitudService = new SolicitudServiceImpl(repository);
    }

    @Test
    @DisplayName("Debe crear una nueva solicitud con estado ABIERTO y datos correctos")
    void testCrearSolicitudExitosamente() {
        SolicitudCreateDTO dto = new SolicitudCreateDTO(
                "Falla al conectar a la VPN",
                "El usuario no puede establecer conexión remota con el servidor.",
                Prioridad.ALTA,
                "Carlos Mendoza",
                "carlos.mendoza@empresa.com",
                "+51 987654321",
                "Tech Solutions S.A.C.",
                "Contabilidad"
        );

        SolicitudResponseDTO resultado = solicitudService.crearSolicitud(dto);

        assertNotNull(resultado.getId(), "El ID generado no debe ser nulo");
        assertEquals("Falla al conectar a la VPN", resultado.getTitulo());
        assertEquals(EstadoSolicitud.ABIERTO, resultado.getEstado(), "El estado inicial debe ser ABIERTO");
        assertEquals(Prioridad.ALTA, resultado.getPrioridad());
        assertNotNull(resultado.getCliente());
        assertEquals("Carlos Mendoza", resultado.getCliente().getNombre());
        assertNull(resultado.getTecnicoAsignado(), "Al crearse no debe tener técnico asignado");
    }

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException al buscar un ID inexistente")
    void testObtenerPorIdInexistente() {
        Long idInexistente = 9999L;

        assertThrows(ResourceNotFoundException.class, () -> {
            solicitudService.obtenerPorId(idInexistente);
        }, "Debe arrojar ResourceNotFoundException si el ID no existe");
    }

    @Test
    @DisplayName("Debe asignar técnico especialista y cambiar el estado automáticamente a ASIGNADO")
    void testAsignarTecnicoExitosamente() {
        // Creamos una solicitud primero
        SolicitudCreateDTO createDTO = new SolicitudCreateDTO(
                "Error en impresión de boletas",
                "La impresora de red no responde a las solicitudes de finanzas.",
                Prioridad.MEDIA,
                "Laura Torres",
                "laura.torres@empresa.pe",
                "+51 944556677",
                "Distribuidora Lima",
                "Ventas"
        );
        SolicitudResponseDTO creada = solicitudService.crearSolicitud(createDTO);

        // Asignamos un técnico
        AsignarTecnicoDTO tecnicoDTO = new AsignarTecnicoDTO(
                "Ing. Ana Paredes",
                "ana.paredes@soporte.com",
                "+51 912345678",
                "Hardware e Impresoras de Red"
        );

        SolicitudResponseDTO resultado = solicitudService.asignarTecnico(creada.getId(), tecnicoDTO);

        assertEquals(EstadoSolicitud.ASIGNADO, resultado.getEstado(), "Al asignar técnico debe pasar a ASIGNADO");
        assertNotNull(resultado.getTecnicoAsignado());
        assertEquals("Ing. Ana Paredes", resultado.getTecnicoAsignado().getNombre());
        assertFalse(resultado.getTecnicoAsignado().isDisponible());
    }

    @Test
    @DisplayName("Regla de negocio: No debe permitir eliminar una solicitud en estado ASIGNADO")
    void testNoPermitirEliminarSolicitudEnAtencion() {
        // En los datos semilla iniciales, el ID 1 ya está ASIGNADO
        SolicitudResponseDTO solicitudEnAtencion = solicitudService.obtenerPorId(1L);
        assertEquals(EstadoSolicitud.ASIGNADO, solicitudEnAtencion.getEstado());

        assertThrows(BusinessRuleException.class, () -> {
            solicitudService.eliminarSolicitud(1L);
        }, "Debe rechazar la eliminación si la solicitud está ASIGNADO");
    }

    @Test
    @DisplayName("Debe listar correctamente las solicitudes filtradas por su estado")
    void testListarPorEstado() {
        List<SolicitudResponseDTO> asignadas = solicitudService.listarPorEstado(EstadoSolicitud.ASIGNADO);

        assertFalse(asignadas.isEmpty(), "La lista filtrada no debe estar vacía");
        assertTrue(asignadas.stream().allMatch(s -> s.getEstado() == EstadoSolicitud.ASIGNADO),
                "Todos los elementos retornados deben tener estado ASIGNADO");
    }
}

