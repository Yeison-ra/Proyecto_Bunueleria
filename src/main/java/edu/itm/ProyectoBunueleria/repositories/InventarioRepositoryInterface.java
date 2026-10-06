package edu.itm.ProyectoBunueleria.repositories;

import edu.itm.ProyectoBunueleria.identities.Inventario;
import edu.itm.ProyectoBunueleria.identities.MovimientoInventario;

public interface InventarioRepositoryInterface {
    Inventario consultarInventario(Integer idProducto);
    MovimientoInventario registrarMovimiento(MovimientoInventario movimiento);
}
