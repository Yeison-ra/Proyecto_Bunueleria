package edu.itm.ProyectoBunueleria.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    private Connection con;

    /*
     * IMPORTANTE:
     * Cambiar USUARIO y CLAVE según la configuración local de MySQL.
     * Se conserva una conexión JDBC directa, igual al enfoque visto en clase.
     */
    private static final String URL = "jdbc:mysql://localhost:3306/bunueleria";
    private static final String USUARIO = "root";
    private static final String CLAVE = "1096*Soyfeliz2026+";

    public Connection obtenerConexion() {
        try {
            con = DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return con;
    }
}
