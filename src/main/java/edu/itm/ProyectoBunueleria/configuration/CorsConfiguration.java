package edu.itm.ProyectoBunueleria.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    //La configuración CORS (Intercambio de Recursos de Origen Cruzado)
    /*
        mecanismo de seguridad del navegador que permite a un servidor web
        aceptar o rechazar peticiones HTTP provenientes de dominios diferentes al suyo
     */

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Aplica la configuración de CORS a todos los endpoints (/**) de tu API
        registry.addMapping("/**")
                // * IMPORTANTE: Define aquí los orígenes permitidos. *
                // En producción, debes usar el dominio real de tu frontend.
                // El "*" es menos seguro, pero funciona para pruebas rápidas.
                .allowedOrigins(
                        "http://localhost:8091",
                        "http://localhost:8089",
                        "*"
                )
                // Define los métodos HTTP que tu frontend puede usar
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                // Permite todas las cabeceras
                .allowedHeaders("*")
                // No permite el envío de credenciales/cookies por defecto
                .allowCredentials(false)
                // Tiempo de cacheo para las peticiones preflight (OPTIONS)
                .maxAge(3600);
    }
}
