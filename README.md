# API RESTful para la Gestión de Solicitudes de Soporte Técnico

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-brightgreen?logo=springboot)
![Maven](https://img.shields.io/badge/Maven-3.10.0-C71A36?logo=apachemaven)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203-85EA2D?logo=swagger)

API desarrollada en **Java 21** con **Spring Boot 3** como solución tecnológica para el registro, consulta, actualización, asignación y seguimiento de solicitudes de incidencias y soporte técnico corporativo.

---

## 📌 Tabla de Contenidos
1. [Descripción del Problema y Solución](#-descripción-del-problema-y-solución)
2. [Arquitectura y Estructura del Proyecto](#-arquitectura-y-estructura-del-proyecto)
3. [Tecnologías y Librerías](#-tecnologías-y-librerías)
4. [Requisitos Previos](#-requisitos-previos)
5. [Instrucciones de Instalación y Ejecución](#-instrucciones-de-instalación-y-ejecución)
6. [Documentación Swagger / OpenAPI](#-documentación-swagger--openapi)
7. [Endpoints de la API](#-endpoints-de-la-api)
8. [Pruebas Funcionales con Postman](#-pruebas-funcionales-con-postman)
9. [Manejo Centralizado de Excepciones](#-manejo-centralizado-de-excepciones)
10. [Equipo de Desarrollo y Roles](#-equipo-de-desarrollo-y-roles)

---

## 🏢 Descripción del Problema y Solución

Una empresa de servicios tecnológicos registraba las solicitudes de soporte de forma desordenada en papel y correos electrónicos, lo que ocasionaba pérdidas de datos y demoras en los tiempos de respuesta.

Esta API RESTful soluciona la problemática al centralizar el ciclo de vida completo de cada incidencia:
- Registro tipado con validaciones de datos del cliente y problema.
- Consulta general o filtrada por estado (`ABIERTO`, `ASIGNADO`, `EN_PROCESO`, `EN_REVISION`, `RESUELTO`, `CERRADO`, `CANCELADO`).
- Asignación de técnicos especialistas con cambio de estado automático.
- Control de integridad para evitar cancelaciones o eliminaciones indebidas.
- Persistencia local eficiente en memoria simulando una base de datos real con `ConcurrentHashMap` y secuencias atómicas `AtomicLong`.

---

## 🏛️ Arquitectura y Estructura del Proyecto

El proyecto sigue una **arquitectura en capas** desacoplada:

```text
com.app.soporte
├── config/             # Configuración OpenAPI 3 (Swagger UI)
├── controller/         # Endpoints REST y mapeo HTTP
├── dto/                # Data Transfer Objects
│   ├── request/        # Validaciones de entrada (@Valid, @NotBlank, etc.)
│   └── response/       # Estructura uniforme ApiResponse<T> y ErrorResponse
├── exception/          # Manejo centralizado @RestControllerAdvice
├── model/              # Clases del dominio de negocio (POO)
│   ├── enums/          # EstadoSolicitud y Prioridad
│   ├── Persona.java    # Clase base abstracta (Abstracción y Herencia)
│   ├── Cliente.java    # Especialización de Persona
│   ├── Tecnico.java    # Especialización de Persona
│   └── Solicitud.java  # Entidad central
├── repository/         # Persistencia simulada en memoria (Inversión de Dependencias)
└── service/            # Lógica de negocio (Interfaces e Implementación)
```

---

## 🛠️ Tecnologías y Librerías

- **Lenguaje:** Java 21 (LTS)
- **Framework:** Spring Boot 3.3.4
  - `spring-boot-starter-web`: Creación de la API RESTful.
  - `spring-boot-starter-validation`: Bean Validation (Jakarta Validation).
- **Documentación:** `springdoc-openapi-starter-webmvc-ui` (v2.6.0).
- **Productividad:** Project Lombok.
- **Herramienta de Construcción:** Apache Maven con Wrapper (`./mvnw`).

---

## 📋 Requisitos Previos

- **Java JDK 21** instalado y configurado en la variable `JAVA_HOME`.
- **Git** (para clonar el repositorio).

*(No se requiere instalar Maven globalmente, ya que se incluye el Maven Wrapper).*

---

## 🚀 Instrucciones de Instalación y Ejecución

### 1. Clonar el repositorio
```bash
git clone https://github.com/TU_USUARIO/soporte-tecnico-api.git
cd soporte-tecnico-api
```

### 2. Ejecutar la aplicación

**En Windows (PowerShell / CMD):**
```powershell
.\mvnw.cmd spring-boot:run
```

> **Nota si obtienes el error `JAVA_HOME is not defined correctly`:**
> Si tu terminal no detecta el JDK instalado, asegúrate de tener instalado **JDK 21** y configúralo con la ruta donde esté instalado en tu equipo (por ejemplo `C:\Program Files\Java\jdk-21` o tu carpeta de JDKs):
> ```powershell
> # Reemplaza con la ruta de tu JDK 21:
> $env:JAVA_HOME = "C:\Ruta\A\Tu\JDK-21"
> .\mvnw.cmd spring-boot:run
> ```
> O configúralo de manera permanente en Windows (PowerShell):
> ```powershell
> [Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Ruta\A\Tu\JDK-21", "User")
> ```

**En Linux / macOS:**
```bash
./mvnw spring-boot:run
```

La aplicación iniciará en: `http://localhost:8080`

---

## 📖 Documentación Swagger / OpenAPI

Una vez iniciada la aplicación, puedes acceder a la documentación interactiva en:

* **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* **OpenAPI JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🌐 Endpoints de la API

| Método | Endpoint | Descripción | Código HTTP |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/solicitudes` | Registra una nueva solicitud | `201 Created` |
| `GET` | `/api/v1/solicitudes` | Lista todas las solicitudes | `200 OK` |
| `GET` | `/api/v1/solicitudes?estado={ESTADO}` | Filtra solicitudes por estado | `200 OK` |
| `GET` | `/api/v1/solicitudes/{id}` | Obtiene una solicitud por su ID | `200 OK` |
| `PUT` | `/api/v1/solicitudes/{id}` | Actualiza datos y estado de la solicitud | `200 OK` |
| `PATCH` | `/api/v1/solicitudes/{id}/asignar-tecnico` | Asigna un técnico especialista | `200 OK` |
| `DELETE` | `/api/v1/solicitudes/{id}` | Elimina una solicitud del sistema | `200 OK` |

### Ejemplo: Registrar Solicitud (`POST /api/v1/solicitudes`)

**Request Body (JSON):**
```json
{
  "titulo": "Falla al conectar a la VPN corporativa",
  "descripcion": "El usuario de contabilidad no puede autenticarse en el servidor tras la actualización.",
  "prioridad": "ALTA",
  "clienteNombre": "Carlos Mendoza",
  "clienteEmail": "carlos.mendoza@empresa.com",
  "clienteTelefono": "+51 987654321",
  "clienteEmpresa": "Tech Solutions S.A.C.",
  "clienteDepartamento": "Contabilidad"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Solicitud de soporte creada exitosamente",
  "data": {
    "id": 2,
    "titulo": "Falla al conectar a la VPN corporativa",
    "descripcion": "El usuario de contabilidad no puede autenticarse en el servidor tras la actualización.",
    "fechaCreacion": "2026-10-13T22:06:10.855799",
    "fechaActualizacion": "2026-10-13T22:06:10.855799",
    "estado": "ABIERTO",
    "prioridad": "ALTA",
    "cliente": {
      "id": 11,
      "nombre": "Carlos Mendoza",
      "email": "carlos.mendoza@empresa.com",
      "telefono": "+51 987654321",
      "empresa": "Tech Solutions S.A.C.",
      "departamento": "Contabilidad"
    },
    "tecnicoAsignado": null
  },
  "timestamp": "2026-10-13T22:06:10.8573061"
}
```

---

## 🧪 Pruebas Funcionales con Postman

En la carpeta `/postman` se encuentra el archivo listo para importar:
`postman/SoporteTecnico_API.postman_collection.json`

Contiene pruebas agrupadas en:
1. **Casos de Éxito:** Flujo CRUD completo (Registro, Consulta general, Consulta individual, Filtro por estado, Actualización, Asignación de técnico y Eliminación).
2. **Casos de Error:**
   - Error de validación 400 Bad Request (campos vacíos, correo inválido, longitud insuficiente).
   - Error 404 Not Found (recurso inexistente).
   - Error 400 Regla de Negocio (intento de eliminar una solicitud en atención activa).

---

## 🛡️ Manejo Centralizado de Excepciones

Implementado mediante `@RestControllerAdvice` en `GlobalExceptionHandler`:
- **400 Bad Request (`MethodArgumentNotValidException`):** Devuelve un mapa detallado con cada campo fallido y su mensaje explicativo.
- **400 Bad Request (`BusinessRuleException`):** Protege la lógica del negocio ante acciones inválidas.
- **404 Not Found (`ResourceNotFoundException`):** Mensaje claro cuando un ID solicitado no existe.
- **500 Internal Server Error:** Atrapa cualquier error no controlado manteniendo una estructura JSON homogénea.

---

## 👥 Equipo de Desarrollo y Roles

| Integrante | Rol en el Proyecto | Responsabilidades Principales |
| :--- | :--- | :--- |
| **Integrante 1** | Líder Técnico & Arquitecto Backend | Modelado de clases POO (Herencia y Abstracción), configuración de Maven, estructura de capas y DTOs. |
| **Integrante 2** | Desarrollador Backend & Integración | Implementación de Servicios, Controlador RESTful, reglas de negocio y persistencia en memoria. |
| **Integrante 3** | QA & Documentación | Configuración de Swagger/OpenAPI, validaciones, creación de la suite Postman y elaboración del Informe Técnico. |

