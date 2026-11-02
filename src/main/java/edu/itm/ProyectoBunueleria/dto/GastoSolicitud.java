package edu.itm.ProyectoBunueleria.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
public record GastoSolicitud(LocalDate fecha, String concepto, BigDecimal valor) { }
