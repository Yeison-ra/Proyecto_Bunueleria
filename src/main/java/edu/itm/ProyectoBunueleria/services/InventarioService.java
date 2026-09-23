package edu.itm.ProyectoBunueleria.services;

import edu.itm.ProyectoBunueleria.identities.Inventario;
import edu.itm.ProyectoBunueleria.identities.MovimientoInventario;
import edu.itm.ProyectoBunueleria.repositories.InventarioRepository;
import org.springframework.stereotype.Service;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public Inventario consultarInventario(Integer idProducto) {
        return inventarioRepository.consultarInventario(idProducto);
    }

    public MovimientoInventario registrarMovimiento(MovimientoInventario movimiento) {
        return inventarioRepository.registrarMovimiento(movimiento);
    }
}
