package edu.itm.ProyectoBunueleria.repositories;

import edu.itm.ProyectoBunueleria.identities.Inventario;
import edu.itm.ProyectoBunueleria.identities.MovimientoInventario;
import edu.itm.ProyectoBunueleria.utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class InventarioRepository {

    @Autowired
    private InventarioDAOHelper helper;

    public Inventario consultarInventario(Integer idProducto) {
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();
        if (con == null) {
            return null;
        }

        try {
            PreparedStatement ps = con.prepareStatement(helper.consultarInventarioProducto());
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Inventario.builder()
                        .idInventario(rs.getInt("id_inventario"))
                        .idProducto(rs.getInt("id_producto"))
                        .fechaActualizacion(rs.getTimestamp("fecha_actualizacion").toLocalDateTime())
                        .cantidadActual(rs.getInt("cantidad_actual"))
                        .build();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return null;
    }

    public MovimientoInventario registrarMovimiento(MovimientoInventario movimiento) {
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();
        if (con == null) {
            return null;
        }

        try {
            con.setAutoCommit(false);

            int cantidadActual = obtenerOCrearCantidadActual(con, movimiento.getIdProducto());
            String tipo = movimiento.getTipoMovimiento().trim().toUpperCase();
            int nuevaCantidad;

            if ("ENTRADA".equals(tipo)) {
                nuevaCantidad = cantidadActual + movimiento.getCantidad();
            } else if ("SALIDA".equals(tipo)) {
                if (movimiento.getCantidad() > cantidadActual) {
                    con.rollback();
                    return null;
                }
                nuevaCantidad = cantidadActual - movimiento.getCantidad();
            } else {
                con.rollback();
                return null;
            }

            PreparedStatement actualizar = con.prepareStatement(helper.actualizarCantidadInventario());
            actualizar.setInt(1, nuevaCantidad);
            actualizar.setInt(2, movimiento.getIdProducto());
            actualizar.executeUpdate();

            PreparedStatement insertar = con.prepareStatement(helper.insertarMovimiento(), Statement.RETURN_GENERATED_KEYS);
            insertar.setInt(1, movimiento.getIdProducto());
            insertar.setString(2, tipo);
            insertar.setString(3, movimiento.getMotivo());
            insertar.setInt(4, movimiento.getCantidad());
            insertar.setString(5, movimiento.getReferencia());
            insertar.executeUpdate();

            ResultSet claves = insertar.getGeneratedKeys();
            if (claves.next()) {
                movimiento.setIdMovimiento(claves.getInt(1));
            }
            movimiento.setTipoMovimiento(tipo);
            movimiento.setFecha(java.time.LocalDateTime.now());

            con.commit();
            return movimiento;
        } catch (Exception exception) {
            try {
                con.rollback();
            } catch (SQLException ignored) {
            }
            exception.printStackTrace();
            return null;
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
            cerrarConexion(con);
        }
    }

    private int obtenerOCrearCantidadActual(Connection con, Integer idProducto) throws SQLException {
        PreparedStatement consultar = con.prepareStatement(helper.consultarInventarioProductoBloqueado());
        consultar.setInt(1, idProducto);
        ResultSet rs = consultar.executeQuery();
        if (rs.next()) {
            return rs.getInt("cantidad_actual");
        }

        PreparedStatement crear = con.prepareStatement(helper.crearInventario());
        crear.setInt(1, idProducto);
        crear.executeUpdate();
        return 0;
    }

    private void cerrarConexion(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }
}
