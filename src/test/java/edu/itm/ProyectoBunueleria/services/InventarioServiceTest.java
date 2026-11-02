package edu.itm.ProyectoBunueleria.services;
import edu.itm.ProyectoBunueleria.jpaEntities.Inventario;
import edu.itm.ProyectoBunueleria.jpaEntities.MovimientoInventario;
import edu.itm.ProyectoBunueleria.jpaRepositories.InventarioJpaRepository;
import edu.itm.ProyectoBunueleria.jpaRepositories.MovimientoInventarioJpaRepository;
import edu.itm.ProyectoBunueleria.jpaRepositories.ProductoJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {
 @Mock InventarioJpaRepository inventarios;
 @Mock MovimientoInventarioJpaRepository movimientos;
 @Mock ProductoJpaRepository productos;
 @InjectMocks InventarioService service;
 private MovimientoInventario movimiento(String tipo, int cantidad){ return MovimientoInventario.builder().idProducto(1).tipoMovimiento(tipo).cantidad(cantidad).build(); }
 @Test void consultarExistente(){ Inventario i=Inventario.builder().idProducto(1).cantidadActual(5).build(); when(inventarios.findByIdProducto(1)).thenReturn(Optional.of(i)); assertSame(i,service.consultarInventario(1)); }
 @Test void consultarInexistente(){ when(inventarios.findByIdProducto(1)).thenReturn(Optional.empty()); assertNull(service.consultarInventario(1)); }
 @Test void entradaActualizaCantidad(){ Inventario i=Inventario.builder().idProducto(1).cantidadActual(5).build(); MovimientoInventario m=movimiento("entrada",3); when(productos.existsById(1)).thenReturn(true); when(inventarios.findByIdProductoForUpdate(1)).thenReturn(Optional.of(i)); when(movimientos.save(m)).thenReturn(m); assertSame(m,service.registrarMovimiento(m)); assertEquals(8,i.getCantidadActual()); assertEquals("ENTRADA",m.getTipoMovimiento()); verify(inventarios).save(i); }
 @Test void salidaActualizaCantidad(){ Inventario i=Inventario.builder().idProducto(1).cantidadActual(5).build(); MovimientoInventario m=movimiento("SALIDA",3); when(productos.existsById(1)).thenReturn(true); when(inventarios.findByIdProductoForUpdate(1)).thenReturn(Optional.of(i)); when(movimientos.save(m)).thenReturn(m); assertSame(m,service.registrarMovimiento(m)); assertEquals(2,i.getCantidadActual()); }
 @Test void salidaSinStockNoGuarda(){ Inventario i=Inventario.builder().idProducto(1).cantidadActual(2).build(); when(productos.existsById(1)).thenReturn(true); when(inventarios.findByIdProductoForUpdate(1)).thenReturn(Optional.of(i)); assertNull(service.registrarMovimiento(movimiento("SALIDA",3))); verify(inventarios,never()).save(any()); verifyNoInteractions(movimientos); }
 @Test void primeraEntradaCreaInventario(){ MovimientoInventario m=movimiento("ENTRADA",4); when(productos.existsById(1)).thenReturn(true); when(inventarios.findByIdProductoForUpdate(1)).thenReturn(Optional.empty()); when(movimientos.save(m)).thenReturn(m); assertSame(m,service.registrarMovimiento(m)); verify(inventarios).save(argThat(i -> i.getCantidadActual()==4 && i.getIdProducto()==1)); }
 @Test void datosInvalidosNoGuardan(){ assertNull(service.registrarMovimiento(null)); assertNull(service.registrarMovimiento(movimiento("ENTRADA",0))); verifyNoInteractions(productos,inventarios,movimientos); }
 @Test void productoInexistenteNoGuarda(){ when(productos.existsById(1)).thenReturn(false); assertNull(service.registrarMovimiento(movimiento("ENTRADA",1))); verifyNoInteractions(inventarios,movimientos); }
 @Test void tipoInvalidoNoGuarda(){ when(productos.existsById(1)).thenReturn(true); assertNull(service.registrarMovimiento(movimiento("AJUSTE",1))); verifyNoInteractions(inventarios,movimientos); }
}
