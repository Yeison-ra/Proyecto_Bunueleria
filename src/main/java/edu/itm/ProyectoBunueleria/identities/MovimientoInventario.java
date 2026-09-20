package edu.itm.ProyectoBunueleria.identities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoInventario {
    private Integer idMovimiento;
    private Integer idProducto;
    private LocalDateTime fecha;
    private String tipoMovimiento;
    private String motivo;
    private Integer cantidad;
    private String referencia;
}
