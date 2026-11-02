package edu.itm.ProyectoBunueleria.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
public record VentaRespuesta(long idVenta, LocalDateTime fecha, BigDecimal total, List<DetalleVentaRespuesta> detalles) { }
