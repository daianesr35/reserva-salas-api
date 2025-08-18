package com.seuprojeto.reserva_salas_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Reservas de Salas",
                version = "1.0",
                description = "API REST para cadastro de salas, reservas e autenticação",
                contact = @Contact(name = "IFSertão-PE", email = "admin@ifsertao-pe.com")
        ),
        // Define que, por padrão, os endpoints exigem o esquema bearerAuth (JWT)
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
    // Sem métodos: as anotações já registram o esquema de segurança.
}
