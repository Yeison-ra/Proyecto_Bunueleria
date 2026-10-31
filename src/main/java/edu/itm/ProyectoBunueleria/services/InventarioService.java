package edu.itm.ProyectoBunueleria.services;

import edu.itm.ProyectoBunueleria.jpaEntities.Inventario;
import edu.itm.ProyectoBunueleria.jpaEntities.MovimientoInventario;
import edu.itm.ProyectoBunueleria.jpaRepositories.InventarioJpaRepository;
import edu.itm.ProyectoBunueleria.jpaRepositories.MovimientoInventarioJpaRepository;
import edu.itm.ProyectoBunueleria.jpaRepositories.ProductoJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventarioService implements InventarioServiceInterface {

    private final InventarioJpaRepository inventarioRepository;
    private final MovimientoInventarioJpaRepository movimientoRepository;
    private final ProductoJpaRepository productoRepository;

    public InventarioService(InventarioJpaRepository inventarioRepository,
                             MovimientoInventarioJpaRepository movimientoRepository,
                             ProductoJpaRepository productoRepository) {
        this.inventarioRepository = inventarioRepository;
        this.movimientoRepository = movimientoRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Inventario consultarInventario(Integer idProducto) {
        return inventarioRepository.findByIdProducto(idProducto).orElse(null);
    }

    @Override
    @Transactional
    public MovimientoInventario registrarMovimiento(MovimientoInventario movimiento) {
        if (movimiento == null
                || movimiento.getIdProducto() == null
                || movimiento.getCantidad() == null
                || movimiento.getCantidad() <= 0
                || movimiento.getTipoMovimiento() == null) {
            return null;
        }

        if (!productoRepository.existsById(movimiento.getIdProducto())) {
            return null;
        }

        String tipo = movimiento.getTipoMovimiento().trim().toUpperCase();
        if (!"ENTRADA".equals(tipo) && !"SALIDA".equals(tipo)) {
            return null;
        }

        Inventario inventario = inventarioRepository
                .findByIdProductoForUpdate(movimiento.getIdProducto())
                .orElseGet(() -> Inventario.builder()
                        .idProducto(movimiento.getIdProducto())
                        .cantidadActual(0)
                        .build());

        int cantidadActual = inventario.getCantidadActual() == null ? 0 : inventario.getCantidadActual();
        int nuevaCantidad;

        if ("ENTRADA".equals(tipo)) {
            nuevaCantidad = cantidadActual + movimiento.getCantidad();
        } else {
            if (movimiento.getCantidad() > cantidadActual) {
                return null;
            }
            nuevaCantidad = cantidadActual - movimiento.getCantidad();
        }

        inventario.setCantidadActual(nuevaCantidad);
        inventarioRepository.save(inventario);

        movimiento.setIdMovimiento(null);
        movimiento.setTipoMovimiento(tipo);
        return movimientoRepository.save(movimiento);
    }
}
