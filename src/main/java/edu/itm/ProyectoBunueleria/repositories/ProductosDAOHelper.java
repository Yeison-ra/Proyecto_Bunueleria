package edu.itm.ProyectoBunueleria.repositories;

import org.springframework.stereotype.Component;

@Component
public class ProductosDAOHelper {

    public String listarProductos() {
        return "SELECT id_producto, nombre, descripcion, id_categoria, estado, costo, precio_venta, stock_minimo " +
                "FROM producto ORDER BY id_producto";
    }

    public String insertarProducto() {
        return "INSERT INTO producto (nombre, descripcion, id_categoria, estado, costo, precio_venta, stock_minimo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    public String actualizarProducto() {
        return "UPDATE producto SET nombre = ?, descripcion = ?, id_categoria = ?, estado = ?, costo = ?, " +
                "precio_venta = ?, stock_minimo = ? WHERE id_producto = ?";
    }

    public String getProducto() {
        return "SELECT id_producto, nombre, descripcion, id_categoria, estado, costo, precio_venta, stock_minimo " +
                "FROM producto WHERE id_producto = ?";
    }

    public String desactivarProducto() {
        return "UPDATE producto SET estado = 'INACTIVO' WHERE id_producto = ?";
    }
}
