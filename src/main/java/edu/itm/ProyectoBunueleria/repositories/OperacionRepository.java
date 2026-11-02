package edu.itm.ProyectoBunueleria.repositories;
import edu.itm.ProyectoBunueleria.dto.*;
import java.math.BigDecimal;
import java.sql.*;
import java.time.*;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class OperacionRepository {
 private final JdbcTemplate jdbc;
 public OperacionRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }
 public record ProductoVenta(int id, String nombre, String estado, BigDecimal precio, BigDecimal costo, int stock) { }
 public Optional<ProductoVenta> bloquearProducto(int id) {
  return jdbc.query("SELECT p.id_producto, p.nombre, p.estado, p.precio_venta, p.costo, i.cantidad_actual FROM producto p JOIN inventario i ON i.id_producto=p.id_producto WHERE p.id_producto=? FOR UPDATE", (rs,n)->new ProductoVenta(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getBigDecimal(4).setScale(2, java.math.RoundingMode.HALF_UP),rs.getBigDecimal(5).setScale(2, java.math.RoundingMode.HALF_UP),rs.getInt(6)),id).stream().findFirst();
 }
 private long insertar(String sql, Object... args) {
  KeyHolder key = new GeneratedKeyHolder();
  jdbc.update(con -> { PreparedStatement ps=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); for(int i=0;i<args.length;i++) ps.setObject(i+1,args[i]); return ps; },key);
  return Objects.requireNonNull(key.getKey(),"No se generó identificador").longValue();
 }
 public long crearVenta(LocalDateTime fecha, BigDecimal total) { return insertar("INSERT INTO venta (fecha,total) VALUES (?,?)",Timestamp.valueOf(fecha),total); }
 public void agregarDetalle(long venta, ProductoVenta p, int cantidad, BigDecimal subtotal) { jdbc.update("INSERT INTO detalle_venta (id_venta,id_producto,nombre_producto,cantidad,precio_unitario,costo_unitario,subtotal) VALUES (?,?,?,?,?,?,?)",venta,p.id(),p.nombre(),cantidad,p.precio(),p.costo(),subtotal); }
 public void descontar(int id, int cantidad, long venta) {
  int actualizados=jdbc.update("UPDATE inventario SET cantidad_actual=cantidad_actual-?, fecha_actualizacion=CURRENT_TIMESTAMP WHERE id_producto=? AND cantidad_actual>=?",cantidad,id,cantidad);
  if(actualizados!=1) throw new edu.itm.ProyectoBunueleria.exceptions.ErrorNegocio("Inventario insuficiente para el producto " + id);
  jdbc.update("INSERT INTO movimiento_inventario (id_producto,fecha,tipo_movimiento,motivo,cantidad,referencia) VALUES (?,CURRENT_TIMESTAMP,'SALIDA','Venta',?,?)",id,cantidad,"Venta " + venta);
 }
 public List<DetalleVentaRespuesta> detalles(long id) { return jdbc.query("SELECT id_producto,nombre_producto,cantidad,precio_unitario,costo_unitario,subtotal FROM detalle_venta WHERE id_venta=? ORDER BY id_detalle",(r,n)->new DetalleVentaRespuesta(r.getInt(1),r.getString(2),r.getInt(3),r.getBigDecimal(4),r.getBigDecimal(5),r.getBigDecimal(6)),id); }
 public Optional<VentaRespuesta> venta(long id) { return jdbc.query("SELECT id_venta,fecha,total FROM venta WHERE id_venta=?",(r,n)->new VentaRespuesta(r.getLong(1),r.getTimestamp(2).toLocalDateTime(),r.getBigDecimal(3),detalles(r.getLong(1))),id).stream().findFirst(); }
 public List<VentaRespuesta> ventas(LocalDate desde, LocalDate hasta) { return jdbc.query("SELECT id_venta,fecha,total FROM venta WHERE fecha>=? AND fecha<? ORDER BY fecha DESC,id_venta DESC",(r,n)->new VentaRespuesta(r.getLong(1),r.getTimestamp(2).toLocalDateTime(),r.getBigDecimal(3),detalles(r.getLong(1))),Timestamp.valueOf(desde.atStartOfDay()),Timestamp.valueOf(hasta.plusDays(1).atStartOfDay())); }
 public long crearGasto(GastoSolicitud g) { return insertar("INSERT INTO gasto (fecha,concepto,valor) VALUES (?,?,?)",java.sql.Date.valueOf(g.fecha()),g.concepto().trim(),g.valor()); }
 public List<GastoRespuesta> gastos(LocalDate desde,LocalDate hasta) { return jdbc.query("SELECT id_gasto,fecha,concepto,valor FROM gasto WHERE fecha>=? AND fecha<=? ORDER BY fecha DESC,id_gasto DESC",(r,n)->new GastoRespuesta(r.getLong(1),r.getDate(2).toLocalDate(),r.getString(3),r.getBigDecimal(4)),java.sql.Date.valueOf(desde),java.sql.Date.valueOf(hasta)); }
 public ReporteResumen resumen(LocalDate desde,LocalDate hasta) {
  Timestamp inicio=Timestamp.valueOf(desde.atStartOfDay()), fin=Timestamp.valueOf(hasta.plusDays(1).atStartOfDay());
  BigDecimal ingresos=jdbc.queryForObject("SELECT COALESCE(SUM(total),0) FROM venta WHERE fecha>=? AND fecha<?",BigDecimal.class,inicio,fin);
  BigDecimal costo=jdbc.queryForObject("SELECT COALESCE(SUM(d.costo_unitario*d.cantidad),0) FROM detalle_venta d JOIN venta v ON v.id_venta=d.id_venta WHERE v.fecha>=? AND v.fecha<?",BigDecimal.class,inicio,fin);
  BigDecimal gastos=jdbc.queryForObject("SELECT COALESCE(SUM(valor),0) FROM gasto WHERE fecha>=? AND fecha<=?",BigDecimal.class,java.sql.Date.valueOf(desde),java.sql.Date.valueOf(hasta));
  return new ReporteResumen(desde,hasta,ingresos,costo,gastos,ingresos.subtract(costo).subtract(gastos));
 }
 public List<ReporteProducto> productosVendidos(LocalDate desde,LocalDate hasta) { return jdbc.query("SELECT d.id_producto,d.nombre_producto,SUM(d.cantidad),SUM(d.subtotal) FROM detalle_venta d JOIN venta v ON v.id_venta=d.id_venta WHERE v.fecha>=? AND v.fecha<? GROUP BY d.id_producto,d.nombre_producto ORDER BY SUM(d.cantidad) DESC, d.id_producto",(r,n)->new ReporteProducto(r.getInt(1),r.getString(2),r.getLong(3),r.getBigDecimal(4)),Timestamp.valueOf(desde.atStartOfDay()),Timestamp.valueOf(hasta.plusDays(1).atStartOfDay())); }
}
