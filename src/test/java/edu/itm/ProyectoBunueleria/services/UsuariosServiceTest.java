package edu.itm.ProyectoBunueleria.services;
import edu.itm.ProyectoBunueleria.identities.Usuario;
import edu.itm.ProyectoBunueleria.repositories.UsuariosRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UsuariosServiceTest {
 @Mock UsuariosRepositoryInterface repository;
 @InjectMocks UsuariosService service;
 @Test void usuarioExistente(){ when(repository.getUsuarioPorEmail("a@ejemplo.com")).thenReturn(Usuario.builder().email("a@ejemplo.com").password("hash").build()); assertEquals("a@ejemplo.com",service.loadUserByUsername("a@ejemplo.com").getUsername()); }
 @Test void usuarioInexistente(){ when(repository.getUsuarioPorEmail("x")).thenReturn(null); assertThrows(UsernameNotFoundException.class,()->service.loadUserByUsername("x")); }
 @Test void usuarioSinPassword(){ when(repository.getUsuarioPorEmail("x")).thenReturn(Usuario.builder().email("x").build()); assertThrows(UsernameNotFoundException.class,()->service.loadUserByUsername("x")); }
}
