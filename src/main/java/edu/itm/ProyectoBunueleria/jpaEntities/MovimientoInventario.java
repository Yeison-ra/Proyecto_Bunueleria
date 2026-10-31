package edu.itm.ProyectoBunueleria.jpaEntities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento_inventario")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Integer idMovimiento;

    @Column(name = "id_producto", nullable = false)
    private Integer idProducto;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(name = "tipo_movimiento", nullable = false, length = 20)
    private String tipoMovimiento;

    @Column(length = 255)
    private String motivo;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(length = 100)
    private String referencia;

    @PrePersist
    public void asignarFecha() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }
}
