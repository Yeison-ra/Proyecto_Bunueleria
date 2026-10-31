package edu.itm.ProyectoBunueleria.services;

import edu.itm.ProyectoBunueleria.jpaEntities.Producto;
import edu.itm.ProyectoBunueleria.jpaRepositories.ProductoJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductosService implements ProductosServiceInterface {

    private final ProductoJpaRepository productoRepository;

    public ProductosService(ProductoJpaRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto insertarProducto(Producto producto) {
        producto.setIdProducto(null);
        if (producto.getEstado() == null || producto.getEstado().isBlank()) {
            producto.setEstado("ACTIVO");
        }
        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizarProducto(Producto producto) {
        if (producto.getIdProducto() == null || !productoRepository.existsById(producto.getIdProducto())) {
            return null;
        }
        return productoRepository.save(producto);
    }

    @Override
    public Producto getProducto(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto desactivarProducto(Integer id) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setEstado("INACTIVO");
                    return productoRepository.save(producto);
                })
                .orElse(null);
    }
}
