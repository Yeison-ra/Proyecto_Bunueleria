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
