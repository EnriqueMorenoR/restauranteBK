CREATE TABLE `categoria` (
  `id_categoria` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_categoria` varchar(50) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id_categoria`),
  KEY `categoriapk` (`id_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;



INSERT INTO `categoria` VALUES (1,'Entradas'),(2,'Platos'),(3,'Bebidas'),(4,'Postres'),(5,'Promos');

CREATE TABLE `producto` (
  `id_producto` int(11) NOT NULL AUTO_INCREMENT,
  `id_categoria` int(11) NOT NULL,
  `nombre_producto` varchar(100) CHARACTER SET utf8 NOT NULL,
  `precio` double NOT NULL,
  `es_preparado` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_producto`),
  KEY `id_categoria` (`id_categoria`),
  CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

INSERT INTO `producto` VALUES (1,1,'Bocas de Chorizo',5.75,1),(2,1,'Nachos',3.85,1),(3,1,'Sopa de Tortilla',3,1),(4,1,'Bocas de Alitas',4.55,1),(6,2,'Puyaso Argentino',12.5,1),(7,2,'Choripan 30 cm',5.99,1),(8,2,'Chanchopan 30cm',5.5,1),(9,2,'Lomo en Salsa de Hongos',13.75,1),(10,2,'Lomo en Salsa Jalapeña',13.75,1),(11,2,'Milanesa de Pollo',9.99,1),(12,2,'Ensalada Cesar',8,1),(13,2,'Costilla a la Barbacoa',12.99,1),(14,3,'Michelada Nacional',2.75,1),(15,3,'Michelada Extranjera',3.25,1),(16,3,'Pilsener',1,0),(17,3,'Golden',1,0),(18,3,'Corona',1.5,0),(19,3,'Heineken',1.5,0),(20,3,'Coca Cola',1.05,0),(21,3,'Sprite',1.05,0),(22,3,'Fanta',1.05,0),(23,3,'Uva',1.05,0),(24,3,'Copa de Vino Tinto',1.99,0),(25,3,'Copa de Vino Blanco',1.99,0),(26,3,'Botella de Agua',1.05,0),(27,3,'Cafe Americano',1.25,0),(28,3,'Cafe Capuchino',2,1),(29,4,'Tres Leches',1.99,0),(30,4,'Quesadilla',1.75,0),(31,4,'Brownie',1.99,0),(32,4,'Pie de manzana',1.99,0),(33,4,'Pie de Queso',2.25,0),(35,4,'sorbete de dos copas',3.25,1),(36,4,'sorbete de dos copas',3.25,1),(37,5,'Cafe mas tres leches',5.35,1),(38,5,'soda al 2x1',2,1),(39,3,'Michelada de Suprema',2.75,1);

CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET utf8 NOT NULL,
  `apellido` varchar(50) CHARACTER SET utf8 NOT NULL,
  `categoria` varchar(30) CHARACTER SET utf8 NOT NULL,
  `password` varchar(70) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
--

INSERT INTO `usuario` VALUES (1,'Frank','Molina','Mesero','1234'),(2,'Oscar','Fernandez','Mesero','Oscar'),(3,'Tony','Herrera','Chef','ToHerre');


CREATE TABLE `orden` (
  `id_orden` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `mesa` varchar(80) COLLATE utf8_spanish2_ci DEFAULT ' ',
  `cliente` varchar(80) COLLATE utf8_spanish2_ci DEFAULT ' ',
  `estado` varchar(5) COLLATE utf8_spanish2_ci DEFAULT 'A',
  `total` double NOT NULL DEFAULT 0,
  `observacion` varchar(200) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_orden`),
  KEY `ordenpk` (`id_orden`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `orden_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

INSERT INTO `orden` VALUES (1,'2019-11-12','1','Moreno','A',59.25,'',1),(2,'2019-11-12','3','Fabian','C',188.25,'',2),(3,'2019-11-12','5','Walter','A',124.29,'',2),(4,'2019-11-12','6','Juarez','A',19.83,'',1),(5,'2019-11-12','8','Monica','A',73.94,'',1),(6,'2019-11-10','9','Lissa','A',7.55,' ',1);


CREATE TABLE `detalle_orden` (
  `id_orden` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio_unitario` decimal(8,2) NOT NULL,
  PRIMARY KEY (`id_orden`,`id_producto`),
  KEY `id_orden` (`id_orden`),
  KEY `id_producto` (`id_producto`),
  CONSTRAINT `detalle_orden_ibfk_1` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`) ON UPDATE CASCADE,
  CONSTRAINT `detalle_orden_ibfk_2` FOREIGN KEY (`id_orden`) REFERENCES `orden` (`id_orden`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

INSERT INTO `detalle_orden` VALUES (1,2,3,3.85),(1,7,2,5.99),(1,11,1,9.99),(1,12,2,8.00),(1,16,2,1.00),(1,24,1,1.99),(1,27,3,1.25),(1,29,1,1.99),(2,2,10,3.85),(2,7,25,5.99),(3,2,16,3.85),(3,4,5,4.55),(3,7,5,5.99),(3,11,1,9.99),(4,2,1,3.85),(4,7,1,5.99),(4,11,1,9.99),(5,11,2,9.99),(5,12,3,8.00),(5,15,5,3.25),(5,24,4,1.99),(5,27,1,1.25),(5,33,2,2.25),(6,15,2,3.25),(6,23,1,1.05);
