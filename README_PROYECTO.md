# Proyecto Buñuelería El Buen Sabor - Primera entrega API REST

Implementación básica universitaria con Java 17, Spring Boot, arquitectura por capas, JDBC, SQL y MySQL.

## Arquitectura

- `controllers`: expone los endpoints HTTP.
- `services`: contiene la capa de servicio.
- `repositories`: ejecuta consultas SQL mediante JDBC.
- `identities`: contiene las entidades del negocio.
- `utilities`: conexión JDBC a MySQL.

Flujo: `Controller -> Service -> Repository -> DAOHelper -> JDBC -> MySQL`.

## Preparación de la base de datos

1. Abrir MySQL Workbench o el cliente MySQL.
2. Ejecutar `database/bunueleria.sql`.
3. Revisar `utilities/Conexion.java`.
4. Si MySQL usa credenciales diferentes, configurar las variables de entorno `BUNUELERIA_DB_USER` y `BUNUELERIA_DB_PASSWORD`.

Por defecto el proyecto usa:

- Base de datos: `bunueleria`
- Usuario: `root`
- Clave por defecto: `admin` (puede cambiarse con la variable de entorno `BUNUELERIA_DB_PASSWORD`)

## Ejecutar

En Windows:

```bash
.\gradlew.bat bootRun
```

En macOS/Linux:

```bash
./gradlew bootRun
```

La aplicación se ejecuta en `http://localhost:9191`.

## API REST de Producto

### Listar

`GET http://localhost:9191/productos/listar`

### Consultar por id

`GET http://localhost:9191/productos/consultar/1`

### Crear

`POST http://localhost:9191/productos/nuevo`

```json
{
  "nombre": "Empanada",
  "descripcion": "Empanada de carne",
  "idCategoria": 1,
  "estado": "ACTIVO",
  "costo": 1500,
  "precioVenta": 3000,
  "stockMinimo": 10
}
```

### Actualizar

`PUT http://localhost:9191/productos/actualizar`

```json
{
  "idProducto": 1,
  "nombre": "Buñuelo grande",
  "descripcion": "Buñuelo tradicional tamaño grande",
  "idCategoria": 1,
  "estado": "ACTIVO",
  "costo": 1400,
  "precioVenta": 2800,
  "stockMinimo": 12
}
```

### Desactivar

`PATCH http://localhost:9191/productos/desactivar/1`

Se usa desactivación lógica porque el modelo del proyecto ya contempla el campo `estado` y permite conservar el historial del producto.

## Ramas sugeridas

- `main`
- `develop`
- `feature/productos-modelo`
- `feature/productos-repository`
- `feature/productos-api`

Cada integrante debe realizar sus propios commits para que el profesor pueda verificar el trabajo colaborativo.

## Interacción de negocio adicional: actualización de inventario

Para mejorar la valoración de la entrega se incluyó una interacción de negocio: registrar una entrada o salida de inventario. La operación actualiza la cantidad disponible y registra el movimiento dentro de la misma transacción JDBC.

### Consultar inventario de un producto

`GET http://localhost:9191/inventario/producto/1`

### Registrar una entrada

`POST http://localhost:9191/inventario/movimiento`

```json
{
  "idProducto": 1,
  "tipoMovimiento": "ENTRADA",
  "motivo": "Compra a proveedor",
  "cantidad": 50,
  "referencia": "COMPRA-001"
}
```

### Registrar una salida

```json
{
  "idProducto": 1,
  "tipoMovimiento": "SALIDA",
  "motivo": "Consumo o ajuste de inventario",
  "cantidad": 5,
  "referencia": "AJUSTE-001"
}
```

Si se intenta registrar una salida mayor a la cantidad disponible, la operación no modifica la base de datos y responde con estado HTTP `409 Conflict`.
