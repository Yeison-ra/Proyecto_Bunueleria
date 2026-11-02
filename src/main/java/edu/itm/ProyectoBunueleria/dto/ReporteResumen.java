package edu.itm.ProyectoBunueleria.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
public record ReporteResumen(LocalDate desde, LocalDate hasta, BigDecimal ingresos, BigDecimal costoProductosVendidos, BigDecimal gastos, BigDecimal gananciaEstimada) { }
