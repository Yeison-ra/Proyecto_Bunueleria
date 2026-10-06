package edu.itm.ProyectoBunueleria.controllers;

import edu.itm.ProyectoBunueleria.identities.Producto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProductosApi {

    @Operation(tags = {"Productos"}, summary = "Obtiene el total de los productos registrados",
            description = "Obtiene la lista de los productos registrados en la base de datos",
            responses = {@ApiResponse(responseCode = "200", description = "Productos obtenidos correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Producto.class)))})
    @GetMapping("/listar")
    ResponseEntity<List<Producto>> getProductos();

    @Operation(tags = {"Productos"}, summary = "Obtiene un producto", description = "Obtiene un producto dado su id")
    @GetMapping("/consultar/{id}")
    ResponseEntity<Producto> getProducto(@PathVariable Integer id);

    @Operation(tags = {"Productos"}, summary = "Crear nuevo producto", description = "Inserta un nuevo producto en la base de datos")
    @PostMapping("/nuevo")
    ResponseEntity<Producto> insertarProducto(@RequestBody Producto producto);

    @Operation(tags = {"Productos"}, summary = "Actualizar producto", description = "Actualiza un producto en la base de datos")
    @PutMapping("/actualizar")
    ResponseEntity<Producto> actualizarProducto(@RequestBody Producto producto);

    @Operation(tags = {"Productos"}, summary = "Desactivar producto", description = "Cambia el estado del producto a INACTIVO")
    @PatchMapping("/desactivar/{id}")
    ResponseEntity<Producto> desactivarProducto(@PathVariable Integer id);
}
