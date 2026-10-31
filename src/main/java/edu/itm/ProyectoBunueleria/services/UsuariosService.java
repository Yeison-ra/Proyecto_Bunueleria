package edu.itm.ProyectoBunueleria.services;

import edu.itm.ProyectoBunueleria.identities.Usuario;
import edu.itm.ProyectoBunueleria.repositories.UsuariosRepositoryInterface;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsuariosService implements UsuariosServiceInterface, UserDetailsService {

    private final UsuariosRepositoryInterface usuariosRepository;

    public UsuariosService(UsuariosRepositoryInterface usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuariosRepository.getUsuarioPorEmail(email);

        if (usuario == null || usuario.getEmail() == null || usuario.getPassword() == null) {
            throw new UsernameNotFoundException(
                    "No existe un usuario activo registrado con el correo: " + email
            );
        }

        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                Collections.emptyList()
        );
    }
}
