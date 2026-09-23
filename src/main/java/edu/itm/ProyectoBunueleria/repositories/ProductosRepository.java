package edu.itm.ProyectoBunueleria.repositories;

import edu.itm.ProyectoBunueleria.identities.Producto;
import edu.itm.ProyectoBunueleria.utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductosRepository {

    @Autowired
    private ProductosDAOHelper helper;

    public List<Producto> getProductos() {
        List<Producto> productos = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        if (con == null) {
            return productos;
        }

        try {
            PreparedStatement ps = con.prepareStatement(helper.listarProductos());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto producto = construirProducto(rs);
                productos.add(producto);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return productos;
    }

    public Producto insertarProducto(Producto producto) {
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        if (con == null) {
            return null;
        }

        try {
            PreparedStatement ps = con.prepareStatement(helper.insertarProducto(), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setInt(3, producto.getIdCategoria());
            ps.setString(4, producto.getEstado());
            ps.setFloat(5, producto.getCosto());
            ps.setFloat(6, producto.getPrecioVenta());
            ps.setInt(7, producto.getStockMinimo());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                ResultSet claves = ps.getGeneratedKeys();
                if (claves.next()) {
                    producto.setIdProducto(claves.getInt(1));
                }
            } else {
                producto = null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            producto = null;
        } finally {
            cerrarConexion(con);
        }
        return producto;
    }

    public Producto actualizarProducto(Producto producto) {
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        if (con == null) {
            return null;
        }

        try {
            PreparedStatement ps = con.prepareStatement(helper.actualizarProducto());
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setInt(3, producto.getIdCategoria());
            ps.setString(4, producto.getEstado());
            ps.setFloat(5, producto.getCosto());
            ps.setFloat(6, producto.getPrecioVenta());
            ps.setInt(7, producto.getStockMinimo());
            ps.setInt(8, producto.getIdProducto());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                producto = null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            producto = null;
        } finally {
            cerrarConexion(con);
        }
        return producto;
    }

    public Producto getProducto(Integer id) {
        Producto result = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        if (con == null) {
            return null;
        }

        try {
            PreparedStatement ps = con.prepareStatement(helper.getProducto());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = construirProducto(rs);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return result;
    }

    public Producto desactivarProducto(Integer id) {
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        if (con == null) {
            return null;
        }

        try {
            PreparedStatement ps = con.prepareStatement(helper.desactivarProducto());
            ps.setInt(1, id);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                return getProducto(id);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return null;
    }

    private Producto construirProducto(ResultSet rs) throws SQLException {
        return Producto.builder()
                .idProducto(rs.getInt("id_producto"))
                .nombre(rs.getString("nombre"))
                .descripcion(rs.getString("descripcion"))
                .idCategoria(rs.getInt("id_categoria"))
                .estado(rs.getString("estado"))
                .costo(rs.getFloat("costo"))
                .precioVenta(rs.getFloat("precio_venta"))
                .stockMinimo(rs.getInt("stock_minimo"))
                .build();
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
