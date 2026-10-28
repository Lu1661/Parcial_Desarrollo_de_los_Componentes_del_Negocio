package com.app.soporte.repository;

import com.app.soporte.model.Cliente;
import com.app.soporte.model.Solicitud;
import com.app.soporte.model.Tecnico;
import com.app.soporte.model.enums.EstadoSolicitud;
import com.app.soporte.model.enums.Prioridad;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * [VERSIÓN 1 - API Backend Pura]
 * Implementación de persistencia en memoria utilizando ConcurrentHashMap y AtomicLong.
 * Con datos semilla iniciales mínimos según la consigna base.
 */
@Repository
public class InMemorySolicitudRepository implements ISolicitudRepository {

    private final Map<Long, Solicitud> dataStore = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(0);

    public InMemorySolicitudRepository() {
        initMockData();
    }

    private void initMockData() {
        Cliente cliente1 = new Cliente(
                1L,
                "Mariana Quispe",
                "mariana.quispe@logistica.pe",
                "+51 987112233",
                "Logística Andina S.A.C.",
                "Operaciones"
        );

        Tecnico tecnico1 = new Tecnico(
                1L,
                "Ing. Roberto Morales",
                "roberto.morales@soporte.com",
                "+51 912334455",
                "Sistemas y Redes",
                false
        );

        Solicitud solicitudInicial = new Solicitud(
                idSequence.incrementAndGet(),
                "Error al sincronizar inventario con ERP",
                "El sistema de almacén no registra los despachos en tiempo real desde las 08:00 AM.",
                LocalDateTime.now().minusHours(3),
                LocalDateTime.now().minusHours(1),
                EstadoSolicitud.ASIGNADO,
                Prioridad.ALTA,
                cliente1,
                tecnico1
        );

        dataStore.put(solicitudInicial.getId(), solicitudInicial);
    }

    @Override
    public Solicitud save(Solicitud solicitud) {
        if (solicitud.getId() == null) {
            solicitud.setId(idSequence.incrementAndGet());
        }
        dataStore.put(solicitud.getId(), solicitud);
        return solicitud;
    }

    @Override
    public Optional<Solicitud> findById(Long id) {
        return Optional.ofNullable(dataStore.get(id));
    }

    @Override
    public List<Solicitud> findAll() {
        return new ArrayList<>(dataStore.values());
    }

    @Override
    public List<Solicitud> findByEstado(EstadoSolicitud estado) {
        return dataStore.values().stream()
                .filter(s -> s.getEstado() == estado)
                .toList();
    }

    @Override
    public boolean deleteById(Long id) {
        return dataStore.remove(id) != null;
    }

    @Override
    public boolean existsById(Long id) {
        return dataStore.containsKey(id);
    }
}
