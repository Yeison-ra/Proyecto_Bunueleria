# Proyecto Buñuelería: preparación y alcance

## Requisitos

Java 17, MySQL y acceso a las dependencias de Gradle. La aplicación utiliza Spring Boot, Gradle, JDBC, JPA, Swagger y JWT.

## Preparación de la base de datos

1. Inicie MySQL y ejecute `database/bunueleria.sql`. El script crea la base `bunueleria`, las tablas existentes del proyecto y datos de ejemplo. Si la tabla `usuario` ya existe, el script crea el usuario de práctica si aún no existe.
2. La aplicación usa por defecto `jdbc:mysql://localhost:3306/bunueleria`, usuario `root` y contraseña `admin`. Configure las variables `BUNUELERIA_DB_URL`, `BUNUELERIA_DB_USER` y `BUNUELERIA_DB_PASSWORD` si sus datos son diferentes. Las variables también las utiliza la conexión JDBC de usuarios.
3. El perfil de desarrollo valida el esquema existente (`ddl-auto: validate`); no crea automáticamente las tablas en MySQL. El perfil de pruebas usa H2 en memoria.
4. Para uso local se incluye el usuario `admin@bunueleria.local` con contraseña `Bunueleria123!`. Es una credencial pública de demostración: debe cambiarse para un entorno distinto de la práctica. Si el usuario ya existía, el script no modifica su contraseña.

## Ejecutar y comprobar

En Windows ejecute `gradlew.bat clean test` y luego `gradlew.bat bootRun`. En macOS o Linux ejecute `./gradlew clean test` y luego `./gradlew bootRun`. Gradle necesita descargar su distribución y dependencias la primera vez. También se necesita MySQL para probar los casos de uso con el perfil de desarrollo.

Swagger: `http://localhost:8080/swagger-ui/index.html`. La colección `ProyectoBunueleria.postman_collection.json` incorpora la petición de login y guarda el token para las demás peticiones. Ejecute primero «Iniciar sesión». En Swagger, use el token obtenido con el botón Authorize.

El endpoint de login recibe `email` y `password` como parámetros de petición, conservando el estilo del proyecto inicial. Se recomienda transportar credenciales de forma segura fuera del entorno local. El token se invalida al reiniciar el servidor porque su clave se genera al arrancar.

## Requisitos implementados

- Productos: operaciones JDBC mediante `JdbcTemplate`, que administra las conexiones, consultas y resultados. Las consultas enumeran sus columnas. Usuarios usa JDBC con cierre automático de recursos.
- Inventario y movimientos: servicios y repositorios JPA, con transacción para registrar el movimiento y actualizar la cantidad.
- Relaciones sobre las tablas existentes: categoría a productos (`OneToMany` y `ManyToOne`), producto a inventario (`OneToOne`) y producto a movimientos (`OneToMany` y `ManyToOne`). Las relaciones usan como referencia los identificadores existentes y se omiten en el JSON para conservar la estructura de las peticiones y evitar ciclos.
- Swagger documenta autenticación, productos, inventario, respuestas y uso del token.
- Hay pruebas unitarias para los métodos de los servicios de productos, inventario y usuarios; además se conserva la prueba de carga del contexto. Ejecutarlas para verificar el resultado en el entorno de entrega.
- Validaciones de datos, manejo de fallos de autenticación y respuesta ante errores.

## Alcance y comprobaciones pendientes

El archivo recibido no incluye el enunciado detallado ni los diagramas de todos los casos de uso. Se implementan y adaptan los casos que ya existían en el proyecto (productos, inventario y autenticación); los scripts incluyen una tabla de categorías, pero no un módulo de gestión de categorías. Confirmar contra el documento original si existen otros casos exigidos.

Esta adaptación no se pudo compilar ni ejecutar en el entorno de preparación porque no estaba disponible la distribución de Gradle y la descarga estaba restringida. Ejecutar las pruebas y comprobar Swagger, login y operaciones contra MySQL antes de presentar. El bloqueo de inventario protege filas existentes; conviene probar la primera entrada concurrente de un mismo producto en MySQL.


## Ampliación: ventas, gastos y reportes

Esta versión añade las necesidades planteadas en el documento inicial. Para una instalación nueva ejecute primero `database/bunueleria.sql` y luego `database/ampliacion_ventas_gastos.sql`. Si ya tiene la base bunueleria, realice una copia de seguridad y ejecute solo el segundo script tras comprobar que esas tablas no existen con una estructura diferente. Los scripts usan MySQL; el perfil de pruebas H2 no crea las tablas SQL nuevas automáticamente. No se ha aplicado ningún cambio a su base de datos.

Todas las operaciones requieren autenticación JWT. Inicie sesión y use Authorize en Swagger. Ejemplo de petición POST `/operaciones/ventas`:

```json
{"productos":[{"idProducto":1,"cantidad":2}]}
```

El producto debe estar activo y tener inventario suficiente (cree primero una entrada con `/inventario/movimiento`). La venta obtiene el costo y precio vigentes, descuenta inventario y registra la salida en una transacción. Los precios y costos del producto existente se almacenan como FLOAT y se redondean a dos decimales al registrar la venta; para precisión monetaria completa debería migrarse la columna original a DECIMAL con una migración coordinada.

POST `/operaciones/gastos`:

```json
{"fecha":"2026-11-02","concepto":"Servicio de energía","valor":50000.00}
```

Consulte `/operaciones/ventas/{id}`, `/operaciones/ventas?desde=2026-11-01&hasta=2026-11-30`, `/operaciones/gastos?desde=2026-11-01&hasta=2026-11-30`, `/operaciones/reportes/resumen?desde=2026-11-01&hasta=2026-11-30` y `/operaciones/reportes/productos?desde=2026-11-01&hasta=2026-11-30`. Las fechas son inclusivas; para consultar un día use la misma fecha en desde y hasta. El reporte de productos incluye unidades e ingresos; se ordena por unidades.

La ganancia es una estimación de ingresos menos costo histórico de productos vendidos y gastos registrados del período. No registre como gasto operativo compras ya incluidas en el costo de los productos: se contarían dos veces. No es un estado contable ni contempla impuestos, devoluciones o ventas anuladas. La primera entrada concurrente de inventario sigue necesitando validación con MySQL.

La nueva clase `OperacionesServiceTest` incluye pruebas unitarias de los métodos del servicio, con casos exitosos y errores. Ejecutar `gradlew.bat clean test` y comprobar el flujo de venta, stock, gastos, reportes y Swagger con MySQL antes de entregar. Esta versión no pudo compilarse ni ejecutarse en el entorno de preparación por falta de acceso a la distribución de Gradle; no se afirma que las pruebas hayan pasado.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/4.1.1/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.1.1/gradle-plugin/packaging-oci-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/4.1.1/reference/web/servlet.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/4.1.1/reference/using/devtools.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)



