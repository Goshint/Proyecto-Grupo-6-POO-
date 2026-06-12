USE master;
GO

-- Eliminar base de datos si existe (opcional, solo para limpieza)
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'VidaFitDB')
    DROP DATABASE VidaFitDB;
GO

-- Crear base de datos
CREATE DATABASE VidaFitDB;
GO

USE VidaFitDB;
GO

-- ============================================
-- 1. Tabla: TipoSuscripcion (catálogo)
-- ============================================
CREATE TABLE TipoSuscripcion (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(20) NOT NULL UNIQUE,
    duracion_dias INT NOT NULL,     -- 30, 90, 365
    precio DECIMAL(10,2) NOT NULL
);
GO

-- ============================================
-- 2. Tabla: Socio (con campos optimizados)
-- ============================================
CREATE TABLE Socio (
    id INT IDENTITY(1,1) PRIMARY KEY,
    numero_socio NVARCHAR(20) NOT NULL UNIQUE,
    nombre NVARCHAR(50) NOT NULL,
    apellido NVARCHAR(50) NOT NULL,
    email NVARCHAR(100) UNIQUE,
    telefono NVARCHAR(20),
    fecha_registro DATE NOT NULL DEFAULT CAST(GETDATE() AS DATE),
    fecha_vencimiento DATE NOT NULL,
    estado NVARCHAR(10) NOT NULL DEFAULT 'inactivo',
    id_tipo_suscripcion_actual INT NOT NULL,
    CONSTRAINT CK_Socio_estado CHECK (estado IN ('activo', 'inactivo')),
    CONSTRAINT FK_Socio_TipoSuscripcion FOREIGN KEY (id_tipo_suscripcion_actual) REFERENCES TipoSuscripcion(id)
);

-- Índices para búsqueda rápida (< 1 segundo)
CREATE NONCLUSTERED INDEX IX_Socio_Numero ON Socio(numero_socio);
CREATE NONCLUSTERED INDEX IX_Socio_NombreApellido ON Socio(nombre, apellido);
CREATE NONCLUSTERED INDEX IX_Socio_EstadoVencimiento ON Socio(estado, fecha_vencimiento);
GO

-- ============================================
-- 3. Tabla: Pago (registro de renovaciones)
-- ============================================
CREATE TABLE Pago (
    id INT IDENTITY(1,1) PRIMARY KEY,
    id_socio INT NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    fecha_pago DATE NOT NULL DEFAULT CAST(GETDATE() AS DATE),
    nueva_fecha_vencimiento DATE NOT NULL,
    metodo_pago NVARCHAR(20),
    CONSTRAINT FK_Pago_Socio FOREIGN KEY (id_socio) REFERENCES Socio(id) ON DELETE CASCADE,
    CONSTRAINT CK_Pago_metodo CHECK (metodo_pago IN ('efectivo', 'tarjeta', 'transferencia'))
);
GO

-- ============================================
-- 4. Tabla: Clase (actividades grupales)
-- ============================================
CREATE TABLE Clase (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(50) NOT NULL,
    instructor NVARCHAR(100) NOT NULL,
    cupo_maximo INT NOT NULL,
    horario DATETIME NOT NULL,
    duracion_minutos INT DEFAULT 60
);
GO

-- ============================================
-- 5. Tabla: InscripcionClase (socio ↔ clase)
-- ============================================
CREATE TABLE InscripcionClase (
    id INT IDENTITY(1,1) PRIMARY KEY,
    id_socio INT NOT NULL,
    id_clase INT NOT NULL,
    fecha_inscripcion DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Inscripcion_Socio FOREIGN KEY (id_socio) REFERENCES Socio(id),
    CONSTRAINT FK_Inscripcion_Clase FOREIGN KEY (id_clase) REFERENCES Clase(id),
    CONSTRAINT UQ_SocioClase UNIQUE (id_socio, id_clase)
);
GO

-- ============================================
-- 6. Tabla: RegistroAcceso (control diario)
-- ============================================
CREATE TABLE RegistroAcceso (
    id INT IDENTITY(1,1) PRIMARY KEY,
    id_socio INT NOT NULL,
    fecha_hora_acceso DATETIME DEFAULT GETDATE(),
    metodo_identificacion NVARCHAR(10) NOT NULL,
    CONSTRAINT FK_Acceso_Socio FOREIGN KEY (id_socio) REFERENCES Socio(id),
    CONSTRAINT CK_Acceso_metodo CHECK (metodo_identificacion IN ('manual', 'barras', 'qr'))
);
GO

-- ============================================
-- 7. Trigger: actualizar estado según vencimiento
-- ============================================
CREATE TRIGGER trg_ActualizarEstadoSocio
ON Socio
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE Socio
    SET estado = 'inactivo'
    WHERE fecha_vencimiento < CAST(GETDATE() AS DATE)
      AND estado = 'activo';
END;
GO

-- ============================================
-- 8. Datos iniciales (tipos de suscripción)
-- ============================================
INSERT INTO TipoSuscripcion (nombre, duracion_dias, precio)
VALUES
('Mensual', 30, 45.00),
('Trimestral', 90, 120.00),
('Anual', 365, 400.00);
GO