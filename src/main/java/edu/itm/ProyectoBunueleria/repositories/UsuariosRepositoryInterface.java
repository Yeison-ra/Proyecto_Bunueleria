package edu.itm.ProyectoBunueleria.repositories;

import edu.itm.ProyectoBunueleria.identities.Usuario;

public interface UsuariosRepositoryInterface {

    Usuario getUsuarioPorEmail(String email);
}
