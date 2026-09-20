package edu.itm.ProyectoBunueleria.services;

import edu.itm.ProyectoBunueleria.identities.Producto;
import edu.itm.ProyectoBunueleria.repositories.ProductosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductosService {

    private final ProductosRepository productosRepository;

    public ProductosService(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }

    public List<Producto> getProductos() {
        return productosRepository.getProductos();
    }

    public Producto insertarProducto(Producto producto) {
        return productosRepository.insertarProducto(producto);
    }

    public Producto actualizarProducto(Producto producto) {
        return productosRepository.actualizarProducto(producto);
    }

    public Producto getProducto(Integer id) {
        return productosRepository.getProducto(id);
    }

    public Producto desactivarProducto(Integer id) {
        return productosRepository.desactivarProducto(id);
    }
}
