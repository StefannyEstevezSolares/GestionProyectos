
CREATE DATABASE IF NOT EXISTS gestion_proyectos;

USE gestion_proyectos;

-- Tabla de empleados
CREATE TABLE IF NOT EXISTS empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(120) NOT NULL UNIQUE,
    cargo VARCHAR(80) NOT NULL
);

-- Tabla de proyectos
CREATE TABLE IF NOT EXISTS proyectos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE,
    estado ENUM('PLANIFICADO', 'EN_CURSO', 'FINALIZADO')
        NOT NULL DEFAULT 'PLANIFICADO'
);

-- Tabla que relaciona empleados y proyectos
CREATE TABLE IF NOT EXISTS empleado_proyecto (
    empleado_id INT NOT NULL,
    proyecto_id INT NOT NULL,
    rol VARCHAR(80) NOT NULL,
    fecha_asignacion DATE NOT NULL,
    horas_semanales INT NOT NULL,

    PRIMARY KEY (empleado_id, proyecto_id),

    FOREIGN KEY (empleado_id)
        REFERENCES empleados(id),

    FOREIGN KEY (proyecto_id)
        REFERENCES proyectos(id)
);
