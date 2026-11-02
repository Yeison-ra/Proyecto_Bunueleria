package edu.itm.ProyectoBunueleria.dto;
import java.math.BigDecimal;
public record ReporteProducto(Integer idProducto, String nombreProducto, long unidadesVendidas, BigDecimal ingresos) { }
