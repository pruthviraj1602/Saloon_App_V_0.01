package com.maven;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@OpenAPIDefinition(
        info = @Info(
                title = "Saloon project REST API Documentation",
                description = "Saloon application REST API Documentation",
                version="v1",
                contact = @Contact(
                        name = "Code-crafter",
                        email = "info@code-crafter.in",
                        url = "https://code-crafter.in"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Saloon application REST API Documentation",
                url = "https://code-crafter.in"
        )
)
@SpringBootApplication
public class SalonBackendApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SalonBackendApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(SalonBackendApplication.class);
    }
}
