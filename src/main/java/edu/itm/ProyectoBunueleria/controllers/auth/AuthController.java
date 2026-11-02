package edu.itm.ProyectoBunueleria.controllers.auth;

import edu.itm.ProyectoBunueleria.services.UsuariosServiceInterface;
import edu.itm.ProyectoBunueleria.utilities.JwtUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuariosServiceInterface service;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(UsuariosServiceInterface service,
                          PasswordEncoder passwordEncoder,
                          JwtUtils jwtUtils) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<Map<String, String>> credencialesInvalidas(RuntimeException error) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Correo o contraseña incorrectos"));
    }

    @Operation(tags = "Autenticación", summary = "Iniciar sesión y obtener un token JWT",
        description = "Enviar email y password como parámetros; usar el token como Bearer en las operaciones protegidas.",
        responses = {@ApiResponse(responseCode="200", description="Token generado"),
                     @ApiResponse(responseCode="400", description="Faltan parámetros", content=@Content),
                     @ApiResponse(responseCode="401", description="Credenciales incorrectas", content=@Content),
                     @ApiResponse(responseCode="500", description="Error interno", content=@Content)})
    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String email,
                                     @RequestParam String password) {

        UserDetails userDetails = service.loadUserByUsername(email);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Correo o contraseña incorrectos");
        }

        String token = jwtUtils.generateToken(userDetails.getUsername());
        return Map.of("token", token);
    }
}
