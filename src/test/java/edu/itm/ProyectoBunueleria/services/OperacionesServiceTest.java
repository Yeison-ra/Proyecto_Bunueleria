package edu.itm.ProyectoBunueleria.services;

import edu.itm.ProyectoBunueleria.dto.*;
import edu.itm.ProyectoBunueleria.exceptions.*;
import edu.itm.ProyectoBunueleria.repositories.OperacionRepository;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OperacionesServiceTest {
 private OperacionRepository repo;
 private OperacionesService service;
 @BeforeEach void iniciar(){repo=mock(OperacionRepository.class);service=new OperacionesService(repo);}
 @Test void ventaDescuentaYGuardaPrecio(){
  var p=new OperacionRepository.ProductoVenta(1,"Buñuelo","ACTIVO",new BigDecimal("2500.00"),new BigDecimal("1200.00"),8);
  when(repo.bloquearProducto(1)).thenReturn(Optional.of(p)); when(repo.crearVenta(any(),eq(new BigDecimal("5000.00")))).thenReturn(7L);
  var venta=new VentaRespuesta(7,LocalDateTime.now(),new BigDecimal("5000.00"),List.of()); when(repo.venta(7)).thenReturn(Optional.of(venta));
  assertSame(venta,service.registrarVenta(new VentaSolicitud(List.of(new LineaVentaSolicitud(1,2)))));
  verify(repo).agregarDetalle(7,p,2,new BigDecimal("5000.00")); verify(repo).descontar(1,2,7);
 }
 @Test void ventaSinStockNoSeRegistra(){ var p=new OperacionRepository.ProductoVenta(1,"Buñuelo","ACTIVO",BigDecimal.TEN,BigDecimal.ONE,1);when(repo.bloquearProducto(1)).thenReturn(Optional.of(p));assertThrows(ErrorNegocio.class,()->service.registrarVenta(new VentaSolicitud(List.of(new LineaVentaSolicitud(1,2)))));verify(repo,never()).crearVenta(any(),any()); }
 @Test void ventaSinProductosEsInvalida(){assertThrows(DatosInvalidos.class,()->service.registrarVenta(new VentaSolicitud(List.of())));verifyNoInteractions(repo);}
 @Test void consultarVentaExistente(){var v=new VentaRespuesta(1,LocalDateTime.now(),BigDecimal.TEN,List.of());when(repo.venta(1)).thenReturn(Optional.of(v));assertSame(v,service.consultarVenta(1));}
 @Test void consultarVentaInexistente(){when(repo.venta(1)).thenReturn(Optional.empty());assertThrows(NoEncontrado.class,()->service.consultarVenta(1));}
 @Test void listarVentas(){var d=LocalDate.of(2026,1,1);when(repo.ventas(d,d)).thenReturn(List.of());assertTrue(service.listarVentas(d,d).isEmpty());verify(repo).ventas(d,d);}
 @Test void registrarGasto(){var d=LocalDate.of(2026,1,1);var g=new GastoSolicitud(d,"Luz",BigDecimal.TEN);when(repo.crearGasto(g)).thenReturn(2L);assertEquals(2,service.registrarGasto(g).idGasto());}
 @Test void rechazarGastoInvalido(){assertThrows(DatosInvalidos.class,()->service.registrarGasto(new GastoSolicitud(LocalDate.now(),"",BigDecimal.TEN)));verifyNoInteractions(repo);}
 @Test void listarGastos(){var d=LocalDate.of(2026,1,1);when(repo.gastos(d,d)).thenReturn(List.of());assertTrue(service.listarGastos(d,d).isEmpty());verify(repo).gastos(d,d);}
 @Test void resumen(){var d=LocalDate.of(2026,1,1);var r=new ReporteResumen(d,d,BigDecimal.TEN,BigDecimal.ONE,BigDecimal.ONE,new BigDecimal("8"));when(repo.resumen(d,d)).thenReturn(r);assertSame(r,service.resumen(d,d));}
 @Test void productosVendidos(){var d=LocalDate.of(2026,1,1);when(repo.productosVendidos(d,d)).thenReturn(List.of());assertTrue(service.productosVendidos(d,d).isEmpty());verify(repo).productosVendidos(d,d);}
 @Test void rangoInvertidoEsInvalido(){assertThrows(DatosInvalidos.class,()->service.resumen(LocalDate.of(2026,2,1),LocalDate.of(2026,1,1)));verifyNoInteractions(repo);}
}
