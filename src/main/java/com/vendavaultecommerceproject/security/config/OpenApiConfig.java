package com.vendavaultecommerceproject.security.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(
        contact = @Contact(
                name = "Elijah Ukeme",
                email = "ukemedmet@gmail.com"
        ),
        description = "Open Api documentation for Vandavault Ecommerce API",
        title = "Open Api Specification",
        version = "1.0"
),
        servers = {
        @Server(
                description = "LOCAL ENVIRONMENT",
                url = "http://localhost:8080"
        ),
                @Server(
                        description = "PRODUCTION ENVIRONMENT",
                        url = "https://"
                )
        },
        security = @SecurityRequirement(name = "BearerAuth"))
@SecurityScheme(name = "BearerAuth",
                description = "Jwt Bearer Authentication",
                 scheme = "bearer",
                  type = SecuritySchemeType.HTTP,
                  bearerFormat = "JWT",
                  in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {
}
