package edu.itm.ProyectoBunueleria.controllers;

import edu.itm.ProyectoBunueleria.identities.Inventario;
import edu.itm.ProyectoBunueleria.identities.MovimientoInventario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface InventarioApi {

    @Operation(tags = {"Inventario"}, summary = "Obtiene el inventario de un producto",
            description = "Consulta el inventario registrado para un producto",
            responses = {@ApiResponse(responseCode = "200", description = "Inventario obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Inventario.class)))})
    @GetMapping("/producto/{idProducto}")
    ResponseEntity<Inventario> consultarInventario(@PathVariable Integer idProducto);

    @Operation(tags = {"Inventario"}, summary = "Registrar movimiento de inventario",
            description = "Registra una ENTRADA o SALIDA de inventario para un producto")
    @PostMapping("/movimiento")
    ResponseEntity<MovimientoInventario> registrarMovimiento(@RequestBody MovimientoInventario movimiento);
}
