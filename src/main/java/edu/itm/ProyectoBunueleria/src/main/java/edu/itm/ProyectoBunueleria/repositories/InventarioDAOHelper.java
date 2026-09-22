package edu.itm.ProyectoBunueleria.repositories;

import org.springframework.stereotype.Component;

@Component
public class InventarioDAOHelper {

    public String consultarInventarioProducto() {
        return "SELECT id_inventario, id_producto, fecha_actualizacion, cantidad_actual " +
                "FROM inventario WHERE id_producto = ?";
    }

    public String consultarInventarioProductoBloqueado() {
        return "SELECT id_inventario, id_producto, fecha_actualizacion, cantidad_actual " +
                "FROM inventario WHERE id_producto = ? FOR UPDATE";
    }

    public String crearInventario() {
        return "INSERT INTO inventario (id_producto, fecha_actualizacion, cantidad_actual) VALUES (?, NOW(), 0)";
    }

    public String actualizarCantidadInventario() {
        return "UPDATE inventario SET cantidad_actual = ?, fecha_actualizacion = NOW() WHERE id_producto = ?";
    }

    public String insertarMovimiento() {
        return "INSERT INTO movimiento_inventario (id_producto, fecha, tipo_movimiento, motivo, cantidad, referencia) " +
                "VALUES (?, NOW(), ?, ?, ?, ?)";
    }
}
