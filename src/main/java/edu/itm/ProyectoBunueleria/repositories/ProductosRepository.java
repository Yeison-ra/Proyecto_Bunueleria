package edu.itm.ProyectoBunueleria.repositories;

import edu.itm.ProyectoBunueleria.jpaEntities.Producto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/** Acceso JDBC: JdbcTemplate administra y cierra los recursos de cada operación. */
@Repository
public class ProductosRepository implements ProductosRepositoryInterface {
    private final JdbcTemplate jdbc;
    private final ProductosDAOHelper sql;
    public ProductosRepository(JdbcTemplate jdbc, ProductosDAOHelper sql) { this.jdbc = jdbc; this.sql = sql; }
    private Producto mapear(java.sql.ResultSet rs, int fila) throws java.sql.SQLException {
        return Producto.builder().idProducto(rs.getInt("id_producto")).nombre(rs.getString("nombre"))
                .descripcion(rs.getString("descripcion")).idCategoria(rs.getInt("id_categoria"))
                .estado(rs.getString("estado")).costo(rs.getFloat("costo"))
                .precioVenta(rs.getFloat("precio_venta")).stockMinimo(rs.getInt("stock_minimo")).build();
    }
    @Override public List<Producto> getProductos() { return jdbc.query(sql.listarProductos(), this::mapear); }
    @Override public Producto getProducto(Integer id) {
        return jdbc.query(sql.getProducto(), this::mapear, id).stream().findFirst().orElse(null);
    }
    @Override public Producto insertarProducto(Producto p) {
        KeyHolder claves = new GeneratedKeyHolder();
        int filas = jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql.insertarProducto(), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getNombre()); ps.setString(2, p.getDescripcion());
            ps.setInt(3, p.getIdCategoria()); ps.setString(4, p.getEstado());
            ps.setFloat(5, p.getCosto()); ps.setFloat(6, p.getPrecioVenta()); ps.setInt(7, p.getStockMinimo());
            return ps;
        }, claves);
        if (filas == 0 || claves.getKey() == null) throw new IllegalStateException("No fue posible registrar el producto");
        p.setIdProducto(claves.getKey().intValue()); return p;
    }
    @Override public Producto actualizarProducto(Producto p) {
        int filas = jdbc.update(sql.actualizarProducto(), p.getNombre(), p.getDescripcion(), p.getIdCategoria(),
                p.getEstado(), p.getCosto(), p.getPrecioVenta(), p.getStockMinimo(), p.getIdProducto());
        return filas == 0 ? null : getProducto(p.getIdProducto());
    }
    @Override public Producto desactivarProducto(Integer id) {
        return jdbc.update(sql.desactivarProducto(), id) == 0 ? null : getProducto(id);
    }
}
