package edu.itm.ProyectoBunueleria.jpaEntities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity @Table(name="categoria") @Getter @Setter @NoArgsConstructor
public class Categoria {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="id_categoria") private Integer idCategoria;
    @Column(nullable=false, length=100) private String nombre;
    @Column(length=255) private String descripcion;
    @Column(nullable=false, length=20) private String estado;
    @JsonIgnore @OneToMany(mappedBy="categoria", fetch=FetchType.LAZY) private List<Producto> productos;
}
