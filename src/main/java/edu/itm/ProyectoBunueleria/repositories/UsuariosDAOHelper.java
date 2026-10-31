package edu.itm.ProyectoBunueleria.repositories;

import org.springframework.stereotype.Component;

@Component
public class UsuariosDAOHelper {

    public String getUsuarioPorEmail() {
        return "SELECT id_usuario, nombre, email, password, estado " +
                "FROM usuario WHERE email = ? AND estado = 'ACTIVO'";
    }
}
