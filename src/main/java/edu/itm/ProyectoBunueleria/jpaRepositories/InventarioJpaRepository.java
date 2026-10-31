package edu.itm.ProyectoBunueleria.jpaRepositories;

import edu.itm.ProyectoBunueleria.jpaEntities.Inventario;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventarioJpaRepository extends JpaRepository<Inventario, Integer> {

    Optional<Inventario> findByIdProducto(Integer idProducto);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Inventario i WHERE i.idProducto = :idProducto")
    Optional<Inventario> findByIdProductoForUpdate(@Param("idProducto") Integer idProducto);
}
