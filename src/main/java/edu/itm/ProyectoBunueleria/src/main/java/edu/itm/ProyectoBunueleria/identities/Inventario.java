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
public class Inventario {
    private Integer idInventario;
    private Integer idProducto;
    private LocalDateTime fechaActualizacion;
    private Integer cantidadActual;
}
