package edu.itm.ProyectoBunueleria.identities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private Integer idCategoria;
    private String estado;
    private Float costo;
    private Float precioVenta;
    private Integer stockMinimo;
}
