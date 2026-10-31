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
