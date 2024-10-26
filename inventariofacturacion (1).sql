-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-10-2024 a las 04:39:50
-- Versión del servidor: 8.0.36
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `inventariofacturacion`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `idCliente` int NOT NULL,
  `nit` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `telefono` varchar(100) NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `usuario_igresa` varchar(100) NOT NULL,
  `activo` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`idCliente`, `nit`, `nombre`, `telefono`, `direccion`, `usuario_igresa`, `activo`) VALUES
(2, '843221-k', 'Ronaldo Choche', '66370130', 'El pajon', 'admin', 'No'),
(3, '8276319-6', 'Rony Choche', '44694669', 'Casa3', 'ADMIN', 'Si'),
(4, '843221k', 'Ronldo1', 'Chsakfjh', 'sfasf', 'ADMIN', 'No'),
(6, '843221k', 'fiouoi', 'uosiufo', 'u', 'ADMIN', 'No'),
(7, '843221k', 'ronaldo', '54154', 'sac', 'ADMIN', 'Si');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compras`
--

CREATE TABLE `compras` (
  `idcompra` int NOT NULL,
  `producto` varchar(30) NOT NULL,
  `marca` varchar(30) NOT NULL,
  `unidadmedida` varchar(30) NOT NULL,
  `proveedor` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `preciou` decimal(15,2) NOT NULL,
  `unidadescompra` int DEFAULT NULL,
  `total` decimal(15,2) NOT NULL,
  `usuario` varchar(15) NOT NULL,
  `fecha` datetime NOT NULL,
  `activo` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `compras`
--

INSERT INTO `compras` (`idcompra`, `producto`, `marca`, `unidadmedida`, `proveedor`, `preciou`, `unidadescompra`, `total`, `usuario`, `fecha`, `activo`) VALUES
(1, 'Arroz', 'Gallo', 'Libra', 'Proveedor1', 2.00, 2, 4.00, 'ADMIN', '2024-10-21 00:00:00', 'Si'),
(2, 'Arroz', 'Gallo', 'Libra', 'Proveedor1', 2.00, 5, 10.00, 'ADMIN', '2024-10-21 00:00:00', 'Si'),
(3, 'Arroz', 'Gallo', 'Libra', 'Proveedor1', 3.00, 14, 42.00, 'ADMIN', '2024-10-21 00:00:00', 'Si'),
(4, 'Agua', 'Gallo', 'Litro', 'Proveedor2', 14.00, 2, 28.00, 'ADMIN', '2024-10-21 00:00:00', 'Si'),
(5, 'Azucar', 'Sully', 'Libra', 'Proveedor1', 2.50, 14, 35.00, 'ADMIN', '2024-10-22 00:00:00', 'Si'),
(6, 'Soda', 'Coca Cola', '500 ml', 'Proveedor4', 3.50, 15, 52.50, 'ADMIN', '2024-10-22 00:00:00', 'Si'),
(7, 'Soda', 'Coca Cola', '500 ml', 'Proveedor3', 3.50, 10, 35.00, 'ADMIN', '2024-10-22 00:00:00', 'Si');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalelfactura`
--

CREATE TABLE `detalelfactura` (
  `iddetalle` int NOT NULL,
  `nofactura` varchar(100) NOT NULL,
  `fecha` date NOT NULL,
  `idproducto` int NOT NULL,
  `producto` varchar(100) NOT NULL,
  `marca` varchar(100) NOT NULL,
  `unidad` varchar(100) NOT NULL,
  `precio` decimal(15,2) NOT NULL,
  `cantidad` int NOT NULL,
  `totalproducto` decimal(15,2) NOT NULL,
  `activa` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `idfactura` int NOT NULL,
  `nofactura` varchar(100) NOT NULL,
  `fechafac` date NOT NULL,
  `nitcliente` varchar(100) NOT NULL,
  `vendedor` varchar(100) NOT NULL,
  `activo` varchar(100) DEFAULT NULL,
  `nomcliente` varchar(100) NOT NULL,
  `subtotal` decimal(15,2) NOT NULL,
  `iva` decimal(15,2) NOT NULL,
  `total` decimal(15,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca`
--

CREATE TABLE `marca` (
  `idmarca` int NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `activo` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `marca`
--

INSERT INTO `marca` (`idmarca`, `nombre`, `activo`) VALUES
(1, 'Gallo', 'Si'),
(2, 'Sully', 'Si'),
(3, 'Suly1', 'No'),
(4, 'Gama', 'Si'),
(5, 'Pepsi Cola', 'Si'),
(6, 'Coca Cola', 'Si');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `nombre` varchar(100) NOT NULL,
  `unidadmedida` varchar(100) NOT NULL,
  `precioventa` decimal(10,2) NOT NULL,
  `activo` varchar(3) NOT NULL,
  `idproducto` int NOT NULL,
  `marca` varchar(100) NOT NULL,
  `unidades` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`nombre`, `unidadmedida`, `precioventa`, `activo`, `idproducto`, `marca`, `unidades`) VALUES
('Arroz', 'Libra', 4.75, 'Si', 1, 'Gallo', 19),
('Arroz1', 'Libra', 4.50, 'No', 2, 'Gallo', 0),
('Maiz', 'Litro', 3.30, 'Si', 3, 'Gal', 0),
('Frijol', 'Kilo', 3.00, 'Si', 4, 'Gallo', 0),
('Agua', 'Litro', 16.20, 'Si', 5, 'Gallo', 2),
('Azucar', 'Libra', 3.50, 'Si', 6, 'Sully', 14),
('Soda', '500 ml', 5.25, 'Si', 7, 'Coca Cola', 25);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `idProveedor` int NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `telefono` varchar(100) NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `activo` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `proveedores`
--

INSERT INTO `proveedores` (`idProveedor`, `nombre`, `telefono`, `direccion`, `activo`) VALUES
(1, 'Proveedor1', '3216548', 'Direccion', 'Si'),
(2, 'Proveedor2', '12547', 'Direccion2', 'Si'),
(3, 'Proveedor3', '45545', 'sadf', 'Si'),
(4, 'Proveedor5', '54', 'sdafjlskd', 'No'),
(5, 'Proveedor5', '54', 'sdafjlskd', 'No'),
(6, 'Proveedor4', '45545', 'sadf', 'Si'),
(7, 'Proveedor6', '54', 'asdf', 'Si'),
(8, 'Proveedor2', '12547', 'Direccion2', 'Si'),
(9, 'Proveedor2', '12547', 'Direccion2', 'Si'),
(10, 'Vend2', '565', 'c3', 'Si');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `idRol` int NOT NULL,
  `nomrol` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`idRol`, `nomrol`) VALUES
(1, 'Administrador'),
(2, 'Gestor'),
(3, 'Operador');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sucursal`
--

CREATE TABLE `sucursal` (
  `idSucursal` int NOT NULL,
  `nombre_sucursal` varchar(100) NOT NULL,
  `direccion_sucursal` varchar(150) NOT NULL,
  `telefono_sucursal` varchar(15) NOT NULL,
  `activo` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `sucursal`
--

INSERT INTO `sucursal` (`idSucursal`, `nombre_sucursal`, `direccion_sucursal`, `telefono_sucursal`, `activo`) VALUES
(1, 'Sucursal1', 'Dir Sucu0asf', '454asdfsdf', 'Si'),
(2, 'Sucursal2', 'Numero dos', '655888', 'Si'),
(3, 'Sucursal3', '454f5as5f5', 'sff', 'No'),
(4, 'Sucursal3', 'asf', 'saf', 'Si');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `unidadmedida`
--

CREATE TABLE `unidadmedida` (
  `idmedida` int NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `activo` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `unidadmedida`
--

INSERT INTO `unidadmedida` (`idmedida`, `nombre`, `activo`) VALUES
(1, 'Libra', 'Si'),
(2, 'Kilo', 'Si'),
(3, 'Litro', 'Si'),
(4, 'litox', 'No'),
(5, 'Gramo', 'Si'),
(6, '500 ml', 'Si');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int NOT NULL,
  `usuario` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `pass` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `rol` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `usuario`, `pass`, `rol`) VALUES
(1, 'admin', '123', 'Administrador'),
(2, 'Rony', 'Programacion1', 'Gestor'),
(3, 'Conta', '123', 'Operador'),
(4, 'Operador1', 'Operador1', 'Operador'),
(7, 'Rony1', '123', 'Operador'),
(8, 'jjj', '123', 'Administrador'),
(9, 'prueba1', '456', 'Operador'),
(10, 'prueba', 'prueba', 'Administrador'),
(11, 'prubae', 'cHJ1YmFl', 'Administrador'),
(12, 'usuariop', '456', 'Administrador');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vendedores`
--

CREATE TABLE `vendedores` (
  `id_vendedor` int NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `direccion` varchar(150) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `sucursal` varchar(50) NOT NULL,
  `activo` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `vendedores`
--

INSERT INTO `vendedores` (`id_vendedor`, `nombre`, `direccion`, `telefono`, `sucursal`, `activo`) VALUES
(1, 'Carlos Vendedor', 'casa1', '45554', 'Sucursal1', 'Si'),
(2, 'Proveesaf', 'Direccion2', '12547', 'Sucursal1', 'Si'),
(3, 'Vendedor2', 'safasdfsadfasdf', '4564445', 'Sucursal2', 'Si'),
(4, 'Vendedor', 'Direccion2', '12547', 'Sucursal1', 'No'),
(5, 'nuevov', 'asdf', '456456asf', 'Sucursal2', 'Si'),
(6, 'sadf', 'saf', 'sfa', 'Sucursal1', 'Si'),
(7, 'kfks', 'jalksf', 'ajdlfk', 'Sucursal1', 'Si');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`idCliente`);

--
-- Indices de la tabla `compras`
--
ALTER TABLE `compras`
  ADD PRIMARY KEY (`idcompra`);

--
-- Indices de la tabla `detalelfactura`
--
ALTER TABLE `detalelfactura`
  ADD PRIMARY KEY (`iddetalle`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`idfactura`);

--
-- Indices de la tabla `marca`
--
ALTER TABLE `marca`
  ADD PRIMARY KEY (`idmarca`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`idproducto`);

--
-- Indices de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD PRIMARY KEY (`idProveedor`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`idRol`);

--
-- Indices de la tabla `sucursal`
--
ALTER TABLE `sucursal`
  ADD PRIMARY KEY (`idSucursal`);

--
-- Indices de la tabla `unidadmedida`
--
ALTER TABLE `unidadmedida`
  ADD PRIMARY KEY (`idmedida`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `vendedores`
--
ALTER TABLE `vendedores`
  ADD PRIMARY KEY (`id_vendedor`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `idCliente` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `compras`
--
ALTER TABLE `compras`
  MODIFY `idcompra` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `detalelfactura`
--
ALTER TABLE `detalelfactura`
  MODIFY `iddetalle` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `idfactura` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `marca`
--
ALTER TABLE `marca`
  MODIFY `idmarca` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `idproducto` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `idProveedor` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `idRol` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `sucursal`
--
ALTER TABLE `sucursal`
  MODIFY `idSucursal` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `unidadmedida`
--
ALTER TABLE `unidadmedida`
  MODIFY `idmedida` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `vendedores`
--
ALTER TABLE `vendedores`
  MODIFY `id_vendedor` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
