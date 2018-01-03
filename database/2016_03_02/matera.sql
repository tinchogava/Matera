CREATE TABLE IF NOT EXISTS `log_evento_presupuesto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL,
  `id_log_evento_tipo` int(11) NOT NULL,
  `id_usuario` int(11) unsigned NOT NULL,
  `id_presupuesto` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_log_evento_tipo` (`id_log_evento_tipo`),
  KEY `id_usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE IF NOT EXISTS `log_evento_remito` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL,
  `id_log_evento_tipo` int(11) NOT NULL,
  `id_usuario` int(11) unsigned NOT NULL,
  `id_remito` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_usuario` (`id_usuario`),
  KEY `id_remito` (`id_remito`),
  KEY `id_evento_tipo` (`id_log_evento_tipo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE IF NOT EXISTS `log_evento_tipo` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

