package edu.itm.ProyectoBunueleria.services;

import edu.itm.ProyectoBunueleria.identities.Inventario;
import edu.itm.ProyectoBunueleria.identities.MovimientoInventario;

public interface InventarioServiceInterface {
    Inventario consultarInventario(Integer idProducto);
    MovimientoInventario registrarMovimiento(MovimientoInventario movimiento);

}
