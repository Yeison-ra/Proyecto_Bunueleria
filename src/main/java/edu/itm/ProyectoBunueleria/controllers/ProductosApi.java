package edu.itm.ProyectoBunueleria.controllers;

import edu.itm.ProyectoBunueleria.jpaEntities.Producto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
public interface ProductosApi {

    @Operation(tags = {"Productos"}, summary = "Obtiene el total de los productos registrados",
            description = "Obtiene la lista de los productos registrados en la base de datos",
            responses = {@ApiResponse(responseCode = "200", description = "Productos obtenidos correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Producto.class)))})
    @ApiResponses({@ApiResponse(responseCode="200", description="Consulta exitosa"), @ApiResponse(responseCode="204", description="No hay resultados", content=@Content), @ApiResponse(responseCode="400", description="Datos inválidos", content=@Content), @ApiResponse(responseCode="401", description="Autenticación requerida", content=@Content), @ApiResponse(responseCode="403", description="Acceso denegado", content=@Content), @ApiResponse(responseCode="500", description="Error interno", content=@Content)})
    @GetMapping("/listar")
    ResponseEntity<List<Producto>> getProductos();

    @Operation(tags = {"Productos"}, summary = "Obtiene un producto", description = "Obtiene un producto dado su id")
    @ApiResponses({@ApiResponse(responseCode="200", description="Consulta exitosa"), @ApiResponse(responseCode="204", description="No hay resultados", content=@Content), @ApiResponse(responseCode="400", description="Datos inválidos", content=@Content), @ApiResponse(responseCode="401", description="Autenticación requerida", content=@Content), @ApiResponse(responseCode="403", description="Acceso denegado", content=@Content), @ApiResponse(responseCode="500", description="Error interno", content=@Content)})
    @GetMapping("/consultar/{id}")
    ResponseEntity<Producto> getProducto(@PathVariable Integer id);

    @Operation(tags = {"Productos"}, summary = "Crear nuevo producto", description = "Inserta un nuevo producto en la base de datos")
    @ApiResponses({@ApiResponse(responseCode="201", description="Registro creado"), @ApiResponse(responseCode="400", description="Datos inválidos", content=@Content), @ApiResponse(responseCode="401", description="Autenticación requerida", content=@Content), @ApiResponse(responseCode="403", description="Acceso denegado", content=@Content), @ApiResponse(responseCode="406", description="No fue posible crear el producto", content=@Content), @ApiResponse(responseCode="500", description="Error interno", content=@Content)})
    @PostMapping("/nuevo")
    ResponseEntity<Producto> insertarProducto(@RequestBody Producto producto);

    @Operation(tags = {"Productos"}, summary = "Actualizar producto", description = "Actualiza un producto en la base de datos")
    @ApiResponses({@ApiResponse(responseCode="202", description="Producto actualizado"), @ApiResponse(responseCode="400", description="Datos inválidos", content=@Content), @ApiResponse(responseCode="406", description="Producto no encontrado", content=@Content), @ApiResponse(responseCode="401", description="Autenticación requerida", content=@Content), @ApiResponse(responseCode="403", description="Acceso denegado", content=@Content), @ApiResponse(responseCode="500", description="Error interno", content=@Content)})
    @PutMapping("/actualizar")
    ResponseEntity<Producto> actualizarProducto(@RequestBody Producto producto);

    @Operation(tags = {"Productos"}, summary = "Desactivar producto", description = "Cambia el estado del producto a INACTIVO")
    @ApiResponses({@ApiResponse(responseCode="200", description="Producto desactivado"), @ApiResponse(responseCode="204", description="Producto no encontrado", content=@Content), @ApiResponse(responseCode="400", description="Datos inválidos", content=@Content), @ApiResponse(responseCode="401", description="Autenticación requerida", content=@Content), @ApiResponse(responseCode="403", description="Acceso denegado", content=@Content), @ApiResponse(responseCode="500", description="Error interno", content=@Content)})
    @PatchMapping("/desactivar/{id}")
    ResponseEntity<Producto> desactivarProducto(@PathVariable Integer id);
}
