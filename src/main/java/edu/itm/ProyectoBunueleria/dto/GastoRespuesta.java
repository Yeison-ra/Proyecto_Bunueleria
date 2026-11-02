package edu.itm.ProyectoBunueleria.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
public record GastoRespuesta(long idGasto, LocalDate fecha, String concepto, BigDecimal valor) { }
