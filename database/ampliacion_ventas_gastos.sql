-- Ejecutar después de bunueleria.sql. No elimina datos existentes.
USE bunueleria;
CREATE TABLE IF NOT EXISTS venta (
 id_venta BIGINT AUTO_INCREMENT PRIMARY KEY,
 fecha DATETIME NOT NULL,
 total DECIMAL(15,2) NOT NULL
);
CREATE TABLE IF NOT EXISTS detalle_venta (
 id_detalle BIGINT AUTO_INCREMENT PRIMARY KEY,
 id_venta BIGINT NOT NULL,
 id_producto INT NOT NULL,
 nombre_producto VARCHAR(100) NOT NULL,
 cantidad INT NOT NULL,
 precio_unitario DECIMAL(15,2) NOT NULL,
 costo_unitario DECIMAL(15,2) NOT NULL,
 subtotal DECIMAL(15,2) NOT NULL,
 CONSTRAINT fk_detalle_venta FOREIGN KEY (id_venta) REFERENCES venta(id_venta),
 CONSTRAINT fk_detalle_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);
CREATE TABLE IF NOT EXISTS gasto (
 id_gasto BIGINT AUTO_INCREMENT PRIMARY KEY,
 fecha DATE NOT NULL,
 concepto VARCHAR(255) NOT NULL,
 valor DECIMAL(15,2) NOT NULL
);
