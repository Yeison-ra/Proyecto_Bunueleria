package edu.itm.ProyectoBunueleria.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("API Proyecto Buñuelería")
                        .version("1.0.0")
                        .description("Documentación de los servicios REST del proyecto Buñuelería.")
                        .contact(new Contact().name("Proyecto Buñuelería")));
    }
}
