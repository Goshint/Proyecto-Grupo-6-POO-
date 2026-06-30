-- ==========================================
-- CREACIÓN DE LA BASE DE DATOS
-- ==========================================
CREATE DATABASE VidaFit_Proyecto;
GO

USE VidaFit_Proyecto;
GO

-- ==========================================
-- 1. TABLA USUARIO
-- ==========================================
CREATE TABLE Usuario(
    id_usuario INT IDENTITY(1,1) PRIMARY KEY,
    usuario VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL
);

-- Datos iniciales (Usuarios)
INSERT INTO Usuario(usuario, password, rol)
VALUES
('admin', '123', 'GERENTE'),
('recepcion', '123', 'RECEPCIONISTA'),
('instructor', '123', 'INSTRUCTOR');

-- ==========================================
-- 2. TABLA TIPO SUSCRIPCIÓN
-- ==========================================
CREATE TABLE TipoSuscripcion(
    id_tipo INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(40) NOT NULL,
    duracion_dias INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    estado VARCHAR(20) DEFAULT 'ACTIVO'
);

-- Datos iniciales (Suscripciones)
INSERT INTO TipoSuscripcion(nombre, duracion_dias, precio)
VALUES
('Mensual', 30, 80),
('Trimestral', 90, 220),
('Semestral', 180, 400),
('Anual', 365, 700);

-- ==========================================
-- 3. TABLA SOCIO
-- ==========================================
CREATE TABLE Socio(
    id_socio INT IDENTITY(1,1) PRIMARY KEY,
    numero_socio VARCHAR(20) UNIQUE,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    dni CHAR(8) UNIQUE,
    telefono VARCHAR(15),
    email VARCHAR(100),
    fecha_registro DATE,
    fecha_vencimiento DATE,
    estado VARCHAR(20),
    id_tipo INT,
    FOREIGN KEY(id_tipo) REFERENCES TipoSuscripcion(id_tipo)
);

-- ==========================================
-- 4. TABLA PAGO
-- ==========================================
CREATE TABLE Pago(
    id_pago INT IDENTITY(1,1) PRIMARY KEY,
    id_socio INT,
    monto DECIMAL(10,2),
    fecha_pago DATETIME DEFAULT GETDATE(),
    metodo_pago VARCHAR(30),
    observacion VARCHAR(200),
    FOREIGN KEY(id_socio) REFERENCES Socio(id_socio)
);

-- ==========================================
-- 5. TABLA CLASE
-- ==========================================
CREATE TABLE Clase(
    id_clase INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(50),
    instructor VARCHAR(60),
    horario VARCHAR(30),
    capacidad INT,
    estado VARCHAR(20)
);

-- Datos de prueba (Clases)
INSERT INTO Clase(nombre, instructor, horario, capacidad, estado)
VALUES
('Spinning', 'Carlos Diaz', '08:00', 20, 'ACTIVO'),
('Yoga', 'María Pérez', '18:00', 25, 'ACTIVO'),
('Crossfit', 'Luis Torres', '19:00', 15, 'ACTIVO');

-- ==========================================
-- 6. TABLA INSCRIPCIÓN
-- ==========================================
CREATE TABLE Inscripcion(
    id_inscripcion INT IDENTITY(1,1) PRIMARY KEY,
    id_socio INT,
    id_clase INT,
    fecha_inscripcion DATE DEFAULT GETDATE(),
    FOREIGN KEY(id_socio) REFERENCES Socio(id_socio),
    FOREIGN KEY(id_clase) REFERENCES Clase(id_clase)
);

-- ==========================================
-- 7. TABLA ACCESO
-- ==========================================
CREATE TABLE RegistroAcceso(
    id_acceso INT IDENTITY(1,1) PRIMARY KEY,
    id_socio INT,
    fecha_hora DATETIME DEFAULT GETDATE(),
    resultado VARCHAR(20),
    observacion VARCHAR(100),
    FOREIGN KEY(id_socio) REFERENCES Socio(id_socio)
);

-- ==========================================
-- 8. TABLA BITÁCORA
-- ==========================================
CREATE TABLE Bitacora(
    id_bitacora INT IDENTITY(1,1) PRIMARY KEY,
    usuario VARCHAR(30),
    accion VARCHAR(100),
    detalle VARCHAR(300),
    fecha DATETIME DEFAULT GETDATE()
);

-- ==========================================
-- 9. TABLA PARÁMETROS
-- ==========================================
CREATE TABLE Parametro(
    nombre VARCHAR(40) PRIMARY KEY,
    valor VARCHAR(50)
);

-- Datos iniciales (Parámetros)
INSERT INTO Parametro(nombre, valor)
VALUES
('dias_alerta', '5');

-- ==========================================
-- 10. HISTORIAL DE MEMBRESÍAS
-- ==========================================
CREATE TABLE HistorialSuscripcion(
    id_historial INT IDENTITY(1,1) PRIMARY KEY,
    id_socio INT,
    id_tipo INT,
    fecha_inicio DATE,
    fecha_fin DATE,
    monto DECIMAL(10,2),
    FOREIGN KEY(id_socio) REFERENCES Socio(id_socio),
    FOREIGN KEY(id_tipo) REFERENCES TipoSuscripcion(id_tipo)
);