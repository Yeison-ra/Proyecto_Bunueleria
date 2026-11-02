package edu.itm.ProyectoBunueleria.repositories;

import edu.itm.ProyectoBunueleria.identities.Usuario;
import edu.itm.ProyectoBunueleria.utilities.Conexion;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UsuariosRepository implements UsuariosRepositoryInterface {

    private final UsuariosDAOHelper helper;

    public UsuariosRepository(UsuariosDAOHelper helper) {
        this.helper = helper;
    }

    @Override
    public Usuario getUsuarioPorEmail(String email) {
        try (Connection con = new Conexion().obtenerConexion()) {
            if (con == null) {
                return null;
            }

            try (PreparedStatement ps = con.prepareStatement(helper.getUsuarioPorEmail())) {
                ps.setString(1, email);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return null;
                    }

                    return Usuario.builder()
                            .idUsuario(rs.getInt("id_usuario"))
                            .nombre(rs.getString("nombre"))
                            .email(rs.getString("email"))
                            .password(rs.getString("password"))
                            .estado(rs.getString("estado"))
                            .build();
                }
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("No fue posible consultar el usuario", exception);
        }
    }
}
