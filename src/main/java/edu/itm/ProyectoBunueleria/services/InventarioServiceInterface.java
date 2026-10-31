package edu.itm.ProyectoBunueleria.services;

import edu.itm.ProyectoBunueleria.jpaEntities.Inventario;
import edu.itm.ProyectoBunueleria.jpaEntities.MovimientoInventario;

public interface InventarioServiceInterface {
    Inventario consultarInventario(Integer idProducto);
    MovimientoInventario registrarMovimiento(MovimientoInventario movimiento);
}
