CREATE TABLE `historico_precio_producto` (
  `id_historico_precio_producto` int(11) NOT NULL AUTO_INCREMENT,
  `id_producto` int(11) NOT NULL,
  `precio_anterior` decimal(12,3) DEFAULT '0.000',
  `precio_nuevo` decimal(12,3) DEFAULT '0.000',
  `fecha_actualizacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_stock` int(11) DEFAULT NULL,
  `id_usuario` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id_historico_precio_producto`),
  KEY `fk_historico_precio_producto_usuario_idx` (`id_usuario`),
  KEY `fk_historico_precio_producto_producto_idx` (`id_producto`),
  KEY `fk_historico_precio_producto_stock_idx` (`id_stock`),
  CONSTRAINT `fk_historico_precio_producto_producto` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_historico_precio_producto_stock` FOREIGN KEY (`id_stock`) REFERENCES `stock` (`id_stock`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_historico_precio_producto_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
