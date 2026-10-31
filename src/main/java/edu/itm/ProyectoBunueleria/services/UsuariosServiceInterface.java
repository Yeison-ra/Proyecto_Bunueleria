package edu.itm.ProyectoBunueleria.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface UsuariosServiceInterface {

    UserDetails loadUserByUsername(String email);
}
