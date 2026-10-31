package edu.itm.ProyectoBunueleria.jpaRepositories;

import edu.itm.ProyectoBunueleria.jpaEntities.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoInventarioJpaRepository extends JpaRepository<MovimientoInventario, Integer> {
}
