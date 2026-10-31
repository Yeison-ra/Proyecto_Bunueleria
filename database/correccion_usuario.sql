USE bunueleria;

-- Si en una versión anterior se creó la tabla persona únicamente para autenticación
-- y no contiene información que se deba conservar, puede eliminarse manualmente:
-- DROP TABLE IF EXISTS persona;

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO'
);
