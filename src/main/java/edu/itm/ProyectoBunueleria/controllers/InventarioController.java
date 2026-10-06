package edu.itm.ProyectoBunueleria.controllers;

import edu.itm.ProyectoBunueleria.identities.Inventario;
import edu.itm.ProyectoBunueleria.identities.MovimientoInventario;
import edu.itm.ProyectoBunueleria.services.InventarioServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario")
public class InventarioController implements InventarioApi{

    private static final Logger logger = LoggerFactory.getLogger(InventarioController.class);
    private final InventarioServiceInterface service;

    public InventarioController(InventarioServiceInterface service) {
        this.service = service;
    }

    public ResponseEntity<Inventario> consultarInventario(@PathVariable Integer idProducto) {
        if (idProducto == null || idProducto <= 0) {
            return new ResponseEntity<>(new Inventario(), HttpStatus.BAD_REQUEST);
        }

        try {
            Inventario inventario = service.consultarInventario(idProducto);
            if (inventario != null) {
                return new ResponseEntity<>(inventario, HttpStatus.OK);
            }
            return new ResponseEntity<>(new Inventario(), HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            logger.error("Error al consultar el inventario del producto {}", idProducto, exception);
            return new ResponseEntity<>(new Inventario(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
        movimiento.setTipoMovimiento(tipo);

        try {
            MovimientoInventario result = service.registrarMovimiento(movimiento);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            }

            // Puede ocurrir si el producto no existe o se intenta una SALIDA
            // superior al inventario disponible.
            return new ResponseEntity<>(movimiento, HttpStatus.CONFLICT);
        } catch (Exception exception) {
            logger.error("Error al registrar movimiento de inventario para el producto {}",
                    movimiento.getIdProducto(), exception);
            return new ResponseEntity<>(movimiento, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}