package com.api.gateway.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Gateway",
        version = "v1.0",
        description = "RESTful API for managing Flights, Hotels, and Bookings.",
        contact = @Contact(name = "Sammy Njue", email = "sammynjue10@gmail.com"),
        license = @License(name = "MIT License", url = "https://github.com/samnjue/gateway")
    ),
    security = @SecurityRequirement(name = "basicAuth")
)
@SecurityScheme(
    name = "basicAuth", 
    type = SecuritySchemeType.HTTP,
    scheme = "basic"
)
public class OpenApiConfig {
    
}
