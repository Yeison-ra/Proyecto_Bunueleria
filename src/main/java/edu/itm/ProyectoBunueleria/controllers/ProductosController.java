package edu.itm.ProyectoBunueleria.controllers;

import edu.itm.ProyectoBunueleria.identities.Producto;
import edu.itm.ProyectoBunueleria.services.ProductosServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductosController implements ProductosApi{

    private static final Logger logger = LoggerFactory.getLogger(ProductosController.class);
    private final ProductosServiceInterface service;

    public ProductosController(ProductosServiceInterface service) {
        this.service = service;
    }

    public ResponseEntity<List<Producto>> getProductos() {
        try {
            return new ResponseEntity<>(service.getProductos(), HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error al listar los productos", exception);
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Producto> getProducto(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(new Producto(), HttpStatus.BAD_REQUEST);
        }

        try {
            Producto result = service.getProducto(id);
            if (result != null && result.getIdProducto() != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(new Producto(), HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            logger.error("Error al consultar el producto {}", id, exception);
            return new ResponseEntity<>(new Producto(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Producto> insertarProducto(@RequestBody Producto producto) {
        if (!productoValido(producto)) {
            return new ResponseEntity<>(producto, HttpStatus.BAD_REQUEST);
        }

        if (ObjectUtils.isEmpty(producto.getEstado())) {
            producto.setEstado("ACTIVO");
        }

        try {
            Producto result = service.insertarProducto(producto);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(producto, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception exception) {
            logger.error("Error al insertar el producto {}", producto.getNombre(), exception);
            return new ResponseEntity<>(producto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Producto> actualizarProducto(@RequestBody Producto producto) {
        if (!productoValido(producto) || ObjectUtils.isEmpty(producto.getIdProducto())) {
            return new ResponseEntity<>(producto, HttpStatus.BAD_REQUEST);
        }

        try {
            Producto result = service.actualizarProducto(producto);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(producto, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception exception) {
            logger.error("Error al actualizar el producto {}", producto.getIdProducto(), exception);
            return new ResponseEntity<>(producto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Producto> desactivarProducto(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(new Producto(), HttpStatus.BAD_REQUEST);
        }

        try {
            Producto result = service.desactivarProducto(id);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(new Producto(), HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            logger.error("Error al desactivar el producto {}", id, exception);
            return new ResponseEntity<>(new Producto(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean productoValido(Producto producto) {
        return !ObjectUtils.isEmpty(producto)
                && !ObjectUtils.isEmpty(producto.getNombre())
                && producto.getIdCategoria() != null
                && producto.getCosto() != null
                && producto.getPrecioVenta() != null
                && producto.getStockMinimo() != null;
    }
}