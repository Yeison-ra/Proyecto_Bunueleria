package edu.itm.ProyectoBunueleria.services;

import edu.itm.ProyectoBunueleria.jpaEntities.Producto;
import edu.itm.ProyectoBunueleria.repositories.ProductosRepositoryInterface;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductosService implements ProductosServiceInterface {
    private final ProductosRepositoryInterface repository;
    public ProductosService(ProductosRepositoryInterface repository) { this.repository = repository; }
    @Override public List<Producto> getProductos() { return repository.getProductos(); }
    @Override public Producto insertarProducto(Producto producto) {
        producto.setIdProducto(null);
        if (producto.getEstado() == null || producto.getEstado().isBlank()) producto.setEstado("ACTIVO");
        return repository.insertarProducto(producto);
    }
    @Override public Producto actualizarProducto(Producto producto) {
        if (producto.getIdProducto() == null) return null;
        return repository.actualizarProducto(producto);
    }
    @Override public Producto getProducto(Integer id) { return repository.getProducto(id); }
    @Override public Producto desactivarProducto(Integer id) { return repository.desactivarProducto(id); }
}
