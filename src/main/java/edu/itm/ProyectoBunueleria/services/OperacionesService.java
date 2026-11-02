package edu.itm.ProyectoBunueleria.services;
import edu.itm.ProyectoBunueleria.dto.*;
import edu.itm.ProyectoBunueleria.exceptions.*;
import edu.itm.ProyectoBunueleria.repositories.OperacionRepository;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperacionesService {
 private final OperacionRepository repository;
 public OperacionesService(OperacionRepository repository) { this.repository=repository; }
 @Transactional
 public VentaRespuesta registrarVenta(VentaSolicitud solicitud) {
  if(solicitud==null || solicitud.productos()==null || solicitud.productos().isEmpty()) throw new DatosInvalidos("La venta debe incluir productos");
  Map<Integer,Integer> cantidades=new TreeMap<>();
  for(LineaVentaSolicitud linea:solicitud.productos()) {
   if(linea==null || linea.idProducto()==null || linea.idProducto()<=0 || linea.cantidad()==null || linea.cantidad()<=0) throw new DatosInvalidos("Cada producto debe tener identificador y cantidad positiva");
   try { cantidades.merge(linea.idProducto(),linea.cantidad(),Math::addExact); } catch(ArithmeticException e) { throw new DatosInvalidos("Cantidad demasiado grande"); }
  }
  Map<Integer,OperacionRepository.ProductoVenta> productos=new LinkedHashMap<>(); BigDecimal total=BigDecimal.ZERO;
  for(var linea:cantidades.entrySet()) {
   var p=repository.bloquearProducto(linea.getKey()).orElseThrow(()->new ErrorNegocio("Producto sin inventario o inexistente: " + linea.getKey()));
   if(!"ACTIVO".equalsIgnoreCase(p.estado())) throw new ErrorNegocio("Producto inactivo: " + p.id());
   if(p.stock()<linea.getValue()) throw new ErrorNegocio("Inventario insuficiente para el producto " + p.id());
   productos.put(p.id(),p); total=total.add(p.precio().multiply(BigDecimal.valueOf(linea.getValue())));
  }
  long id=repository.crearVenta(LocalDateTime.now(),total);
  for(var linea:cantidades.entrySet()) { var p=productos.get(linea.getKey()); repository.agregarDetalle(id,p,linea.getValue(),p.precio().multiply(BigDecimal.valueOf(linea.getValue()))); repository.descontar(p.id(),linea.getValue(),id); }
  return repository.venta(id).orElseThrow(()->new IllegalStateException("No se pudo recuperar la venta"));
 }
 @Transactional(readOnly=true) public VentaRespuesta consultarVenta(long id) { if(id<=0) throw new DatosInvalidos("Identificador inválido"); return repository.venta(id).orElseThrow(()->new NoEncontrado("Venta no encontrada")); }
 @Transactional(readOnly=true) public List<VentaRespuesta> listarVentas(LocalDate desde,LocalDate hasta) { validarFechas(desde,hasta); return repository.ventas(desde,hasta); }
 @Transactional public GastoRespuesta registrarGasto(GastoSolicitud g) {
  if(g==null || g.fecha()==null || g.concepto()==null || g.concepto().isBlank() || g.concepto().trim().length()>255 || g.valor()==null || g.valor().signum()<=0 || g.valor().scale()>2 || g.valor().precision()-g.valor().scale()>13) throw new DatosInvalidos("Indique fecha, concepto (máximo 255 caracteres) y valor positivo con máximo dos decimales");
  long id=repository.crearGasto(g); return new GastoRespuesta(id,g.fecha(),g.concepto().trim(),g.valor());
 }
 @Transactional(readOnly=true) public List<GastoRespuesta> listarGastos(LocalDate desde,LocalDate hasta) { validarFechas(desde,hasta); return repository.gastos(desde,hasta); }
 @Transactional(readOnly=true) public ReporteResumen resumen(LocalDate desde,LocalDate hasta) { validarFechas(desde,hasta); return repository.resumen(desde,hasta); }
 @Transactional(readOnly=true) public List<ReporteProducto> productosVendidos(LocalDate desde,LocalDate hasta) { validarFechas(desde,hasta); return repository.productosVendidos(desde,hasta); }
 private void validarFechas(LocalDate desde,LocalDate hasta) { if(desde==null || hasta==null || desde.isAfter(hasta) || hasta.equals(LocalDate.MAX)) throw new DatosInvalidos("Indique un rango de fechas válido: desde debe ser anterior o igual a hasta"); }
}
