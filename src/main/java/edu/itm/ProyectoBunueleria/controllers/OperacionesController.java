package edu.itm.ProyectoBunueleria.controllers;
import edu.itm.ProyectoBunueleria.dto.*;
import edu.itm.ProyectoBunueleria.services.OperacionesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operaciones")
@SecurityRequirement(name="bearerAuth")
public class OperacionesController {
 private final OperacionesService service;
 public OperacionesController(OperacionesService service) { this.service=service; }
 @Operation(tags="Ventas",summary="Registrar una venta y descontar inventario",description="El precio y costo se toman del producto. La operación completa se revierte si falla alguna línea.")
 @ApiResponses({@ApiResponse(responseCode="201",description="Venta registrada"),@ApiResponse(responseCode="400",description="Datos inválidos"),@ApiResponse(responseCode="401",description="Autenticación requerida"),@ApiResponse(responseCode="409",description="Producto inactivo, inexistente o sin stock")})
 @PostMapping("/ventas") public ResponseEntity<VentaRespuesta> vender(@RequestBody VentaSolicitud solicitud) { return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarVenta(solicitud)); }
 @Operation(tags="Ventas",summary="Consultar detalle de una venta") @ApiResponses({@ApiResponse(responseCode="200",description="Venta encontrada"),@ApiResponse(responseCode="404",description="Venta inexistente")})
 @GetMapping("/ventas/{id}") public VentaRespuesta venta(@PathVariable long id) { return service.consultarVenta(id); }
 @Operation(tags="Ventas",summary="Consultar historial de ventas",description="Rango inclusivo con fechas ISO: aaaa-MM-dd.") @ApiResponses({@ApiResponse(responseCode="200",description="Listado de ventas"),@ApiResponse(responseCode="400",description="Fechas inválidas")})
 @GetMapping("/ventas") public List<VentaRespuesta> ventas(@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate desde,@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate hasta) { return service.listarVentas(desde,hasta); }
 @Operation(tags="Gastos",summary="Registrar gasto",description="No registre aquí compras de productos ya incluidas en su costo para evitar doble conteo en la ganancia.") @ApiResponses({@ApiResponse(responseCode="201",description="Gasto registrado"),@ApiResponse(responseCode="400",description="Datos inválidos")})
 @PostMapping("/gastos") public ResponseEntity<GastoRespuesta> gasto(@RequestBody GastoSolicitud solicitud) { return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarGasto(solicitud)); }
 @Operation(tags="Gastos",summary="Consultar gastos por fechas") @ApiResponses({@ApiResponse(responseCode="200",description="Listado de gastos"),@ApiResponse(responseCode="400",description="Fechas inválidas")})
 @GetMapping("/gastos") public List<GastoRespuesta> gastos(@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate desde,@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate hasta) { return service.listarGastos(desde,hasta); }
 @Operation(tags="Reportes",summary="Consultar ingresos, costos, gastos y ganancia estimada",description="Ganancia estimada = ingresos - costo de productos vendidos - gastos registrados.") @ApiResponses({@ApiResponse(responseCode="200",description="Resumen del período"),@ApiResponse(responseCode="400",description="Fechas inválidas")})
 @GetMapping("/reportes/resumen") public ReporteResumen resumen(@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate desde,@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate hasta) { return service.resumen(desde,hasta); }
 @Operation(tags="Reportes",summary="Consultar productos vendidos",description="Ordenados por unidades vendidas; incluye ingresos por producto.") @ApiResponses({@ApiResponse(responseCode="200",description="Productos vendidos"),@ApiResponse(responseCode="400",description="Fechas inválidas")})
 @GetMapping("/reportes/productos") public List<ReporteProducto> productos(@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate desde,@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate hasta) { return service.productosVendidos(desde,hasta); }
}
