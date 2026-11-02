package edu.itm.ProyectoBunueleria.controllers;

import edu.itm.ProyectoBunueleria.jpaEntities.Inventario;
import edu.itm.ProyectoBunueleria.jpaEntities.MovimientoInventario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
public interface InventarioApi {

    @Operation(tags = {"Inventario"}, summary = "Obtiene el inventario de un producto",
            description = "Consulta el inventario registrado para un producto",
            responses = {@ApiResponse(responseCode = "200", description = "Inventario obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Inventario.class)))})
    @ApiResponses({@ApiResponse(responseCode="200", description="Consulta exitosa"), @ApiResponse(responseCode="204", description="No hay resultados", content=@Content), @ApiResponse(responseCode="400", description="Datos inválidos", content=@Content), @ApiResponse(responseCode="401", description="Autenticación requerida", content=@Content), @ApiResponse(responseCode="403", description="Acceso denegado", content=@Content), @ApiResponse(responseCode="500", description="Error interno", content=@Content)})
    @GetMapping("/producto/{idProducto}")
    ResponseEntity<Inventario> consultarInventario(@PathVariable Integer idProducto);

    @Operation(tags = {"Inventario"}, summary = "Registrar movimiento de inventario",
            description = "Registra una ENTRADA o SALIDA de inventario para un producto")
    @ApiResponses({@ApiResponse(responseCode="201", description="Registro creado"), @ApiResponse(responseCode="400", description="Datos inválidos", content=@Content), @ApiResponse(responseCode="401", description="Autenticación requerida", content=@Content), @ApiResponse(responseCode="403", description="Acceso denegado", content=@Content), @ApiResponse(responseCode="409", description="Operación no permitida", content=@Content), @ApiResponse(responseCode="500", description="Error interno", content=@Content)})
    @PostMapping("/movimiento")
    ResponseEntity<MovimientoInventario> registrarMovimiento(@RequestBody MovimientoInventario movimiento);
}
