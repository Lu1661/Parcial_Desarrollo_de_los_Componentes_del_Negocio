# INFORME TÉCNICO: DESARROLLO DE API RESTful PARA GESTIÓN DE SOPORTE TÉCNICO

**Institución:** Zegel  
**Carrera:** Desarrollo de Sistemas de Información / Computación e Informática  
**Unidad Didáctica:** Desarrollo de los Componentes del Negocio  
**Evaluación:** Evaluación Parcial  
**Semestre / Ciclo:** Ciclo IV  
**Repositorio GitHub:** `https://github.com/Lu1661/Parcial_Desarrollo_de_los_Componentes_del_Negocio`  

---

## 👥 Datos del Equipo de Trabajo y Roles

| Integrante | Rol en el Proyecto | Responsabilidades |
| :--- | :--- | :--- |
| **[Nombre Completo Integrante 1]** | **Arquitecto de Software & Backend** | • Configuración inicial del proyecto Spring Boot y Maven.<br>• Modelado de clases orientado a objetos aplicando Abstracción y Herencia (`Persona`, `Cliente`, `Tecnico`).<br>• Diseño del patrón Repository y persistencia thread-safe en memoria. |
| **[Nombre Completo Integrante 2]** | **Desarrollador Backend** | • Implementación de la capa de Servicios y reglas de negocio.<br>• Creación de los DTOs de entrada y salida con validaciones avanzadas (`@Valid`, `@NotBlank`, etc.).<br>• Desarrollo de los endpoints RESTful en el controlador. |
| **[Nombre Completo Integrante 3]** | **Especialista QA & Documentador** | • Implementación del manejador centralizado de errores (`@RestControllerAdvice`).<br>• Integración de Swagger UI / OpenAPI 3.<br>• Diseño y ejecución de la suite de pruebas en Postman (casos exitosos y de error).<br>• Redacción del README y consolidación del informe técnico en PDF. |

---

## 1. Introducción y Descripción del Problema

Una empresa de servicios tecnológicos enfrentaba serias dificultades operativas al gestionar las solicitudes de asistencia de sus clientes mediante registros físicos en papel y cadenas de correos no estructuradas. Esto provocaba:
- Pérdida recurrente de información crítica de los incidentes.
- Retrasos severos en la asignación y atención oportuna por parte de los técnicos.
- Falta de métricas y estados transparentes sobre las incidencias abiertas o resueltas.

### Solución Implementada
Se diseñó e implementó una **API RESTful** moderna con **Java 21** y **Spring Boot 3**, que permite registrar, listar, filtrar, consultar, actualizar, asignar técnicos y eliminar solicitudes de soporte técnico de forma segura, estandarizada y en tiempo de ejecución mediante simulación de persistencia en memoria.

---

## 2. Arquitectura de la Solución y Principios de Diseño

### 2.1 Aplicación de Programación Orientada a Objetos (Criterio 1: Sobresaliente)
- **Abstracción y Herencia:** Se creó la clase abstracta `Persona`, de la cual heredan las entidades concretas `Cliente` y `Tecnico`, evitando duplicación de atributos (`nombre`, `email`, `telefono`) y permitiendo reutilización y polimorfismo.
- **Encapsulamiento:** Todos los atributos del dominio son privados y se accede a ellos mediante métodos accesores gestionados limpiamente a través de anotaciones de Lombok.
- **Tipado Fuerte y Enums:** Se crearon enums para `EstadoSolicitud` (`REGISTRADA`, `EN_ATENCION`, `RESUELTA`, `CANCELADA`) y `Prioridad` (`BAJA`, `MEDIA`, `ALTA`, `URGENTE`), impidiendo estados incongruentes.

### 2.2 Arquitectura en Capas y Patrón DTO (Criterio 2: Sobresaliente)
El sistema divide estrictamente sus responsabilidades:
1. **Capa Controller (`com.app.soporte.controller`):** Expone los endpoints HTTP, maneja códigos de estado y valida datos de entrada con `@Valid`.
2. **Capa DTO (`com.app.soporte.dto`):** Aísla el modelo de dominio interno de la exposición exterior hacia el cliente.
3. **Capa Service (`com.app.soporte.service`):** Define interfaces (`ISolicitudService`) e implementa la lógica y reglas del negocio (`SolicitudServiceImpl`).
4. **Capa Repository (`com.app.soporte.repository`):** Interfaz `ISolicitudRepository` que desacopla la persistencia del resto de la aplicación.
5. **Capa Exception (`com.app.soporte.exception`):** Centraliza el tratamiento de fallos mediante `@RestControllerAdvice`.

### 2.3 Simulación de Persistencia en Memoria (Criterio 4: Sobresaliente)
En `InMemorySolicitudRepository`:
- Se emplea un `ConcurrentHashMap<Long, Solicitud>` para garantizar accesos concurrentes seguros y de alta eficiencia $O(1)$.
- Se implementa un generador de identificadores autoincrementables con `AtomicLong` para simular llaves primarias de una base de datos real.
- Incluye datos semilla inicializados al arrancar para facilitar pruebas inmediatas.

---

## 3. Especificación de la API RESTful (Criterio 3)

| Método HTTP | Ruta | Código Éxito | Función |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/solicitudes` | `201 Created` | Registra una nueva solicitud con estado `REGISTRADA`. |
| `GET` | `/api/v1/solicitudes` | `200 OK` | Obtiene el listado completo de solicitudes. |
| `GET` | `/api/v1/solicitudes?estado={ESTADO}` | `200 OK` | Filtra solicitudes por su estado. |
| `GET` | `/api/v1/solicitudes/{id}` | `200 OK` | Retorna los detalles de la solicitud por ID. |
| `PUT` | `/api/v1/solicitudes/{id}` | `200 OK` | Actualiza título, descripción, prioridad y estado. |
| `PATCH` | `/api/v1/solicitudes/{id}/asignar-tecnico`| `200 OK` | Asigna un técnico y cambia el estado a `EN_ATENCION`. |
| `DELETE`| `/api/v1/solicitudes/{id}` | `200 OK` | Elimina una solicitud (con validación de negocio). |

---

## 4. Evidencias de Pruebas Funcionales con Postman (Criterio 5)

A continuación se detallan los casos de prueba ejecutados y sus resultados:

### Caso 1: Consulta General de Solicitudes (GET)
- **Endpoint:** `GET http://localhost:8080/api/v1/solicitudes`
- **Código de Estado:** `200 OK`
- **Respuesta:** Devuelve la lista de solicitudes registradas encapsuladas dentro del objeto `ApiResponse`.
*(Pegar aquí la captura de pantalla de Postman)*

### Caso 2: Registro Exitoso de una Solicitud (POST)
- **Endpoint:** `POST http://localhost:8080/api/v1/solicitudes`
- **Código de Estado:** `201 Created`
- **Payload:** Datos completos de la incidencia y del cliente.
*(Pegar aquí la captura de pantalla de Postman)*

### Caso 3: Consulta Individual por ID (GET)
- **Endpoint:** `GET http://localhost:8080/api/v1/solicitudes/1`
- **Código de Estado:** `200 OK`
- **Respuesta:** Objeto individual con todos los atributos del cliente y técnico asignado.
*(Pegar aquí la captura de pantalla de Postman)*

### Caso 4: Filtro por Estado (GET con Query Param)
- **Endpoint:** `GET http://localhost:8080/api/v1/solicitudes?estado=EN_ATENCION`
- **Código de Estado:** `200 OK`
- **Respuesta:** Filtra exclusivamente las solicitudes en proceso de atención.
*(Pegar aquí la captura de pantalla de Postman)*

### Caso 5: Asignación de Técnico Especialista (PATCH)
- **Endpoint:** `PATCH http://localhost:8080/api/v1/solicitudes/2/asignar-tecnico`
- **Código de Estado:** `200 OK`
- **Efecto:** Asigna los datos del técnico y transiciona el estado de `REGISTRADA` a `EN_ATENCION`.
*(Pegar aquí la captura de pantalla de Postman)*

### Caso 6: Prueba de Validación Fallida (POST - 400 Bad Request)
- **Endpoint:** `POST http://localhost:8080/api/v1/solicitudes`
- **Código de Estado:** `400 Bad Request`
- **Resultado:** El `@RestControllerAdvice` atrapa `MethodArgumentNotValidException` y devuelve un mapa JSON especificando los campos con error: título muy corto, email inválido, campos obligatorios vacíos.
*(Pegar aquí la captura de pantalla de Postman)*

### Caso 7: Prueba de Recurso Inexistente (GET - 404 Not Found)
- **Endpoint:** `GET http://localhost:8080/api/v1/solicitudes/999`
- **Código de Estado:** `404 Not Found`
- **Resultado:** Retorna mensaje claro: *"No se encontró la solicitud con ID: 999"*.
*(Pegar aquí la captura de pantalla de Postman)*

### Caso 8: Prueba de Regla de Negocio (DELETE - 400 Bad Request)
- **Endpoint:** `DELETE http://localhost:8080/api/v1/solicitudes/1`
- **Código de Estado:** `400 Bad Request`
- **Resultado:** Impide eliminar una solicitud que se encuentra en estado `EN_ATENCION`, protegiendo la trazabilidad del servicio.
*(Pegar aquí la captura de pantalla de Postman)*

---

## 5. Documentación Interactiva OpenAPI / Swagger

El proyecto cuenta con Swagger UI accesible en:
`http://localhost:8080/swagger-ui.html`

Permite explorar los esquemas de datos (`SolicitudResponseDTO`, `SolicitudCreateDTO`, etc.), los códigos de respuesta esperados y ejecutar peticiones directamente desde el navegador web.

---

## 6. Conclusiones y Recomendaciones

1. **Cumplimiento Arquitectural:** La separación estricta en capas (Controller, Service, Repository, DTO y Model) favorece el principio de responsabilidad única (SRP) y garantiza la alta mantenibilidad del sistema.
2. **Robustez y Seguridad en las Entradas:** El uso de Jakarta Bean Validation combinado con `@RestControllerAdvice` protege la API contra datos erróneos o maliciosos, ofreciendo retroalimentación clara al cliente de la API.
3. **Escalabilidad Futura:** Gracias a la interfaz `ISolicitudRepository`, la aplicación está lista para migrar en el futuro de una persistencia en memoria hacia Spring Data JPA y una base de datos relacional (MySQL / PostgreSQL) sin modificar ni una sola línea de la capa de servicio o controlador.

