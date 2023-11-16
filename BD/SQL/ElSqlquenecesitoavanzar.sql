create database P_Sales_System

use P_Sales_System
go
-------------------------


CREATE TABLE Categoria(
	idCategoria int identity(100,1) primary key,
	catnombre varchar(100)
);
GO

CREATE TABLE Proveedor(
	idProveedor int identity(1000,5) primary key,
	pronombre varchar(100),
	direccion varchar(255)
);
GO

CREATE TABLE Detalle(
	idDetalleVenta int identity(1,1) primary key,
	idVenta int,
	r_producto varchar(100),
	cantidad int,
	precio float,
	descuento float,
	subtotal float,
	foreign key (idVenta) REFERENCES Venta(idVenta)
)

CREATE TABLE Producto(
	idProducto int identity (418,6) primary key, 
	nombre varchar(100),
	precio float,
	receta VARCHAR (300),
	foto image,
	categoria int foreign key references Categoria(idCategoria),
	proveedor int foreign key references Proveedor(idProveedor)
);
GO
ALTER TABLE Producto
ADD receta VARCHAR(300);

ALTER TABLE Producto
ADD foto image;


Select * From Producto


CREATE TABLE Venta(
	idVenta int identity(1,1) primary key,
	fecha date,
	total float
);
GO

CREATE TABLE DetalleVenta(
	idDetalleVenta int identity(1,1) primary key,
	idVenta int,
	r_producto varchar(100),
	cantidad int,
	precio float,
	descuento float,
	subtotal float,
	impuesto float,
	total float,
	foreign key (idVenta) REFERENCES Venta(idVenta)
);
GO

CREATE TABLE PROBANDO(
	idProbando int identity(1,1) primary key,
	r_producto varchar(100),
	cantidad int,
	precio float,
	descuento float,
	subtotal float,
	impuesto float,
	total float,
);
GO

DROP TABLE DetalleVenta;
select * from PROBANDO;


CREATE TABLE Factura(
	idFactura int identity (1,1) primary key,
	fecha datetime,
	total float,
	venta int foreign key references Venta(idVenta)
);
GO
CREATE TABLE Cuenta (
IDCuenta int identity (1,1) primary key,
Usuario varchar (50),
Contraseña varchar (50)
);
Go
----------------------------------
--Cuenta de la reposteria:

Insert Into Cuenta Values ('Paula','paula123456');
-----------------------------------
--CATEGORÍAS
INSERT INTO Categoria (catnombre) VALUES
('Pasteles'),
('Galletas'),
('Flanes'),
('Gelatinas');
go

--PROVEEDORES
INSERT INTO Proveedor (pronombre, direccion) VALUES
('Mercado de Ingeniería', 'Av. Luna Pizarro 277, San Martín de Porres 15103'),
('Mercado Milagros','Ca. Sta. Mercedes 184, San Martín de Porres 15103')
go

--PRODUCTOS
INSERT INTO Producto (nombre,precio,categoria,proveedor) VALUES
('Keke de Chocolate', 20.0, 100, 1005),
('Pastel 3 Leches', 22.0, 100, 1000),
('Pie de Manzana', 18.0, 100, 1000),
('Pastel de Queso', 25.0, 100, 1000),
('Pastel de Selva Negra', 45.0, 100, 1005),
('Pastel de Moka', 50.0, 100, 1005),

('Rosquita', 0.20, 101, 1005),
('Alfajores', 0.30, 101, 1000),
('Empanadas Dulces', 1.0, 101, 1000),
('Empanadas Dulces con relleno', 0.40, 101, 1000),
('Galletas de Chocolate', 0.40, 101, 1005),
('Milhojas', 1.50, 101, 1000),

('Flan Clasico', 1.50, 102, 1005),
('Flan de Coco', 2.0, 102, 1005),
('Flan de Maracuya', 2.0, 102, 1005),
('Flan de Chocolate', 2.0, 102, 1005),
('Flan con Gelatina', 2.0, 102, 1005),
('Flan Napolitano', 2.0, 102, 1005),

('Gelatina de Mosaico', 2.50, 103, 1005),
('Gelatina con Leche', 1.50, 103, 1000),
('Torta Helada', 50.0, 103, 1005)
Go


--------------------------------

CREATE PROCEDURE SP_E_DETALLEVENTA 
	@idVenta int,
	@r_producto varchar(100),
	@cantidad int,
	@descuento float,
	@subtotal float,
	@impuesto float,
	@total float
AS
BEGIN	
	INSERT INTO DetalleVenta (idVenta, r_producto, cantidad, descuento, subtotal, impuesto, total)
					  VALUES (@idVenta, @r_producto, @cantidad, @descuento, @subtotal, @impuesto, @total)
END;


CREATE PROCEDURE SP_E_VENTA
	@total float
AS
BEGIN
	INSERT INTO Venta (fecha, total)	
			   VALUES (GETDATE(), @total)
END;


CREATE PROCEDURE SP_E_FACTURA
	@total float,
	@venta int
AS
BEGIN
	INSERT INTO Factura (fecha, total, venta)
				 VALUES (GETDATE(), @total, @venta)
END;
--------------------------------------------------------
CREATE PROCEDURE InsertarProducto
    @nombre VARCHAR(100),
    @precio FLOAT,
    @categoria INT,
    @proveedor INT,
    @receta VARCHAR(300),
    @foto image
AS
BEGIN
    INSERT INTO Producto (nombre, precio, categoria, proveedor, receta, foto)
    VALUES (@nombre, @precio, @categoria, @proveedor, @receta, @foto);
END;
---------------------------------------------------------
CREATE PROCEDURE ActualizarProducto
    @idProducto INT,
    @nombre VARCHAR(100),
    @precio FLOAT,
    @categoria INT,
    @proveedor INT,
    @receta VARCHAR(300),
    @foto image
AS
BEGIN
    UPDATE Producto
    SET nombre = @nombre, precio = @precio, categoria = @categoria, proveedor = @proveedor, receta = @receta, foto = @foto
    WHERE idProducto = @idProducto;
END;

CREATE PROCEDURE ActualizarProducto2
    @idProducto INT,
    @nombre VARCHAR(100),
    @precio FLOAT,
    @categoria INT,
    @proveedor INT,
    @receta VARCHAR(300)
AS
BEGIN
    UPDATE Producto
    SET nombre = @nombre, precio = @precio, categoria = @categoria, proveedor = @proveedor, receta = @receta
    WHERE idProducto = @idProducto;
END;
----------------------------------------------------------------------
CREATE PROCEDURE EliminarProducto
    @idProducto INT
AS
BEGIN
    DELETE FROM Producto
    WHERE idProducto = @idProducto;
END;

-------------------------------------
CREATE PROCEDURE MostrarTodosLosProductos
AS
BEGIN
    SELECT * FROM Producto;
END;

------------
CREATE PROCEDURE sp_GetProveedorNames
AS
BEGIN
    SELECT idProveedor, pronombre FROM Proveedor;
END;

-----------
CREATE PROCEDURE sp_GetCategoria
AS
BEGIN
    SELECT idCategoria, catnombre FROM Categoria;
END;

---------------------
CREATE PROCEDURE sp_GetProductoNames
AS
BEGIN
    SELECT nombre FROM Producto;
END;

-------------------------
CREATE PROCEDURE sp_GetPrecioPorNombre(p_nombre VARCHAR(255))
BEGIN
    SELECT precio FROM Producto WHERE nombre = p_nombre;
END;
----------------------------------
CREATE PROCEDURE InsertarDatosVentas
    @r_producto VARCHAR(100),
    @cantidad INT,
    @precio FLOAT,
    @descuento FLOAT,
    @subtotal FLOAT,
    @impuesto FLOAT,
    @total FLOAT
AS
BEGIN
    INSERT INTO PROBANDO (r_producto, cantidad, precio, descuento, subtotal, impuesto, total)
    VALUES (@r_producto, @cantidad, @precio, @descuento, @subtotal, @impuesto, @total);
END;
------------------
CREATE PROCEDURE ObtenerDatosCuenta
    @usuario VARCHAR(50),
    @contrasena VARCHAR(50)
AS
BEGIN
    SELECT
        IDCuenta AS codigo,
        Usuario AS nombrecompleto,
        '****' AS clave
    FROM
        Cuenta
    WHERE
        Usuario = @usuario AND Contraseña = @contrasena;
END;
-----------------------------


DROP PROCEDURE sp_GetProveedorNames

Select * from Detalle
Select * from PROBANDO

Select * From Producto

