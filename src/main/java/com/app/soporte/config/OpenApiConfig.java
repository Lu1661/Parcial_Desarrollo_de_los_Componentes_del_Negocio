package com.app.soporte.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI 3 / Swagger para documentar los endpoints de la API REST.
 * Cumple con el Criterio 5.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST de Gestión de Solicitudes de Soporte Técnico")
                        .version("1.0.0")
                        .description("API construida con Java 21 y Spring Boot 3 para el registro, consulta, atención y seguimiento de solicitudes de soporte técnico empresarial.")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo Backend - Zegel")
                                .email("soporte.tecnico@zegel.pe"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}

