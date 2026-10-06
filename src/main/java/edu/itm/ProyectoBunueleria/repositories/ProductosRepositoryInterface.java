package edu.itm.ProyectoBunueleria.repositories;

import edu.itm.ProyectoBunueleria.identities.Producto;
import java.util.List;

public interface ProductosRepositoryInterface {
    List<Producto> getProductos();
    Producto insertarProducto(Producto producto);
    Producto actualizarProducto(Producto producto);
    Producto getProducto(Integer id);
    Producto desactivarProducto(Integer id);
}
