package com.everylog.Everylog.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
    name = "bearerAuth", // Name of the security scheme
    type = SecuritySchemeType.HTTP, // Type of the security scheme
    scheme = "bearer", // Scheme (e.g., bearer)
    bearerFormat = "JWT" // Format of the token
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // Add security requirement
                .components(new Components());
    }
}