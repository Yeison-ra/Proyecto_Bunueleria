package edu.itm.ProyectoBunueleria.controllers;

import edu.itm.ProyectoBunueleria.identities.Inventario;
import edu.itm.ProyectoBunueleria.identities.MovimientoInventario;
import edu.itm.ProyectoBunueleria.services.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService service;

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<Inventario> consultarInventario(@PathVariable Integer idProducto) {
        if (idProducto == null || idProducto <= 0) {
            return new ResponseEntity<>(new Inventario(), HttpStatus.BAD_REQUEST);
        }

        Inventario inventario = service.consultarInventario(idProducto);
        if (inventario != null) {
            return new ResponseEntity<>(inventario, HttpStatus.OK);
        }
        return new ResponseEntity<>(new Inventario(), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/movimiento")
    public ResponseEntity<MovimientoInventario> registrarMovimiento(@RequestBody MovimientoInventario movimiento) {
        if (ObjectUtils.isEmpty(movimiento)
                || movimiento.getIdProducto() == null
                || movimiento.getCantidad() == null
                || movimiento.getCantidad() <= 0
                || ObjectUtils.isEmpty(movimiento.getTipoMovimiento())) {
            return new ResponseEntity<>(movimiento, HttpStatus.BAD_REQUEST);
        }

        String tipo = movimiento.getTipoMovimiento().trim().toUpperCase();
        if (!"ENTRADA".equals(tipo) && !"SALIDA".equals(tipo)) {
            return new ResponseEntity<>(movimiento, HttpStatus.BAD_REQUEST);
        }

        MovimientoInventario result = service.registrarMovimiento(movimiento);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }

        // Puede ocurrir si el producto no existe, hay un error de BD o se intenta
        // una SALIDA superior al inventario disponible.
        return new ResponseEntity<>(movimiento, HttpStatus.CONFLICT);
    }
}
