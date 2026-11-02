package edu.itm.ProyectoBunueleria.dto;
import java.math.BigDecimal;
public record DetalleVentaRespuesta(Integer idProducto, String nombreProducto, int cantidad, BigDecimal precioUnitario, BigDecimal costoUnitario, BigDecimal subtotal) { }
