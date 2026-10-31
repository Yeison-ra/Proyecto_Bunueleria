package edu.itm.ProyectoBunueleria.jpaEntities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @Column(name = "id_categoria", nullable = false)
    private Integer idCategoria;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(nullable = false)
    private Float costo;

    @Column(name = "precio_venta", nullable = false)
    private Float precioVenta;

    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo;
}
