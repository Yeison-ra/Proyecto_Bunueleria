package edu.itm.ProyectoBunueleria.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    private Connection con;

    /*
     * Conexión JDBC directa, siguiendo el enfoque visto en clase.
     * Las credenciales pueden configurarse mediante variables de entorno.
     * Si no se definen, se usan valores locales de desarrollo.
     */
    private static final String URL = obtenerVariable("BUNUELERIA_DB_URL", "jdbc:mysql://localhost:3306/bunueleria");
    private static final String USUARIO = obtenerVariable("BUNUELERIA_DB_USER", "root");
    private static final String CLAVE = obtenerVariable("BUNUELERIA_DB_PASSWORD", "admin");

    private static String obtenerVariable(String nombre, String valorPorDefecto) {
        String valor = System.getenv(nombre);
        return (valor == null || valor.isBlank()) ? valorPorDefecto : valor;
    }

    public Connection obtenerConexion() {
        try {
            con = DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, "No fue posible conectar con MySQL", ex);
        }
        return con;
    }
}
