CREATE DATABASE IF NOT EXISTS bunueleria;
USE bunueleria;

CREATE TABLE IF NOT EXISTS categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO'
);

CREATE TABLE IF NOT EXISTS producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    id_categoria INT NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    costo FLOAT NOT NULL,
    precio_venta FLOAT NOT NULL,
    stock_minimo INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_producto_categoria
        FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

CREATE TABLE IF NOT EXISTS inventario (
    id_inventario INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL UNIQUE,
    fecha_actualizacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cantidad_actual INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_inventario_producto
        FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

CREATE TABLE IF NOT EXISTS movimiento_inventario (
    id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento VARCHAR(20) NOT NULL,
    motivo VARCHAR(255),
    cantidad INT NOT NULL,
    referencia VARCHAR(100),
    CONSTRAINT fk_movimiento_producto
        FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- Usuario del sistema: reemplaza la tabla "persona" del proyecto del profesor.
-- Esta tabla existe exclusivamente para soportar autenticación en Buñuelería.
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO'
);

INSERT INTO categoria (nombre, descripcion, estado)
SELECT 'Panadería', 'Productos de panadería y fritos', 'ACTIVO'
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nombre = 'Panadería');

INSERT INTO categoria (nombre, descripcion, estado)
SELECT 'Bebidas', 'Café, chocolate y bebidas', 'ACTIVO'
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nombre = 'Bebidas');

INSERT INTO producto (nombre, descripcion, id_categoria, estado, costo, precio_venta, stock_minimo)
SELECT 'Buñuelo', 'Buñuelo tradicional',
       (SELECT id_categoria FROM categoria WHERE nombre = 'Panadería' LIMIT 1),
       'ACTIVO', 1200, 2500, 10
WHERE NOT EXISTS (SELECT 1 FROM producto WHERE nombre = 'Buñuelo');

-- Usuario solo para demostración local; reemplazar las credenciales en otros entornos.
INSERT INTO usuario (nombre, email, password, estado)
SELECT 'Administrador', 'admin@bunueleria.local', '$2b$12$LLfog/om7k/r9f5MnbKuE.Mce4rgFyIJ8jRrS6fCHo1iVSgSDIfw2', 'ACTIVO'
WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE email = 'admin@bunueleria.local');
