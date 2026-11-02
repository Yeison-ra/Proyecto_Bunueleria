package edu.itm.ProyectoBunueleria.services;
import edu.itm.ProyectoBunueleria.jpaEntities.Producto;
import edu.itm.ProyectoBunueleria.repositories.ProductosRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ProductosServiceTest {
 @Mock ProductosRepositoryInterface repository;
 @InjectMocks ProductosService service;
 @Test void listar(){ Producto p=Producto.builder().idProducto(1).build(); when(repository.getProductos()).thenReturn(List.of(p)); assertEquals(1,service.getProductos().size()); }
 @Test void consultar(){ Producto p=Producto.builder().idProducto(1).build(); when(repository.getProducto(1)).thenReturn(p); assertSame(p,service.getProducto(1)); }
 @Test void insertarAsignaEstadoEIgnoraId(){ Producto p=Producto.builder().idProducto(5).build(); when(repository.insertarProducto(p)).thenReturn(p); assertSame(p,service.insertarProducto(p)); assertNull(p.getIdProducto()); assertEquals("ACTIVO",p.getEstado()); }
 @Test void actualizarExistente(){ Producto p=Producto.builder().idProducto(1).build(); when(repository.actualizarProducto(p)).thenReturn(p); assertSame(p,service.actualizarProducto(p)); }
 @Test void actualizarSinId(){ assertNull(service.actualizarProducto(new Producto())); verifyNoInteractions(repository); }
 @Test void desactivar(){ Producto p=Producto.builder().idProducto(1).estado("INACTIVO").build(); when(repository.desactivarProducto(1)).thenReturn(p); assertEquals("INACTIVO",service.desactivarProducto(1).getEstado()); }
}
