package edu.itm.ProyectoBunueleria.services;

import edu.itm.ProyectoBunueleria.identities.Producto;

import java.util.List;

public interface ProductosServiceInterface {
    List<Producto> getProductos();
    Producto insertarProducto(Producto producto);
    Producto actualizarProducto(Producto producto);
    Producto getProducto(Integer id);
    Producto desactivarProducto(Integer id);

}
