-- CREATE DATABASE  IF NOT EXISTS `matera_testing` /*!40100 DEFAULT CHARACTER SET latin1 */;
-- USE `matera_testing`;
-- MySQL dump 10.13  Distrib 5.6.27, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: matera_testing
-- ------------------------------------------------------
-- Server version	5.6.27-0ubuntu0.15.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping routines for database 'matera_testing'
--
/*!50003 DROP FUNCTION IF EXISTS `calculaGanancia` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `calculaGanancia`(base DECIMAL(9,2)) RETURNS decimal(9,2)
BEGIN

DECLARE aliGanancia DECIMAL(6,3);

SELECT (valor/100) INTO aliGanancia
  FROM porcentaje
  WHERE porcentaje.id_grupoPorcentaje = 3;
        
  
  
  RETURN ROUND(base * aliGanancia, 2);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `calculaIIBB` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `calculaIIBB`(neto DECIMAL(9,2),
                                                id_zona INT) RETURNS decimal(9,2)
BEGIN

  DECLARE aliIIBB DECIMAL(6,3);
  
  SELECT (valor/100) INTO aliIIBB
  FROM porcentaje
  WHERE porcentaje.id_grupoPorcentaje = 5 AND
        porcentaje.id_zona = id_zona;
        
  
  
  RETURN ROUND(neto * aliIIBB, 2);
  
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `cantPresuAprobados` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `cantPresuAprobados`(id_entidad INT,
                                                                desde DATE,
                                                                hasta DATE) RETURNS int(11)
BEGIN

DECLARE cantidad INT;
  
  SELECT COUNT(*) INTO cantidad
  FROM presupuesto
  WHERE presupuesto.id_entidad = id_entidad AND
  presupuesto.estado NOT IN ('R', 'N', ' ') AND
  IF(desde IS NULL, true, presupuesto.fecha >= desde) AND
  IF(hasta IS NULL, true, presupuesto.fecha <= hasta);
  
  RETURN cantidad;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `cantPresuEmitidos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `cantPresuEmitidos`(id_entidad INT,
                                                                desde DATE,
                                                                hasta DATE) RETURNS int(11)
BEGIN

DECLARE cantidad INT;
  
  SELECT COUNT(*) INTO cantidad
  FROM presupuesto
  WHERE presupuesto.id_entidad = id_entidad AND
        IF(desde IS NULL, true, presupuesto.fecha >= desde) AND
        IF(hasta IS NULL, true, presupuesto.fecha <= hasta);
  
  RETURN cantidad;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `cantPresuFacturados` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `cantPresuFacturados`(id_entidad INT,
                                                                    desde DATE,
                                                                    hasta DATE) RETURNS int(11)
BEGIN

DECLARE cantidad INT;
  
  SELECT COUNT(*) INTO cantidad
  FROM presupuesto
  WHERE presupuesto.id_entidad = id_entidad AND
    presupuesto.id_presupuesto IN (SELECT factura.id_presupuesto
                                    FROM factura
                                    WHERE IF(desde IS NULL, true, factura.fecha >= desde) AND
                                            IF(hasta IS NULL, true, factura.fecha <= hasta));
  
  RETURN cantidad;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `costoPesos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `costoPesos`(id_producto INT) RETURNS decimal(15,2)
BEGIN

DECLARE costo decimal(12,3);

SELECT (producto.costo * IFNULL(ultimaCotizacion(producto.id_moneda), 1)) INTO costo
FROM producto
WHERE producto.id_producto = id_producto;

RETURN ROUND(costo, 2);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `detalleLiquidacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `detalleLiquidacion`(id_liquidacion INT) RETURNS text CHARSET latin1
BEGIN

DECLARE detalle TEXT;

SELECT ifnull(GROUP_CONCAT(concat_ws(' ', banco,cheque,DATE_FORMAT(vencimiento, "%d/%m/%Y")) SEPARATOR ", "), '') INTO detalle

FROM liquidacionDetalle

WHERE liquidacionDetalle.id_liquidacion = id_liquidacion

GROUP BY liquidacionDetalle.id_liquidacion;

RETURN detalle;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `devengado` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `devengado`(id_presupuesto INT, fechaCirugia DATE) RETURNS tinyint(1)
BEGIN

DECLARE cantidad INT;
SELECT COUNT(*) INTO cantidad
FROM factura
WHERE factura.id_presupuesto = id_presupuesto AND
		DATEDIFF(factura.fecha, fechaCirugia) >= 0;

RETURN (cantidad > 0);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `diasDiferencia` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `diasDiferencia`(fechaRecibo DATE, fechaFactura DATE) RETURNS int(11)
BEGIN

DECLARE dias INT;

SELECT CEILING(DATEDIFF(fechaRecibo, fechaFactura) / 30) * 30 INTO dias;

RETURN dias;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `facturaAntes` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `facturaAntes`(id_presupuesto INT,
								mes INT) RETURNS tinyint(1)
BEGIN

DECLARE neto DECIMAL(9,2);
SELECT sum(if(factura.id_tipoComp IN (3, 7, 10, 13, 25, 21), -factura.neto, factura.neto)) INTO neto

	FROM factura
    
    WHERE factura.id_presupuesto = id_presupuesto AND
			MONTH(factura.fecha) < mes
            
	GROUP BY factura.id_presupuesto;

RETURN neto > 0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `fechaComprobante` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `fechaComprobante`(id_tipoComp INT,
                                            numComp VARCHAR(12),
                                            id_empresa INT) RETURNS date
BEGIN

DECLARE fecha DATE;

SELECT factura.fecha INTO fecha

FROM factura

WHERE factura.id_tipoComp = id_tipoComp AND
        factura.numComp = numComp AND
        factura.id_empresa = id_empresa
        
LIMIT 1;

RETURN fecha;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `is_id_in_ids` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `is_id_in_ids`(`strIDs` TEXT,`_id` BIGINT) RETURNS bit(1)
BEGIN

  DECLARE strLen    INT DEFAULT 0;
  DECLARE subStrLen INT DEFAULT 0;
  DECLARE subs      VARCHAR(255);

  IF strIDs IS NULL THEN
    SET strIDs = '';
  END IF;

  do_this:
    LOOP
      SET strLen = LENGTH(strIDs);
      SET subs = SUBSTRING_INDEX(strIDs, ',', 1);

      if ( CAST(subs AS UNSIGNED) = _id ) THEN
        -- founded
        return(1);
      END IF;

      SET subStrLen = LENGTH(SUBSTRING_INDEX(strIDs, ',', 1));
      SET strIDs = MID(strIDs, subStrLen+2, strLen);

      IF strIDs = NULL or trim(strIds) = '' THEN
        LEAVE do_this;
      END IF;

  END LOOP do_this;

   -- not founded
  return(0);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `montoFactTotal` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `montoFactTotal`(id_entidad INT,
                                                            desde DATE,
                                                            hasta DATE) RETURNS decimal(20,2)
BEGIN

DECLARE monto DECIMAL(20,2);
  
  SELECT SUM(IF(factura.dc = 'D', 1, (-1)) * neto + percIIBB + iva) INTO monto
  FROM factura
  WHERE factura.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                    FROM presupuesto
                                    WHERE presupuesto.id_entidad = id_entidad) AND
        IF(desde IS NULL, true, factura.fecha >= desde) AND
        IF(hasta IS NULL, true, factura.fecha <= hasta);
  
  RETURN monto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `montoFacturado` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `montoFacturado`(id_presupuesto INT) RETURNS decimal(9,2)
BEGIN

DECLARE monto DECIMAL(20,2);
  
  SELECT SUM(IF(factura.dc = 'D', 1, (-1)) * neto + percIIBB + iva) INTO monto
  FROM factura
  WHERE factura.id_presupuesto = id_presupuesto
  GROUP BY factura.id_presupuesto;
  
  RETURN monto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `montoNetoFacturado` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `montoNetoFacturado`(id_presupuesto INT) RETURNS decimal(20,2)
BEGIN

DECLARE monto DECIMAL(20,2);
  
  SELECT SUM(IF(factura.dc = 'D', neto, (-neto))) INTO monto
  FROM factura
  WHERE factura.id_presupuesto = id_presupuesto AND
		factura.id_tipoComp NOT IN (4,16,17,18)
  GROUP BY factura.id_presupuesto;
  
  RETURN monto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `montoPresuAprobados` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `montoPresuAprobados`(id_entidad INT,
                                                                    desde DATE,
                                                                    hasta DATE) RETURNS decimal(20,2)
BEGIN

DECLARE monto DECIMAL(20,2);
  
  SELECT SUM(cantidad * pxUnit) INTO monto
  FROM presupuestoprod
  WHERE presupuestoprod.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                            FROM presupuesto
                                            WHERE presupuesto.id_entidad = id_entidad AND
                                                presupuesto.estado NOT IN ('R', 'N', ' ') AND
                                                IF(desde IS NULL, true, presupuesto.fecha >= desde) AND
                                                IF(hasta IS NULL, true, presupuesto.fecha <= hasta));
  
  RETURN monto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `montoPresuEmitidos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `montoPresuEmitidos`(id_entidad INT,
                                                                desde DATE,
                                                                hasta DATE) RETURNS decimal(20,2)
BEGIN

DECLARE monto DECIMAL(20,2);
  
  SELECT SUM(cantidad * pxUnit) INTO monto
  FROM presupuestoprod
  WHERE presupuestoprod.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                            FROM presupuesto
                                            WHERE presupuesto.id_entidad = id_entidad AND
                                            IF(desde IS NULL, true, presupuesto.fecha >= desde) AND
                                            IF(hasta IS NULL, true, presupuesto.fecha <= hasta));
  
  RETURN monto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `montoPresupuesto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `montoPresupuesto`(id_presupuesto INT) RETURNS decimal(9,2)
BEGIN

DECLARE monto DECIMAL(20,2);
  
  SELECT SUM(cantidad * pxUnit) INTO monto
  FROM presupuestoprod
  WHERE presupuestoprod.id_presupuesto = id_presupuesto
  GROUP BY presupuestoprod.id_presupuesto;
  
  RETURN monto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `proximaLiquidacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `proximaLiquidacion`(id_empresa INT) RETURNS int(11)
BEGIN

DECLARE proximaLiquidacion INT;

SELECT MAX(liquidacion.id_liquidacion) INTO proximaLiquidacion
FROM liquidacion
WHERE liquidacion.id_empresa = id_empresa;

RETURN proximaLiquidacion;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `proximaPreliquidacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `proximaPreliquidacion`(id_empresa INT) RETURNS int(11)
BEGIN

DECLARE proximaPreliquidacion INT;

SELECT (MAX(preliquidacion) + 1) INTO proximaPreliquidacion

FROM mayorprofesional

WHERE mayorprofesional.id_empresa = id_empresa;

RETURN proximaPreliquidacion;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `proximoPresupuesto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `proximoPresupuesto`(id_empresa INT) RETURNS int(11)
BEGIN
DECLARE proximoPresupuesto INT;

SELECT MAX(presupuesto.id_presupuesto) INTO proximoPresupuesto
FROM presupuesto
WHERE presupuesto.id_empresa = id_empresa;

RETURN proximoPresupuesto;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `proximoRemito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `proximoRemito`(id_empresa INT) RETURNS int(11)
BEGIN

DECLARE proximoRemito INT;

SELECT MAX(remito.id_remito) INTO proximoRemito
FROM remito
WHERE remito.id_empresa = id_empresa;

RETURN proximoRemito;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `saldoFactura` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `saldoFactura`(id_presupuesto INT, id_empresa INT) RETURNS decimal(15,2)
BEGIN

DECLARE saldoFactura DECIMAL(15,2);

SELECT SUM(IF(factura.dc = 'D', factura.neto, -factura.neto)) INTO saldoFactura

FROM factura

WHERE factura.id_empresa = id_empresa AND
        factura.id_presupuesto = id_presupuesto AND
        factura.id_tipoComp NOT IN (4, 16, 17, 18, 19);
        
RETURN saldoFactura;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `saldoInicialProfesional` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `saldoInicialProfesional`(id_profesional INT, desde DATE, id_empresa INT) RETURNS decimal(9,2)
BEGIN

DECLARE saldo DECIMAL(9,2);

SELECT IF(desde IS NULL, 0, SUM(IF(mayorprofesional.dc = 'D', mayorprofesional.importe, -mayorprofesional.importe))) INTO saldo

FROM mayorprofesional

WHERE mayorprofesional.id_empresa = id_empresa AND 
    mayorprofesional.id_profesional = id_profesional AND
    IF(desde IS NULL, true, mayorprofesional.fecha < desde) AND
    IF(mayorprofesional.preliquidacion = 0, true, IF(liquidacion > 0, true, false))

GROUP BY mayorprofesional.id_profesional;

RETURN saldo;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `stockSaldoInicial` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `stockSaldoInicial`(id_zona INT,id_producto INT,desde DATE,id_empresa INT) RETURNS int(11)
BEGIN

DECLARE saldoInicial INT;

SELECT SUM(IF(stockdetalle.dc = 'D', stockdetalle.cantidad, -stockdetalle.cantidad)) into saldoInicial

FROM stockdetalle

WHERE stockdetalle.id_producto = id_producto AND
    (stockdetalle.id_stock IN (SELECT id_stock 
                                    FROM stock 
                                    WHERE stock.id_empresa = id_empresa AND
                                    stock.fecha < desde AND
                                    stock.id_zona = id_zona) OR
                                    
        stockdetalle.id_remito IN (SELECT id_remito
                                    FROM remito
                                    WHERE remito.id_empresa = id_empresa AND
                                    remito.fecha < desde AND
                                    (remito.id_zona = id_zona or
										(remito.id_zonaDestino = id_zona AND remito.recibido = 'S'))) OR
                                    
        stockdetalle.id_ajusteStock IN (SELECT id_ajusteStock
                                    FROM ajusteStock
                                    WHERE ajusteStock.id_empresa = id_empresa AND
                                    ajusteStock.fecha < desde AND
                                    ajusteStock.id_zona = id_zona)) AND
                                    
	stockdetalle.id_zona = id_zona; 
RETURN saldoInicial;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `traeDebeAnticipo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `traeDebeAnticipo`(id_profesional INT) RETURNS decimal(9,2)
BEGIN
DECLARE debe DECIMAL(9,2);
  
  SELECT SUM(IF(dc = 'D', importe, 0)) INTO debe
  FROM mayorprofesional
  WHERE mayorprofesional.id_profesional = id_profesional AND
        mayorprofesional.id_presupuesto = 0 AND
        mayorprofesional.liquidacion > 0
  GROUP BY mayorprofesional.id_presupuesto;
  
  RETURN debe;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `traeDebePreliquidacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `traeDebePreliquidacion`(id_presupuesto INT,
                                                                    id_profesional INT) RETURNS decimal(9,2)
BEGIN

DECLARE debe DECIMAL(9,2);
  
  SELECT SUM(IF(dc = 'D', importe, 0)) INTO debe
  FROM mayorprofesional
  WHERE mayorprofesional.id_profesional = id_profesional AND
        mayorprofesional.id_presupuesto = id_presupuesto AND
        IF(mayorprofesional.preliquidacion = 0, true, IF(liquidacion > 0, true, false))
  GROUP BY mayorprofesional.id_presupuesto;
  
  RETURN debe;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `traeHaberPreliquidacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `traeHaberPreliquidacion`(id_presupuesto INT,
                                                                    id_profesional INT) RETURNS decimal(9,2)
BEGIN

DECLARE haber DECIMAL(9,2);
  
  SELECT SUM(IF(dc = 'C', importe, 0)) INTO haber
  FROM mayorprofesional
  WHERE mayorprofesional.id_profesional = id_profesional AND
        mayorprofesional.id_presupuesto = id_presupuesto AND
        mayorprofesional.preliquidacion = 0 AND
        mayorprofesional.liquidacion <= 0
  GROUP BY mayorprofesional.id_presupuesto;
  
  RETURN haber;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `traePendiente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `traePendiente`(id_presupuesto INT,
                                                           id_profesional INT) RETURNS decimal(9,2)
BEGIN

DECLARE pendiente DECIMAL(9,2);
  
  SELECT SUM(importe) INTO pendiente
  FROM mayorprofesional
  WHERE mayorprofesional.id_presupuesto = id_presupuesto AND
        mayorprofesional.id_profesional = id_profesional AND
        mayorprofesional.preliquidacion > 0 AND
        mayorprofesional.liquidacion <= 0
  GROUP BY mayorprofesional.id_presupuesto;
  
  RETURN pendiente;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `traeRM` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `traeRM`(id_presupuesto INT,
                                                    desde DATE,
                                                    hasta DATE) RETURNS decimal(9,2)
BEGIN

  DECLARE rm DECIMAL(9,2);
  
  SELECT SUM(IF(mayorprofesional.dc = 'D', importe, -importe)) INTO rm
  FROM mayorprofesional
  WHERE mayorprofesional.id_presupuesto = id_presupuesto AND
        mayorprofesional.preliquidacion = 0
  GROUP BY mayorprofesional.id_presupuesto;
  
  RETURN rm;
  
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `traeRmCobranza` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `traeRmCobranza`(id_presupuesto INT) RETURNS decimal(9,2)
BEGIN

  DECLARE rm DECIMAL(9,2);
  
  SELECT SUM(mayorprofesional.importe) INTO rm
  FROM mayorprofesional
  WHERE mayorprofesional.id_presupuesto = id_presupuesto AND
        mayorProfesional.dc = 'C' AND
        mayorprofesional.preliquidacion = 0 AND
        mayorprofesional.liquidacion = 0
  GROUP BY mayorprofesional.id_presupuesto;
  
  RETURN rm;
  
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `ultimaCotizacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` FUNCTION `ultimaCotizacion`(id_moneda INT) RETURNS decimal(12,3)
BEGIN

DECLARE cotizacion DECIMAL(12,3);

SELECT cotizacion.valor INTO cotizacion

FROM cotizacion

WHERE cotizacion.id_moneda = id_moneda

ORDER BY cotizacion.id_cotizacion DESC

LIMIT 1;

RETURN cotizacion;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizaCotizacionStockDetalle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `actualizaCotizacionStockDetalle`(IN mes INT,
														año INT,
                                                        id_moneda INT,
                                                        valor DECIMAL(12,3),
                                                        id_empresa INT,
                                                        usuario VARCHAR(45),
                                                        equipo VARCHAR(255))
BEGIN

DECLARE log TEXT;

UPDATE stockDetalle SET
	stockDetalle.cotizacion = valor
    
WHERE stockDetalle.id_moneda = id_moneda AND 
	(stockDetalle.id_stock IN (SELECT stock.id_stock
								FROM stock
                                WHERE MONTH(stock.fecha) = mes AND
										YEAR(stock.fecha) = año AND
                                        stock.id_empresa = id_empresa) OR 
                                
	stockDetalle.id_remito IN (SELECT remito.id_remito
								FROM remito
                                WHERE MONTH(remito.fecha) = mes AND
										YEAR(remito.fecha) = año AND
                                        remito.id_empresa = id_empresa) OR
                                        
	stockDetalle.id_ajusteStock IN (SELECT ajusteStock.id_ajusteStock
									FROM ajusteStock
									WHERE MONTH(ajusteStock.fecha) = mes AND
											YEAR(ajusteStock.fecha) = año AND
											ajusteStock.id_empresa = id_empresa));
                                            
SET log = CONCAT("Actualiza cotización de id_moneda = ", id_moneda, ", periodo ", mes, "/", año, ", con valor ", valor);

CALL `matera`.`logSistema`(usuario, equipo, log);


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizaFactura` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `actualizaFactura`(IN id_factura INT,
                                                            fecha DATE,
                                                            id_tipoComp INT,
                                                            numComp VARCHAR(12),
                                                            id_tipoCompRel INT,
                                                            numCompRel VARCHAR(12),
                                                            vencimiento DATE,
                                                            id_formaPago INT,
                                                            dc VARCHAR(1),
                                                            bonificacion DECIMAL(9,2),
                                                            neto DECIMAL(9,2),
                                                            aliPercIIBB DECIMAL(9,2),
                                                            percIIBB DECIMAL(9,2),
                                                            aliIva DECIMAL(9,2),
                                                            iva DECIMAL(9,2),
                                                            observaciones TEXT,
                                                            usuario VARCHAR(45),
                                                            equipo VARCHAR(255))
BEGIN

DECLARE log TEXT;

UPDATE factura SET
    factura.fecha = fecha,
    factura.id_tipoComp = id_tipoComp,
    factura.numComp = numComp,
    factura.id_tipoCompRel = id_tipoCompRel,
    factura.numCompRel = numCompRel,
    factura.vencimiento = vencimiento,
    factura.id_formaPago = id_formaPago,
    factura.dc = dc,
    factura.bonificacion = bonificacion,
    factura.neto = neto,
    factura.aliPercIIBB = aliPercIIBB,
    factura.percIIBB = percIIBB,
    factura.aliIva = aliIva,
    factura.iva = iva,
    factura.observaciones = observaciones,
    factura.usuario = usuario,
    factura.fechaReal = CURRENT_TIMESTAMP
    
WHERE factura.id_factura = id_factura;

SET log = CONCAT("Actualiza factura con id = ", id_factura);

call logSistema(usuario, equipo, log);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizaLiquidacionTecnico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `actualizaLiquidacionTecnico`(IN id_empresa INT)
BEGIN

UPDATE numeracionliquidaciones 
    SET numeracionliquidaciones.tecnico = numeracionliquidaciones.tecnico + 1
    WHERE numeracionliquidaciones.id_empresa = id_empresa;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `anulaCircuito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `anulaCircuito`(IN id_presupuesto INT,
                                            usuario VARCHAR(45))
BEGIN

DECLARE log TEXT;

UPDATE presupuesto SET 
    presupuesto.estado = 'P',
    presupuesto.id_motivo = 0,
    presupuesto.motivo = "",
    presupuesto.usuario = usuario,
	presupuesto.fechaReal = CURRENT_TIMESTAMP()
WHERE presupuesto.id_presupuesto = id_presupuesto;
    
DELETE
FROM liquidacionDetalle
WHERE liquidacionDetalle.id_liquidacion IN (SELECT liquidacion.id_liquidacion
                                                FROM liquidacion
                                                WHERE liquidacion.id_liquidacion IN (SELECT mayorProfesional.liquidacion
                                                                                        FROM mayorProfesional
                                                                                        WHERE mayorProfesional.id_presupuesto = id_presupuesto));
                                                                                        
DELETE
FROM liquidacion
WHERE liquidacion.id_liquidacion IN (SELECT mayorProfesional.liquidacion
                                       FROM mayorProfesional
                                       WHERE mayorProfesional.id_presupuesto = id_presupuesto);
                                       
DELETE
FROM mayorProfesional
WHERE mayorProfesional.id_presupuesto = id_presupuesto;

DELETE
FROM stockDetalle
WHERE stockDetalle.id_remito IN (SELECT remito.id_remito
                                    FROM remito
                                    WHERE remito.id_presupuesto = id_presupuesto);
                                    
DELETE
FROM remito
WHERE remito.id_presupuesto = id_presupuesto;

DELETE
FROM factura
WHERE factura.id_presupuesto = id_presupuesto;

DELETE
FROM cajaAsignada
WHERE cajaAsignada.id_presupuesto = id_presupuesto;

DELETE
FROM mayorTecnico
WHERE mayorTecnico.id_presupuesto = id_presupuesto;

DELETE
FROM mayorVendedor
WHERE mayorVendedor.id_presupuesto = id_presupuesto;

DELETE
FROM nivelesCertificado
WHERE nivelesCertificado.id_presupuesto = id_presupuesto;

SET log = CONCAT("Anula circuito de presupuesto ", id_presupuesto);

CALL logSistema(usuario, "", log);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `anulaLiquidacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `anulaLiquidacion`(IN id_liquidacion INT,
                                                        id_empresa INT)
BEGIN

DELETE FROM liquidacion

WHERE liquidacion.id_liquidacion = id_liquidacion AND
        liquidacion.id_empresa = id_empresa;
        
DELETE FROM liquidaciondetalle

WHERE liquidaciondetalle.id_liquidacion = id_liquidacion AND
        liquidaciondetalle.id_empresa = id_empresa;

DELETE FROM mayorprofesional
    
WHERE mayorprofesional.liquidacion = id_liquidacion AND
    mayorprofesional.id_empresa = id_empresa;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `aplicaAnticipo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `aplicaAnticipo`(IN id_profesional INT,
                                                        aplicacion DECIMAL(7,2),
                                                        id_presupuesto INT,
                                                        usuario VARCHAR(45))
BEGIN

INSERT INTO mayorProfesional (fecha, id_presupuesto, id_profesional, dc, importe,
                                observaciones, preliquidacion, liquidacion, transferencia, id_empresa,
                                usuario)
                                
						VALUES (CURRENT_DATE(), 0, id_profesional, "C", aplicacion,
								CONCAT('Aplicacion manual de anticipos - ', usuario), 0, -1, "N", 1,
                                usuario);
    
INSERT INTO mayorProfesional (fecha, id_presupuesto, id_profesional, dc, importe,
                                observaciones, preliquidacion, liquidacion, transferencia, id_empresa,
                                usuario)
                                
						VALUES (CURRENT_DATE(), id_presupuesto, id_profesional, "D", aplicacion,
								CONCAT('Aplicacion manual de anticipos - ', usuario), 0, -1, "N", 1,
                                usuario);
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `armaBotonesDetalleForecast` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `armaBotonesDetalleForecast`(IN id_entidad INT,
													id_vendedor INT,
                                                    id_productoFact INT,
                                                    anio INT,
                                                    mes INT)
BEGIN

select sum(IF(presupuesto.vip = "S", IFNULL(presupuestoprod.cantidad, 0), 0)) as vip, 
		sum(IF(presupuesto.estado = "A", IFNULL(presupuestoprod.cantidad, 0), 0)) as aprobados,
		SUM(IF(presupuesto.estado = "Z" AND (presupuesto.id_presupuesto NOT IN (SELECT factura.id_presupuesto
																					FROM factura)), IFNULL(presupuestoprod.cantidad, 0), 0)) as pendientes,
		SUM(IF(presupuesto.estado = "Z" AND (IF(mes  = 0, true, MONTH(presupuesto.fecha) = mes) AND presupuesto.id_presupuesto IN (SELECT factura.id_presupuesto
																																		FROM factura)), IFNULL(presupuestoprod.cantidad, 0), 0)) as facturados
from presupuesto
left join presupuestoprod on presupuestoprod.id_presupuesto = presupuesto.id_presupuesto AND 
								presupuestoprod.id_productofact = id_productoFact
WHERE presupuesto.id_lugarCirugia = id_entidad AND
		presupuesto.id_vendedor = id_vendedor AND
		YEAR(presupuesto.fecha) = anio AND
		(presupuesto.vip = "S" OR
			presupuesto.estado = "A" or
			(presupuesto.estado = "Z" AND (presupuesto.id_presupuesto NOT IN (SELECT factura.id_presupuesto
																					FROM factura) OR
			(IF(mes  = 0, true, MONTH(presupuesto.fecha) = mes) AND presupuesto.id_presupuesto IN (SELECT factura.id_presupuesto
																										FROM factura))))) AND
		presupuesto.id_presupuesto IN (SELECT presupuestoProd.id_presupuesto
										FROM presupuestoProd
                                        WHERE presupuestoProd.id_productoFact = id_productoFact);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `armaForecastEntidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `armaForecastEntidad`(IN id_entidad INT,
											anio INT,
											mes INT,
                                            id_vendedor INT,
                                            id_forecastGrupo INT)
BEGIN

select IFNULL(prodpresu.cantidad, 0) as cantidad
from forecast
left join productoFact on productoFact.id_productoFact = forecast.id_productoFact
left join (SELECT SUM(presupuestoProd.cantidad) as cantidad, presupuestoprod.id_productoFact
			from presupuestoprod
            where presupuestoProd.id_presupuesto IN (SELECT presupuesto.id_presupuesto
														FROM presupuesto
														WHERE IF(id_entidad > 0, presupuesto.id_lugarCirugia = id_entidad, presupuesto.id_lugarCirugia NOT IN (SELECT forecast.id_entidad
																																						FROM forecast
																																						where forecast.anio = anio AND
																																								forecast.id_vendedor = id_vendedor)) AND
																presupuesto.id_vendedor = id_vendedor AND
																YEAR(presupuesto.fecha) = anio AND
																(presupuesto.estado = "A" OR (presupuesto.estado = "Z" AND (presupuesto.id_presupuesto NOT IN (SELECT factura.id_presupuesto
																																	FROM factura) OR
																															(IF(mes  = 0, true, MONTH(presupuesto.fecha) = mes) AND presupuesto.id_presupuesto IN (SELECT factura.id_presupuesto
																																																					FROM factura))))))
group by presupuestoprod.id_productoFact) as prodpresu ON prodpresu.id_productoFact = forecast.id_productoFact
									
where forecast.anio = anio AND
		forecast.id_vendedor = id_vendedor AND
        IF(id_forecastGrupo = 0, true, forecast.id_productoFact IN (SELECT productoFact.id_productoFact
																		FROM productoFact
                                                                        WHERE productoFact.id_forecastGrupo = id_forecastGrupo))
group by productoFact.codigo
order by productoFact.codigo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `cambiaContraseña` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `cambiaContraseña`(IN id_usuario INT,
                                                        pass VARCHAR(45))
BEGIN

UPDATE usuario SET
    usuario.contraseña = pass
    
WHERE usuario.id_usuario = id_usuario;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `cambiaObservaciones` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `cambiaObservaciones`(IN id_presupuesto INT,
											observaciones TEXT,
                                            usuario VARCHAR(45))
BEGIN

UPDATE presupuesto SET
		presupuesto.observaciones = observaciones,
        presupuesto.fechaReal = current_timestamp,
        presupuesto.usuario = usuario
        
WHERE presupuesto.id_presupuesto = id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `cambiaPrecios` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `cambiaPrecios`(IN id_producto INT,
                                                        costo DECIMAL(12,3))
BEGIN

UPDATE producto SET

producto.costo = costo

WHERE producto.id_producto = id_producto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `cambioPrecio` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `cambioPrecio`(IN costoPesos DECIMAL(12,2),
													id_producto INT,
                                                    id_stockDetalle INT)
BEGIN

UPDATE producto SET
	producto.costo = costoPesos
WHERE producto.id_producto = id_producto;

UPDATE stockDetalle SET
    stockDetalle.costoPesos = costoPesos
WHERE stockDetalle.id_stockDetalle = id_stockDetalle;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `chequeaStock` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `chequeaStock`(IN id_stock INT)
BEGIN

UPDATE stock SET
    stock.chequeado = 'S'
WHERE stock.id_stock = id_stock;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `columnasForecast` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `columnasForecast`(IN id_zona INT,
										id_empresa INT)
BEGIN

SELECT entidad.nombre, entidad.id_entidad

FROM entidad

WHERE entidad.id_entidad IN (SELECT forecastEntidad.id_entidad
								FROM forecastEntidad) AND
		entidad.id_zona = id_zona AND
        entidad.id_empresa = id_empresa;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `confirmaTurno` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `confirmaTurno`(IN id_presupuesto INT,
                                                        id_tecnico INT,
                                                        id_prestacion INT,
                                                        importePrestacion DECIMAL(9,2),
                                                        observaciones TEXT,
                                                        id_entidad INT,
                                                        id_lugarCirugia INT,
                                                        id_profesional1 INT,
                                                        fechaCirugia DATE,
                                                        paciente VARCHAR(45),
                                                        usuario VARCHAR(45),
                                                        equipo VARCHAR(255))
BEGIN

DECLARE log TEXT;

UPDATE presupuesto SET
    presupuesto.id_tecnico = id_tecnico,
    presupuesto.id_prestacion = id_prestacion,
    presupuesto.importePrestacion = importePrestacion,
    presupuesto.observaciones = observaciones,
    presupuesto.id_entidad = id_entidad,
    presupuesto.id_lugarCirugia = id_lugarCirugia,
    presupuesto.id_profesional1 = id_profesional1,
    presupuesto.fechaCirugia = fechaCirugia,
    presupuesto.paciente = paciente,
    presupuesto.estado = 'C',
    presupuesto.usuario = usuario,
    presupuesto.fechaReal = CURRENT_TIMESTAMP
    
WHERE presupuesto.id_presupuesto = id_presupuesto;

SET log = CONCAT("Confirma turno ", id_presupuesto);

CALL logSistema(usuario, equipo, log);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaAltaStock` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaAltaStock`(IN desde DATE,
                                                                    hasta DATE,
                                                                    id_empresa INT)
BEGIN

SELECT stock.id_stock, stock.fecha, tipocomp.abreviatura as tipoComp,
        stock.numComp, proveedor.nombre as proveedor, zona.nombre as zona

FROM stock
    LEFT JOIN tipocomp ON tipocomp.id_tipoComp = stock.id_tipoComp
    LEFT JOIN proveedor ON proveedor.id_proveedor = stock.id_proveedor
    LEFT JOIN zona ON zona.id_zona = stock.id_zona
    
WHERE IF(hasta IS NULL, IF(desde IS NULL, TRUE, stock.fecha >= desde),
                        IF(desde IS NULL, stock.fecha <= hasta, stock.fecha BETWEEN desde AND hasta)
            )
         AND stock.id_empresa = id_empresa
         
ORDER BY stock.fecha DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaAnalisisCobranza` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaAnalisisCobranza`(IN desde DATE,
                                                        hasta DATE,
                                                        id_zona INT,
                                                        id_empresa INT)
BEGIN

SELECT zona.nombre as zona, vendedor.nombre as vendedor, factura.id_presupuesto, factura.fecha, factura.numComp, factura.neto,
    calculaIIBB(ROUND(factura.neto / 1.21, 2), presupuesto.id_zona) as iibb, calculaGanancia(factura.neto) as ganancia,
    IFNULL(traeRmCobranza(factura.id_presupuesto), 0.00) as rm, tipoOperacion.nombre as tipoOperacion
    
FROM factura

LEFT JOIN vendedor ON vendedor.id_vendedor = factura.id_vendedor
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = factura.id_presupuesto
LEFT JOIN zona ON zona.id_zona = presupuesto.id_zona
LEFT JOIN tipoOperacion ON tipoOperacion.id_tipoOperacion = presupuesto.id_tipoOperacion

WHERE factura.id_tipoComp IN (4, 16, 17) AND
    IF(desde IS NULL, true, factura.fecha >= desde) AND
    IF(hasta IS NULL, true, factura.fecha <= hasta) AND
    factura.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                                        FROM presupuesto
                                                        WHERE IF(id_zona = 0, true, presupuesto.id_zona = id_zona)) AND
    factura.id_empresa = id_empresa;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaAnalisisFacturacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaAnalisisFacturacion`(IN desde DATE,
                                                                    hasta DATE,
                                                                    id_zona INT,
                                                                    id_especialidad INT,
                                                                    id_empresa INT)
BEGIN

SELECT presupuesto.id_presupuesto as id_presupuesto, IFNULL(tipoOperacion.nombre, '') as tipoOperacion,
    sum(factura.aliPercIIBB) as aliPercIIBB, 
    sum(if(factura.id_tipoComp IN (3, 7, 10, 13, 25, 21), -factura.percIIBB, factura.percIIBB)) as percIIBB, 
    sum(if(factura.aliIva = 21, if(factura.id_tipoComp IN (3, 7, 10, 13, 25, 21), -factura.iva, factura.iva), 0.00)) as iva21,
    sum(if(factura.aliIva = 10.5, if(factura.id_tipoComp IN (3, 7, 10, 13, 25, 21), -factura.iva, factura.iva), 0.00)) as iva105,
    sum(if(factura.aliIva = 21, if(factura.id_tipoComp IN (3, 7, 10, 13, 25, 21), -factura.neto, factura.neto), 0.00)) as neto21,
    sum(if(factura.aliIva = 10.5, if(factura.id_tipoComp IN (3, 7, 10, 13, 25, 21), -factura.neto, factura.neto), 0.00)) as neto105,
    sum(if(factura.aliIva = 0 AND factura.id_tipoComp IN (23, 24, 25),if(factura.id_tipoComp IN (3, 7, 10, 13, 25, 21), -factura.neto, factura.neto), 0.00)) as netoX,
    sum(if(factura.aliIva = 0 AND factura.id_tipoComp NOT IN (23, 24, 25),if(factura.id_tipoComp IN (3, 7, 10, 13, 25, 21), -factura.neto, factura.neto), 0.00)) as netoExento,
    sum(IF(factura.id_tipoComp IN (1, 2, 3, 5, 6, 7), IFNULL(calculaIIBB(factura.neto, presupuesto.id_zona), 0.00), 0)) as iibb,
    IF(facturaAntes(presupuesto.id_presupuesto, MONTH(desde)), 0.00, IFNULL(traeRM(presupuesto.id_presupuesto, desde, hasta), -(presupuesto.rm1 + presupuesto.rm2 + presupuesto.rm3))) as rm, 
    zona.nombre as zona, presupuesto.id_tipoOperacion as id_tipoOperacion
    
FROM factura

LEFT JOIN presupuesto ON presupuesto.id_presupuesto = factura.id_presupuesto
LEFT JOIN tipoOperacion ON tipoOperacion.id_tipoOperacion = presupuesto.id_tipoOperacion
LEFT JOIN zona ON zona.id_zona = presupuesto.id_zona

WHERE factura.id_tipoComp NOT IN (4, 16, 17, 18, 19) AND
    IF(desde IS NULL, true, factura.fecha >= desde) AND
    IF(hasta IS NULL, true, factura.fecha <= hasta) AND
    factura.id_empresa = id_empresa AND
    factura.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                FROM presupuesto
                                WHERE IF(id_zona = 0, true, presupuesto.id_zona = id_zona))
                                
GROUP BY factura.id_presupuesto
                                
ORDER BY zona.nombre, presupuesto.id_presupuesto;                                        

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaAnalisisFacturacionAdm` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaAnalisisFacturacionAdm`(IN desde DATE,
                                                                    hasta DATE,
                                                                    id_zona INT,
                                                                    id_especialidad INT,
                                                                    id_empresa INT)
BEGIN

SELECT presupuesto.id_presupuesto as id_presupuesto, factura.aliPercIIBB, 
    if(factura.id_tipoComp IN (3, 7, 10, 13, 25, 21), -factura.percIIBB, factura.percIIBB) as percIIBB, 
    factura.aliIva, 
    if(factura.id_tipoComp IN (3, 7, 10, 13, 25, 21), -factura.iva, factura.iva) as iva, 
    if(factura.id_tipoComp IN (3, 7, 10, 13, 25, 21), -factura.neto, factura.neto) as neto, 
    IFNULL(tipoComp.abreviatura, '') as tipoComp, factura.numComp, factura.id_tipoComp,
    zona.nombre as zona
    
FROM factura

LEFT JOIN presupuesto ON presupuesto.id_presupuesto = factura.id_presupuesto
LEFT JOIN zona ON zona.id_zona = presupuesto.id_zona
LEFT JOIN tipoComp ON tipoComp.id_tipoComp = factura.id_tipoComp

WHERE factura.id_tipoComp NOT IN (4, 16, 17, 18, 19) AND
    IF(desde IS NULL, true, factura.fecha >= desde) AND
    IF(hasta IS NULL, true, factura.fecha <= hasta) AND
    factura.id_empresa = id_empresa AND
    factura.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                FROM presupuesto
                                WHERE IF(id_zona = 0, true, presupuesto.id_zona = id_zona))
                                
ORDER BY zona.nombre, presupuesto.id_presupuesto;                                         

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaAnticipoAplicacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaAnticipoAplicacion`(IN id_profesional INT,
                                                                    id_empresa INT)
BEGIN

SELECT DATE_FORMAT(mayorprofesional.fecha, '%d/%m/%Y') as fecha, 
	SUM(IF(mayorprofesional.dc = "D", mayorprofesional.importe, -mayorprofesional.importe)) as importe,
    mayorprofesional.id_mayorprofesional

FROM mayorprofesional

WHERE mayorprofesional.id_profesional = id_profesional AND
    mayorprofesional.id_empresa = id_empresa AND
    mayorprofesional.id_presupuesto = 0 AND
    mayorprofesional.liquidacion <> 0
    
HAVING importe <> 0;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaAsignadas` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaAsignadas`(IN id_presupuesto INT)
BEGIN

SELECT cajadeposito.codigo as codigo, caja.nombre as nombre, zona.nombre as zona, 
	caja.id_caja as id_caja, cajadeposito.id_cajaDeposito

FROM cajadeposito

LEFT JOIN caja ON caja.id_caja = cajadeposito.id_caja
LEFT JOIN zona ON cajadeposito.id_zona = zona.id_zona

WHERE cajadeposito.id_cajaDeposito IN (SELECT cajaasignada.id_cajaDeposito
                                        FROM cajaasignada
                                        WHERE cajaasignada.id_presupuesto = id_presupuesto);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaCaja` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaCaja`(IN soloHabilitados BOOL)
BEGIN

SELECT caja.id_caja, caja.nombre, caja.habilita

FROM caja

WHERE caja.habilita <> IF(soloHabilitados, 'N', 'X')

ORDER BY caja.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaCajaComposicion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaCajaComposicion`(IN id_cajaDeposito INT)
BEGIN

SELECT cajacomposicion.cantidad, cajacomposicion.id_producto, 
    producto.codigo as codigo, producto.gtin as gtin, producto.nombre as nombre, producto.pm as pm,
    producto.costo as costo, producto.id_moneda as id_moneda, IFNULL(ultimaCotizacion(producto.id_moneda), 1) as cotizacion,
    IFNULL(clasiproducto.nombre, '') as tipo

FROM cajacomposicion

LEFT JOIN producto ON producto.id_producto = cajacomposicion.id_producto
LEFT JOIN clasiproducto ON clasiproducto.id_clasiproducto = producto.id_clasiproducto

WHERE cajacomposicion.id_cajaDeposito = id_cajaDeposito AND
		cajaComposicion.id_producto IN (SELECT producto.id_producto from producto where producto.habilita = 'S')

ORDER BY producto.codigo;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaCajaDeposito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaCajaDeposito`(IN id_empresa INT,
                                                        soloHabilitados BOOL)
BEGIN

SELECT cajadeposito.id_cajaDeposito, cajadeposito.id_zona, cajadeposito.id_caja, cajadeposito.codigo, 
    cajadeposito.habilita, zona.nombre AS zona, caja.nombre AS caja
    
FROM cajadeposito

LEFT JOIN zona ON zona.id_zona = cajadeposito.id_zona
LEFT JOIN caja ON caja.id_caja = cajadeposito.id_caja

WHERE cajadeposito.id_empresa = id_empresa AND
    cajadeposito.habilita <> IF(soloHabilitados, 'N', 'X')
    
ORDER BY zona.nombre, cajadeposito.codigo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaCajaPrestada` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaCajaPrestada`(IN id_zonaDestino INT,
											id_empresa INT)
BEGIN

SELECT cajadeposito.codigo, caja.nombre as caja, zona.nombre as zona, cajadeposito.id_cajadeposito, cajadeposito.id_caja,
	intersucursalcaja.id_intersucursalcaja

FROM intersucursalcaja

LEFT JOIN cajadeposito ON cajadeposito.id_cajadeposito = intersucursalcaja.id_cajadeposito
LEFT JOIN caja ON caja.id_caja = cajadeposito.id_caja
LEFT JOIN zona ON zona.id_zona = cajadeposito.id_zona

WHERE intersucursalcaja.id_intersucursal IN (SELECT intersucursal.id_intersucursal
												FROM intersucursal
                                                WHERE intersucursal.recibido = 'S' AND
														intersucursal.id_remito IN (SELECT remito.id_remito
																					FROM remito
                                                                                    WHERE remito.id_zonaDestino = id_zonaDestino)) AND
                                                                                    
	intersucursalcaja.devuelto = 'N'
        
ORDER BY cajadeposito.codigo;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaCambioPrecios` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaCambioPrecios`(IN id_clasiProducto INT,
                                                                id_proveedor INT,
                                                                    id_empresa INT)
BEGIN

SELECT producto.id_producto, producto.codigo, producto.gtin, producto.nombre, 
    moneda.simbolo as moneda, producto.costo
        
FROM producto
LEFT JOIN moneda ON moneda.id_moneda = producto.id_moneda

WHERE producto.id_empresa = id_empresa AND
    producto.habilita = 'S' AND
    IF(id_clasiProducto = 0, true, producto.id_clasiProducto = id_clasiProducto) AND
    IF(id_proveedor = 0, true, producto.id_proveedor = id_proveedor)
    
ORDER BY producto.codigo;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaCertificadosSinControl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaCertificadosSinControl`()
BEGIN

SELECT COUNT(*) as cantidad

FROM nivelesCertificado

WHERE nivelescertificado.controlado = 'N';

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaClasiEntidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaClasiEntidad`(IN soloHabilitados BOOL)
BEGIN

SELECT clasientidad.id_clasiEntidad, clasientidad.nombre, clasientidad.habilita

FROM clasientidad

WHERE clasientidad.habilita <> IF(soloHabilitados, 'N', 'X')

ORDER BY clasientidad.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaClasiProducto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaClasiProducto`(IN soloHabilitados BOOL)
BEGIN

SELECT clasiproducto.id_clasiProducto, clasiproducto.nombre, clasiproducto.habilita

FROM clasiproducto

WHERE clasiproducto.habilita <> IF(soloHabilitados, 'N', 'X')

ORDER BY clasiproducto.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaCobrador` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaCobrador`(IN id_empresa INT,
                                                        soloHabilitados BOOL)
BEGIN

SELECT vendedor.id_vendedor, vendedor.nombre, vendedor.id_zona, vendedor.id_especialidad, vendedor.comision, 
    vendedor.beneficio, vendedor.habilita, zona.nombre AS zona, especialidad.nombre AS especialidad
    
FROM vendedor

LEFT JOIN zona ON zona.id_zona = vendedor.id_zona
LEFT JOIN especialidad ON especialidad.id_especialidad = vendedor.id_especialidad

WHERE vendedor.id_empresa = id_empresa AND
    vendedor.id_especialidad = 10 AND
    vendedor.habilita <> IF(soloHabilitados, 'N', 'X')
    
ORDER BY vendedor.nombre;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaCodConsumo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaCodConsumo`(IN soloHabilitados BOOL)
BEGIN

SELECT codconsumo.id_codConsumo, codconsumo.codigo, codconsumo.nombre, codconsumo.habilita

FROM codconsumo

WHERE codconsumo.habilita <> IF(soloHabilitados, 'N', 'X')

ORDER BY codconsumo.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaComision` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaComision`(IN desde DATE,
                                                            hasta DATE,
                                                            id_vendedor INT,
                                                            id_especialidad INT,
                                                            id_zona INT,
                                                            beneficio BOOLEAN,
                                                            id_tipoOperacion INT)
BEGIN

SELECT factura.id_presupuesto, ifnull(tipoOperacion.nombre, '') as tipoOperacion,
    ROUND((SUM(IF(factura.dc = 'D' or id_especialidad = 10, (factura.neto + factura.percIIBB + factura.iva), -(factura.neto + factura.percIIBB + factura.iva)))) / 1.21, 2) as neto,
    IF(facturaAntes(presupuesto.id_presupuesto, MONTH(desde)), 0.00, IFNULL(traeRM(presupuesto.id_presupuesto, desde, hasta), -(presupuesto.rm1 + presupuesto.rm2 + presupuesto.rm3))) as rm, 
    presupuesto.id_tipoOperacion, factura.comparte

FROM factura

LEFT JOIN presupuesto ON presupuesto.id_presupuesto = factura.id_presupuesto
LEFT JOIN tipoOperacion ON tipoOperacion.id_tipoOperacion = presupuesto.id_tipoOperacion

WHERE IF(id_especialidad = 10, factura.id_tipoComp IN (4, 16, 17) AND IF(beneficio, true, (factura.id_vendedor = id_vendedor OR factura.id_vendedorComparte = id_vendedor)), 
								factura.id_tipoComp NOT IN (4, 16, 17, 18, 19) AND 
										IF(beneficio, true, factura.id_presupuesto IN (SELECT presupuesto.id_presupuesto
																								FROM presupuesto
																								WHERE presupuesto.id_vendedor = id_vendedor))) AND
        IF(id_zona = 0, true, factura.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                                                FROM presupuesto
                                                                WHERE presupuesto.id_zona = id_zona)) AND

        IF(desde IS NULL, true, factura.fecha >= desde) AND
        IF(hasta IS NULL, true, factura.fecha <= hasta) AND
        IF(id_tipoOperacion = 0, true, factura.id_presupuesto IN (SELECT presupuesto.id_presupuesto
																	FROM presupuesto
                                                                    WHERE presupuesto.id_tipoOperacion = id_tipoOperacion))

GROUP BY factura.id_presupuesto
ORDER BY factura.id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaComparativas` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaComparativas`(IN desde DATE,
                                                            hasta DATE,
                                                            id_entidad INT,
                                                            id_productoFact INT,
                                                            id_profesional INT,
                                                            id_zona INT,
                                                            id_vendedor INT,
                                                            aprobado VARCHAR(1),
                                                            facturado BOOL,
                                                            fechaCx BOOL,
                                                            id_empresa INT)
BEGIN

SELECT presupuesto.id_presupuesto as turno, presupuesto.fecha,
    productoFact.codigo as codigo, GROUP_CONCAT(productoFact.nombre) as producto, profesional.nombre as profesional,
    entidad.nombre as entidad,
    presupuesto.paciente as paciente, montoPresupuesto(presupuesto.id_presupuesto) as montoPresupuesto,
    IF(presupuesto.estado IN ('R', 'N', 'P'), 'NO', 'SI') as aprobado, 
    IFNULL(montoFacturado(presupuesto.id_presupuesto), 0.00) as montoFacturado,
    IFNULL(presupuesto.competencia, '') as competencia, 
    IFNULL(presupuesto.lobby, '') as lobby, 
    IFNULL(presupuesto.observaciones, '') as observaciones
    
FROM presupuestoprod

LEFT JOIN presupuesto ON presupuesto.id_presupuesto = presupuestoprod.id_presupuesto
LEFT JOIN productoFact ON productoFact.id_productoFact = presupuestoprod.id_productoFact
LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad

WHERE IF(id_productoFact = 0, true, presupuestoprod.id_productoFact = id_productoFact) AND
    presupuestoprod.id_presupuesto IN (

    SELECT presupuesto.id_presupuesto
    
    FROM presupuesto
    
    WHERE IF(desde IS NULL, true, presupuesto.fecha >= desde) AND
            IF(hasta IS NULL, true, presupuesto.fecha <= hasta) AND
            IF(id_entidad = 0, true, presupuesto.id_entidad = id_entidad) AND
            IF(id_profesional = 0, true, presupuesto.id_profesional1 = id_profesional) AND
            IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
            IF(id_vendedor = 0, true, presupuesto.id_vendedor = id_vendedor) AND
            IF(aprobado = 'S', presupuesto.estado NOT IN ('R', 'N', 'P'),
                IF(aprobado = 'N', presupuesto.estado = 'P', true)) AND
            IF(aprobado = 'T', true, IF(facturado, presupuesto.id_presupuesto IN (SELECT factura.id_presupuesto
                                                            FROM factura
                                                            WHERE factura.id_empresa = id_empresa),
                          presupuesto.id_presupuesto NOT IN (SELECT factura.id_presupuesto
                                                            FROM factura
                                                            WHERE factura.id_empresa = id_empresa)) AND                                  
            IF(fechaCx, presupuesto.fechaCirugia IS NOT NULL, presupuesto.fechaCirugia IS NULL)) AND
            presupuesto.id_empresa = id_empresa
)
GROUP BY presupuesto.id_presupuesto 
ORDER BY entidad.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaComprobantesAsociados` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaComprobantesAsociados`(IN id_presupuesto INT,
                                                                id_empresa INT)
BEGIN

SELECT DATE_FORMAT(factura.fecha, '%d/%m/%Y') as fecha, tipoComp.abreviatura as tipoComp, 
    factura.numComp, IF(factura.dc = 'D', factura.neto + percIIBB + iva, 0) as debe,
    IF(factura.dc = 'C', factura.neto + percIIBB + iva, 0) as haber
    
FROM factura

LEFT JOIN tipoComp ON tipoComp.id_tipoComp = factura.id_tipoComp

WHERE factura.id_presupuesto = id_presupuesto AND
        factura.id_empresa = id_empresa
        
ORDER BY factura.fecha;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaConfirmados` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaConfirmados`(IN desde DATE,
                                                            hasta DATE,
                                                            id_zona INT,
                                                            id_empresa INT)
BEGIN

SELECT presupuesto.id_presupuesto, presupuesto.paciente, presupuesto.dni,
    presupuesto.telefono, presupuesto.observaciones, presupuesto.fechaCirugia,
    ifnull(zona.nombre, '') as zona, ifnull(entidad.nombre, '') as entidad, ifnull(lugar.nombre, '') as lugarCirugia, 
    ifnull(profesional.nombre, '') as profesional,
    IFNULL(tecnico.nombre,'') as tecnico, ifnull(prestacion.nombre, '') as prestacion, presupuesto.id_zona
    
FROM presupuesto

LEFT JOIN zona ON zona.id_zona = presupuesto.id_zona
LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad
LEFT JOIN entidad lugar ON lugar.id_entidad = presupuesto.id_lugarCirugia
LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
LEFT JOIN tecnico ON tecnico.id_tecnico = presupuesto.id_tecnico
LEFT JOIN prestacion ON prestacion.id_prestacion = presupuesto.id_prestacion

WHERE presupuesto.estado = 'C' AND
		presupuesto.id_presupuesto NOT IN (SELECT id_presupuesto from remito where remito.recibido = 'N') AND
        IF(desde IS NULL, true, IF(presupuesto.fechaCirugia IS NULL,  true, presupuesto.fechaCirugia >= desde)) AND
        IF(hasta IS NULL, true, IF(presupuesto.fechaCirugia IS NULL,  true, presupuesto.fechaCirugia <= hasta)) AND
        IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
        presupuesto.id_empresa = id_empresa;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaConfirmaTurno` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaConfirmaTurno`(IN desde DATE,
                                                                hasta DATE,
                                                                id_zona INT,
                                                                id_empresa INT,
                                                                tipoBusqueda INT)
BEGIN

SELECT presupuesto.id_presupuesto, presupuesto.paciente, presupuesto.telefono, presupuesto.observaciones,
    fechaAgenda, IFNULL(entidad.nombre, '') as entidad, 
    IFNULL(lugar.nombre, '') as lugar, IFNULL(profesional.nombre, '') as profesional, presupuesto.id_zona
    
FROM presupuesto

LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad
LEFT JOIN entidad lugar ON lugar.id_entidad = presupuesto.id_lugarCirugia
LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1

WHERE presupuesto.id_presupuesto NOT IN (SELECT presupuesto.id_presupuesto
											FROM presupuesto
                                            WHERE presupuesto.fechaAprobacion = CURRENT_DATE AND
													presupuesto.fechaAgenda IS NULL AND
													IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
													(presupuesto.estado = 'A' OR presupuesto.estado = 'S') AND
													presupuesto.id_empresa = id_empresa) AND

IF(desde IS NULL, true, IF(tipoBusqueda = 1, presupuesto.fecha >= desde, presupuesto.fechaAgenda >= desde)) AND
    IF(hasta IS NULL, true, IF(tipoBusqueda = 1, presupuesto.fecha <= hasta, presupuesto.fechaAgenda <= hasta)) AND
    IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
    (presupuesto.estado = 'A' OR presupuesto.estado = 'S') AND
    presupuesto.id_empresa = id_empresa
    
ORDER BY id_presupuesto DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaConfirmaTurnoHoy` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaConfirmaTurnoHoy`(IN id_zona INT,
                                                id_empresa INT)
BEGIN

SELECT presupuesto.id_presupuesto, presupuesto.paciente, presupuesto.telefono, presupuesto.observaciones,
    fechaAgenda, IFNULL(entidad.nombre, '') as entidad, 
    IFNULL(lugar.nombre, '') as lugar, IFNULL(profesional.nombre, '') as profesional, presupuesto.id_zona
    
FROM presupuesto

LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad
LEFT JOIN entidad lugar ON lugar.id_entidad = presupuesto.id_lugarCirugia
LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1

WHERE presupuesto.fechaAprobacion = CURRENT_DATE AND
	presupuesto.fechaAgenda IS NULL AND
    IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
    (presupuesto.estado = 'A' OR presupuesto.estado = 'S') AND
    presupuesto.id_empresa = id_empresa
    
ORDER BY presupuesto.id_presupuesto DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaConsolidadoCobranza` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaConsolidadoCobranza`(IN desde DATE,
                                                            hasta DATE,
                                                            id_zona INT,
                                                            id_entidad INT,
                                                            operaciones VARCHAR(4),
                                                            entidad BOOL,
                                                            id_empresa INT)
BEGIN

SELECT entidad.nombre as entidad, MONTH(factura.fecha) as mes, YEAR(factura.fecha) as year, factura.neto as importeAplicado
    
FROM factura

LEFT JOIN presupuesto ON presupuesto.id_presupuesto = factura.id_presupuesto
LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad

WHERE factura.id_tipoComp IN (4, 16, 17) AND
    IF(desde IS NULL, true, factura.fecha >= desde) AND
    IF(hasta IS NULL, true, factura.fecha <= hasta) AND
    factura.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                                        FROM presupuesto
                                                        WHERE IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
                                                                IF(id_entidad = 0, true, presupuesto.id_entidad = id_entidad)) AND
    IF(operaciones = "XXXX", true, IF(operaciones = "9999", LEFT(factura.numComp, 4) = operaciones, LEFT(factura.numComp, 4) <> "9999")) AND
    factura.id_empresa = id_empresa
    
ORDER BY IF(entidad, entidad.nombre, null), year, mes;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaConsumido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaConsumido`(IN id_presupuesto INT,
                                                    id_empresa INT)
BEGIN

SELECT producto.codigo as producto, producto.gtin as gtin, producto.nombre as nombre, stockDetalle.pm, stockDetalle.lote, stockDetalle.serie, 
    DATE_FORMAT(stockDetalle.vencimiento, '%d/%m/%Y') as vencimiento,
    SUM(IF(stockDetalle.dc = 'C', stockDetalle.cantidad, -stockDetalle.cantidad)) as consumido, remito.numComp
    
FROM stockDetalle

LEFT JOIN producto ON producto.id_producto = stockDetalle.id_producto
LEFT JOIN remito ON remito.id_remito = stockDetalle.id_remito

WHERE stockDetalle.id_remito IN (SELECT remito.id_remito
                                    FROM remito
                                    WHERE remito.id_presupuesto = id_presupuesto AND
                                            remito.id_empresa = id_empresa)
                                    
GROUP BY stockdetalle.id_remito, stockDetalle.id_producto

HAVING consumido > 0;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaControlPrecios` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaControlPrecios`(IN desde DATE,
                                                                    hasta DATE,
                                                                    id_empresa INT)
BEGIN

SELECT stock.id_stock, stock.fecha, tipocomp.abreviatura as tipoComp,
        stock.numComp, proveedor.nombre as proveedor, zona.nombre as zona, stock.observaciones

FROM stock
    LEFT JOIN tipocomp ON tipocomp.id_tipoComp = stock.id_tipoComp
    LEFT JOIN proveedor ON proveedor.id_proveedor = stock.id_proveedor
    LEFT JOIN zona ON zona.id_zona = stock.id_zona
    
WHERE IF(desde IS NULL, TRUE, stock.fecha >= desde) AND
      IF(hasta IS NULL, TRUE, stock.fecha <= hasta) AND 
      stock.id_empresa = id_empresa AND
      stock.chequeado = 'N'
         
ORDER BY stock.fecha DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaCostoVentas` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaCostoVentas`(IN tipo INT,
                                                            desde DATE,
                                                            hasta DATE,
                                                            id_zona INT,
                                                            id_proveedor INT,
                                                            id_empresa INT)
BEGIN

SELECT remito.id_presupuesto as id_presupuesto, 
    SUM(IF(stockDetalle.dc = 'C',stockDetalle.cantidad,-stockDetalle.cantidad)) as cantidad,
    producto.codigo as producto, producto.gtin as gtin, ROUND(stockdetalle.costoPesos * stockDetalle.cotizacion, 2) as costoPesos, montoPresupuesto(remito.id_presupuesto) as montoPresupuesto,
    ifnull(tipoOperacion.nombre,'') as tipoOperacion, (presupuesto.rm1 + presupuesto.rm2 + presupuesto.rm3) as rmTotal,
    montoNetoFacturado(remito.id_presupuesto) as saldoFactura, ifnull(vendedor.nombre, '') as vendedor, 
    producto.nombre as descProd, presupuesto.fechaCirugia
    
FROM stockDetalle

LEFT JOIN remito ON remito.id_remito = stockDetalle.id_remito
LEFT JOIN producto ON producto.id_producto = stockDetalle.id_producto
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = remito.id_presupuesto
LEFT JOIN vendedor ON vendedor.id_vendedor = presupuesto.id_vendedor
LEFT JOIN tipoOperacion ON tipoOperacion.id_tipoOperacion = presupuesto.id_tipoOperacion

WHERE IF(tipo = 2, stockDetalle.id_remito IN (SELECT remito.id_remito
															  FROM remito
															  WHERE IF(desde IS NULL, true, remito.fecha >= desde) AND
																	IF(hasta IS NULL, true, remito.fecha <= hasta) AND
																	remito.id_presupuesto NOT IN (SELECT factura.id_presupuesto
																									FROM factura
																									WHERE factura.id_empresa = id_empresa AND
																										  factura.id_tipoComp NOT IN (1,5,8,11,14,15,20,23))),
								 IF(tipo = 3 OR tipo = 3, stockDetalle.id_remito IN (SELECT remito.id_remito
																			 FROM remito
																			 WHERE remito.id_presupuesto IN (SELECT factura.id_presupuesto
																											   FROM factura
																											   WHERE factura.id_empresa = id_empresa AND
																													 factura.id_tipoComp IN (1,5,8,11,14,15,20,23) AND
																													  IF(desde IS NULL, true, factura.fecha >= desde) AND
																													  IF(hasta IS NULL, true, factura.fecha <= hasta))),
											   IF(tipo = 4, stockDetalle.id_remito IN (SELECT remito.id_remito
																						 FROM remito
																						 WHERE remito.id_presupuesto IN (Select presupuesto.id_presupuesto
																															FROM presupuesto
																															Where presupuesto.estado = 'Z' AND
																																	IF(desde IS NULL, true, presupuesto.fechaCirugia >= desde) AND
																																	IF(hasta IS NULL, true, presupuesto.fechaCirugia <= hasta)) AND
																								remito.recibido = 'S'), 
                                                                             
															stockDetalle.id_remito IN (SELECT remito.id_remito
																								 FROM remito
																								 WHERE IF(desde IS NULL, true, DATE(remito.fechaReal) >= desde) AND
																										IF(hasta IS NULL, true, DATE(remito.fechaReal) <= hasta))))) AND
    
    IF(id_zona = 0, true, stockDetalle.id_remito IN (SELECT remito.id_remito
                                                       FROM remito
                                                       WHERE remito.id_zona = id_zona AND
                                                             remito.id_empresa = id_empresa)) AND
    IF(id_proveedor = 0, true, stockDetalle.id_producto IN (SELECT producto.id_producto
                                                            FROM producto
                                                            WHERE producto.id_empresa = id_empresa AND
                                                                    producto.id_proveedor = id_proveedor)) AND
    stockDetalle.id_remito > 0 AND
    stockDetalle.id_remito IN (SELECT remito.id_remito
                                FROM remito
                                WHERE remito.id_empresa = id_empresa AND
										remito.recibido = 'S')
                                
GROUP BY remito.id_presupuesto, stockDetalle.id_producto

HAVING cantidad > 0;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaCotizacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaCotizacion`(IN id_empresa INT)
BEGIN

SELECT cotizacion.id_moneda, cotizacion.mes, cotizacion.año, cotizacion.valor, moneda.nombre as moneda

FROM cotizacion

LEFT JOIN moneda ON moneda.id_moneda = cotizacion.id_moneda

WHERE cotizacion.id_empresa = id_empresa

ORDER BY cotizacion.año, cotizacion.mes DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaCumple` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaCumple`(IN id_empresa INT, 
                                                    mes INT(2), 
                                                    id_zona INT,
                                                    id_vendedor INT)
BEGIN

SELECT DATE_FORMAT(fechaNac,'%e') AS dia, profesional.nombre, profesional.email, 
    profesional.telParticular, profesional.telMovil,
    IFNULL(zona.nombre,'') as zona, IFNULL(vendedor.nombre,'') as vendedor
    
    FROM profesional
    
    LEFT JOIN zona ON zona.id_zona = profesional.id_zona
    LEFT JOIN vendedor ON vendedor.id_vendedor = profesional.id_vendedor
    
    WHERE 
        profesional.habilita = 'S' AND 
        profesional.id_empresa = id_empresa AND
        DATE_FORMAT(profesional.fechaNac,'%c') = mes AND
        IF(id_zona > 0, profesional.id_zona = id_zona, true) AND
        if(id_vendedor > 0, profesional.id_vendedor = id_vendedor, true)
        
ORDER BY EXTRACT(DAY FROM fechaNac);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaDepartamento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaDepartamento`(IN id_provincia INT)
BEGIN

SELECT departamento.id_departamento, departamento.nombre

FROM departamento

WHERE IF(id_provincia > 0, departamento.id_provincia = id_provincia, true)

ORDER BY departamento.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaDetalladoCobranza` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaDetalladoCobranza`(IN desde DATE,
                                                            hasta DATE,
                                                            id_zona INT,
                                                            id_entidad INT,
                                                            operaciones VARCHAR(4),
                                                            entidad BOOL,
                                                            id_empresa INT)
BEGIN

SELECT entidad.nombre as entidad, factura.fecha as fechaRecibo, factura.numComp as numRecibo, factura.id_presupuesto,
    fechaComprobante(factura.id_tipoCompRel, factura.numCompRel, factura.id_empresa) as fechaFactura, factura.numCompRel as numFactura,
    factura.neto as importeAplicado, presupuesto.rm1 as rm1, presupuesto.rm2 as rm2, tipoOperacion.nombre as tipoOperacion
    
FROM factura

LEFT JOIN presupuesto ON presupuesto.id_presupuesto = factura.id_presupuesto
LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad
LEFT JOIN tipoOperacion ON tipoOperacion.id_tipoOperacion = presupuesto.id_tipoOperacion

WHERE factura.id_tipoComp IN (4, 16, 17) AND
    IF(desde IS NULL, true, factura.fecha >= desde) AND
    IF(hasta IS NULL, true, factura.fecha <= hasta) AND
    factura.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                                        FROM presupuesto
                                                        WHERE IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
                                                                IF(id_entidad = 0, true, presupuesto.id_entidad = id_entidad)) AND
    IF(operaciones = "XXXX", true, IF(operaciones = "9999", LEFT(factura.numComp, 4) = operaciones, LEFT(factura.numComp, 4) <> "9999")) AND
    factura.id_empresa = id_empresa
    
ORDER BY IF(entidad, entidad.nombre, null), factura.fecha, factura.id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaDetalleConsumido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaDetalleConsumido`(IN id_presupuesto INT)
BEGIN

SELECT stockdetalle.id_stockdetalle, producto.codigo as codigo, producto.gtin as gtin, producto.nombre as nombre, stockDetalle.pm, 
    stockDetalle.lote, stockDetalle.serie, IFNULL(DATE_FORMAT(stockDetalle.vencimiento, '%d/%m/%Y'), '') as vencimiento,
    SUM(IF(stockdetalle.dc = 'C', stockDetalle.cantidad, -stockDetalle.cantidad)) as consumo_actual,
    stockDetalle.id_producto, stockDetalle.costoPesos, stockDetalle.id_moneda, stockDetalle.id_zona,
    remito.numComp as remito, stockdetalle.id_remito, presupuesto.observaciones as observaciones

FROM stockDetalle

LEFT JOIN producto ON producto.id_producto = stockDetalle.id_producto
LEFT JOIN remito ON remito.id_remito = stockdetalle.id_remito
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = remito.id_presupuesto
                                    
WHERE stockDetalle.id_remito IN (SELECT remito.id_remito
									FROM remito
                                    WHERE remito.id_presupuesto = id_presupuesto)
                                    
GROUP BY stockdetalle.id_remito, stockDetalle.id_producto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaDiaria` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaDiaria`(IN id_empresa INT,
                                                        soloHabilitados BOOL)
BEGIN

SELECT diaria.id_diaria, diaria.nombre, diaria.direccion, diaria.id_provincia, diaria.id_departamento,
        diaria.id_localidad, diaria.codPostal, diaria.id_opcion, diaria.cuit, diaria.telefono1,
        diaria.telefono2, diaria.telefono3, diaria.telefono4, diaria.email, diaria.secretaria,
        diaria.contacto, diaria.telContacto, diaria.movContacto, diaria.emailContacto, diaria.id_zona,
        diaria.id_formaPago, diaria.observaciones, diaria.habilita, provincia.nombre as provincia,
        departamento.nombre as departamento, localidad.nombre as localidad, opcion.nombre as posicionIva,
        zona.nombre as zona, formapago.nombre as formaPago
        
FROM diaria

LEFT JOIN provincia ON provincia.id_provincia = diaria.id_provincia
LEFT JOIN departamento ON departamento.id_departamento = diaria.id_departamento
LEFT JOIN localidad ON localidad.id_localidad = diaria.id_localidad
LEFT JOIN opcion ON opcion.id_opcion = diaria.id_opcion
LEFT JOIN zona ON zona.id_zona = diaria.id_zona
LEFT JOIN formapago ON formapago.id_formapago = diaria.id_formapago

WHERE diaria.id_empresa = id_empresa AND
        diaria.habilita <> IF(soloHabilitados, 'N', 'X')
        
ORDER BY diaria.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaDiferenciasIntersucursal` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaDiferenciasIntersucursal`(IN desde DATE,
														hasta DATE,
                                                        id_empresa INT)
BEGIN

SELECT remito.fecha, remito.numComp, zona.nombre as origen, destino.nombre as destino, producto.codigo as codigo, producto.gtin as gtin, producto.nombre as nombre,
		SUM(IF(dc = 'C', cantidad, -cantidad)) as diferencia, intersucursal.usuario, DATE_FORMAT(intersucursal.fechaReal, "%d/%m/%Y a las %H:%i") as fechaRecepcion
        
FROM stockdetalle

LEFT JOIN remito ON remito.id_remito = stockdetalle.id_remito
LEFT JOIN zona ON zona.id_zona = remito.id_zona
LEFT JOIN zona destino ON destino.id_zona = remito.id_zonaDestino
LEFT JOIN producto ON producto.id_producto = stockdetalle.id_producto
LEFT JOIN intersucursal ON intersucursal.id_remito = remito.id_remito

where stockdetalle.id_remito IN (select remito.id_remito 
									from remito
									where remito.id_empresa = id_empresa AND
											IF(desde IS NULL, true, remito.fecha >= desde) AND
											IF(hasta IS NULL, true, remito.fecha <= hasta) AND
											remito.id_remito IN (SELECT intersucursal.id_remito
																	from intersucursal 
																	where intersucursal.recibido = 'S'))
group by remito.numComp, codigo
having diferencia <> 0
order by remito.fecha;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaEmpresa` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaEmpresa`(IN soloHabilitados BOOL)
BEGIN

SELECT empresa.id_empresa, empresa.razonSocial, empresa.fantasia, empresa.direccion, empresa.departamento, 
        empresa.provincia, empresa.pais, empresa.posicionIva, empresa.cuit,
        empresa.ingresosBrutos, empresa.establecimiento, empresa.sedeTimbrado, empresa.inicioActividades, 
        empresa.claveAccesos, empresa.habilita,
        opcion.abreviado AS descIVA
        
FROM empresa

LEFT JOIN opcion ON opcion.id_opcion = empresa.posicionIva

WHERE empresa.habilita <> IF(soloHabilitados, 'N', 'X')

ORDER BY empresa.fantasia;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaEntidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaEntidad`(IN id_empresa INT,
                                                        id_zona INT,
                                                        soloHabilitados BOOL)
BEGIN

SELECT entidad.id_entidad, entidad.nombre, entidad.direccion, entidad.codPostal, entidad.email,
        entidad.telefono1, entidad.telefono2, entidad.auditor, entidad.secretaria, entidad.certImplante,
        entidad.recomendaciones, entidad.cuit, entidad.riesgoCredito, entidad.reqFacturacion,
        entidad.observaciones, entidad.habilita, entidad.id_zona, provincia.nombre as provincia,
        departamento.nombre as departamento, localidad.nombre as localidad, zona.nombre as zona,
        formapago.nombre as formaPago, opcion.nombre as posicionIva, clasiEntidad.nombre as clasiEntidad,
        encargadocompras.nombre as comprasNombre, encargadocompras.telefono as comprasTelefono,
        encargadocompras.email as comprasEmail, encargadotesoreria.nombre as tesoreriaNombre, 
        encargadotesoreria.telefono as tesoreriaTelefono, encargadotesoreria.email as tesoreriaEmail, 
        encargadocontable.nombre as contableNombre, encargadocontable.telefono as contableTelefono,
        encargadocontable.email as contableEmail, encargadofarmacia.nombre as farmaciaNombre, 
        encargadofarmacia.telefono as farmaciaTelefono, encargadofarmacia.email as farmaciaEmail,
        entidad.gln
        
FROM entidad

LEFT JOIN provincia ON provincia.id_provincia = entidad.id_provincia
LEFT JOIN departamento ON departamento.id_departamento = entidad.id_departamento
LEFT JOIN localidad ON localidad.id_localidad = entidad.id_localidad
LEFT JOIN zona ON zona.id_zona = entidad.id_zona
LEFT JOIN formapago ON formapago.id_formapago = entidad.id_formapago
LEFT JOIN opcion ON opcion.id_opcion = entidad.id_opcion
LEFT JOIN clasientidad ON clasientidad.id_clasientidad = entidad.id_clasientidad
LEFT JOIN encargadocompras ON encargadocompras.id_entidad = entidad.id_entidad
LEFT JOIN encargadotesoreria ON encargadotesoreria.id_entidad = entidad.id_entidad
LEFT JOIN encargadocontable ON encargadocontable.id_entidad = entidad.id_entidad
LEFT JOIN encargadofarmacia ON encargadofarmacia.id_entidad = entidad.id_entidad

WHERE entidad.id_empresa = id_empresa AND
    IF(id_zona > 0, entidad.id_zona = id_zona, true) AND
    entidad.habilita <> IF(soloHabilitados, 'N', 'X')
    
ORDER BY entidad.nombre;
        
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaEspecialidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaEspecialidad`(IN soloHabilitados BOOL)
BEGIN

SELECT especialidad.id_especialidad, especialidad.nombre, especialidad.habilita

FROM especialidad

WHERE especialidad.habilita <> IF(soloHabilitados, 'N', 'X')

ORDER BY especialidad.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaEstadoSistema` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaEstadoSistema`()
BEGIN

SELECT estadosistema.estado

FROM estadosistema

WHERE estadosistema.enProceso = 'S'

LIMIT 1;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaEstrategia` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaEstrategia`(IN desde DATE,
                                                            hasta DATE,
                                                            id_profesional INT,
                                                            id_vendedor INT,
                                                            id_zona INT,
                                                            id_empresa INT,
                                                            historico VARCHAR(1))
BEGIN

SELECT profesional.nombre as profesional, estrategia.fechaInicio,
    estrategia.fechaAgenda, estrategia.perfil, estrategia.estrategia,
    estrategia.respuesta, estrategia.id_estrategia, estrategia.historico
    
FROM estrategia

LEFT JOIN profesional ON profesional.id_profesional = estrategia.id_profesional

WHERE estrategia.id_empresa = id_empresa AND
    IF(historico = 'X', true, estrategia.historico = historico) AND
    IF(desde IS NULL, true, estrategia.fechaAgenda >= desde) AND
    IF(hasta IS NULL, true, estrategia.fechaAgenda <= hasta) AND
    IF(id_profesional = 0, true, estrategia.id_profesional = id_profesional) AND
    IF(id_vendedor = 0, true, estrategia.id_profesional IN (SELECT profesional.id_profesional
                                                            FROM profesional
                                                            WHERE profesional.id_vendedor = id_vendedor)) AND
    IF(id_zona = 0, true, estrategia.id_profesional IN (SELECT profesional.id_profesional
                                                            FROM profesional
                                                            WHERE profesional.id_zona = id_zona))
                                                            
ORDER BY fechaAgenda DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaEstrategiaEntidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaEstrategiaEntidad`(IN desde DATE,
                                                            hasta DATE,
                                                            id_entidad INT,
                                                            id_zona INT,
                                                            id_empresa INT,
                                                            historico VARCHAR(1))
BEGIN

SELECT entidad.nombre as entidad, estrategiaEntidad.fechaInicio,
    estrategiaEntidad.fechaAgenda, estrategiaEntidad.requisitos, estrategiaEntidad.estrategia,
    estrategiaEntidad.respuesta, estrategiaEntidad.id_estrategiaEntidad, estrategiaEntidad.historico
    
FROM estrategiaEntidad

LEFT JOIN entidad ON entidad.id_entidad = estrategiaEntidad.id_entidad

WHERE estrategiaEntidad.id_empresa = id_empresa AND
    IF(historico = 'X', true, estrategiaEntidad.historico = historico) AND
    IF(desde IS NULL, true, estrategiaEntidad.fechaAgenda >= desde) AND
    IF(hasta IS NULL, true, estrategiaEntidad.fechaAgenda <= hasta) AND
    IF(id_entidad = 0, true, estrategiaEntidad.id_entidad = id_entidad) AND
    IF(id_zona = 0, true, estrategiaEntidad.id_entidad IN (SELECT entidad.id_entidad
                                                            FROM entidad
                                                            WHERE entidad.id_zona = id_zona))
                                                            
ORDER BY fechaAgenda DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaFacturaRecibo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaFacturaRecibo`(IN factura INT,
                                                                desde DATE,
                                                                hasta DATE,
                                                                id_zona INT,
                                                                id_entidad INT,
                                                                id_empresa INT)
BEGIN

SELECT factura.id_presupuesto, factura.fecha,
    tipoComp.abreviatura as tipoComp, factura.numCompRel,
    (factura.neto + factura.percIIBB + factura.iva) as total,
    SUM(IF(factura.dc = 'D',factura.neto + factura.percIIBB + factura.iva, -(factura.neto + factura.percIIBB + factura.iva))) as pendiente,
    factura.id_tipoCompRel, IFNULL(entidad.nombre, '') as entidad
    
FROM factura

LEFT JOIN tipoComp ON tipoComp.id_tipoComp = factura.id_tipoCompRel
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = factura.id_presupuesto
left join entidad on entidad.id_entidad = presupuesto.id_entidad

WHERE IF(desde IS NULL, true, factura.fecha >= desde) AND
    IF(hasta IS NULL, true, factura.fecha <= hasta) AND
    factura.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                FROM presupuesto
                                WHERE IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
                                    IF(id_entidad = 0, true, presupuesto.id_entidad = id_entidad)) AND
    factura.id_empresa = id_empresa
    
GROUP BY factura.id_tipoCompRel, factura.numCompRel

HAVING IF(factura = 0, true, IF(factura = 1, pendiente > 0, pendiente = 0))

ORDER BY entidad, factura.fecha;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaForecastAsignados` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaForecastAsignados`(IN anio INT,
												id_vendedor INT,
												id_entidad INT,
                                                id_forecastGrupo INT)
BEGIN

SELECT productoFact.id_productoFact, productoFact.nombre, IFNULL(forecast.cantidad, 0) as cantidad

FROM productoFact

LEFT JOIN forecast ON forecast.id_productoFact = productoFact.id_productoFact and
						forecast.anio = anio AND
                        forecast.id_vendedor = id_vendedor AND
                        forecast.id_entidad = id_entidad
                        
WHERE productoFact.id_forecastGrupo = id_forecastGrupo AND
		productoFact.habilita = 'S'
        
ORDER BY nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaForecastEntidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaForecastEntidad`(IN id_empresa INT)
BEGIN

SELECT entidad.nombre, entidad.id_entidad, IF(forecastEntidad.id_entidad IS NULL, "NO", "SI") as asignada

FROM entidad

LEFT JOIN forecastEntidad ON forecastEntidad.id_entidad = entidad.id_entidad AND
								forecastEntidad.id_empresa = id_empresa

WHERE entidad.id_empresa = id_empresa AND
		entidad.habilita = 'S'

ORDER BY entidad.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaForecastEntidades` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaForecastEntidades`(IN id_empresa INT)
BEGIN

SELECT forecastEntidad.id_entidad, entidad.nombre, entidad.id_zona

from forecastEntidad

left join entidad ON entidad.id_entidad = forecastEntidad.id_entidad

where forecastEntidad.id_empresa = id_empresa

order by nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaForecastGrupo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaForecastGrupo`(IN id_empresa INT,
											soloHabilitados BOOL)
BEGIN

SELECT forecastGrupo.id_forecastGrupo, forecastGrupo.nombre, forecastGrupo.habilita

FROM forecastGrupo

WHERE forecastGrupo.id_empresa = id_empresa AND
	IF(soloHabilitados, forecastGrupo.habilita = 'S', true)
    
ORDER BY forecastGrupo.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaFormaPago` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaFormaPago`(IN soloHabilitados BOOL)
BEGIN

SELECT formapago.id_formapago, formapago.nombre, formapago.habilita

FROM formapago

WHERE formapago.habilita <> IF(soloHabilitados, 'N', 'X')

ORDER BY formapago.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaHistoricoCirugias` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaHistoricoCirugias`(IN desde DATE,
												hasta DATE,
                                                id_lugarCirugia INT,
                                                id_zona INT,
                                                id_empresa INT)
BEGIN

SELECT presupuesto.id_presupuesto, presupuesto.fechaCirugia, 
	IFNULL(GROUP_CONCAT(DISTINCT cajadeposito.codigo ORDER BY cajadeposito.codigo SEPARATOR ' / '), '') as cajas, 
    ifnull(lugar.nombre, '') AS lugar, ifnull(zona.nombre, '') as zona, ifnull(profesional.nombre, '') as profesional, presupuesto.paciente,    
    IFNULL(GROUP_CONCAT(DISTINCT remito.numComp ORDER BY remito.numComp SEPARATOR ' / '), '') as remitos,
    ifnull(prestacion.nombre, '') as prestacion, IFNULL(tecnico.nombre, '') as tecnico, presupuesto.observaciones,
    IFNULL(GROUP_CONCAT(DISTINCT factura.numComp ORDER BY factura.numComp SEPARATOR ' / '), '') as facturas,
    ifnull(entidad.nombre, '') AS entidad, IFNULL(remito.sets, '') as sets
    
FROM presupuesto

LEFT JOIN cajaasignada ON cajaasignada.id_presupuesto = presupuesto.id_presupuesto
LEFT JOIN cajadeposito ON cajadeposito.id_cajadeposito = cajaasignada.id_cajadeposito
LEFT JOIN remito ON remito.id_presupuesto = presupuesto.id_presupuesto
LEFT JOIN entidad lugar ON lugar.id_entidad = presupuesto.id_lugarCirugia
LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad
LEFT JOIN zona ON zona.id_zona = presupuesto.id_zona
LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
LEFT JOIN prestacion ON prestacion.id_prestacion = presupuesto.id_prestacion
LEFT JOIN tecnico ON tecnico.id_tecnico = presupuesto.id_tecnico
LEFT JOIN factura ON factura.id_presupuesto = presupuesto.id_presupuesto

WHERE presupuesto.id_empresa = id_empresa and
	presupuesto.estado NOT IN ('P', 'A', 'S', 'R', 'N') and
    IF(desde IS NULL, true, presupuesto.fechaCirugia >= desde) AND
    IF(hasta IS NULL, true, presupuesto.fechaCirugia <= hasta) AND
    IF(id_lugarCirugia = 0, true, presupuesto.id_lugarCirugia = id_lugarCirugia) AND
    IF(id_zona = 0, true, presupuesto.id_zona = id_zona)
    
GROUP BY presupuesto.id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaHistoricoOperaciones` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaHistoricoOperaciones`(IN dias INT,
													id_zona INT,
                                                    id_empresa INT)
BEGIN

SELECT presupuesto.id_presupuesto, presupuesto.fechaCirugia, 
	IFNULL(GROUP_CONCAT(DISTINCT remito.numComp ORDER BY remito.numComp SEPARATOR ' / '), '') as remitos, entidad.nombre AS entidad, 
    IFNULL(GROUP_CONCAT(DISTINCT factura.numComp ORDER BY factura.numComp SEPARATOR ' / '), '') as facturas,
    presupuesto.observaciones, profesional.nombre as profesional, presupuesto.paciente,
    prestacion.nombre as prestacion, IFNULL(tecnico.nombre, '') as tecnico
    
FROM presupuesto

LEFT JOIN remito ON remito.id_presupuesto = presupuesto.id_presupuesto
LEFT JOIN factura ON factura.id_presupuesto = presupuesto.id_presupuesto
LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad
LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
LEFT JOIN prestacion ON prestacion.id_prestacion = presupuesto.id_prestacion
LEFT JOIN tecnico ON tecnico.id_tecnico = presupuesto.id_tecnico

WHERE (presupuesto.estado = 'Z' OR presupuesto.id_presupuesto IN (SELECT factura.id_presupuesto
																		FROM factura
                                                                        Where IF(dias = 0, true, DATEDIFF(current_date,factura.fecha) <= dias))) AND
	presupuesto.id_empresa = id_empresa and
    IF(dias = 0, true, IF(presupuesto.fechaCirugia is null, true, DATEDIFF(current_date,presupuesto.fechaCirugia) <= dias)) AND
    IF(id_zona = 0, true, presupuesto.id_zona = id_zona)
    
GROUP BY presupuesto.id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaIntersucursalRecepcion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaIntersucursalRecepcion`(IN id_zona INT,
														id_zonaDestino INT,
                                                        id_empresa INT)
BEGIN

SELECT remito.fecha, remito.numComp as numero, 
		IFNULL(zona.nombre, '') as origen, IFNULL(destino.nombre, '') as destino,
		remito.id_remito, remito.observaciones, remito.id_zonaDestino
        
FROM remito

LEFT JOIN zona ON zona.id_zona = remito.id_zona
LEFT JOIN zona destino ON destino.id_zona = remito.id_zonaDestino

WHERE IF(id_zona = 0, true, remito.id_zona = id_zona) AND
		IF(id_zonaDestino = 0, true, remito.id_zonaDestino = id_zonaDestino) AND
        remito.id_remito IN (SELECT intersucursal.id_remito
								FROM intersucursal
                                WHERE intersucursal.recibido = 'N' AND
										intersucursal.id_intersucursal NOT IN (SELECT intersucursalCaja.id_intersucursal
																				FROM intersucursalCaja
                                                                                WHERE intersucursalCaja.devuelto = 'S')) AND
                                
		remito.id_empresa = id_empresa
        
ORDER BY remito.fecha;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaIntersucursalRecibeDevolucion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaIntersucursalRecibeDevolucion`(IN id_zona INT,
														id_zonaDestino INT,
                                                        id_empresa INT)
BEGIN

SELECT remito.fecha, remito.numComp as numero, 
		IFNULL(zona.nombre, '') as origen, IFNULL(destino.nombre, '') as destino,
		remito.id_remito, remito.observaciones, remito.id_zonaDestino
        
FROM remito

LEFT JOIN zona ON zona.id_zona = remito.id_zona
LEFT JOIN zona destino ON destino.id_zona = remito.id_zonaDestino

WHERE IF(id_zona = 0, true, remito.id_zona = id_zona) AND
		IF(id_zonaDestino = 0, true, remito.id_zonaDestino = id_zonaDestino) AND
        remito.id_remito IN (SELECT intersucursal.id_remito
								FROM intersucursal
                                WHERE intersucursal.recibido = 'N' AND
										intersucursal.id_intersucursal IN (SELECT intersucursalCaja.id_intersucursal
																				FROM intersucursalCaja
                                                                                WHERE intersucursalCaja.devuelto = 'S')) AND
                                
		remito.id_empresa = id_empresa
        
ORDER BY remito.fecha;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaLiquidacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaLiquidacion`(IN desde DATE,
                                                                hasta DATE,
                                                                id_zona INT,
                                                                id_profesional INT,
                                                                id_empresa INT)
BEGIN

SELECT liquidacion.fecha, profesional.nombre as profesional, 
    liquidacion.id_liquidacion as liquidacion, profesional.dni as dni, olp

FROM mayorprofesional

LEFT JOIN liquidacion ON liquidacion.id_liquidacion = mayorprofesional.liquidacion
LEFT JOIN profesional ON profesional.id_profesional = mayorprofesional.id_profesional
LEFT JOIN liquidacion_olp as olp on olp.id = liquidacion.id_liquidacion

WHERE mayorprofesional.liquidacion IN (SELECT liquidacion.id_liquidacion
                                        FROM liquidacion
                                        WHERE IF(desde IS NULL, true, liquidacion.fecha >= desde) AND
                                            IF(hasta IS NULL, true, liquidacion.fecha <= hasta) AND
                                            liquidacion.id_empresa = id_empresa) AND
    
    IF(id_zona  = 0, true, mayorprofesional.id_profesional in (Select profesional.id_profesional
																FROM profesional
                                                                WHERE profesional.id_zona = id_zona AND
																		profesional.habilita = 'S')) AND
    IF(id_profesional  = 0, true, mayorprofesional.id_profesional = id_profesional) AND
    mayorprofesional.liquidacion > 0
    
GROUP BY liquidacion.id_liquidacion

ORDER BY liquidacion.id_liquidacion DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaLiquidacionTecnico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaLiquidacionTecnico`(IN desde DATE,
                                                                    hasta DATE,
                                                                    id_zona INT,
                                                                    id_prestacion INT,
                                                                    id_tecnico INT,
                                                                    liquidacion INT,
                                                                    id_empresa INT)
BEGIN

SELECT presupuesto.id_presupuesto, presupuesto.fechaCirugia,
        profesional.nombre as profesional, presupuesto.paciente, mayortecnico.importe as importe,
        tecnico.nombre as tecnico, prestacion.nombre as prestacion,
        mayortecnico.liquidacion as liquidacion, mayortecnico.id_mayorTecnico as id_mayortecnico
        
FROM presupuesto

LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
LEFT JOIN mayortecnico ON mayortecnico.id_presupuesto = presupuesto.id_presupuesto
LEFT JOIN tecnico ON tecnico.id_tecnico = presupuesto.id_tecnico
LEFT JOIN prestacion ON prestacion.id_prestacion = presupuesto.id_prestacion

WHERE IF(desde IS NULL, true, presupuesto.fechaCirugia >= desde) AND
        IF(hasta IS NULL, true, presupuesto.fechaCirugia <= hasta) AND
        IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
        IF(id_prestacion = 0, true, presupuesto.id_prestacion = id_prestacion) AND
        IF(id_tecnico = 0, true, presupuesto.id_tecnico = id_tecnico) AND
        presupuesto.id_empresa = id_empresa
                                            
ORDER BY presupuesto.fechaCirugia; 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaLocalidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaLocalidad`(IN id_departamento INT)
BEGIN

SELECT localidad.id_localidad, localidad.nombre

FROM localidad

WHERE IF(id_departamento > 0, localidad.id_departamento = id_departamento, true)

ORDER BY localidad.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaMantenimiento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaMantenimiento`(IN soloHabilitados BOOL)
BEGIN

SELECT mantenimiento.id_mantenimiento, mantenimiento.nombre, mantenimiento.habilita

FROM mantenimiento

WHERE mantenimiento.habilita <> IF(soloHabilitados, 'N', 'X')

ORDER BY mantenimiento.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaMapa` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaMapa`(IN cajas INT, 

                                            desde DATE,

                                            hasta DATE,

                                            id_entidad INT,

                                            id_lugarCx INT,

                                            id_zona INT,

                                            id_empresa INT)
BEGIN



SELECT cajaAsignada.id_presupuesto, cajadeposito.codigo as caja, profesional.nombre as profesional, entidad.nombre as entidad,

        presupuesto.fechaCirugia, IFNULL(remito.numComp, "") as remito, 

        presupuesto.paciente as paciente,lugar.nombre as lugarCirugia, 

        montoPresupuesto(cajaAsignada.id_presupuesto) as importePresupuesto,
		
        prestacion.nombre as prestacion, IFNULL(tecnico.nombre, '') as tecnico,
        
        presupuesto.observaciones as observaciones

        

FROM cajaAsignada



LEFT JOIN presupuesto ON presupuesto.id_presupuesto = cajaAsignada.id_presupuesto

LEFT JOIN cajaDeposito ON cajaDeposito.id_cajaDeposito = cajaAsignada.id_cajaDeposito

LEFT JOIN caja ON caja.id_caja = cajaDeposito.id_caja

LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1

LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad

LEFT JOIN entidad lugar ON lugar.id_entidad = presupuesto.id_lugarCirugia

LEFT JOIN remito ON remito.id_presupuesto = cajaAsignada.id_presupuesto

LEFT JOIN prestacion ON prestacion.id_prestacion = presupuesto.id_prestacion

LEFT JOIN tecnico ON tecnico.id_tecnico = presupuesto.id_tecnico



WHERE cajaAsignada.id_presupuesto IN (SELECT presupuesto.id_presupuesto

                                        FROM presupuesto

                                        WHERE IF(desde IS NULL, true, presupuesto.fechaCirugia >= desde) AND

                                                IF(hasta IS NULL, true, presupuesto.fechaCirugia <= hasta) AND

                                                IF(id_entidad = 0, true, presupuesto.id_entidad = id_entidad) AND

                                                IF(id_lugarCx = 0, true, presupuesto.id_lugarCirugia = id_lugarCx) AND

                                                IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND

                                                presupuesto.estado = 'C' AND

                                                presupuesto.id_empresa = id_empresa) AND

                                                

    IF(cajas = 0, true, IF(cajas = 1, cajaAsignada.id_presupuesto NOT IN (SELECT remito.id_presupuesto

                                                                        FROM remito

                                                                        WHERE remito.id_empresa = id_empresa),

                                    cajaAsignada.id_presupuesto IN (SELECT remito.id_presupuesto

                                                                        FROM remito

                                                                        WHERE remito.id_empresa = id_empresa AND remito.recibido = 'N')))

                                                                        

ORDER BY entidad, cajaAsignada.id_presupuesto;



END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaMapaRecepcion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaMapaRecepcion`(IN desde DATE,

                                                        hasta DATE,

                                                        id_zona INT,

                                                        id_empresa INT)
BEGIN



SELECT presupuesto.id_presupuesto AS turno, presupuesto.fechacirugia,

    remito.numComp AS remito, ifnull(zona.nombre,'') AS zona, ifnull(entidad.nombre,'') AS entidad, ifnull(prestacion.nombre, '') AS prestacion,

    IFNULL(tecnico.nombre,'') AS tecnico, remito.id_remito, presupuesto.observaciones, presupuesto.paciente, presupuesto.estado,
	
    IFNULL(profesional.nombre, '') as profesional, ifnull(remito.cajas, '') as cajas, ifnull(lugar.nombre, '') as lugar

    

FROM remito



LEFT JOIN presupuesto ON presupuesto.id_presupuesto = remito.id_presupuesto

LEFT JOIN zona ON zona.id_zona = remito.id_zona

LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad

LEFT JOIN entidad lugar ON lugar.id_entidad = presupuesto.id_lugarCirugia

LEFT JOIN prestacion ON prestacion.id_prestacion = presupuesto.id_prestacion

LEFT JOIN tecnico ON tecnico.id_tecnico = presupuesto.id_tecnico

LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1



WHERE remito.recibido = 'N' AND 
		remito.id_presupuesto IN (SELECT id_presupuesto 

                                    FROM presupuesto

                                    WHERE IF(desde IS NULL, true, presupuesto.fechaCirugia >= desde) AND

                                           IF(hasta IS NULL, true, presupuesto.fechaCirugia <= hasta) AND

                                           presupuesto.id_empresa = id_empresa) AND

        IF(id_zona = 0, true, remito.id_zona = id_zona) AND

        remito.id_empresa = id_empresa

ORDER BY presupuesto.fechacirugia DESC;



END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaMayorComposicion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaMayorComposicion`(IN ids TEXT,
                                                            desde DATE,
                                                            hasta DATE,
                                                            id_empresa INT)
BEGIN

(SELECT profesional.nombre as profesional, ifnull(desde, '1990-01-01') as fecha, -1 as id_presupuesto, 
	'' as paciente, '' as tipoOperacion, '' as observaciones,
    0 as saldo, ifnull(saldoInicialProfesional(profesional.id_profesional, desde, id_empresa), 0.00) as saldoInicial
    
    FROM profesional
    
    WHERE is_id_in_ids(ids, profesional.id_profesional))
    
union

(SELECT profesional.nombre as profesional, mayorprofesional.fecha, mayorprofesional.id_presupuesto,
    presupuesto.paciente as paciente, tipooperacion.nombre as tipoOperacion, mayorprofesional.observaciones,
    SUM(IF(mayorprofesional.dc = 'D', mayorprofesional.importe, -mayorprofesional.importe)) as saldo,
    ifnull(saldoInicialProfesional(profesional.id_profesional, desde, id_empresa), 0.00) as saldoInicial
    
FROM mayorprofesional

LEFT JOIN profesional ON profesional.id_profesional = mayorprofesional.id_profesional
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = mayorprofesional.id_presupuesto
LEFT JOIN tipooperacion ON tipooperacion.id_tipoOperacion = presupuesto.id_tipoOperacion

WHERE mayorprofesional.id_empresa = id_empresa AND
    is_id_in_ids(ids, mayorprofesional.id_profesional) AND
    IF(desde IS NULL, true, mayorprofesional.fecha >= desde) AND
    IF(hasta IS NULL, true, mayorprofesional.fecha <= hasta) AND
    IF(mayorprofesional.preliquidacion = 0, true, IF(mayorprofesional.liquidacion > 0, true, false))
    
GROUP BY profesional, mayorprofesional.id_presupuesto

HAVING saldo != 0
    
ORDER BY profesional, mayorprofesional.fecha)

ORDER BY profesional, fecha;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaMayorComposicion2` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaMayorComposicion2`(IN ids TEXT,
                                                            desde DATE,
                                                            hasta DATE,
                                                            id_empresa INT)
BEGIN

(SELECT profesional.nombre as profesional, ifnull(desde, '1990-01-01') as fecha, -1 as id_presupuesto, 
	'' as paciente, '' as tipoOperacion, '' as observaciones,
    0 as saldo, ifnull(saldoInicialProfesional(profesional.id_profesional, desde, id_empresa), 0.00) as saldoInicial
    
    FROM profesional
    
    WHERE is_id_in_ids(ids, profesional.id_profesional))
    
union all

(SELECT profesional.nombre as profesional, mayorprofesional.fecha, mayorprofesional.id_presupuesto,
    presupuesto.paciente as paciente, tipooperacion.nombre as tipoOperacion, mayorprofesional.observaciones,
    SUM(IF(mayorprofesional.dc = 'D', mayorprofesional.importe, -mayorprofesional.importe)) as saldo,
    ifnull(saldoInicialProfesional(profesional.id_profesional, desde, id_empresa), 0.00) as saldoInicial
    
FROM mayorprofesional

LEFT JOIN profesional ON profesional.id_profesional = mayorprofesional.id_profesional
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = mayorprofesional.id_presupuesto
LEFT JOIN tipooperacion ON tipooperacion.id_tipoOperacion = presupuesto.id_tipoOperacion

WHERE mayorprofesional.id_empresa = id_empresa AND
    is_id_in_ids(ids, mayorprofesional.id_profesional) AND
    IF(desde IS NULL, true, mayorprofesional.fecha >= desde) AND
    IF(hasta IS NULL, true, mayorprofesional.fecha <= hasta) AND
    IF(mayorprofesional.preliquidacion = 0, true, IF(mayorprofesional.liquidacion > 0, true, false))
    
GROUP BY profesional, mayorprofesional.id_presupuesto

HAVING saldo != 0
    
ORDER BY profesional, mayorprofesional.fecha)

ORDER BY profesional, fecha;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaMayorDevengado` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaMayorDevengado`(IN ids TEXT,
                                                            desde DATE,
                                                            hasta DATE,
                                                            id_empresa INT)
BEGIN

SELECT profesional.nombre as profesional, presupuesto.fechaCirugia as fechaCirugia, factura.fecha as fechaFactura, 
    mayorprofesional.id_presupuesto, presupuesto.paciente as paciente, tipooperacion.nombre as tipoOperacion, 
    mayorprofesional.observaciones,
    IF(mayorprofesional.dc = 'D', mayorprofesional.importe, 0) as debe,
    IF(mayorprofesional.dc = 'C', mayorprofesional.importe, 0) as haber
    
FROM mayorProfesional

LEFT JOIN profesional ON profesional.id_profesional = mayorprofesional.id_profesional
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = mayorprofesional.id_presupuesto
LEFT JOIN factura ON factura.id_presupuesto = mayorprofesional.id_presupuesto
LEFT JOIN tipooperacion ON tipooperacion.id_tipoOperacion = presupuesto.id_tipoOperacion
                        
WHERE mayorProfesional.id_empresa = id_empresa AND
        is_id_in_ids(ids, mayorprofesional.id_profesional) AND
        mayorProfesional.preliquidacion = 0 AND
        mayorProfesional.id_presupuesto > 0 AND
        mayorProfesional.transferencia = 'N' AND
        mayorProfesional.id_presupuesto IN (SELECT factura.id_presupuesto
											FROM factura
                                            WHERE IF(desde IS NULL, true, factura.fecha >= desde) AND
													IF(hasta IS NULL, true, factura.fecha <= hasta) AND
													factura.id_tipoComp IN (1, 5, 8, 23)) AND
                                                    
		mayorprofesional.id_presupuesto IN (SELECT presupuesto.id_presupuesto
											FROM presupuesto
                                            WHERE presupuesto.estado = 'Z' AND
													devengado(presupuesto.id_presupuesto, presupuesto.fechaCirugia))
       
GROUP BY profesional, mayorProfesional.fechaReal 

ORDER BY profesional, mayorProfesional.id_presupuesto;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaMayorDevengado2` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaMayorDevengado2`(IN ids TEXT,
                                                            desde DATE,
                                                            hasta DATE,
                                                            id_empresa INT)
BEGIN

SELECT profesional.nombre as profesional, presupuesto.fechaCirugia as fechaCirugia, factura.fecha as fechaFactura, 
    mayorprofesional.id_presupuesto, presupuesto.paciente as paciente, tipooperacion.nombre as tipoOperacion, 
    mayorprofesional.observaciones,
    IF(mayorprofesional.dc = 'D', mayorprofesional.importe, 0) as debe,
    IF(mayorprofesional.dc = 'C', mayorprofesional.importe, 0) as haber
    
FROM mayorProfesional

LEFT JOIN profesional ON profesional.id_profesional = mayorprofesional.id_profesional
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = mayorprofesional.id_presupuesto
LEFT JOIN factura ON factura.id_presupuesto = mayorprofesional.id_presupuesto
LEFT JOIN tipooperacion ON tipooperacion.id_tipoOperacion = presupuesto.id_tipoOperacion
                        
WHERE mayorProfesional.id_empresa = id_empresa AND
        is_id_in_ids(ids, mayorprofesional.id_profesional) AND
        mayorProfesional.preliquidacion = 0 AND
        mayorProfesional.id_presupuesto > 0 AND
        mayorProfesional.transferencia = 'N' AND
        mayorProfesional.id_presupuesto IN (SELECT factura.id_presupuesto
											FROM factura
                                            WHERE IF(desde IS NULL, true, factura.fecha >= desde) AND
													IF(hasta IS NULL, true, factura.fecha <= hasta) AND
													factura.id_tipoComp IN (1, 5, 8, 23)) AND
                                                    
		mayorprofesional.id_presupuesto IN (SELECT presupuesto.id_presupuesto
											FROM presupuesto
                                            WHERE presupuesto.estado = 'Z' AND
													devengado(presupuesto.id_presupuesto, presupuesto.fechaCirugia))
       
GROUP BY profesional, mayorProfesional.fechaReal 

ORDER BY profesional, mayorProfesional.id_presupuesto;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaMayorGeneral` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaMayorGeneral`(IN ids TEXT,
                                                            desde DATE,
                                                            hasta DATE,
                                                            id_empresa INT)
BEGIN

(SELECT profesional.nombre as profesional, ifnull(desde, '1990-01-01') as fecha, -1 as id_presupuesto, 
	'' as paciente, '' as tipoOperacion, '' as observaciones,
    0 as debe, 0 as haber, ifnull(saldoInicialProfesional(profesional.id_profesional, desde, id_empresa), 0.00) as saldoInicial
    
    FROM profesional
    
    WHERE is_id_in_ids(ids, profesional.id_profesional))
    
union

(SELECT profesional.nombre as profesional, mayorprofesional.fecha, mayorprofesional.id_presupuesto,
    presupuesto.paciente as paciente, tipooperacion.nombre as tipoOperacion, mayorprofesional.observaciones,
    IF(mayorprofesional.dc = 'D', mayorprofesional.importe, 0) as debe,
    IF(mayorprofesional.dc = 'C', mayorprofesional.importe, 0) as haber,
    ifnull(saldoInicialProfesional(profesional.id_profesional, desde, id_empresa), 0.00) as saldoInicial
    
FROM mayorprofesional

LEFT JOIN profesional ON profesional.id_profesional = mayorprofesional.id_profesional
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = mayorprofesional.id_presupuesto
LEFT JOIN tipooperacion ON tipooperacion.id_tipoOperacion = presupuesto.id_tipoOperacion

WHERE mayorprofesional.id_empresa = id_empresa AND
    is_id_in_ids(ids, mayorprofesional.id_profesional) AND
    IF(desde IS NULL, true, mayorprofesional.fecha >= desde) AND
    IF(hasta IS NULL, true, mayorprofesional.fecha <= hasta) AND
    IF(mayorprofesional.preliquidacion = 0, true, IF(mayorprofesional.liquidacion > 0, true, false))
    
ORDER BY mayorprofesional.fecha)

ORDER BY profesional, fecha;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaMayorGeneral2` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaMayorGeneral2`(IN ids TEXT,
                                                            desde DATE,
                                                            hasta DATE,
                                                            id_empresa INT)
BEGIN

(SELECT profesional.nombre as profesional, ifnull(desde, '1990-01-01') as fecha, -1 as id_presupuesto, 
	'' as paciente, '' as tipoOperacion, '' as observaciones,
    0 as debe, 0 as haber, ifnull(saldoInicialProfesional(profesional.id_profesional, desde, id_empresa), 0.00) as saldoInicial
    
    FROM profesional
    
    WHERE is_id_in_ids(ids, profesional.id_profesional))
    
union all

(SELECT profesional.nombre as profesional, mayorprofesional.fecha, mayorprofesional.id_presupuesto,
    presupuesto.paciente as paciente, tipooperacion.nombre as tipoOperacion, 
    IF(mayorProfesional.liquidacion <= 0, mayorprofesional.observaciones, detalleLiquidacion(mayorProfesional.liquidacion)) as observaciones,
    IF(mayorprofesional.dc = 'D', mayorprofesional.importe, 0) as debe,
    IF(mayorprofesional.dc = 'C', mayorprofesional.importe, 0) as haber,
    ifnull(saldoInicialProfesional(profesional.id_profesional, desde, id_empresa), 0.00) as saldoInicial
    
FROM mayorprofesional

LEFT JOIN profesional ON profesional.id_profesional = mayorprofesional.id_profesional
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = mayorprofesional.id_presupuesto
LEFT JOIN tipooperacion ON tipooperacion.id_tipoOperacion = presupuesto.id_tipoOperacion

WHERE mayorprofesional.id_empresa = id_empresa AND
    is_id_in_ids(ids, mayorprofesional.id_profesional) AND
    IF(desde IS NULL, true, mayorprofesional.fecha >= desde) AND
    IF(hasta IS NULL, true, mayorprofesional.fecha <= hasta) AND
    IF(mayorprofesional.preliquidacion = 0, true, IF(mayorprofesional.liquidacion > 0, true, false))
    
ORDER BY mayorprofesional.fecha)

ORDER BY profesional, fecha;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaMayorPagado` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaMayorPagado`(IN ids TEXT,
                                                            desde DATE,
                                                            hasta DATE,
                                                            id_empresa INT)
BEGIN

SELECT profesional.nombre as profesional, liquidacion.fecha AS fechaLiquidacion, 
    mayorProfesional.id_presupuesto, presupuesto.paciente AS paciente,
    CONCAT('Liquidación N° ', liquidacion.id_liquidacion, ' - (', liquidacion.usuario, ')') AS detallePago,
    mayorProfesional.importe

FROM mayorProfesional

LEFT JOIN profesional ON profesional.id_profesional = mayorprofesional.id_profesional
LEFT JOIN liquidacion ON liquidacion.id_liquidacion = mayorProfesional.liquidacion
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = mayorProfesional.id_presupuesto

WHERE mayorProfesional.id_empresa = id_empresa AND
    is_id_in_ids(ids, mayorprofesional.id_profesional) AND
    IF(desde IS NULL, true, mayorProfesional.fecha >= desde) AND
    IF(hasta IS NULL, true, mayorProfesional.fecha <= hasta) AND
    mayorProfesional.liquidacion > 0
    
ORDER BY profesional, liquidacion.fecha, mayorProfesional.id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaMayorPagado2` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaMayorPagado2`(IN ids TEXT,
                                                            desde DATE,
                                                            hasta DATE,
                                                            id_empresa INT)
BEGIN

SELECT profesional.nombre as profesional, liquidacion.fecha AS fechaLiquidacion, 
    mayorProfesional.id_presupuesto, presupuesto.paciente AS paciente,
    IF(mayorProfesional.liquidacion <= 0, mayorprofesional.observaciones, detalleLiquidacion(mayorProfesional.liquidacion)) as detallePago,
    mayorProfesional.importe

FROM mayorProfesional

LEFT JOIN profesional ON profesional.id_profesional = mayorprofesional.id_profesional
LEFT JOIN liquidacion ON liquidacion.id_liquidacion = mayorProfesional.liquidacion
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = mayorProfesional.id_presupuesto

WHERE mayorProfesional.id_empresa = id_empresa AND
    is_id_in_ids(ids, mayorprofesional.id_profesional) AND
    IF(desde IS NULL, true, mayorProfesional.fecha >= desde) AND
    IF(hasta IS NULL, true, mayorProfesional.fecha <= hasta) AND
    mayorProfesional.liquidacion > 0
    
ORDER BY profesional, liquidacion.fecha, mayorProfesional.id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaMayorPendiente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaMayorPendiente`(IN ids TEXT,
                                                            desde DATE,
                                                            hasta DATE,
                                                            id_empresa INT)
BEGIN

SELECT profesional.nombre as profesional, mayorprofesional.fecha, mayorprofesional.id_presupuesto, 
    IFNULL(presupuesto.paciente, '') as paciente, IFNULL(tipooperacion.nombre, '') as tipoOperacion, 
    mayorprofesional.observaciones,
    SUM(IF(mayorprofesional.dc = 'D', mayorprofesional.importe, -mayorprofesional.importe)) as saldo
    
FROM mayorProfesional

LEFT JOIN profesional ON profesional.id_profesional = mayorprofesional.id_profesional
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = mayorprofesional.id_presupuesto
LEFT JOIN tipooperacion ON tipooperacion.id_tipoOperacion = presupuesto.id_tipoOperacion
                        
WHERE mayorProfesional.id_empresa = id_empresa AND
        is_id_in_ids(ids, mayorprofesional.id_profesional) AND
        IF(mayorprofesional.preliquidacion = 0, true, IF(mayorprofesional.liquidacion > 0, true, false)) AND
        IF(desde IS NULL, true, mayorprofesional.fecha >= desde) AND
        IF(hasta IS NULL, true, mayorprofesional.fecha <= hasta)
        
GROUP BY profesional, id_presupuesto

Having saldo <> 0
       
ORDER BY profesional, mayorProfesional.fecha;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaMayorPendiente2` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaMayorPendiente2`(IN ids TEXT,
                                                            desde DATE,
                                                            hasta DATE,
                                                            id_empresa INT)
BEGIN

SELECT profesional.nombre as profesional, mayorprofesional.fecha, mayorprofesional.id_presupuesto, 
    IFNULL(presupuesto.paciente, '') as paciente, IFNULL(tipooperacion.nombre, '') as tipoOperacion, 
    mayorprofesional.observaciones,
    SUM(IF(mayorprofesional.dc = 'D', mayorprofesional.importe, -mayorprofesional.importe)) as saldo
    
FROM mayorProfesional

LEFT JOIN profesional ON profesional.id_profesional = mayorprofesional.id_profesional
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = mayorprofesional.id_presupuesto
LEFT JOIN tipooperacion ON tipooperacion.id_tipoOperacion = presupuesto.id_tipoOperacion
                        
WHERE mayorProfesional.id_empresa = id_empresa AND
        is_id_in_ids(ids, mayorprofesional.id_profesional) AND
        IF(mayorprofesional.preliquidacion = 0, true, IF(mayorprofesional.liquidacion > 0, true, false)) AND
        IF(desde IS NULL, true, mayorprofesional.fecha >= desde) AND
        IF(hasta IS NULL, true, mayorprofesional.fecha <= hasta)
        
GROUP BY profesional, id_presupuesto

Having saldo <> 0
       
ORDER BY profesional, mayorProfesional.fecha;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaModificaFactura` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaModificaFactura`(IN desde DATE,
                                                                    hasta DATE,
                                                                    id_tipoComp INT,
                                                                    id_zona INT,
                                                                    id_empresa INT)
BEGIN

SELECT factura.id_presupuesto, factura.fecha,
    IFNULL(tipoComp.abreviatura, '') as tipoComp, factura.numComp, factura.bonificacion, factura.neto,
    factura.aliPercIIBB, factura.percIIBB, factura.aliIva, factura.iva, 
    factura.vencimiento as vencimiento, 
    IFNULL(formaPago.nombre, '') as formaPago, factura.observaciones, factura.id_factura
    
FROM factura

LEFT JOIN tipoComp ON tipoComp.id_tipoComp = factura.id_tipoComp
LEFT JOIN formaPago ON formaPago.id_formaPago = factura.id_formaPago

WHERE IF(desde IS NULL, true, factura.fecha >= desde) AND
        IF(hasta IS NULL, true, factura.fecha <= hasta) AND
        IF(id_tipoComp = 0, true, factura.id_tipoComp = id_tipoComp) AND
        if(id_zona = 0, true, factura.id_presupuesto IN (SELECT presupuesto.id_presupuesto
															FROM presupuesto
                                                            WHERE presupuesto.id_zona = id_zona AND
																	presupuesto.id_empresa = id_empresa)) AND
        factura.id_empresa = id_empresa
        
ORDER BY factura.fecha DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaMoneda` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaMoneda`(IN soloHabilitados BOOL)
BEGIN

SELECT moneda.id_moneda, moneda.codigo, moneda.nombre, moneda.simbolo, 
        moneda.habilita, IFNULL(ultimaCotizacion(moneda.id_moneda),1.000) AS cotizacion
    
FROM moneda

WHERE moneda.habilita <> IF(soloHabilitados, 'N', 'X')
    
ORDER BY moneda.nombre;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaMotivo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaMotivo`()
BEGIN

SELECT motivo.id_motivo, motivo.nombre

FROM motivo

WHERE motivo.habilita = 'S'

ORDER BY motivo.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaMovimientosStock` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaMovimientosStock`(IN id_empresa INT,
                                                                                ids TEXT,
                                                                                desde DATE,
                                                                                hasta DATE,
                                                                                lote VARCHAR(45),
                                                                                serie VARCHAR(45),
                                                                                id_zona INT,
                                                                                dc VARCHAR(1))
BEGIN
SET @d = desde;
SET @h = hasta;
SET @l = lote;
SET @serie = serie;

set @s = CONCAT("(SELECT ifnull(producto.codigo, '') as codigo, ifnull(producto.gtin, '') as gtin, ifnull(producto.nombre, '') as producto, '' as dc, -1 as cantidad, '' as pm, '' as lote, '' as serie, null as vencimiento,",
				"'' as observaciones, null as fecha,",
                "if(? is null, 0, ifnull(stockSaldoInicial(", id_zona, ", producto.id_producto, ?, ", id_empresa, "), 0)) as saldoInicial, 0.00 as costo ",
                "from producto ",
                "WHERE producto.id_producto IN (", ids, ")) ",
                "UNION ALL",
                "(SELECT ifnull(producto.codigo, '') as codigo, ifnull(producto.gtin, '') as gtin, ifnull(producto.nombre, '') as producto, stockdetalle.dc, stockdetalle.cantidad, stockdetalle.pm, UPPER(stockdetalle.lote), stockdetalle.serie, stockdetalle.vencimiento,",
                "stockdetalle.observaciones, IF(stockdetalle.id_stock > 0, stock.fecha, IF(stockdetalle.id_remito > 0, remito.fecha, ajustestock.fecha)) as fecha, 0 as saldoInicial, ",
                "IFNULL(costoPesos(stockdetalle.id_producto), 0.00) as costo ",
                "FROM stockdetalle ",
                "LEFT JOIN producto ON producto.id_producto = stockdetalle.id_producto ",
                "LEFT JOIN stock ON stock.id_stock = stockdetalle.id_stock ",
                "LEFT JOIN remito ON remito.id_remito = stockdetalle.id_remito ",
                "LEFT JOIN ajustestock ON ajustestock.id_ajusteStock = stockdetalle.id_ajusteStock ",
                "WHERE stockdetalle.id_producto IN (", ids, ") AND ",
                "stockdetalle.id_zona = ", id_zona, " AND ",
                "IF(? = '', true, stockdetalle.lote = ?) AND ",
                "IF(? = '', true, stockdetalle.serie = ?) AND ",
                "stockdetalle.dc <> '", dc, "' AND ",
                "(stockdetalle.id_stock IN (SELECT id_stock FROM stock WHERE stock.id_empresa = ", id_empresa, " AND IF(? IS NULL, true, stock.fecha >= ?) AND IF(? IS NULL, true, stock.fecha <= ?)) OR ",
                "stockdetalle.id_remito IN (SELECT id_remito FROM remito WHERE remito.id_empresa = ", id_empresa, " AND IF(? IS NULL, true, remito.fecha >= ?) AND IF(? IS NULL, true, remito.fecha <= ?) AND (remito.id_zona = ", id_zona, " or (remito.id_zonaDestino = ", id_zona, " AND remito.recibido = 'S'))) OR ",
                "stockdetalle.id_ajusteStock IN (SELECT id_ajusteStock FROM ajustestock WHERE ajustestock.id_empresa = ", id_empresa, " AND IF(? IS NULL, true, ajustestock.fecha >= ?) AND IF(? IS NULL, true, ajustestock.fecha <= ?))) ",
                "ORDER BY stockdetalle.pm, stockDetalle.lote, fecha) ",
                "order by codigo, lote, fecha");

PREPARE stmt FROM @s;

execute stmt using @d, @d, @l, @l, @serie, @serie, @d, @d, @h, @h, @d, @d, @h, @h, @d, @d, @h, @h;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaMovimientosStock2` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaMovimientosStock2`(IN id_empresa INT,
                                                                                ids TEXT,
                                                                                desde DATE,
                                                                                hasta DATE,
                                                                                lote VARCHAR(45),
                                                                                serie VARCHAR(45),
                                                                                id_zona INT,
                                                                                dc VARCHAR(1))
BEGIN
(SELECT ifnull(producto.codigo, '') as codigo, ifnull(producto.gtin, '') as gtin, ifnull(producto.nombre, '') as producto, '' as dc, -1 as cantidad, '' as pm, '' as lote, null as vencimiento,
	'' as observaciones, ifnull(desde, '1990-01-01') as fecha,
	ifnull(stockSaldoInicial(zona.id_zona, producto.id_producto, desde, id_empresa), 0.00) as saldoInicial,
    zona.nombre as zona
    
    FROM producto
    left join zona on zona.habilita = 'S'
    
    WHERE is_id_in_ids(ids, producto.id_producto))
    
union

(SELECT ifnull(producto.codigo, '') as codigo, ifnull(producto.gtin, '') as gtin, ifnull(producto.nombre, '') as producto, stockdetalle.dc, stockdetalle.cantidad, stockdetalle.pm, stockDetalle.lote, stockDetalle.serie, stockdetalle.vencimiento,
    stockdetalle.observaciones, IF(stockdetalle.id_stock > 0, stock.fecha, IF(stockdetalle.id_remito > 0, remito.fecha, ajusteStock.fecha)) as fecha,
    0.00 as saldoInicial,
    IFNULL(IF(stockdetalle.id_stock > 0, zStock.nombre, IF(stockdetalle.id_remito > 0, zRemito.nombre, zAjuste.nombre)), '') as zona
    
FROM stockdetalle

LEFT JOIN producto ON producto.id_producto = stockdetalle.id_producto
LEFT JOIN stock ON stock.id_stock = stockdetalle.id_stock
LEFT JOIN remito ON remito.id_remito = stockdetalle.id_remito
LEFT JOIN ajusteStock ON ajusteStock.id_ajusteStock = stockdetalle.id_ajusteStock
LEFT JOIN zona zStock ON zStock.id_zona = stock.id_zona
LEFT JOIN zona zRemito ON zRemito.id_zona = remito.id_zona
LEFT JOIN zona zAjuste ON zAjuste.id_zona = ajusteStock.id_zona

WHERE is_id_in_ids(ids, stockdetalle.id_producto) AND
        IF(lote = "", true, stockdetalle.lote = lote) AND
        stockdetalle.dc <> dc AND
        (stockdetalle.id_stock IN (SELECT id_stock 
                                    FROM stock 
                                    WHERE stock.id_empresa = id_empresa AND
                                    IF(desde IS NULL, true, stock.fecha >= desde) AND
                                    IF(hasta IS NULL, true, stock.fecha <= hasta) AND
                                    IF(id_zona = 0, true, stock.id_zona = id_zona)) OR
                                    
        stockdetalle.id_remito IN (SELECT id_remito
                                    FROM remito
                                    WHERE remito.id_empresa = id_empresa AND
                                    IF(desde IS NULL, true, remito.fecha >= desde) AND
                                    IF(hasta IS NULL, true, remito.fecha <= hasta) AND
                                    IF(id_zona = 0, true, remito.id_zona = id_zona)) OR
                                    
        stockdetalle.id_ajusteStock IN (SELECT id_ajusteStock
                                    FROM ajusteStock
                                    WHERE ajusteStock.id_empresa = id_empresa AND
                                    IF(desde IS NULL, true, ajusteStock.fecha >= desde) AND
                                    IF(hasta IS NULL, true, ajusteStock.fecha <= hasta) AND
                                    IF(id_zona = 0, true, ajusteStock.id_zona = id_zona)))
                                    
ORDER BY stockdetalle.pm, stockDetalle.lote, stockDetalle.serie, fecha)

order by zona, producto, pm, lote, serie, fecha;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaNegociosPendientes` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaNegociosPendientes`(IN id_zona INT,
                                                                    id_vendedor INT,
                                                                    id_entidad INT,
                                                                    id_empresa INT)
BEGIN

SELECT presupuesto.id_presupuesto, presupuesto.fecha,
    entidad.nombre as entidad,
    presupuesto.paciente, presupuesto.direccion, presupuesto.telefono, presupuesto.observaciones,
    montoPresupuesto(presupuesto.id_presupuesto) as total, IFNULL(profesional.nombre, '') as profesional
    
FROM presupuesto

LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad
LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1

WHERE presupuesto.estado = 'P' AND
    presupuesto.vip = 'S' AND
    presupuesto.id_empresa = id_empresa AND
    IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
    IF(id_vendedor = 0, true, presupuesto.id_vendedor = id_vendedor) AND
    IF(id_entidad = 0, true, presupuesto.id_entidad = id_entidad)
    
ORDER BY entidad, presupuesto.id_presupuesto DESC;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaNotaPresu` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaNotaPresu`(IN id_empresa INT)
BEGIN

SELECT notapresu.nota

FROM notapresu

WHERE notapresu.id_empresa = id_empresa;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaOpcion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaOpcion`(IN grupo INT)
BEGIN

SELECT opcion.id_opcion, opcion.nombre, opcion.abreviado

FROM opcion

WHERE opcion.habilita = 'S' AND
        opcion.grupo = grupo

ORDER BY opcion.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaPendienteFacturacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaPendienteFacturacion`(id_entidad INT,
                                                                    id_profesional INT,
                                                                    id_zona INT,
                                                                    id_vendedor INT,
                                                                    id_empresa INT)
BEGIN

SELECT presupuesto.id_presupuesto, presupuesto.fechaCirugia,
    entidad.nombre as entidad, profesional.nombre as profesional, presupuesto.paciente, 
    montoPresupuesto(presupuesto.id_presupuesto) as montoPresupuesto,
    (montoPresupuesto(presupuesto.id_presupuesto) - IFNULL(montoFacturado(presupuesto.id_presupuesto), 0.00)) as montoFacturado,
    lugar.nombre as lugarCx
    
FROM presupuesto

LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad
LEFT JOIN entidad lugar ON lugar.id_entidad = presupuesto.id_lugarCirugia

WHERE presupuesto.estado = 'Z' AND
	presupuesto.desactivado = 'N' AND
	presupuesto.id_empresa = id_empresa AND
    IF(id_entidad = 0, true, presupuesto.id_entidad = id_entidad) AND
    IF(id_profesional = 0, true, presupuesto.id_profesional1 = id_profesional) AND
    IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
    IF(id_vendedor = 0, true, presupuesto.id_vendedor = id_vendedor)

HAVING montoFacturado <> 0

ORDER BY entidad.nombre, presupuesto.id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaPlazo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaPlazo`(IN soloHabilitados BOOL)
BEGIN

SELECT plazo.id_plazo, plazo.nombre, plazo.habilita

FROM plazo

WHERE plazo.habilita <> IF(soloHabilitados, 'N', 'X')

ORDER BY plazo.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaPlazosCobranza` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaPlazosCobranza`(IN desde DATE,
                                                            hasta DATE,
                                                            id_zona INT,
                                                            id_entidad INT,
                                                            operaciones VARCHAR(4),
                                                            entidad BOOL,
                                                            id_empresa INT)
BEGIN

SELECT entidad.nombre as entidad, 
    ((period_diff(date_format(factura.fecha, '%Y%m'), 
                date_format(fechaComprobante(factura.id_tipoCompRel, factura.numCompRel, factura.id_empresa), '%Y%m'))) * 30) as dias,
    SUM(factura.neto) as importeAplicado
    
FROM factura

LEFT JOIN presupuesto ON presupuesto.id_presupuesto = factura.id_presupuesto
LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad

WHERE factura.id_tipoComp IN (4, 16, 17) AND
    IF(desde IS NULL, true, factura.fecha >= desde) AND
    IF(hasta IS NULL, true, factura.fecha <= hasta) AND
    factura.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                                        FROM presupuesto
                                                        WHERE IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
                                                                IF(id_entidad = 0, true, presupuesto.id_entidad = id_entidad)) AND
    IF(operaciones = "XXXX", true, IF(operaciones = "9999", LEFT(factura.numComp, 4) = operaciones, LEFT(factura.numComp, 4) <> "9999")) AND
    factura.id_empresa = id_empresa

GROUP BY IF(entidad, entidad.nombre, null), dias
    
ORDER BY dias, IF(entidad, entidad.nombre, null);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaPrestacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaPrestacion`(IN id_empresa INT,
                                                        soloHabilitados BOOL)
BEGIN

SELECT prestacion.id_prestacion, prestacion.nombre, prestacion.importe, prestacion.habilita

FROM prestacion

WHERE prestacion.id_empresa = id_empresa AND
    prestacion.habilita <> IF(soloHabilitados, 'N', 'X')
    
ORDER BY prestacion.nombre;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaPresuAplicacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaPresuAplicacion`(IN id_profesional INT,
                                                                    id_empresa INT)
BEGIN

SELECT presupuesto.fechaCirugia, mayorprofesional.id_presupuesto, 
        presupuesto.paciente as paciente, entidad.nombre as entidad,
        SUM(IF(mayorprofesional.dc = 'C', mayorprofesional.importe, -mayorprofesional.importe)) as pendiente
        
FROM mayorprofesional

LEFT JOIN presupuesto ON presupuesto.id_presupuesto = mayorprofesional.id_presupuesto
LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad

WHERE mayorprofesional.id_profesional = id_profesional AND
    mayorprofesional.id_presupuesto > 0 AND
    IF(mayorprofesional.preliquidacion = 0, true, mayorprofesional.liquidacion > 0) AND
    mayorprofesional.id_empresa = id_empresa
    
GROUP BY mayorprofesional.id_presupuesto

HAVING pendiente > 0;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaPresuCertificado` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaPresuCertificado`(desde DATE,
																hasta DATE,
                                                                id_zona INT,
                                                                id_prestacion INT,
                                                                id_tecnico INT,
                                                                muestra INT,
                                                                calificaciones TEXT,
                                                                id_empresa INT)
BEGIN

SET @d = desde;
SET @h = hasta;
SET @id_z = id_zona;
SET @id_p = id_prestacion;
SET @id_t = id_tecnico;
SET @m = muestra;
SET @id_e = id_empresa;


SET @s = CONCAT("SELECT presupuesto.id_presupuesto, presupuesto.fechaCirugia,",
    "presupuesto.paciente, presupuesto.dni, presupuesto.telefono, profesional.nombre as profesional,",
    "entidad.nombre as entidad, lugar.nombre as lugarCirugia,",
    "IFNULL(tecnico.nombre,'') as tecnico, prestacion.nombre as prestacion,",
    "presupuesto.observaciones,",
    "IFNULL(nivelesCertificado.alternativas, 99) as alternativas,",
    "IFNULL(nivelesCertificado.instrumental, 99) as instrumental,",
    "IFNULL(nivelesCertificado.asistencia, 99) as asistencia,",
    "IFNULL(nivelesCertificado.puntualidad, 99) as puntualidad,",
    "IFNULL(nivelesCertificado.id_nivelesCertificado, 0) as id_nivelesCertificado,",
    "prestacion.importe as importePrestacion, presupuesto.id_tecnico ",
    
	"FROM presupuesto ",
    
	"LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad ",
	"LEFT JOIN entidad lugar ON lugar.id_entidad = presupuesto.id_lugarCirugia ",
	"LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1 ",
	"LEFT JOIN tecnico ON tecnico.id_tecnico = presupuesto.id_tecnico ",
	"LEFT JOIN prestacion ON prestacion.id_prestacion = presupuesto.id_prestacion ",
	"LEFT JOIN nivelesCertificado ON nivelesCertificado.id_presupuesto = presupuesto.id_presupuesto ",
    
	"WHERE presupuesto.estado = 'Z' AND  ",
	"IF(? IS NULL, true, presupuesto.fechaCirugia >= ?) AND ",
	"IF(? IS NULL, true, presupuesto.fechaCirugia <= ?) AND ",
	"IF(? = 0, true, presupuesto.id_zona = ?) AND ",
	"IF(? = 0, true, presupuesto.id_prestacion = ?) AND ",
	"IF(? = 0, true, presupuesto.id_tecnico = ?) AND ",
	"presupuesto.id_empresa = ? ",
    
	"HAVING IF(? = 0, true, IF(? = 1, (alternativas IN (", calificaciones, ") OR instrumental IN (", calificaciones, ") OR asistencia IN (", calificaciones, ") OR puntualidad IN (", calificaciones, ")), id_nivelesCertificado = 0)) ",    
    
	"ORDER BY presupuesto.fechaCirugia DESC");
    
PREPARE stmt FROM @s;

execute stmt using @d, @d, @h, @h, @id_z, @id_z, @id_p, @id_p, @id_t, @id_t, @id_e, @m, @m;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaPresuFactura` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaPresuFactura`(IN id_zona INT,
                                                                id_empresa INT)
BEGIN

SELECT presupuesto.id_presupuesto, presupuesto.fechaCirugia,
        ifnull(entidad.nombre, '') as lugarCirugia, ifnull(profesional.nombre, '') as profesional, presupuesto.paciente, 
        ifnull(formaPago.nombre, '') as formaPago, presupuesto.total, 
        
        SUM(IF(factura.dc = 'D', 
                IFNULL(factura.neto + factura.percIIBB + factura.iva, 0), 
                IFNULL(-factura.neto - factura.percIIBB - factura.iva, 0))) as facturado,
                
		ifnull(prestacion.nombre, '') as prestacion, IFNULL(tecnico.nombre, '') as tecnico,
        
        presupuesto.observaciones
                
FROM presupuesto

LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_lugarCirugia
LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
LEFT JOIN formaPago ON formaPago.id_formaPago = presupuesto.id_formaPago
LEFT JOIN factura ON factura.id_presupuesto = presupuesto.id_presupuesto AND factura.id_empresa = id_empresa
LEFT JOIN prestacion ON prestacion.id_prestacion = presupuesto.id_prestacion
LEFT JOIN tecnico ON tecnico.id_tecnico = presupuesto.id_tecnico

WHERE presupuesto.id_empresa = id_empresa AND
        IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
        presupuesto.estado  NOT IN ('R', 'N', 'P') and
        presupuesto.desactivado = 'N'
        
GROUP BY presupuesto.id_presupuesto

ORDER BY presupuesto.id_presupuesto DESC;        

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaPresuHonorarios` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaPresuHonorarios`(IN desde DATE,
                                                                    hasta DATE,
                                                                    id_prestacion INT,
                                                                    id_tecnico INT,
                                                                    id_zona INT,
                                                                    id_empresa INT)
BEGIN

SELECT presupuesto.id_presupuesto, presupuesto.fechaCirugia,
        profesional.nombre as profesional, presupuesto.paciente, prestacion.nombre as prestacion,
        IFNULL(tecnico.nombre, '') as tecnico,
        
        IF(nivelesCertificado.alternativas = 0, 'MALO', 
            IF(nivelesCertificado.alternativas = 1, 'REGULAR', 
                IF(nivelesCertificado.alternativas = 2, 'BUENO', 'EXCELENTE'))) as alternativas,
                
        IF(nivelesCertificado.instrumental = 0, 'MALO', 
            IF(nivelesCertificado.instrumental = 1, 'REGULAR', 
                IF(nivelesCertificado.instrumental = 2, 'BUENO', 'EXCELENTE'))) as instrumental,
                
        IF(nivelesCertificado.asistencia = 0, 'MALO', 
            IF(nivelesCertificado.asistencia = 1, 'REGULAR', 
                IF(nivelesCertificado.asistencia = 2, 'BUENO', 'EXCELENTE'))) as asistencia,
                
        IF(nivelesCertificado.puntualidad = 0, 'MALO', 
            IF(nivelesCertificado.puntualidad = 1, 'REGULAR', 
                IF(nivelesCertificado.puntualidad = 2, 'BUENO', 'EXCELENTE'))) as puntualidad,
                
        prestacion.importe as importePrestacion, presupuesto.id_tecnico
                
FROM presupuesto

LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
LEFT JOIN prestacion ON prestacion.id_prestacion = presupuesto.id_prestacion
LEFT JOIN tecnico ON tecnico.id_tecnico = presupuesto.id_tecnico
LEFT JOIN nivelesCertificado ON nivelesCertificado.id_presupuesto = presupuesto.id_presupuesto

WHERE IF(desde IS NULL, true, presupuesto.fechaCirugia >= desde) AND
        IF(hasta IS NULL, true, presupuesto.fechaCirugia <= hasta) AND
        IF(id_prestacion = 0, true, presupuesto.id_prestacion = id_prestacion) AND
        IF(id_tecnico = 0, true, presupuesto.id_tecnico = id_tecnico) AND
        IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
        presupuesto.id_presupuesto IN (SELECT nivelesCertificado.id_presupuesto
                                        FROM nivelesCertificado) AND
        presupuesto.id_presupuesto NOT IN (SELECT mayorTecnico.id_presupuesto
                                            FROM mayorTecnico
                                            WHERE mayorTecnico.id_empresa = id_empresa)
                                            
ORDER BY presupuesto.fechaCirugia DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaPresuLiquidacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaPresuLiquidacion`(IN id_zona INT,
                                                                    id_profesional INT,
                                                                    id_empresa INT)
BEGIN

SELECT profesional.nombre as profesional, mayorprofesional.fecha,
        mayorprofesional.id_presupuesto, mayorprofesional.importe, IFNULL(presupuesto.paciente, '') as paciente,
        IFNULL(entidad.nombre, '') as entidad, IFNULL(formaPago.nombre, '') as formaPago, mayorprofesional.observaciones,
        mayorprofesional.id_mayorProfesional, profesional.dni
        
FROM mayorprofesional

LEFT JOIN profesional ON profesional.id_profesional = mayorprofesional.id_profesional
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = mayorprofesional.id_presupuesto
LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad
LEFT JOIN formapago ON formapago.id_formapago = presupuesto.id_formapago

WHERE mayorprofesional.id_profesional IN (SELECT profesional.id_profesional
                                            FROM profesional
                                            WHERE IF(id_zona = 0, true, profesional.id_zona = id_zona)) AND
    IF(id_profesional = 0, true, mayorprofesional.id_profesional = id_profesional) AND
    mayorprofesional.id_empresa = id_empresa AND
    mayorprofesional.preliquidacion > 0 AND
    mayorprofesional.liquidacion = 0
    
ORDER BY profesional.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaPresuPreliquidacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaPresuPreliquidacion`(IN id_profesional INT,
                                                                    id_empresa INT)
BEGIN

(SELECT presupuesto.fechaCirugia, presupuesto.id_presupuesto,
    presupuesto.paciente, entidad.nombre as entidad, 
    IFNULL(traeDebePreliquidacion(presupuesto.id_presupuesto,id_profesional), 0.00) as debe,
    IFNULL(traeHaberPreliquidacion(presupuesto.id_presupuesto,id_profesional), 0.00) as haber, 
    IFNULL(traePendiente(presupuesto.id_presupuesto,id_profesional), 0.00) as pendiente, 
    (IFNULL(traeHaberPreliquidacion(presupuesto.id_presupuesto,id_profesional), 0.00) - 
        IFNULL(traeDebePreliquidacion(presupuesto.id_presupuesto,id_profesional), 0.00) - 
            IFNULL(traePendiente(presupuesto.id_presupuesto,id_profesional), 0.00)) as disponible,
    prestacion.nombre as prestacion, IFNULL(tecnico.nombre, '') as tecnico
    
FROM presupuesto

LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad
LEFT JOIN prestacion ON prestacion.id_prestacion = presupuesto.id_prestacion
LEFT JOIN tecnico ON tecnico.id_tecnico = presupuesto.id_tecnico

WHERE presupuesto.id_presupuesto IN (SELECT mayorprofesional.id_presupuesto
                                        FROM mayorprofesional
                                        WHERE mayorprofesional.id_profesional = id_profesional AND
                                            mayorprofesional.liquidacion <= 0 AND
                                            mayorprofesional.preliquidacion = 0 AND
                                            mayorprofesional.id_empresa = id_empresa)
                                            
HAVING disponible > 0)

UNION

(SELECT mayorprofesional.fecha as fechaCirugia, mayorprofesional.id_presupuesto,
		"" as paciente, "" as entidad,
    SUM(IF(mayorprofesional.dc = "D", mayorprofesional.importe, -mayorprofesional.importe)) as debe,
    0.00 as haber, 
    0.00 as pendiente, 
    0.00 as disponible,
    "" as prestacion, "" as tecnico 
    
FROM mayorprofesional

WHERE mayorprofesional.id_presupuesto = 0 AND
		mayorprofesional.id_profesional = id_profesional AND
		mayorprofesional.liquidacion <> 0 AND
        mayorprofesional.id_empresa = id_empresa
        
GROUP BY mayorprofesional.id_presupuesto
HAVING debe <> 0);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaProducto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaProducto`(IN id_clasiProducto INT,
                                                            id_empresa INT,
                                                            soloHabilitados BOOL)
BEGIN

/*
SELECT producto.id_producto, producto.codigo, producto.gtin, producto.nombre, producto.costo, producto.stockMinimo,
        producto.pm, producto.habilita, CONCAT(codconsumo.nombre, ' (', codconsumo.codigo, ')') as codConsumo,
        moneda.simbolo as moneda, clasiproducto.nombre as clasiProducto, proveedor.nombre as proveedor,
        costoPesos(producto.id_producto) as costoPesos, producto.id_codConsumo
*/

SELECT producto.*, CONCAT(codconsumo.nombre, ' (', codconsumo.codigo, ')') as codConsumo, moneda.simbolo as moneda, clasiproducto.nombre as clasiProducto, proveedor.nombre as proveedor,
        costoPesos(producto.id_producto) as costoPesos
                
FROM producto

LEFT JOIN codconsumo ON codconsumo.id_codConsumo = producto.id_codConsumo
LEFT JOIN moneda ON moneda.id_moneda = producto.id_moneda
LEFT JOIN clasiproducto ON clasiproducto.id_clasiProducto = producto.id_clasiProducto
LEFT JOIN proveedor ON proveedor.id_proveedor = producto.id_proveedor

WHERE IF(id_clasiProducto > 0, producto.id_clasiProducto = id_clasiProducto, true) AND
        producto.id_empresa = id_empresa AND
        producto.habilita <> IF(soloHabilitados, 'N', 'X')
        
ORDER BY producto.codigo;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaProductoDesc` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaProductoDesc`(IN id_clasiProducto INT,
                                                            id_empresa INT,
                                                            soloHabilitados BOOL)
BEGIN

SELECT producto.id_producto, producto.codigo, producto.gtin, producto.nombre, producto.costo, producto.stockMinimo,
        producto.pm, producto.habilita, CONCAT(codconsumo.nombre, ' (', codconsumo.codigo, ')') as codConsumo,
        moneda.simbolo as moneda, clasiproducto.nombre as clasiProducto, proveedor.nombre as proveedor,
        costoPesos(producto.id_producto) as costoPesos
        
FROM producto

LEFT JOIN codconsumo ON codconsumo.id_codConsumo = producto.id_codConsumo
LEFT JOIN moneda ON moneda.id_moneda = producto.id_moneda
LEFT JOIN clasiproducto ON clasiproducto.id_clasiProducto = producto.id_clasiProducto
LEFT JOIN proveedor ON proveedor.id_proveedor = producto.id_proveedor

WHERE IF(id_clasiProducto > 0, producto.id_clasiProducto = id_clasiProducto, true) AND
        producto.id_empresa = id_empresa AND
        producto.habilita <> IF(soloHabilitados, 'N', 'X')
        
ORDER BY producto.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaProductoFact` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaProductoFact`(IN soloHabilitados BOOLEAN,
																		ordena INT)
BEGIN

SELECT productofact.id_productoFact, productofact.codigo, productofact.nombre, productofact.id_especialidad,
    productofact.id_subespecialidad, productofact.habilita, IFNULL(especialidad.nombre, '') as especialidad,
    IFNULL(subespecialidad.nombre, '') as subespecialidad, productofact.id_forecastGrupo, IFNULL(forecastGrupo.nombre, '') as forecastGrupo
    
FROM productofact

LEFT JOIN especialidad ON especialidad.id_especialidad = productofact.id_especialidad
LEFT JOIN subespecialidad ON subespecialidad.id_subespecialidad = productofact.id_subespecialidad
LEFT JOIN forecastGrupo ON forecastGrupo.id_forecastGrupo = productofact.id_forecastGrupo

WHERE IF(soloHabilitados, productofact.habilita = 'S', true)

ORDER BY IF(ordena = 1, productofact.codigo, productofact.nombre);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaProfesional` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaProfesional`(IN id_zona INT,
                                                        id_empresa INT,
                                                        soloHabilitados BOOL)
BEGIN

SELECT profesional.id_profesional, profesional.nombre, profesional.direccion, profesional.id_provincia,profesional.id_departamento, 
        profesional.id_localidad, profesional.codPostal, profesional.fechaNac, profesional.dni, profesional.matricula, 
        profesional.contacto, profesional.perfil, profesional.email, profesional.telParticular, profesional.telMovil, 
        profesional.telOtros, profesional.telConsultorio, profesional.secretaria, profesional.dirConsultorio, profesional.id_especialidad, 
        profesional.id_subespecialidad, profesional.id_zona, profesional.id_vendedor, profesional.id_entidad, profesional.observaciones, 
        profesional.habilita, 
        
        provincia.nombre as provincia, departamento.nombre as departamento, localidad.nombre as localidad, 
        especialidad.nombre as especialidad, subespecialidad.nombre as subespecialidad, 
        zona.nombre as zona, vendedor.nombre as vendedor, entidad.nombre as entidad
        
FROM profesional

LEFT JOIN provincia ON provincia.id_provincia = profesional.id_provincia
LEFT JOIN departamento ON departamento.id_departamento = profesional.id_departamento
LEFT JOIN localidad ON localidad.id_localidad = profesional.id_localidad
LEFT JOIN especialidad ON especialidad.id_especialidad = profesional.id_especialidad
LEFT JOIN subespecialidad ON subespecialidad.id_subespecialidad = profesional.id_subespecialidad
LEFT JOIN zona ON zona.id_zona = profesional.id_zona
LEFT JOIN vendedor ON vendedor.id_vendedor = profesional.id_vendedor
LEFT JOIN entidad ON entidad.id_entidad = profesional.id_entidad

WHERE IF(id_zona > 0, profesional.id_zona = id_zona, true) AND
    profesional.id_empresa = id_empresa AND
    profesional.habilita <> IF(soloHabilitados, 'N', 'X')
    
ORDER BY profesional.nombre;
        
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaProfesionalMayor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaProfesionalMayor`(IN id_especialidad INT,
                                                                    id_zona INT,
                                                                    id_empresa INT)
BEGIN

SELECT profesional.nombre, IFNULL(especialidad.nombre, '') as especialidad, IFNULL(zona.nombre, '') as zona,
    profesional.id_profesional
    
FROM profesional

LEFT JOIN especialidad ON especialidad.id_especialidad = profesional.id_especialidad
LEFT JOIN zona ON zona.id_zona = profesional.id_zona

WHERE profesional.habilita = 'S' AND
    IF(id_entidad = 0, true, profesional.id_entidad = id_entidad) AND
    IF(id_zona = 0, true, profesional.id_zona = id_zona) AND
    profesional.id_empresa = id_empresa
    
ORDER BY profesional.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaProveedor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaProveedor`(IN id_empresa INT,
                                                        soloHabilitados BOOL)
BEGIN

SELECT proveedor.id_proveedor, proveedor.nombre, proveedor.direccion, proveedor.codPostal, 
        proveedor.telefono1, proveedor.telefono2, proveedor.email, proveedor.cuit, 
        proveedor.gerente, proveedor.cuentaBanco, proveedor.observaciones, proveedor.habilita, 
        localidad.nombre as localidad, departamento.nombre as departamento, provincia.nombre as provincia,
        formapago.nombre as formaPago, 
        encargadopago.nombre as pagoNombre, encargadopago.telefono as pagoTelefono,
        encargadopago.email as pagoEmail, atencioncliente.nombre as clienteNombre, 
        atencioncliente.telefono as clienteTelefono, atencioncliente.email as clienteEmail, 
        encargadodeposito.nombre as depositoNombre, encargadodeposito.telefono as depositoTelefono,
        encargadodeposito.email as depositoEmail, proveedor.gln
        
FROM proveedor

LEFT JOIN provincia ON provincia.id_provincia = proveedor.id_provincia
LEFT JOIN departamento ON departamento.id_departamento = proveedor.id_departamento
LEFT JOIN localidad ON localidad.id_localidad = proveedor.id_localidad
LEFT JOIN formapago ON formapago.id_formapago = proveedor.id_formapago
LEFT JOIN encargadopago ON encargadopago.id_proveedor = proveedor.id_proveedor
LEFT JOIN atencioncliente ON atencioncliente.id_proveedor = proveedor.id_proveedor
LEFT JOIN encargadodeposito ON encargadodeposito.id_proveedor = proveedor.id_proveedor

WHERE proveedor.id_empresa = id_empresa AND
    proveedor.habilita <> IF(soloHabilitados, 'N', 'X')
    
ORDER BY proveedor.nombre;
        
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaProvincia` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaProvincia`()
BEGIN

SELECT provincia.id_provincia, provincia.nombre

FROM provincia

ORDER BY nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaRanking` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaRanking`(IN desde DATE,
                                                            hasta DATE,
                                                            por INT,
                                                            id_profesional INT,
                                                            id_productoFact INT,
                                                            aprobado VARCHAR(1),
                                                            facturado BOOL,
                                                            id_zona INT,
                                                            id_vendedor INT,
                                                            id_empresa INT)
BEGIN

SELECT presupuesto.id_presupuesto as turno, DATE_FORMAT(presupuesto.fecha, '%d/%m/%Y') as fecha,
    IFNULL(productoFact.codigo, '') as codigo, IFNULL(productoFact.nombre, 'PRODUCTO INEXISTENTE') as producto, 
    IFNULL(profesional.nombre, 'PROFESIONAL INEXISTENTE') as profesional,
    zona.nombre as zona, presupuestoProd.cantidad, presupuestoProd.pxUnit,
    IF(presupuesto.estado IN ('P'), 'NO', 'SI') as aprobado, 
    IFNULL(montoFacturado(presupuesto.id_presupuesto), 0.00) as montoFacturado
    
FROM presupuestoprod

LEFT JOIN presupuesto ON presupuesto.id_presupuesto = presupuestoprod.id_presupuesto
LEFT JOIN productoFact ON productoFact.id_productoFact = presupuestoprod.id_productoFact
LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
LEFT JOIN zona ON zona.id_zona = presupuesto.id_zona

WHERE IF(desde IS NULL, true, IF(facturado, presupuestoProd.id_presupuesto IN (SELECT factura.id_presupuesto
                                                                                    FROM factura
                                                                                    WHERE factura.fecha >= desde),
                                            presupuestoProd.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                                                                    FROM presupuesto
                                                                                    WHERE presupuesto.fecha >= desde))) AND
    IF(hasta IS NULL, true, IF(facturado, presupuestoProd.id_presupuesto IN (SELECT factura.id_presupuesto
                                                                                    FROM factura
                                                                                    WHERE factura.fecha <= hasta),
                                            presupuestoProd.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                                                                    FROM presupuesto
                                                                                    WHERE presupuesto.fecha <= hasta))) AND
    
    presupuestoprod.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                         FROM presupuesto
                                         WHERE IF(id_profesional = 0, true, presupuesto.id_profesional1 = id_profesional) AND
                                            IF(aprobado = 'S', presupuesto.estado NOT IN ('R', 'N', 'P'),
                                                               IF(aprobado = 'N', presupuesto.estado = 'P', 
                                                                                    presupuesto.estado NOT IN ('R', 'N'))) AND
                                            IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
                                            IF(id_vendedor = 0, true, presupuesto.id_vendedor = id_vendedor) AND
                                            presupuesto.id_empresa = id_empresa) AND
                                            
    IF(facturado, presupuestoprod.id_presupuesto IN (SELECT factura.id_presupuesto
                                                        FROM factura
                                                        WHERE factura.id_empresa = id_empresa), true) AND
                                                                        
    IF(id_productoFact = 0, true, presupuestoprod.id_productoFact = id_productoFact)
    
ORDER BY IF(por = 0, profesional.nombre, productoFact.nombre), IF(por = 1, profesional.nombre, productoFact.nombre);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaRankingCirugias` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaRankingCirugias`(IN desde DATE,
												hasta DATE,
                                                por INT,
                                                id_productoFact INT,
                                                id_profesional INT,
                                                id_zona INT,
                                                id_empresa INT)
BEGIN

SELECT IFNULL(productoFact.codigo, '') as codigo, IFNULL(productoFact.nombre, '') as producto, 
		IFNULL(profesional.nombre, '') as profesional, SUM(presupuestoProd.cantidad) as cantidad
        
FROM presupuestoProd

LEFT JOIN productoFact ON productoFact.id_productoFact = presupuestoProd.id_productoFact
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = presupuestoProd.id_presupuesto
LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1

WHERE presupuestoProd.id_presupuesto IN (SELECT presupuesto.id_presupuesto
											FROM presupuesto
                                            WHERE presupuesto.estado = 'Z' AND
													presupuesto.id_empresa = id_empresa AND
													IF(desde IS NULL, true, presupuesto.fechaCirugia >= desde) AND
													IF(hasta IS NULL, true, presupuesto.fechaCirugia <= hasta) AND
													IF(id_profesional = 0, true, presupuesto.id_profesional1 = id_profesional) AND
                                                    IF(id_zona = 0, true, presupuesto.id_zona = id_zona)) AND
	
    IF(id_productoFact = 0, true, presupuestoProd.id_productoFact = id_productoFact)
    
GROUP BY productoFact.codigo, profesional.nombre

ORDER BY
CASE WHEN por = 1 THEN codigo END, 
CASE WHEN por = 2 THEN profesional END,
			cantidad DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaRankingVentas` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaRankingVentas`(IN desde DATE,
																			hasta DATE,
                                                                            orden INT,
																			id_profesional INT,
																			id_productoFact INT,
                                                                            visualiza INT,
																			aprobado VARCHAR(1),
																			facturado BOOL,
																			id_zona INT,
																			id_vendedor INT,
                                                                            id_proveedor INT,
																			id_empresa INT)
BEGIN

SELECT IFNULL(productoFact.nombre, 'PRODUCTO INEXISTENTE') as producto, 
    IFNULL(profesional.nombre, 'PROFESIONAL INEXISTENTE') as profesional,
    zona.nombre as zona, SUM(presupuestoProd.cantidad * IF(visualiza = 0, 1, presupuestoProd.pxUnit)) as totalProducto, 
    IF(orden = 1, 0, IFNULL(montoFacturado(presupuesto.id_presupuesto), 0.00)) as montoFacturado
    
FROM presupuestoprod

LEFT JOIN presupuesto ON presupuesto.id_presupuesto = presupuestoprod.id_presupuesto
LEFT JOIN productoFact ON productoFact.id_productoFact = presupuestoprod.id_productoFact
LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
LEFT JOIN zona ON zona.id_zona = presupuesto.id_zona

WHERE IF(desde IS NULL, true, IF(facturado, presupuestoProd.id_presupuesto IN (SELECT factura.id_presupuesto
                                                                                    FROM factura
                                                                                    WHERE factura.fecha >= desde AND
																							factura.id_tipoComp NOT IN (4,16,17,18)),
                                            presupuestoProd.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                                                                    FROM presupuesto
                                                                                    WHERE presupuesto.fecha >= desde))) AND
    IF(hasta IS NULL, true, IF(facturado, presupuestoProd.id_presupuesto IN (SELECT factura.id_presupuesto
                                                                                    FROM factura
                                                                                    WHERE factura.fecha <= hasta AND
																							factura.id_tipoComp NOT IN (4,16,17,18)),
                                            presupuestoProd.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                                                                    FROM presupuesto
                                                                                    WHERE presupuesto.fecha <= hasta))) AND
    
    presupuestoprod.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                         FROM presupuesto
                                         WHERE IF(id_profesional = 0, true, presupuesto.id_profesional1 = id_profesional) AND
                                            IF(aprobado = 'S', presupuesto.estado NOT IN ('R', 'N', 'P'),
                                                               IF(aprobado = 'N', presupuesto.estado = 'P', 
                                                                                    presupuesto.estado NOT IN ('R', 'N'))) AND
                                            IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
                                            IF(id_vendedor = 0, true, presupuesto.id_vendedor = id_vendedor) AND
                                            presupuesto.id_empresa = id_empresa) AND
                                            
    IF(facturado, presupuestoprod.id_presupuesto IN (SELECT factura.id_presupuesto
                                                        FROM factura
                                                        WHERE factura.id_empresa = id_empresa AND
																factura.id_tipoComp NOT IN (4,16,17,18)), true) AND
                                                                        
    IF(id_productoFact = 0, true, presupuestoprod.id_productoFact = id_productoFact) AND
    IF(id_proveedor = 0, true, presupuestoprod.id_proveedor = id_proveedor)
    
GROUP BY 
CASE WHEN orden = 0 THEN profesional.nombre END,
CASE WHEN orden = 0 THEN productoFact.nombre END,
CASE WHEN orden = 1 THEN productoFact.nombre END,
CASE WHEN orden = 1 THEN profesional.nombre END 

ORDER BY IF(orden = 0, profesional.nombre, productoFact.nombre), IF(orden = 1, profesional.nombre, productoFact.nombre);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaRankingVentasA` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaRankingVentasA`(IN desde DATE,
																			hasta DATE,
                                                                            orden INT,
																			id_profesional INT,
																			id_productoFact INT,
                                                                            visualiza INT,
																			aprobado VARCHAR(1),
																			facturado BOOL,
																			id_zona INT,
																			id_vendedor INT,
                                                                            id_proveedor INT,
																			id_empresa INT)
BEGIN

SELECT IFNULL(productoFact.nombre, 'PRODUCTO INEXISTENTE') as producto,  
    IFNULL(profesional.nombre, 'PROFESIONAL INEXISTENTE') as profesional,
    zona.nombre as zona, SUM(presupuestoProd.cantidad * IF(visualiza = 0, 1, presupuestoProd.pxUnit)) as totalProducto, 
    SUM(Distinct IF(orden = 1, 0, IFNULL(montoFacturado(presupuesto.id_presupuesto), 0.00))) as montoFacturado
    
FROM presupuestoprod

LEFT JOIN presupuesto ON presupuesto.id_presupuesto = presupuestoprod.id_presupuesto
LEFT JOIN productoFact ON productoFact.id_productoFact = presupuestoprod.id_productoFact
LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
LEFT JOIN zona ON zona.id_zona = presupuesto.id_zona

WHERE IF(desde IS NULL, true, IF(facturado, presupuestoProd.id_presupuesto IN (SELECT factura.id_presupuesto
                                                                                    FROM factura
                                                                                    WHERE factura.fecha >= desde AND
																							factura.id_tipoComp NOT IN (4,16,17,18)),
                                            presupuestoProd.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                                                                    FROM presupuesto
                                                                                    WHERE presupuesto.fecha >= desde))) AND
    IF(hasta IS NULL, true, IF(facturado, presupuestoProd.id_presupuesto IN (SELECT factura.id_presupuesto
                                                                                    FROM factura
                                                                                    WHERE factura.fecha <= hasta AND
																							factura.id_tipoComp NOT IN (4,16,17,18)),
                                            presupuestoProd.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                                                                    FROM presupuesto
                                                                                    WHERE presupuesto.fecha <= hasta))) AND
    
    presupuestoprod.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                         FROM presupuesto
                                         WHERE IF(id_profesional = 0, true, presupuesto.id_profesional1 = id_profesional) AND
                                            IF(aprobado = 'S', presupuesto.estado NOT IN ('R', 'N', 'P'),
                                                               IF(aprobado = 'N', presupuesto.estado = 'P', 
                                                                                    presupuesto.estado NOT IN ('R', 'N'))) AND
                                            IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
                                            IF(id_vendedor = 0, true, presupuesto.id_vendedor = id_vendedor) AND
                                            presupuesto.id_empresa = id_empresa) AND
                                            
    IF(facturado, presupuestoprod.id_presupuesto IN (SELECT factura.id_presupuesto
                                                        FROM factura
                                                        WHERE factura.id_empresa = id_empresa AND
															factura.id_tipoComp NOT IN (4,16,17,18)), true) AND
                                                                        
    IF(id_productoFact = 0, true, presupuestoprod.id_productoFact = id_productoFact) AND
    IF(id_proveedor = 0, true, presupuestoprod.id_proveedor = id_proveedor)
    
GROUP BY 
CASE WHEN orden = 0 THEN profesional.nombre END,
CASE WHEN orden = 1 THEN productoFact.nombre END

ORDER BY totalProducto DESC; 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaRemito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaRemito`(IN desde DATE,
                                                        hasta DATE,
                                                        id_zona INT,
                                                        id_empresa INT)
BEGIN

SELECT remito.id_remito, remito.id_presupuesto, remito.fecha,
    remito.numComp, remito.observaciones, 
    remito.id_entidad, IFNULL(entidad.nombre,'') as entidad, 
    IFNULL(presupuesto.id_lugarCirugia, 0) as id_lugarCirugia, IFNULL(lugar.nombre, '') as lugarCirugia, IFNULL(lugar.direccion, '') as dirLugarCirugia,
    IFNULL(presupuesto.paciente, '') as paciente, IFNULL(presupuesto.telefono, '') as telefono, IFNULL(profesional.nombre, '') as profPresupuesto,
    remito.id_proveedor, IFNULL(proveedor.nombre,'') as proveedor, 
    remito.id_destino, IFNULL(destino.nombre,'') as profesional,
    remito.id_zonaDestino, IFNULL(zonaDestino.nombre,'') as zonaDestino,
    ifnull(zona.nombre, '') AS origen, ifnull(remito.cajas, '') as cajas
            
FROM remito

LEFT JOIN entidad ON entidad.id_entidad = remito.id_entidad
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = remito.id_presupuesto
LEFT JOIN entidad lugar ON lugar.id_entidad = presupuesto.id_lugarCirugia
LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
LEFT JOIN proveedor ON proveedor.id_proveedor = remito.id_proveedor
LEFT JOIN profesional destino ON destino.id_profesional = remito.id_destino
left join zona zonaDestino ON zonaDestino.id_zona = remito.id_zonaDestino
LEFT JOIN zona ON zona.id_zona = remito.id_zona

WHERE IF(desde IS NULL, true, remito.fecha >= desde) AND
        IF(hasta IS NULL, true, remito.fecha <= hasta) AND
        IF(id_zona = 0, true, remito.id_zona = id_zona OR remito.id_destino = id_zona) AND
        remito.id_empresa = id_empresa

ORDER BY remito.fecha DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaRemitoConsumido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaRemitoConsumido`(IN id_presupuesto INT,
                                                        id_empresa INT)
BEGIN

SELECT remito.id_remito, remito.numComp, remito.observaciones

FROM remito

WHERE remito.id_presupuesto = id_presupuesto AND
        remito.id_empresa = id_empresa;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaRemitoDetalleConConsumido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaRemitoDetalleConConsumido`(IN id_remito INT)
BEGIN

SELECT producto.codigo as codigo, producto.gtin as gtin, producto.nombre as nombre, 
	ifnull(GROUP_CONCAT(distinct (case when stockdetalle.lote='' then null else stockdetalle.lote end) separator ' / '), '') as lote, stockDetalle.serie as serie, 
	stockdetalle.pm, DATE_FORMAT(stockdetalle.vencimiento, '%d/%m/%Y') as vencimiento, 
	SUM(IF(stockdetalle.dc = 'C', stockdetalle.cantidad, 0)) as entregado, SUM(IF(remito.recibido = 'N', 0, IF(stockdetalle.dc = 'C', stockdetalle.cantidad, -stockdetalle.cantidad))) as consumido, 
    IFNULL(clasiProducto.nombre, '') as clasiProducto, ifnull(date_format(remito.fechaConsumido, '%d/%m/%Y'), '') as fechaConsumido, ifnull(remito.cajas, '') as cajas
    
FROM stockdetalle

LEFT JOIN producto ON producto.id_producto = stockdetalle.id_producto
LEFT JOIN clasiProducto ON clasiProducto.id_clasiProducto = producto.id_clasiProducto
LEFT JOIN remito ON remito.id_remito = id_remito

WHERE stockdetalle.id_remito = id_remito

GROUP BY stockdetalle.id_producto, serie;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaRemitosTurno` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaRemitosTurno`(IN id_presupuesto INT)
begin

SELECT DATE_FORMAT(remito.fecha, "%d/%m/%Y") as fecha, remito.numComp, remito.id_remito, IFNULL(remito.cajas, '') as cajas

FROM remito

WHERE remito.id_presupuesto = id_presupuesto

ORDER BY remito.fecha;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaStock` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaStock`(IN id_proveedor INT,
                                                        id_clasiProducto INT,
                                                        id_zona INT,
                                                        id_empresa INT)
BEGIN

select producto.codigo, producto.gtin, producto.nombre,
    SUM(IFNULL(IF(stockdetalle.dc = 'D', stockdetalle.cantidad, -stockdetalle.cantidad), 0)) AS existencia, 
    producto.id_producto, producto.pm 
from producto 
left join stockdetalle on stockdetalle.id_producto = producto.id_producto and
							IF(id_zona = 0, true, stockdetalle.id_zona = id_zona)
where producto.habilita = 'S'AND 
		IF(id_clasiProducto = 0, true, producto.id_clasiProducto = id_clasiProducto) AND
		IF(id_proveedor = 0, true, producto.id_proveedor = id_proveedor) AND
		producto.id_empresa = id_empresa		
group by producto.id_producto
order by codigo; 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaSubespecialidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaSubespecialidad`(IN id_especialidad INT, 
                                                                soloHabilitados BOOL)
BEGIN

SELECT subespecialidad.id_subespecialidad, subespecialidad.id_especialidad, subespecialidad.nombre,
    subespecialidad.habilita, especialidad.nombre AS especialidad
    
FROM subespecialidad

LEFT JOIN especialidad ON especialidad.id_especialidad = subespecialidad.id_especialidad

WHERE IF(id_especialidad > 0, subespecialidad.id_especialidad = id_especialidad, true) AND
        subespecialidad.habilita <> IF(soloHabilitados, 'N', 'X')
    
ORDER BY subespecialidad.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaTecnico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaTecnico`(IN id_empresa INT,
                                                            soloHabilitados BOOL)
BEGIN

SELECT tecnico.id_tecnico, tecnico.nombre, tecnico.habilita

FROM tecnico

WHERE tecnico.id_empresa = id_empresa AND
        tecnico.habilita <> IF(soloHabilitados, 'N', 'X')

ORDER BY tecnico.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaTipoComp` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaTipoComp`()
BEGIN

SELECT tipocomp.id_tipoComp, tipocomp.codigo, tipocomp.denominacion, tipocomp.abreviatura

FROM tipocomp
    
WHERE tipocomp.habilita = 'S'
        
ORDER BY tipocomp.denominacion;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaTipoOperacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaTipoOperacion`()
BEGIN

SELECT tipooperacion.id_tipooperacion, tipooperacion.nombre

FROM tipooperacion

WHERE tipooperacion.habilita = 'S'

ORDER BY tipooperacion.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaTrazabilidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaTrazabilidad`(IN desde DATE,
											hasta DATE,
                                            id_zona INT,
                                            id_proveedor INT,
                                            id_codConsumo INT,
                                            lote VARCHAR(45),
                                            serie VARCHAR(45),
                                            id_empresa INT)
BEGIN

SELECT presupuesto.id_presupuesto as id_presupuesto, presupuesto.fechaCirugia as fechaCx, producto.codigo as producto, 
	remito.numComp as remito, ifnull(GROUP_CONCAT(distinct (case when stockdetalle.lote='' then null else stockdetalle.lote end) separator ' / '), '') as lote, 
    stockdetalle.serie as serie, SUM(IF(stockdetalle.dc = 'C', stockdetalle.cantidad, -stockdetalle.cantidad)) as cantidad, profesional.nombre as profesional, lugar.nombre as lugar,
    entidad.nombre as entidad, presupuesto.paciente as paciente, producto.nombre as nombreProd,
    prestacion.nombre as prestacion, IFNULL(tecnico.nombre, '') as tecnico, presupuesto.observaciones as observaciones
    
FROM stockdetalle

LEFT JOIN producto ON producto.id_producto = stockdetalle.id_producto
LEFT JOIN remito ON remito.id_remito = stockdetalle.id_remito
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = remito.id_presupuesto
LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad
LEFT JOIN entidad lugar On lugar.id_entidad = presupuesto.id_lugarCirugia
LEFT JOIN prestacion ON prestacion.id_prestacion = presupuesto.id_prestacion
LEFT JOIN tecnico ON tecnico.id_tecnico = presupuesto.id_tecnico

WHERE stockdetalle.id_remito IN (SELECT remito.id_remito
									FROM remito
                                    WHERE IF(desde IS NULL, true, remito.fecha >= desde) AND
										remito.id_empresa = id_empresa AND
                                        remito.id_presupuesto IN (SELECT presupuesto.id_presupuesto
																	FROM presupuesto
                                                                    WHERE presupuesto.estado = 'Z') AND
																		IF(desde IS NULL, true, presupuesto.fechaCirugia >= desde) AND
																		IF(hasta IS NULL, true, presupuesto.fechaCirugia <= hasta) AND
																		IF(id_zona = 0, true, remito.id_zona = id_zona)) AND
                                        
	stockdetalle.id_producto IN (SELECT producto.id_producto
									FROM producto
                                    WHERE IF(id_proveedor = 0, true, producto.id_proveedor = id_proveedor) and
										IF(id_codConsumo = 0, true, producto.id_codConsumo = id_codConsumo) and
                                        producto.id_empresa = id_empresa) and
                                        
	IF(lote = "", true, stockdetalle.lote = lote) AND
    IF(serie = "", true, stockdetalle.serie = serie)
    
GROUP BY stockdetalle.id_remito, presupuesto.id_presupuesto, stockdetalle.id_producto

HAVING cantidad > 0
    
ORDER BY presupuesto.id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaTrazabilidadConsumo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaTrazabilidadConsumo`(IN desde DATE,
												hasta DATE,
												id_zona INT,
												id_proveedor INT,
												id_codConsumo INT,
												lote VARCHAR(45),
                                                serie VARCHAR(45),
												id_empresa INT)
BEGIN

SELECT producto.codigo as producto, producto.gtin, producto.nombre as nombreProd,
	SUM(IF(stockdetalle.dc = 'C', stockdetalle.cantidad, -stockdetalle.cantidad)) as cantidad, 
    round(avg(stockdetalle.costoPesos * stockDetalle.cotizacion), 2) as costoProm,
    ifnull(proveedor.nombre, '') as proveedor
    
FROM stockdetalle

LEFT JOIN producto ON producto.id_producto = stockdetalle.id_producto
LEFT JOIN proveedor on proveedor.id_proveedor = producto.id_proveedor
LEFT JOIN remito ON remito.id_remito = stockdetalle.id_remito
LEFT JOIN presupuesto ON presupuesto.id_presupuesto = remito.id_presupuesto

WHERE stockdetalle.id_remito IN (SELECT remito.id_remito
									FROM remito
                                    WHERE IF(desde IS NULL, true, remito.fecha >= desde) AND
										remito.id_empresa = id_empresa AND
                                        remito.id_presupuesto IN (SELECT presupuesto.id_presupuesto
																	FROM presupuesto
                                                                    WHERE presupuesto.estado = 'Z') AND
																		IF(desde IS NULL, true, presupuesto.fechaCirugia >= desde) AND
																		IF(hasta IS NULL, true, presupuesto.fechaCirugia <= hasta) AND
																		IF(id_zona = 0, true, remito.id_zona = id_zona)) AND
                                        
	stockdetalle.id_producto IN (SELECT producto.id_producto
									FROM producto
                                    WHERE IF(id_proveedor = 0, true, producto.id_proveedor = id_proveedor) and
										IF(id_codConsumo = 0, true, producto.id_codConsumo = id_codConsumo) and
                                        producto.id_empresa = id_empresa) and
                                        
	IF(lote = "", true, stockdetalle.lote = lote) AND
    IF(serie = "", true, stockdetalle.serie = serie)
    
    
GROUP BY stockdetalle.id_producto

HAVING cantidad > 0
    
ORDER BY proveedor, producto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaUltimaRevision` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaUltimaRevision`()
BEGIN

SELECT id_revisionSoftware as revision, critica, link, detalleRevision
    FROM revisionsoftware
    WHERE lanzada = 'S'
    ORDER BY id_revisionSoftware DESC
    LIMIT 1;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaUsuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaUsuario`(IN id_empresa INT,
                                                        soloSistema BOOL,
                                                        soloHabilitados BOOL)
BEGIN

SELECT usuario.id_usuario, usuario.nombre, usuario.documento, usuario.cuil, usuario.fechaNac,
    usuario.direccion, usuario.codPostal, usuario.id_provincia, usuario.id_departamento, usuario.id_localidad,
    usuario.fechaIngreso,usuario.fechaEgreso,usuario.usaSistema,usuario.contraseña, usuario.email, 
    usuario.habilita, usuario.id_zona, usuario.id_zonaSistema,
    
    provincia.nombre as provincia, departamento.nombre as departamento, localidad.nombre as localidad

FROM usuario

LEFT JOIN provincia ON provincia.id_provincia = usuario.id_provincia
LEFT JOIN departamento ON departamento.id_departamento = usuario.id_departamento
LEFT JOIN localidad ON localidad.id_localidad = usuario.id_localidad

WHERE usuario.id_empresa = id_empresa AND
    IF(soloSistema, usuario.usaSistema = 'S', true) AND
    IF(soloHabilitados, usuario.habilita = 'S', true)
    
ORDER BY usuario.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaVendedor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaVendedor`(IN id_empresa INT,
                                                        id_zona INT,
                                                        soloHabilitados BOOL)
BEGIN

SELECT vendedor.id_vendedor, vendedor.nombre, vendedor.id_zona, vendedor.id_especialidad, vendedor.comision, 
    vendedor.beneficio, vendedor.habilita, zona.nombre AS zona, especialidad.nombre AS especialidad
    
FROM vendedor

LEFT JOIN zona ON zona.id_zona = vendedor.id_zona
LEFT JOIN especialidad ON especialidad.id_especialidad = vendedor.id_especialidad

WHERE vendedor.id_empresa = id_empresa AND
    IF(id_zona > 0, vendedor.id_zona = id_zona, true) AND
    vendedor.habilita <> IF(soloHabilitados, 'N', 'X')
    
ORDER BY vendedor.nombre;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaVendedorComision` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaVendedorComision`(IN id_zona INT,
																			id_especialidad INT,
                                                                            id_empresa INT)
BEGIN

SELECT vendedor.nombre, especialidad.nombre as especialidad, 
    IFNULL(zona.nombre, '') as zona, vendedor.beneficio, vendedor.id_vendedor,
    vendedor.comision, vendedor.id_especialidad, vendedor.id_zona
    
FROM vendedor

LEFT JOIN zona ON zona.id_zona = vendedor.id_zona
LEFT JOIN especialidad ON especialidad.id_especialidad = vendedor.id_especialidad

WHERE vendedor.habilita = 'S' AND 
    IF(id_zona = 0, true, vendedor.id_zona = id_zona) AND 
    IF(id_especialidad = 0, true, vendedor.id_especialidad = id_especialidad) AND
    vendedor.id_empresa = id_empresa

ORDER BY zona.nombre, vendedor.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaZona` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `consultaZona`(IN soloHabilitados BOOL)
BEGIN

SELECT zona.id_zona, zona.nombre, zona.habilita

FROM zona

WHERE zona.habilita <> IF(soloHabilitados, 'N', 'X')

ORDER BY zona.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `controlObrasSociales` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `controlObrasSociales`(IN desde DATE,
                                                                hasta DATE,
                                                                id_zona INT,
                                                                id_empresa INT)
BEGIN

select entidad.id_entidad, entidad.nombre as entidad, ifnull(zona.nombre, '') as zona,
	count(presupuesto.id_presupuesto) as cantPresuEmitidos, sum(ifnull(presupuesto.total, 0.00)) as montoPresuEmitidos, 
    sum(if(presupuesto.estado NOT IN ('R', 'N', 'P'), 1, 0)) as cantPresuAprobados, sum(if(presupuesto.estado NOT IN ('R', 'N', 'P'), presupuesto.total, 0.00)) as montoPresuAprobados,
    SUM(ifnull(IF(factura.dc = 'D', 1, (-1)) * neto + percIIBB + iva, 0)) as montoFactTotal, count(DISTINCT factura.id_presupuesto) as cantPresuFacturados

from entidad 

left join presupuesto on entidad.id_entidad = presupuesto.id_entidad AND 
						IF(desde is null, true, presupuesto.fecha >= desde) AND 
                        IF(hasta is null, true, presupuesto.fecha <= hasta) AND
                        if(id_zona = 0, true, presupuesto.id_zona = id_zona)
                        
left join factura on factura.id_presupuesto = presupuesto.id_presupuesto AND 
						factura.id_tipoComp NOT IN (4,16,17,18) and 
                        IF(desde is null, true, factura.fecha >= desde) AND 
                        IF(hasta is null, true, factura.fecha <= hasta)

left join zona on zona.id_zona = presupuesto.id_zona

                        
where entidad.habilita = 'S' AND
		entidad.id_empresa = id_empresa

group by zona, entidad.id_entidad

having cantPresuEmitidos > 0

order by zona, entidad;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `creaMenu` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `creaMenu`(IN id_usuario INT,
                                                    id_empresa INT)
BEGIN

SELECT menu.posicion, menu.nombre, menu.accion 

FROM acceso 

LEFT JOIN menu ON menu.id_menu = acceso.id_menu

WHERE acceso.id_usuario = id_usuario AND 
    acceso.id_empresa = id_empresa AND
    acceso.id_menu IN (SELECT menu.id_menu
                        FROM menu
                        WHERE menu.id_empresa = id_empresa AND
                                menu.habilita = 'S')
                                
ORDER BY menu.posicion;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `cumple` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `cumple`(IN id_empresa INT(11), IN mes INT(2), IN id_zona INT(11), IN id_vendedor INT(11))
BEGIN

SELECT DATE_FORMAT(fechaNac,'%e') AS dia, profesional.nombre, profesional.email, 
    profesional.telParticular, profesional.telMovil,
    IFNULL(zona.nombre,''), IFNULL(vendedor.nombre,'')
    
    FROM profesional
    
    LEFT JOIN zona ON zona.id_zona = profesional.id_zona
    LEFT JOIN vendedor ON vendedor.id_vendedor = profesional.id_vendedor
    
    WHERE 
        profesional.habilita = 'S' AND 
        profesional.id_empresa = id_empresa AND
        DATE_FORMAT(profesional.fechaNac,'%c') = mes AND
        profesional.id_zona = id_zona AND
        profesional.id_vendedor = id_vendedor;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `cumpleNoVendedor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `cumpleNoVendedor`(IN id_empresa INT(11), IN mes INT(2), IN id_zona INT(11))
BEGIN

SELECT DATE_FORMAT(fechaNac,'%e') AS dia, profesional.nombre, profesional.email, 
    profesional.telParticular, profesional.telMovil,
    IFNULL(zona.nombre,''), IFNULL(vendedor.nombre,'')
    
    FROM profesional
    
    LEFT JOIN zona ON zona.id_zona = profesional.id_zona
    LEFT JOIN vendedor ON vendedor.id_vendedor = profesional.id_vendedor
    
    WHERE 
        profesional.habilita = 'S' AND 
        profesional.id_empresa = id_empresa AND
        DATE_FORMAT(profesional.fechaNac,'%c') = mes AND
        profesional.id_zona = id_zona;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `cumpleNoZonaNoVendedor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `cumpleNoZonaNoVendedor`(IN id_empresa int(11), IN mes int(2))
BEGIN

SELECT DATE_FORMAT(fechaNac,'%e') AS dia, profesional.nombre, profesional.email, 
    profesional.telParticular, profesional.telMovil,
    IFNULL(zona.nombre,''), IFNULL(vendedor.nombre,'')
    
    FROM profesional
    
    LEFT JOIN zona ON zona.id_zona = profesional.id_zona
    LEFT JOIN vendedor ON vendedor.id_vendedor = profesional.id_vendedor
    
    WHERE 
        profesional.habilita = 'S' AND 
        profesional.id_empresa = id_empresa AND
        DATE_FORMAT(profesional.fechaNac,'%c') = mes;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `deleteAcceso` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `deleteAcceso`(IN id_usuario INT,
                                                        id_empresa INT)
BEGIN

DELETE FROM acceso

WHERE acceso.id_usuario = id_usuario AND
        acceso.id_empresa = id_empresa;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `deleteCajaAsignada` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `deleteCajaAsignada`(IN id_presupuesto INT)
BEGIN

DELETE

FROM cajaasignada

WHERE cajaasignada.id_presupuesto = id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `deleteCajaComposicion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `deleteCajaComposicion`(IN id_cajaDeposito INT)
BEGIN

DELETE FROM cajacomposicion

WHERE cajacomposicion.id_cajaDeposito = id_cajaDeposito;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `deleteForecast` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `deleteForecast`(IN anio INT,
										id_vendedor INT,
                                        id_entidad INT,
                                        id_forecastGrupo INT,
                                        usuario VARCHAR(45),
                                        equipo VARCHAR(255))
BEGIN

DECLARE log TEXT;

DELETE FROM forecast

WHERE forecast.anio = anio AND
		forecast.id_vendedor = id_vendedor AND
        forecast.id_entidad = id_entidad AND
        forecast.id_productoFact IN (SELECT productoFact.id_productoFact
										FROM productoFact
                                        WHERE productoFact.id_forecastGrupo = id_forecastGrupo);
        
SET log = CONCAT("Actualiza datos de Forecast del año ", anio, " para id_vendedor = ", id_vendedor, ", id_entidad = ", id_entidad, ", id_forecastGrupo = ", id_forecastGrupo);

CALL logSistema(usuario, equipo, log);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `deleteForecastComposicionGrupo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `deleteForecastComposicionGrupo`(IN id_forecastGrupo INT,
														id_empresa INT)
BEGIN

DELETE FROM forecastComposicionGrupo

WHERE forecastComposicionGrupo.id_forecastGrupo = id_forecastGrupo AND
		forecastComposicionGrupo.id_empresa = id_empresa;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `deleteForecastEntidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `deleteForecastEntidad`(IN usuario VARCHAR(45),
																equipo VARCHAR(255),
                                                                id_empresa INT)
BEGIN

DECLARE log TEXT;

DELETE FROM forecastEntidad

WHERE forecastEntidad.id_empresa = id_empresa;

SET log = CONCAT("Actualiza entidades afectadas a Forecast");

CALL logSistema(usuario, equipo, log);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `deleteMayorProfesional` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `deleteMayorProfesional`(IN id_mayorProfesional INT)
BEGIN

DELETE FROM mayorProfesional

WHERE mayorProfesional.id_mayorProfesional = id_mayorProfesional;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `deletePresupuestoProd` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `deletePresupuestoProd`(IN id_presupuesto INT(11))
BEGIN

DELETE FROM `matera`.`presupuestoprod` WHERE `id_presupuesto` = id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `deleteStockDetalle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `deleteStockDetalle`(IN id_stock INT)
BEGIN

if id_stock > 0 then
DELETE FROM stockdetalle WHERE stockdetalle.id_stock = id_stock;
end if;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `desactivaPresupuesto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `desactivaPresupuesto`(IN id_presupuesto INT)
BEGIN

UPDATE presupuesto SET
	presupuesto.desactivado = 'S'
    
WHERE presupuesto.id_presupuesto = id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `desactivaVip` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `desactivaVip`(IN id_presupuesto INT)
BEGIN

UPDATE presupuesto SET
    vip = 'N'
WHERE presupuesto.id_presupuesto = id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `detalleForecast` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `detalleForecast`(IN id_vendedor INT,
										id_entidad INT,
                                        id_productoFact INT,
                                        anio INT,
                                        mes INT,
                                        tipo INT,
                                        vip Boolean)
BEGIN

SELECT * from (
select presupuesto.id_presupuesto, presupuesto.fecha, presupuesto.paciente, presupuesto.direccion, presupuesto.telefono,
		presupuesto.total, presupuesto.observaciones, presupuestoprod.observaciones as obsProducto, presupuesto.vip, presupuestoprod.cantidad
        
from presupuestoprod
left join presupuesto ON presupuesto.id_presupuesto = presupuestoprod.id_presupuesto

where presupuestoprod.id_productoFact = id_productoFact AND
		presupuestoProd.id_presupuesto IN (SELECT presupuesto.id_presupuesto
											FROM presupuesto
                                            WHERE presupuesto.id_lugarCirugia = id_entidad AND
													presupuesto.id_vendedor = id_vendedor AND
													YEAR(presupuesto.fecha) = anio AND
                                                    IF(tipo = 0, presupuesto.estado = "A",
																					presupuesto.estado = "Z" AND IF(tipo = 1, IF(mes = 0, true, MONTH(presupuesto.fecha) = mes) AND presupuesto.id_presupuesto IN (SELECT factura.id_presupuesto
																																																					FROM factura),
																															IF(tipo = 2, presupuesto.id_presupuesto NOT IN (SELECT factura.id_presupuesto
																																											FROM factura),
																																			presupuesto.estado = "A" OR 
																																				presupuesto.estado = "Z" AND (IF(mes = 0, true, MONTH(presupuesto.fecha) = mes) AND presupuesto.id_presupuesto IN (SELECT factura.id_presupuesto
																																																																	FROM factura)) or
																																												presupuesto.id_presupuesto NOT IN (SELECT factura.id_presupuesto
																																																					FROM factura))))) 
) consulta

WHERE IF(vip, consulta.vip = "S", true);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `devuelveIntersucursalCaja` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `devuelveIntersucursalCaja`(IN id_intersucursalCaja INT)
BEGIN

UPDATE intersucursalCaja SET
		intersucursalCaja.devuelto = 'S'
        
WHERE intersucursalCaja.id_intersucursalCaja = id_intersucursalCaja;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `estadoPresupuesto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `estadoPresupuesto`(IN id_presupuesto INT)
BEGIN

SELECT presupuesto.estado

FROM presupuesto

WHERE presupuesto.id_presupuesto = id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `existeFactura` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `existeFactura`(IN id_tipoComp INT,
										numComp VARCHAR(12),
                                        id_empresa INT)
BEGIN

SELECT factura.id_presupuesto
from factura
where factura.id_tipoComp = id_tipoComp AND
		factura.numComp = numComp AND
        factura.id_empresa = id_empresa;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `finalizaPresupuesto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `finalizaPresupuesto`(IN id_presupuesto INT,
                                                            estado VARCHAR(1),
                                                            id_tecnico INT,
                                                            id_prestacion INT,
                                                            observaciones TEXT,
                                                            usuario VARCHAR(45),
                                                            equipo VARCHAR(255))
BEGIN

DECLARE log TEXT;

UPDATE presupuesto SET
    presupuesto.estado = estado,
    presupuesto.id_tecnico = id_tecnico,
    presupuesto.id_prestacion = id_prestacion,
    presupuesto.observaciones = observaciones,
    presupuesto.usuario = usuario,
    presupuesto.fechaReal = CURRENT_TIMESTAMP
    
WHERE presupuesto.id_presupuesto = id_presupuesto;

SET log = CONCAT("Finaliza presupuesto ", id_presupuesto, ", estado = ", estado);

CALL logSistema(usuario, equipo, log);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getPresupuestoCajasAsignadas` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `getPresupuestoCajasAsignadas`(id_presupuesto INT)
BEGIN
select b.codigo, c.nombre, z.nombre as deposito, a.id_cajadeposito, 'NO' as prestada from cajaasignada as a
	join cajadeposito as b on a.id_cajaDeposito = b.id_cajaDeposito
	join caja as c on c.id_caja = b.id_caja
	join zona as z on z.id_zona = b.id_zona
	where a.id_presupuesto = id_presupuesto;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `guardaConfirmaTurno` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `guardaConfirmaTurno`(IN id_presupuesto INT,
                                                        id_tecnico INT,
                                                        id_prestacion INT,
                                                        importePrestacion DECIMAL(9,2),
                                                        observaciones TEXT,
                                                        id_entidad INT,
                                                        id_lugarCirugia INT,
                                                        id_profesional1 INT,
                                                        fechaCirugia DATE,
                                                        fechaAgenda DATE,
                                                        paciente VARCHAR(45),
                                                        usuario VARCHAR(45),
                                                        equipo VARCHAR(255))
BEGIN

DECLARE log TEXT;

UPDATE presupuesto SET
    presupuesto.id_tecnico = id_tecnico,
    presupuesto.id_prestacion = id_prestacion,
    presupuesto.importePrestacion = importePrestacion,
    presupuesto.observaciones = observaciones,
    presupuesto.id_entidad = id_entidad,
    presupuesto.id_lugarCirugia = id_lugarCirugia,
    presupuesto.id_profesional1 = id_profesional1,
    presupuesto.fechaCirugia = fechaCirugia,
    presupuesto.fechaAgenda = fechaAgenda,
    presupuesto.paciente = paciente,
    presupuesto.usuario = usuario,
    presupuesto.fechaReal = CURRENT_TIMESTAMP
    
WHERE presupuesto.id_presupuesto = id_presupuesto;

SET log = CONCAT("Modifica datos de presupuesto ", id_presupuesto);

CALL logSistema(usuario, equipo, log);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertAcceso` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertAcceso`(IN id_usuario INT,
                                                        id_menu INT,
                                                        id_empresa INT)
BEGIN

INSERT INTO acceso (id_usuario, id_menu, id_empresa)
            VALUES (id_usuario, id_menu, id_empresa);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertAjusteStock` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertAjusteStock`(IN id_zona INT,
                                                fecha DATE,
                                                id_empresa INT,
                                                usuario VARCHAR(45))
BEGIN

INSERT INTO ajusteStock (id_zona, fecha, id_empresa, usuario)

            VALUES (id_zona, fecha, id_empresa, usuario);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertAjusteStockDetalle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertAjusteStockDetalle`(id_producto INT,
                                                            cantidad INT,
                                                            dc VARCHAR(1),
                                                            lote VARCHAR(45),
															serie VARCHAR(255),
                                                            pm VARCHAR(45),
                                                            vencimiento DATE,
                                                            observaciones TEXT,
                                                            id_zona INT,
                                                            id_empresa INT)
BEGIN

DECLARE id INT;
DECLARE id_moneda INT;
DECLARE costoPesos DECIMAL(12,3);
DECLARE cotizacion DECIMAL(12,3);

SET id = (SELECT max(ajusteStock.id_ajusteStock) 
                FROM ajusteStock 
                WHERE ajusteStock.id_empresa = id_empresa);
                
SELECT producto.id_moneda, producto.costo INTO id_moneda, costoPesos 
	FROM producto 
    WHERE producto.id_producto = id_producto;

SET cotizacion = 1.000;
SELECT IFNULL(cotizacion.valor, 1.000) INTO cotizacion 
	FROM cotizacion 
    WHERE cotizacion.id_moneda = id_moneda AND cotizacion.id_empresa = id_empresa 
    ORDER BY cotizacion.id_cotizacion DESC LIMIT 1;

INSERT INTO stockdetalle (id_ajusteStock, id_producto, cantidad, dc, lote, serie,
                            pm, vencimiento, costoPesos, id_moneda, cotizacion, 
                            observaciones, id_zona)
VALUES (id, id_producto, cantidad, dc, lote, serie,
        pm, vencimiento, costoPesos, id_moneda, cotizacion, 
        observaciones, id_zona);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertCaja` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertCaja`(IN id_caja INT,
                                                    nombre VARCHAR(45),
                                                    habilita VARCHAR(1))
BEGIN

IF (id_caja = 0) THEN

    INSERT INTO caja (nombre, habilita)
        VALUES (nombre, habilita);
        
ELSE

    UPDATE caja SET
        nombre = nombre,
        habilita = habilita
        
    WHERE caja.id_caja = id_caja;

END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertCajaAsignada` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertCajaAsignada`(IN id_presupuesto INT, id_cajaDeposito INT)
BEGIN

INSERT INTO cajaasignada ( id_presupuesto, id_cajaDeposito)
    VALUES ( id_presupuesto, id_cajaDeposito);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertCajaComposicion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertCajaComposicion`(IN id_cajaDeposito INT,
                                                                id_producto INT,
                                                                cantidad INT,
                                                                usuario VARCHAR(45))
BEGIN

INSERT INTO cajacomposicion (id_cajaDeposito, id_producto, cantidad, usuario, fechaReal)

    VALUES (id_cajaDeposito, id_producto, cantidad, usuario, CURRENT_TIMESTAMP);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertCajaDeposito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertCajaDeposito`(IN id_cajaDeposito INT,
                                                        id_zona INT,
                                                        id_caja INT,
                                                        codigo VARCHAR(25),
                                                        habilita VARCHAR(1),
                                                        id_empresa INT)
BEGIN

IF (id_cajaDeposito = 0) THEN
    
    INSERT INTO cajadeposito (id_zona, id_caja, codigo, habilita, id_empresa)
                            
    VALUES (id_zona, id_caja, codigo, habilita, id_empresa);
            
ELSE

    UPDATE cajadeposito SET
        id_zona = id_zona, 
        id_caja = id_caja,
        codigo = codigo,
        habilita = habilita
    
    WHERE cajadeposito.id_cajaDeposito = id_cajaDeposito;
    
END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertClasiEntidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertClasiEntidad`(IN id_clasiEntidad INT,
                                                    nombre VARCHAR(45),
                                                    habilita VARCHAR(1))
BEGIN

IF (id_clasiEntidad = 0) THEN

    INSERT INTO clasientidad (nombre, habilita)
        VALUES (nombre, habilita);
        
ELSE

    UPDATE clasientidad SET
        nombre = nombre,
        habilita = habilita
        
    WHERE clasientidad.id_clasiEntidad = id_clasiEntidad;

END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertClasiProducto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertClasiProducto`(IN id_clasiProducto INT,
                                                    nombre VARCHAR(45),
                                                    habilita VARCHAR(1))
BEGIN

IF (id_clasiProducto = 0) THEN

    INSERT INTO clasiproducto (nombre, habilita)
        VALUES (nombre, habilita);
        
ELSE

    UPDATE clasiproducto SET
        nombre = nombre,
        habilita = habilita
        
    WHERE clasiproducto.id_clasiProducto = id_clasiProducto;

END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertCodConsumo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertCodConsumo`(IN id_codConsumo INT,
                                                    codigo VARCHAR(10),
                                                    nombre VARCHAR(45),
                                                    habilita VARCHAR(1))
BEGIN

IF (id_codConsumo = 0) THEN

    INSERT INTO codconsumo (codigo, nombre, habilita)
        VALUES (codigo, nombre, habilita);
        
ELSE

    UPDATE codconsumo SET
        codigo = codigo,
        nombre = nombre,
        habilita = habilita
        
    WHERE codconsumo.id_codConsumo = id_codConsumo;

END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertCotizacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertCotizacion`(IN id_moneda INT,
										mes INT,
                                        año INT,
                                        valor DECIMAL(12,3),
                                        id_empresa INT,
                                        usuario VARCHAR(45))
BEGIN

INSERT INTO cotizacion (id_moneda, mes, año, valor, id_empresa,
						usuario)
                        
			VALUES (id_moneda, mes, año, valor, id_empresa,
						usuario);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertDiaria` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertDiaria`(IN id_diaria INT,
                                                        nombre VARCHAR(100),
                                                        direccion VARCHAR(100),
                                                        id_provincia INT,
                                                        id_departamento INT,
                                                        id_localidad INT,
                                                        codPostal VARCHAR(6),
                                                        id_opcion INT,
                                                        cuit VARCHAR(11),
                                                        telefono1 VARCHAR(45),
                                                        telefono2 VARCHAR(45),
                                                        telefono3 VARCHAR(45),
                                                        telefono4 VARCHAR(45),
                                                        email VARCHAR(45),
                                                        secretaria VARCHAR(45),
                                                        contacto VARCHAR(45),
                                                        telContacto VARCHAR(45),
                                                        movContacto VARCHAR(45),
                                                        emailContacto VARCHAR(45),
                                                        id_zona INT,
                                                        id_formaPago INT,
                                                        observaciones VARCHAR(100),
                                                        habilita VARCHAR(1),
                                                        id_empresa INT)
BEGIN

IF (id_diaria = 0) THEN
    INSERT INTO diaria (nombre, direccion, id_provincia, id_departamento, id_localidad,
                        codPostal, id_opcion, cuit, telefono1, telefono2,
                        telefono3, telefono4, email, secretaria, contacto,
                        telContacto, movContacto, emailContacto, id_zona, id_formaPago,
                        observaciones, habilita, id_empresa)
                        
                VALUES (nombre, direccion, id_provincia, id_departamento, id_localidad,
                        codPostal, id_opcion, cuit, telefono1, telefono2,
                        telefono3, telefono4, email, secretaria, contacto,
                        telContacto, movContacto, emailContacto, id_zona, id_formaPago,
                        observaciones, habilita, id_empresa);
                        
ELSE
    
    UPDATE diaria SET
        nombre = nombre, 
        direccion = direccion, 
        id_provincia = id_provincia, 
        id_departamento = id_departamento, 
        id_localidad = id_localidad,
        codPostal = codPostal, 
        id_opcion = id_opcion, 
        cuit = cuit, 
        telefono1 = telefono1, 
        telefono2 = telefono2,
        telefono3 = telefono3, 
        telefono4 = telefono4, 
        email = email, 
        secretaria = secretaria, 
        contacto = contacto,
        telContacto = telContacto, 
        movContacto = movContacto, 
        emailContacto = emailContacto, 
        id_zona = id_zona, 
        id_formaPago = id_formaPago,
        observaciones = observaciones, 
        habilita = habilita 
        
    WHERE diaria.id_diaria = id_diaria;
    
END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertEntidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertEntidad`(IN id_entidad INT,
                                                        nombre VARCHAR(100),
                                                        direccion VARCHAR(100),
                                                        id_provincia INT,
                                                        id_departamento INT,
                                                        id_localidad INT,
                                                        codPostal VARCHAR(10),
                                                        email VARCHAR(45),
                                                        telefono1 VARCHAR(45),
                                                        telefono2 VARCHAR(45),
                                                        auditor VARCHAR(45),
                                                        secretaria VARCHAR(45),
                                                        id_zona INT,
                                                        certImplante VARCHAR(1),
                                                        recomendaciones VARCHAR(1),
                                                        id_formaPago INT,
                                                        id_opcion INT,
                                                        cuit VARCHAR(11),
                                                        id_clasiEntidad INT,
                                                        riesgoCredito DECIMAL(9,3),
                                                        reqFacturacion TEXT,
                                                        observaciones TEXT,
                                                        comprasNombre VARCHAR(45),
                                                        comprasTelefono VARCHAR(45),
                                                        comprasEmail VARCHAR(45),
                                                        tesoreriaNombre VARCHAR(45),
                                                        tesoreriaTelefono VARCHAR(45),
                                                        tesoreriaEmail VARCHAR(45),
                                                        contableNombre VARCHAR(45),
                                                        contableTelefono VARCHAR(45),
                                                        contableEmail VARCHAR(45),
                                                        farmaciaNombre VARCHAR(45),
                                                        farmaciaTelefono VARCHAR(45),
                                                        farmaciaEmail VARCHAR(45),
                                                        gln VARCHAR(14),
                                                        habilita VARCHAR(1),
                                                        id_empresa INT)
BEGIN

DECLARE newID INT;

IF (id_entidad = 0) THEN
    INSERT INTO entidad (nombre, direccion, id_provincia, id_departamento, id_localidad,
                        codPostal, email, telefono1, telefono2, auditor, 
                        secretaria, id_zona, certImplante, recomendaciones, id_formaPago,
                        id_opcion, cuit, id_clasiEntidad, riesgoCredito, reqFacturacion,
                        observaciones, gln, habilita, id_empresa)
                        
                VALUES (nombre, direccion, id_provincia, id_departamento, id_localidad,
                        codPostal, email, telefono1, telefono2, auditor, 
                        secretaria, id_zona, certImplante, recomendaciones, id_formaPago,
                        id_opcion, cuit, id_clasiEntidad, riesgoCredito, reqFacturacion,
                        observaciones, gln, habilita, id_empresa);
                        
    SET newID = (SELECT MAX(entidad.id_entidad) FROM entidad WHERE entidad.id_empresa = id_empresa);
                        
ELSE
    
    UPDATE entidad SET
        entidad.nombre = nombre, 
        entidad.direccion = direccion, 
        entidad.id_provincia = id_provincia, 
        entidad.id_departamento = id_departamento, 
        entidad.id_localidad = id_localidad,
        entidad.codPostal = codPostal, 
        entidad.email = email, 
        entidad.telefono1 = telefono1, 
        entidad.telefono2 = telefono2, 
        entidad.auditor = auditor, 
        entidad.secretaria = secretaria, 
        entidad.id_zona = id_zona, 
        entidad.certImplante = certImplante, 
        entidad.recomendaciones = recomendaciones, 
        entidad.id_formaPago = id_formaPago,
        entidad.id_opcion = id_opcion, 
        entidad.cuit = cuit, 
        entidad.id_clasiEntidad = id_clasiEntidad, 
        entidad.riesgoCredito = riesgoCredito, 
        entidad.reqFacturacion = reqFacturacion,
        entidad.observaciones = observaciones, 
        entidad.gln = gln, 
        entidad.habilita = habilita
        
    WHERE entidad.id_entidad = id_entidad;
    
    SET newID = id_entidad;
    
END IF;

DELETE FROM encargadocompras WHERE encargadocompras.id_entidad = newID;
DELETE FROM encargadotesoreria WHERE encargadotesoreria.id_entidad = newID;
DELETE FROM encargadocontable WHERE encargadocontable.id_entidad = newID;
DELETE FROM encargadofarmacia WHERE encargadofarmacia.id_entidad = newID;

INSERT INTO encargadocompras (id_entidad, nombre, telefono, email)
VALUES (newID, comprasNombre, comprasTelefono, comprasEmail);

INSERT INTO encargadotesoreria (id_entidad, nombre, telefono, email)
VALUES (newID, tesoreriaNombre, tesoreriaTelefono, tesoreriaEmail);

INSERT INTO encargadocontable (id_entidad, nombre, telefono, email)
VALUES (newID, contableNombre, contableTelefono, contableEmail);

INSERT INTO encargadofarmacia (id_entidad, nombre, telefono, email)
VALUES (newID, farmaciaNombre, farmaciaTelefono, farmaciaEmail);



END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertEspecialidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertEspecialidad`(IN id_especialidad INT,
                                                    nombre VARCHAR(45),
                                                    habilita VARCHAR(1))
BEGIN

IF (id_especialidad = 0) THEN

    INSERT INTO especialidad (nombre, habilita)
        VALUES (nombre, habilita);
        
ELSE

    UPDATE especialidad SET
        nombre = nombre,
        habilita = habilita
        
    WHERE especialidad.id_especialidad = id_especialidad;

END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertEstrategia` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertEstrategia`(IN id_estrategia INT,
                                                        id_profesional INT,
                                                        fechaInicio DATE,
                                                        fechaAgenda DATE,
                                                        perfil TEXT,
                                                        estrategia TEXT,
                                                        respuesta TEXT,
                                                        historico VARCHAR(1),
                                                        id_empresa INT,
                                                        usuario VARCHAR(45))
BEGIN

IF (id_estrategia = 0) THEN

    INSERT INTO estrategia (id_estrategia, id_profesional, fechaInicio, fechaAgenda, perfil,
                            estrategia, respuesta, historico, id_empresa, usuario)
                            
                    VALUES (id_estrategia, id_profesional, fechaInicio, fechaAgenda, perfil,
                            estrategia, respuesta, historico, id_empresa, usuario);
                            
ELSE

    UPDATE estrategia SET 
        id_estrategia = id_estrategia,
        id_profesional = id_profesional,
        fechaInicio = fechaInicio,
        fechaAgenda = fechaAgenda,
        perfil = perfil,
        estrategia = estrategia,
        respuesta = respuesta,
        historico = historico,
        usuario = usuario,
        fechaReal = CURRENT_TIMESTAMP;
        
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertEstrategiaEntidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertEstrategiaEntidad`(IN id_estrategiaEntidad INT,
                                                        id_entidad INT,
                                                        fechaInicio DATE,
                                                        fechaAgenda DATE,
                                                        requisitos TEXT,
                                                        estrategia TEXT,
                                                        respuesta TEXT,
                                                        historico VARCHAR(1),
                                                        id_empresa INT,
                                                        usuario VARCHAR(45))
BEGIN

IF (id_estrategiaEntidad = 0) THEN

    INSERT INTO estrategiaEntidad (id_estrategiaEntidad, id_entidad, fechaInicio, fechaAgenda, requisitos,
                            estrategia, respuesta, historico, id_empresa, usuario)
                            
                    VALUES (id_estrategiaEntidad, id_entidad, fechaInicio, fechaAgenda, requisitos,
                            estrategia, respuesta, historico, id_empresa, usuario);
                            
ELSE

    UPDATE estrategiaEntidad SET 
        estrategiaEntidad.id_estrategiaEntidad = id_estrategiaEntidad,
        estrategiaEntidad.id_entidad = id_entidad,
        estrategiaEntidad.fechaInicio = fechaInicio,
        estrategiaEntidad.fechaAgenda = fechaAgenda,
        estrategiaEntidad.requisitos = requisitos,
        estrategiaEntidad.estrategia = estrategia,
        estrategiaEntidad.respuesta = respuesta,
        estrategiaEntidad.historico = historico,
        estrategiaEntidad.usuario = usuario,
        estrategiaEntidad.fechaReal = CURRENT_TIMESTAMP;
        
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertFactura` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertFactura`(IN fecha DATE,
                                                        id_presupuesto INT,
                                                        id_tipoComp INT,
                                                        numComp VARCHAR(12),
                                                        id_tipoCompRel INT,
                                                        numCompRel VARCHAR(12),
                                                        vencimiento DATE,
                                                        id_formaPago INT,
                                                        dc VARCHAR(1),
                                                        bonificacion DECIMAL(9,2),
                                                        neto DECIMAL(9,2),
                                                        aliPercIIBB DECIMAL(9,2),
                                                        percIIBB DECIMAL(9,2),
                                                        aliIva DECIMAL(9,2),
                                                        iva DECIMAL(9,2),
                                                        observaciones TEXT,
                                                        id_vendedor INT,
                                                        comparte VARCHAR(1),
                                                        id_vendedorComparte INT,
                                                        id_empresa INT,
                                                        usuario VARCHAR(45))
BEGIN

INSERT INTO factura (fecha, id_presupuesto, id_tipoComp, numComp, id_tipoCompRel,
                        numCompRel, vencimiento, id_formaPago, dc, bonificacion, 
                        neto, percIIBB, iva, observaciones, id_vendedor, 
                        id_empresa, usuario, aliPercIIBB, aliIva, comparte,
                        id_vendedorComparte)
                        
                VALUES (fecha, id_presupuesto, id_tipoComp, numComp, id_tipoCompRel,
                        numCompRel, vencimiento, id_formaPago, dc, bonificacion, 
                        neto, percIIBB, iva, observaciones, id_vendedor, 
                        id_empresa, usuario, aliPercIIBB, aliIva, comparte,
                        id_vendedorComparte);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertForecast` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertForecast`(IN anio INT,
										id_vendedor INT,
                                        id_entidad INT,
                                        id_productoFact INT,
                                        cantidad INT,
                                        usuario VARCHAR(45))
BEGIN

INSERT INTO forecast (anio, id_vendedor, id_entidad, id_productoFact, cantidad,
						usuario)
                        
				VALUES (anio, id_vendedor, id_entidad, id_productoFact, cantidad,
						usuario);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertForecastComposicionGrupo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertForecastComposicionGrupo`(IN id_forecastGrupo INT,
														id_productoFact INT,
                                                        id_empresa INT)
BEGIN

INSERT INTO forecastComposicionGrupo (id_forecastGrupo, id_productoFact, id_empresa)
						VALUES (id_forecastGrupo, id_productoFact, id_empresa);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertForecastEntidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertForecastEntidad`(IN id_entidad INT,
											id_empresa INT)
BEGIN

INSERT INTO forecastEntidad (id_entidad, id_empresa)
				VALUES (id_entidad, id_empresa);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertForecastGrupo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertForecastGrupo`(IN id_forecastGrupo INT, 
											nombre VARCHAR(45),
                                            habilita VARCHAR(1),
                                            id_empresa INT)
BEGIN

IF id_forecastGrupo = 0 then

	INSERT INTO forecastGrupo (id_forecastGrupo, nombre, habilita, id_empresa)
						VALUES (id_forecastGrupo, nombre, habilita, id_empresa);
                        
ELSE

	UPDATE forecastGrupo SET
			forecastGrupo.nombre = nombre,
            forecastGrupo.habilita = habilita
            
	WHERE forecastGrupo.id_forecastGrupo = id_forecastGrupo;

END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertFormaPago` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertFormaPago`(IN id_formapago INT,
                                                    nombre VARCHAR(45),
                                                    habilita VARCHAR(1))
BEGIN

IF (id_formapago = 0) THEN

    INSERT INTO formapago (nombre, habilita)
        VALUES (nombre, habilita);
        
ELSE

    UPDATE formapago SET
        nombre = nombre,
        habilita = habilita
        
    WHERE formapago.id_formapago = id_formapago;

END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertIntersucursal` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertIntersucursal`(IN usuario VARCHAR(45),
																		id_empresa INT)
BEGIN

DECLARE id_remito INT;

SELECT max(remito.id_remito) INTO id_remito
FROM remito 
WHERE remito.id_empresa = id_empresa;

INSERT INTO intersucursal (id_remito, recibido, usuario)
				VALUES (id_remito, 'N', usuario);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertIntersucursalCaja` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertIntersucursalCaja`(IN id_cajaDeposito INT,
																		devuelto VARCHAR(1))
begin

DECLARE id_intersucursal INT;

SELECT MAX(intersucursal.id_intersucursal) INTO id_intersucursal
FROM intersucursal;

INSERT INTO intersucursalCaja (id_intersucursal, id_cajaDeposito, devuelto)
					VALUES (id_intersucursal, id_cajaDeposito, devuelto);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertLiquidacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertLiquidacion`(IN fecha DATE,
                                                            observaciones TEXT,
                                                            id_empresa INT,
                                                            usuario VARCHAR(45))
BEGIN

INSERT INTO liquidacion (fecha, observaciones, id_empresa, usuario)
                VALUES (fecha, observaciones, id_empresa, usuario);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertLiquidacionDetalle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertLiquidacionDetalle`(IN id_liquidacion INT,
                                                                    banco VARCHAR(45),
                                                                    cheque VARCHAR(45),
                                                                    vencimiento DATE,
                                                                    importe DECIMAL(7,2),
                                                                    id_empresa INT,
                                                                    usuario VARCHAR(45))
BEGIN

INSERT INTO liquidacionDetalle (id_liquidacion, banco, cheque, vencimiento, importe,
                                id_empresa, usuario)
                        
                        VALUES (id_liquidacion, banco, cheque, vencimiento, importe,
                                id_empresa, usuario);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertMantenimiento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertMantenimiento`(IN id_mantenimiento INT,
                                                    nombre VARCHAR(45),
                                                    habilita VARCHAR(1))
BEGIN

IF (id_mantenimiento = 0) THEN

    INSERT INTO mantenimiento (nombre, habilita)
        VALUES (nombre, habilita);
        
ELSE

    UPDATE mantenimiento SET
        nombre = nombre,
        habilita = habilita
        
    WHERE mantenimiento.id_mantenimiento = id_mantenimiento;

END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertMayorProfesional` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertMayorProfesional`(IN fecha DATE,
                                                    id_presupuesto INT,
                                                    id_profesional INT,
                                                    dc VARCHAR(1),
                                                    importe DECIMAL(9,2),
                                                    observaciones TEXT,
                                                    id_empresa INT,
                                                    usuario VARCHAR(45),
                                                    transferencia VARCHAR(1))
BEGIN

INSERT INTO mayorProfesional (fecha, id_presupuesto, id_profesional, dc, importe,
                    observaciones, id_empresa, usuario, transferencia)
            VALUES (fecha, id_presupuesto, id_profesional, dc, importe,
                    observaciones, id_empresa, usuario, transferencia);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertMayorTecnico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertMayorTecnico`(IN fecha DATE,
                                                            id_presupuesto INT,
                                                            id_tecnico INT,
                                                            dc VARCHAR(1),
                                                            importe DECIMAL(7,2),
                                                            observaciones TEXT,
                                                            id_empresa INT,
                                                            usuario VARCHAR(45))
BEGIN

INSERT INTO mayorTecnico (fecha, id_presupuesto, id_tecnico, dc, importe, 
                            observaciones, id_empresa, usuario)
                    VALUES(fecha, id_presupuesto, id_tecnico, dc, importe, 
                            observaciones, id_empresa, usuario);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertMoneda` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertMoneda`(IN id_moneda INT,
                                                        codigo VARCHAR(3),
                                                        nombre VARCHAR(45),
                                                        simbolo VARCHAR(3),
                                                        habilita VARCHAR(1),
                                                        cotizacion DECIMAL(12,3))
BEGIN

IF (id_moneda = 0) THEN
    
    INSERT INTO moneda (codigo, nombre, simbolo, habilita, cotizacion)
                            
    VALUES (codigo, nombre, simbolo, habilita, cotizacion);
            
ELSE

    UPDATE moneda SET
        codigo = codigo,
        nombre = nombre,
        simbolo = simbolo, 
        habilita = habilita,
        cotizacion = cotizacion
    
    WHERE moneda.id_moneda = id_moneda;
    
END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertNivelesCertificado` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertNivelesCertificado`(IN id_nivelesCertificado INT,
																	id_presupuesto INT,
                                                                    alternativas INT,
                                                                    instrumental INT,
                                                                    asistencia INT,
                                                                    puntualidad INT,
                                                                    controlado VARCHAR(1),
                                                                    usuario VARCHAR(45))
BEGIN

IF id_nivelesCertificado = 0 THEN

	INSERT INTO nivelesCertificado (id_presupuesto, alternativas, instrumental, asistencia, puntualidad,
										controlado, usuario)
							VALUES (id_presupuesto, alternativas, instrumental, asistencia, puntualidad,
										controlado, usuario);

ELSE
	
    UPDATE nivelesCertificado SET
		nivelesCertificado.alternativas = alternativas,
        nivelesCertificado.instrumental = instrumental,
        nivelesCertificado.asistencia = asistencia,
        nivelesCertificado.puntualidad = puntualidad,
        nivelesCertificado.controlado = controlado,
        nivelesCertificado.usuario = usuario,
        nivelesCertificado.fechaReal = current_timestamp
        
	WHERE nivelesCertificado.id_nivelesCertificado = id_nivelesCertificado;
    
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertNotaPresu` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertNotaPresu`(IN nota TEXT,
                                                        id_empresa INT,
                                                        usuario VARCHAR(45))
BEGIN

UPDATE notapresu SET
    nota = nota,
    fechaReal = CURRENT_TIMESTAMP,
    usuario = usuario
    
WHERE notapresu.id_empresa = id_empresa;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertPadre` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertPadre`(IN id_usuario INT,
                                                        id_empresa INT,
                                                        posicion VARCHAR(30))
BEGIN

DECLARE id_menu INT;

SELECT menu.id_menu INTO id_menu
FROM menu
WHERE menu.posicion = posicion AND
    menu.id_empresa = id_empresa AND
    menu.habilita = 'S';

INSERT INTO acceso (id_usuario, id_menu, id_empresa)
            VALUES (id_usuario, id_menu, id_empresa);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertPlazo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertPlazo`(IN id_plazo INT,
                                                    nombre VARCHAR(45),
                                                    habilita VARCHAR(1))
BEGIN

IF (id_plazo = 0) THEN

    INSERT INTO plazo (nombre, habilita)
        VALUES (nombre, habilita);
        
ELSE

    UPDATE plazo SET
        nombre = nombre,
        habilita = habilita
        
    WHERE plazo.id_plazo = id_plazo;

END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertPreliquidacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertPreliquidacion`(IN fecha DATE,
                                                                id_presupuesto INT,
                                                                id_profesional INT,
                                                                dc VARCHAR(1),
                                                                importe DECIMAL(9,2),
                                                                observaciones TEXT,
                                                                preliquidacion INT,
                                                                id_empresa INT,
                                                                usuario VARCHAR(45))
BEGIN

INSERT INTO mayorprofesional (fecha, id_presupuesto, id_profesional, dc, importe,
                                observaciones, preliquidacion, liquidacion, id_empresa, usuario)
                    VALUES (fecha, id_presupuesto, id_profesional, dc, importe,
                                observaciones, preliquidacion, 0, id_empresa, usuario);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertPrestacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertPrestacion`(IN id_prestacion INT,
                                                        nombre VARCHAR(45),
                                                        importe DECIMAL(9,2),
                                                        habilita VARCHAR(1),
                                                        id_empresa INT)
BEGIN

IF (id_prestacion = 0) THEN
    
    INSERT INTO prestacion (nombre, importe, habilita, id_empresa)
                            
    VALUES (nombre, importe, habilita, id_empresa);
            
ELSE

    UPDATE prestacion SET
        nombre = nombre,
        importe = importe,
        habilita = habilita
    
    WHERE prestacion.id_prestacion = id_prestacion;
    
END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertPresupuesto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertPresupuesto`(IN id_presupuesto INT,
                                                        estado VARCHAR(1),
                                                        fecha DATE,
                                                        id_zona INT,
                                                        id_entidad INT,
                                                        id_lugarCirugia INT,
                                                        acompañamiento VARCHAR(1),
                                                        fechaApertura DATE,
                                                        horaApertura VARCHAR(4),
                                                        expediente VARCHAR(45),
                                                        contratacion VARCHAR(45),
                                                        paciente VARCHAR(45),
                                                        vip VARCHAR(1),
                                                        dni VARCHAR(15),
                                                        direccion VARCHAR(100),
                                                        telefono VARCHAR(45),
                                                        total DECIMAL(9,2),
                                                        id_profesional1 INT,
                                                        rm1 DECIMAL(7,2),
                                                        id_profesional2 INT,
                                                        rm2 DECIMAL(7,2),
                                                        id_profesional3 INT,
                                                        rm3 DECIMAL(7,2),
                                                        id_vendedor INT,
                                                        id_tipoOperacion INT,
                                                        id_formaPago INT,
                                                        formaPagoOtro VARCHAR(45),
                                                        id_plazo INT,
                                                        plazoOtro VARCHAR(45),
                                                        id_mantenimiento INT,
                                                        mantenimientoOtro VARCHAR(45),
                                                        notaPresu TEXT,
                                                        observaciones TEXT,
                                                        id_motivo INT,
                                                        motivo TEXT,
                                                        notaExtra TEXT,
                                                        id_empresa INT,
                                                        usuario VARCHAR(45),
                                                        fechaAprobacion DATE,
                                                        equipo VARCHAR(255),
                                                        costo_venta DECIMAL(9,2))
BEGIN

DECLARE log TEXT;

IF id_presupuesto NOT IN (SELECT presupuesto.id_presupuesto FROM presupuesto) then
	INSERT INTO presupuesto (id_presupuesto, estado,fecha,id_zona,id_entidad,id_lugarCirugia,
                            acompañamiento,fechaApertura,horaApertura,expediente,contratacion,
                            paciente,vip,dni,direccion,telefono,
                            total,id_profesional1,rm1,id_profesional2,rm2,
                            id_profesional3,rm3,id_vendedor,id_tipoOperacion,id_formaPago,
                            formaPagoOtro,id_plazo,plazoOtro,id_mantenimiento,mantenimientoOtro,
                            notaPresu,observaciones,id_motivo,motivo,id_empresa,
                            usuario, fechaAprobacion, notaExtra, costo_venta)
                    
                    VALUES (id_presupuesto, estado,fecha,id_zona,id_entidad,id_lugarCirugia,
                            acompañamiento,fechaApertura,horaApertura,expediente,contratacion,
                            paciente,vip,dni,direccion,telefono,
                            total,id_profesional1,rm1,id_profesional2,rm2,
                            id_profesional3,rm3,id_vendedor,id_tipoOperacion,id_formaPago,
                            formaPagoOtro,id_plazo,plazoOtro,id_mantenimiento,mantenimientoOtro,
                            notaPresu,observaciones,id_motivo,motivo,id_empresa,
                            usuario, fechaAprobacion, notaExtra, costo_venta);
                            
	SET log = CONCAT("Crea presupuesto ", proximoPresupuesto(id_empresa));
else
	UPDATE presupuesto SET
            presupuesto.estado = estado,
            presupuesto.fecha = fecha,
            presupuesto.id_zona = id_zona,
            presupuesto.id_entidad = id_entidad,
            presupuesto.id_lugarCirugia = id_lugarCirugia,
            presupuesto.acompañamiento = acompañamiento,
            presupuesto.fechaApertura = fechaApertura,
            presupuesto.horaApertura = horaApertura,
            presupuesto.expediente = expediente,
            presupuesto.contratacion = contratacion,
            presupuesto.paciente = paciente,
            presupuesto.vip = vip,
            presupuesto.dni = dni,
            presupuesto.direccion = direccion,
            presupuesto.telefono = telefono,
            presupuesto.total = total,
            presupuesto.id_profesional1 = id_profesional1,
            presupuesto.rm1 = rm1,
            presupuesto.id_profesional2 = id_profesional2,
            presupuesto.rm2 = rm2,
            presupuesto.id_profesional3 = id_profesional3,
            presupuesto.rm3 = rm3,
            presupuesto.id_vendedor = id_vendedor,
            presupuesto.id_tipoOperacion = id_tipoOperacion,
            presupuesto.id_formaPago = id_formaPago,
            presupuesto.formaPagoOtro = formaPagoOtro,
            presupuesto.id_plazo = id_plazo,
            presupuesto.plazoOtro = plazoOtro,
            presupuesto.id_mantenimiento = id_mantenimiento,
            presupuesto.mantenimientoOtro = mantenimientoOtro,
            presupuesto.notaExtra = notaExtra,
            presupuesto.notaPresu = notaPresu,
            presupuesto.observaciones = observaciones,
            presupuesto.id_motivo = id_motivo,
            presupuesto.motivo = motivo,
            presupuesto.fechaAgenda = fechaAgenda,
            presupuesto.usuario = usuario,
            presupuesto.fechaReal = CURRENT_TIMESTAMP,
            presupuesto.fechaAprobacion = fechaAprobacion,
            presupuesto.costo_venta = costo_venta
            
	WHERE presupuesto.id_presupuesto = id_presupuesto;
    
    SET log = CONCAT("Actualiza presupuesto ", id_presupuesto, ", estado = ", estado);

END IF;

call logSistema(usuario, equipo, log);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertPresupuestoProd` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertPresupuestoProd`(IN id_empresa INT,
                                                                    id_presupuesto INT(11),
                                                                    cantidad INT,
                                                                    id_productoFact INT(11),
                                                                    observaciones TEXT,
                                                                    pxUnit DECIMAL(8,2),
                                                                    primero boolean,
                                                                    id_proveedor INT)
BEGIN

IF primero THEN
	DELETE FROM presupuestoprod WHERE presupuestoprod.id_presupuesto = id_presupuesto;
END IF;

INSERT INTO presupuestoprod (id_presupuesto, cantidad, id_productoFact, observaciones, pxUnit,
								id_proveedor)
    
                    VALUES(id_presupuesto, cantidad, id_productoFact, observaciones, pxUnit,
								id_proveedor);
                    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertProducto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertProducto`(IN id_producto INT,
                                                        codigo VARCHAR(25),
                                                        gtin VARCHAR(13),
                                                        nombre VARCHAR(255),
                                                        id_codConsumo INT,
                                                        id_moneda INT,
                                                        costo DECIMAL(12,3),
                                                        stockMinimo INT,
                                                        id_clasiProducto INT,
                                                        pm VARCHAR(45),
                                                        id_proveedor INT,
                                                        habilita VARCHAR(1),
                                                        id_empresa INT)
BEGIN

IF (id_producto = 0) THEN

    INSERT INTO producto (codigo, gtin, nombre, id_codConsumo, id_moneda, costo,
                            stockMinimo, id_clasiProducto, pm, id_proveedor, habilita,
                            id_empresa)
                            
                VALUES (codigo, gtin, nombre, id_codConsumo, id_moneda, costo,
                            stockMinimo, id_clasiProducto, pm, id_proveedor, habilita,
                            id_empresa);
                            
ELSE

    UPDATE producto SET
        producto.codigo = codigo, 
        producto.gtin = gtin, 
        producto.nombre = nombre, 
        producto.id_codConsumo = id_codConsumo, 
        producto.id_moneda = id_moneda, 
        producto.costo = costo,
        producto.stockMinimo = stockMinimo, 
        producto.id_clasiProducto = id_clasiProducto, 
        producto.pm = pm, 
        producto.id_proveedor = id_proveedor, 
        producto.habilita = habilita
        
    WHERE producto.id_producto = id_producto;
    
END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertProductoFact` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertProductoFact`(IN id_productoFact INT,
                                                    codigo VARCHAR(10),
                                                    nombre VARCHAR(45),
                                                    id_especialidad INT,
                                                    id_subespecialidad INT,
                                                    id_forecastGrupo INT,
                                                    habilita VARCHAR(1))
BEGIN

IF (id_productoFact = 0) THEN
    INSERT INTO productoFact (id_productoFact, codigo, nombre, id_especialidad, id_subespecialidad,
                                id_forecastGrupo, habilita)
                                
                    VALUES (id_productoFact, codigo, nombre, id_especialidad, id_subespecialidad,
                                id_forecastGrupo, habilita);
                                
ELSE

    UPDATE productoFact SET
        productoFact.codigo = codigo,
        productoFact.nombre = nombre,
        productoFact.id_especialidad = id_especialidad,
        productoFact.id_subespecialidad = id_subespecialidad,
        productoFact.id_forecastGrupo = id_forecastGrupo,
        productoFact.habilita = habilita
        
    WHERE productoFact.id_productoFact = id_productoFact;
        
END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertProfesional` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertProfesional`(IN id_profesional INT,
                                                        nombre VARCHAR(45),
                                                        direccion TEXT,
                                                        id_provincia INT,
                                                        id_departamento INT,
                                                        id_localidad INT,
                                                        codPostal VARCHAR(6),
                                                        fechaNac DATE,
                                                        dni VARCHAR(10),
                                                        matricula VARCHAR(12),
                                                        contacto VARCHAR(45),
                                                        perfil TEXT,
                                                        email VARCHAR(45),
                                                        telParticular VARCHAR(45),
                                                        telMovil VARCHAR(45),
                                                        telOtros VARCHAR(45),
                                                        telConsultorio VARCHAR(45),
                                                        secretaria VARCHAR(45),
                                                        dirConsultorio TEXT,
                                                        id_especialidad INT,
                                                        id_subespecialidad INT,
                                                        id_zona INT,
                                                        id_vendedor INT,
                                                        id_entidad INT,
                                                        observaciones TEXT,
                                                        habilita VARCHAR(1),
                                                        id_empresa INT)
BEGIN

IF (id_profesional = 0) THEN
    INSERT INTO profesional (nombre, direccion, id_provincia, id_departamento, id_localidad,
                        codPostal, fechaNac, dni, matricula, contacto, 
                        perfil, email, telParticular, telMovil, telOtros, 
                        telConsultorio, secretaria, dirConsultorio, id_especialidad, id_subespecialidad,
                        id_zona, id_vendedor, id_entidad, observaciones, habilita, 
                        id_empresa)
                        
                VALUES (nombre, direccion, id_provincia, id_departamento, id_localidad,
                        codPostal, fechaNac, dni, matricula, contacto, 
                        perfil, email, telParticular, telMovil, telOtros, 
                        telConsultorio, secretaria, dirConsultorio, id_especialidad, id_subespecialidad,
                        id_zona, id_vendedor, id_entidad, observaciones, habilita, 
                        id_empresa);
                        
ELSE
    
    UPDATE profesional SET
        profesional.nombre = nombre, 
        profesional.direccion = direccion, 
        profesional.id_provincia = id_provincia, 
        profesional.id_departamento = id_departamento, 
        profesional.id_localidad = id_localidad,
        profesional.codPostal = codPostal, 
        profesional.fechaNac = fechaNac, 
        profesional.dni = dni, 
        profesional.matricula = matricula, 
        profesional.contacto = contacto, 
        profesional.perfil = perfil, 
        profesional.email = email, 
        profesional.telParticular = telParticular, 
        profesional.telMovil = telMovil, 
        profesional.telOtros = telOtros, 
        profesional.telConsultorio = telConsultorio, 
        profesional.secretaria = secretaria, 
        profesional.dirConsultorio = dirConsultorio, 
        profesional.id_especialidad = id_especialidad, 
        profesional.id_subespecialidad = id_subespecialidad, 
        profesional.id_zona = id_zona, 
        profesional.id_vendedor = id_vendedor, 
        profesional.id_entidad = id_entidad, 
        profesional.observaciones = observaciones, 
        profesional.habilita = habilita
        
    WHERE profesional.id_profesional = id_profesional;
    
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertProveedor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertProveedor`(IN id_proveedor INT,
                                                        nombre VARCHAR(45),
                                                        direccion VARCHAR(100),
                                                        id_localidad INT,
                                                        id_departamento INT,
                                                        id_provincia INT,
                                                        codPostal VARCHAR(6),
                                                        telefono1 VARCHAR(45),
                                                        telefono2 VARCHAR(45),
                                                        email VARCHAR(45),
                                                        cuit VARCHAR(11),
                                                        gerente VARCHAR(45),
                                                        cuentaBanco VARCHAR(45),
                                                        id_formaPago INT,
                                                        observaciones TEXT,
                                                        gln VARCHAR(14),
                                                        pagoNombre VARCHAR(45),
                                                        pagoTelefono VARCHAR(45),
                                                        pagoEmail VARCHAR(45),
                                                        clienteNombre VARCHAR(45),
                                                        clienteTelefono VARCHAR(45),
                                                        clienteEmail VARCHAR(45),
                                                        depositoNombre VARCHAR(45),
                                                        depositoTelefono VARCHAR(45),
                                                        depositoEmail VARCHAR(45),
                                                        habilita VARCHAR(1),
                                                        id_empresa INT)
BEGIN

DECLARE newID INT;

IF (id_proveedor = 0) THEN
    INSERT INTO proveedor (nombre, direccion,  id_localidad, id_departamento, id_provincia,
                        codPostal, telefono1, telefono2, email, cuit, 
                        gerente, cuentaBanco, id_formaPago, observaciones, habilita, 
                        id_empresa, gln)
                        
                VALUES (nombre, direccion,  id_localidad, id_departamento, id_provincia,
                        codPostal, telefono1, telefono2, email, cuit, 
                        gerente, cuentaBanco, id_formaPago, observaciones, habilita, 
                        id_empresa, gln);
                        
    SET newID = (SELECT MAX(proveedor.id_proveedor) FROM proveedor WHERE proveedor.id_empresa = id_empresa);
                        
ELSE
    
    UPDATE proveedor SET
        proveedor.nombre = nombre, 
        proveedor.direccion = direccion, 
        proveedor.id_localidad = id_localidad,
        proveedor.id_departamento = id_departamento, 
        proveedor.id_provincia = id_provincia, 
        proveedor.codPostal = codPostal, 
        proveedor.telefono1 = telefono1, 
        proveedor.telefono2 = telefono2, 
        proveedor.email = email, 
        proveedor.cuit = cuit, 
        proveedor.gerente = gerente, 
        proveedor.cuentaBanco = cuentaBanco, 
        proveedor.id_formaPago = id_formaPago,
        proveedor.observaciones = observaciones, 
        proveedor.gln = gln, 
        proveedor.habilita = habilita
        
    WHERE proveedor.id_proveedor = id_proveedor;
    
    SET newID = id_proveedor;
    
END IF;

DELETE FROM encargadopago WHERE encargadopago.id_proveedor = newID;
DELETE FROM atencioncliente WHERE atencioncliente.id_proveedor = newID;
DELETE FROM encargadodeposito WHERE encargadodeposito.id_proveedor = newID;

INSERT INTO encargadopago (id_proveedor, nombre, telefono, email)
VALUES (newID, pagoNombre, pagoTelefono, pagoEmail);

INSERT INTO atencioncliente (id_proveedor, nombre, telefono, email)
VALUES (newID, clienteNombre, clienteTelefono, clienteEmail);

INSERT INTO encargadodeposito (id_proveedor, nombre, telefono, email)
VALUES (newID, depositoNombre, depositoTelefono, depositoEmail);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertRemito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertRemito`(IN id_remito INT,
                                                    id_presupuesto INT,
                                                    fecha DATE,
                                                    id_tipoComp INT,
                                                    numComp VARCHAR(12),
                                                    id_entidad INT,
                                                    id_proveedor INT,
                                                    id_destino INT,
                                                    id_zonaDestino INT,
                                                    id_zona INT,
                                                    observaciones TEXT,
                                                    cajas TEXT,
                                                    sets TEXT,
                                                    id_empresa INT,
                                                    usuario VARCHAR(45),
                                                    equipo VARCHAR(255))
BEGIN

DECLARE log TEXT;

IF (id_remito = 0) THEN
    INSERT INTO remito (id_presupuesto, fecha, id_tipoComp, numComp, id_entidad, 
                        id_proveedor, id_destino, id_zona, observaciones, id_empresa, 
                        usuario, id_zonaDestino, cajas, sets)
    VALUES (id_presupuesto, fecha, id_tipoComp, numComp, id_entidad, 
            id_proveedor, id_destino, id_zona, observaciones, id_empresa, 
            usuario, id_zonaDestino, cajas, sets);
            
	SET log = CONCAT("Crea remito ", numComp, ", id_remito = ", proximoRemito(id_empresa), " a presupuesto ", id_presupuesto);
ELSE
    UPDATE remito SET
        remito.id_presupuesto = id_presupuesto,
        remito.fecha = fecha,
        remito.id_tipoComp = id_tipoComp,
        remito.numComp = numComp,
        remito.id_entidad = id_entidad,
        remito.id_proveedor = id_proveedor,
        remito.id_destino = id_destino,
        remito.id_zonaDestino = id_zonaDestino,
        remito.id_zona = id_zona,
        remito.observaciones = observaciones,
        remito.cajas = cajas,
        remito.sets = sets,
        remito.usuario = usuario,
        remito.fechaReal = CURRENT_TIMESTAMP
        
    WHERE remito.id_remito = id_remito;
    SET log = CONCAT("Actualiza remito ", numComp, ", id_remito = ", id_remito);
END IF;

CALL logSistema(usuario, equipo, log);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertRemitoDetalle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertRemitoDetalle`(IN id_remito INT,
                                                            id_producto INT,
                                                            cantidad INT,
                                                            dc VARCHAR(1),
                                                            lote VARCHAR(45),
                                                            serie VARCHAR(45),
                                                            pm VARCHAR(45),
                                                            vencimiento DATE,
                                                            costo DECIMAL(12,2),
                                                            id_moneda INT,
                                                            observaciones TEXT,
                                                            id_zona INT,
                                                            id_empresa INT)
BEGIN

INSERT INTO stockdetalle (id_remito, id_producto, cantidad, dc, lote, serie,
                            pm, vencimiento, costoPesos, id_moneda, cotizacion, 
                            observaciones, id_zona)
                            
VALUES (id_remito, id_producto, cantidad, dc, lote, serie,
        pm, vencimiento, costo, id_moneda, IFNULL(ultimaCotizacion(id_moneda), 1), 
        observaciones, id_zona);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertRevisionSoftware` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertRevisionSoftware`(IN detalleRevision TEXT)
BEGIN

INSERT INTO revisionSoftware (detalleRevision)
                VALUES (detalleRevision);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertStock` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertStock`(IN id_stock INT,
                                                    fecha DATE,
                                                    id_tipoComp INT,
                                                    numComp VARCHAR(12),
                                                    id_proveedor INT,
                                                    id_zona INT,
                                                    observaciones TEXT,
                                                    id_empresa INT,
                                                    usuario VARCHAR(45))
BEGIN

IF (id_stock = 0) THEN
    INSERT INTO stock (fecha, id_tipoComp, numComp, id_proveedor, id_zona,
                        observaciones, id_empresa, usuario)
    VALUES (fecha, id_tipoComp, numComp, id_proveedor, id_zona,
            observaciones, id_empresa, usuario);
ELSE
    UPDATE stock SET
        stock.fecha = fecha,
        stock.id_tipoComp = id_tipoComp,
        stock.numComp = numComp,
        stock.id_proveedor = id_proveedor,
        stock.id_zona = id_zona,
        stock.observaciones = observaciones,
        stock.usuario = usuario,
        stock.fechaReal = CURRENT_TIMESTAMP
        
    WHERE stock.id_stock = id_stock;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertStockDetalle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertStockDetalle`(IN id_stock INT,
                                                            id_producto INT,
                                                            cantidad INT,
                                                            dc VARCHAR(1),
                                                            lote VARCHAR(45),
															serie VARCHAR(255),
                                                            pm VARCHAR(45),
                                                            vencimiento DATE,
                                                            costoPesos DECIMAL(12,2),
                                                            observaciones TEXT,
                                                            id_zona INT,
                                                            id_empresa INT)
BEGIN

DECLARE id INT;
DECLARE id_moneda INT;
DECLARE cotizacion DECIMAL(12,3);

IF id_stock = 0 THEN

    SET id = (SELECT max(stock.id_stock) 
                FROM stock 
                WHERE stock.id_empresa = id_empresa);
    
ELSEIF id_stock > 0 THEN

    SET id = id_stock;    
    
END IF;

SELECT producto.id_moneda INTO id_moneda 
	FROM producto 
	WHERE producto.id_producto = id_producto;

SET cotizacion = 1.000;
SELECT IFNULL(cotizacion.valor, 1.000) INTO cotizacion 
	FROM cotizacion 
    WHERE cotizacion.id_moneda = id_moneda AND cotizacion.id_empresa = id_empresa 
    ORDER BY cotizacion.id_cotizacion DESC LIMIT 1;

INSERT INTO stockdetalle (id_stock, id_producto, cantidad, dc, lote, serie,
                            pm, vencimiento, costoPesos, id_moneda, cotizacion, 
                            observaciones, id_zona)
VALUES (id, id_producto, cantidad, dc, lote, serie,
        pm, vencimiento, costoPesos, id_moneda, cotizacion, 
        observaciones, id_zona);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertSubespecialidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertSubespecialidad`(IN id_subespecialidad INT,
                                                        id_especialidad INT,
                                                        nombre VARCHAR(45),
                                                        habilita VARCHAR(1))
BEGIN

IF (id_subespecialidad = 0) THEN
    
    INSERT INTO subespecialidad (id_especialidad, nombre, habilita)
                            
    VALUES (id_especialidad, nombre, habilita);
            
ELSE

    UPDATE subespecialidad SET
        id_especialidad = id_especialidad, 
        nombre = nombre,
        habilita = habilita
    
    WHERE subespecialidad.id_subespecialidad = id_subespecialidad;
    
END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertTecnico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertTecnico`(IN id_tecnico INT,
                                                    nombre VARCHAR(45),
                                                    habilita VARCHAR(1),
                                                    id_empresa INT)
BEGIN

IF (id_tecnico = 0) THEN

    INSERT INTO tecnico (nombre, habilita, id_empresa)
        VALUES (nombre, habilita, id_empresa);
        
ELSE

    UPDATE tecnico SET
        nombre = nombre,
        habilita = habilita
        
    WHERE tecnico.id_tecnico = id_tecnico;

END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertUsuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertUsuario`(IN id_usuario INT,
                                                        nombre VARCHAR(45),
                                                        documento VARCHAR(10),
                                                        cuil VARCHAR(11),
                                                        fechaNac DATE,
                                                        direccion VARCHAR(100),
                                                        codPostal VARCHAR(10),
                                                        id_provincia INT,
                                                        id_departamento INT,
                                                        id_localidad INT,
                                                        fechaIngreso DATE,
                                                        fechaEgreso DATE,
                                                        usaSistema VARCHAR(1),
                                                        contraseña VARCHAR(15),
                                                        email VARCHAR(45),
                                                        id_zona INT,
                                                        habilita VARCHAR(1),
                                                        id_empresa INT)
BEGIN

IF (id_usuario = 0) THEN
    
    INSERT INTO usuario (nombre, documento, cuil, fechaNac, direccion,
                        codPostal, id_provincia, id_departamento, id_localidad, fechaIngreso,
                        fechaEgreso, usaSistema,contraseña, email, habilita, 
                        id_empresa, id_zona)
                VALUES (nombre, documento, cuil, fechaNac, direccion,
                        codPostal, id_provincia, id_departamento, id_localidad, fechaIngreso,
                        fechaEgreso, usaSistema,contraseña, email, habilita, 
                        id_empresa, id_zona);
    
ELSE
    
    UPDATE usuario SET
        usuario.nombre = nombre,
        usuario.documento = documento,
        usuario.cuil = cuil,
        usuario.fechaNac = fechaNac,
        usuario.direccion = direccion,
        usuario.codPostal = codPostal,
        usuario.id_provincia = id_provincia,
        usuario.id_departamento = id_departamento,
        usuario.id_localidad = id_localidad,
        usuario.fechaIngreso = fechaIngreso,
        usuario.fechaEgreso = fechaEgreso,
        usuario.usaSistema = usaSistema,
        usuario.contraseña = contraseña,
        usuario.email = email,
        usuario.id_zona = id_zona,
        usuario.habilita = habilita
    
    WHERE usuario.id_usuario = id_usuario;
    
END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertVendedor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertVendedor`(IN id_vendedor INT,
                                                        nombre VARCHAR(45),
                                                        id_zona INT,
                                                        id_especialidad INT,
                                                        comision DECIMAL(5,2),
                                                        beneficio VARCHAR(1),
                                                        habilita VARCHAR(1),
                                                        id_empresa INT)
BEGIN

IF (id_vendedor = 0) THEN
    
    INSERT INTO vendedor (nombre, id_zona, id_especialidad, comision, beneficio,
                            habilita, id_empresa)
                            
    VALUES (nombre, id_zona, id_especialidad, comision, beneficio,
            habilita, id_empresa);
            
ELSE

    UPDATE vendedor SET
        nombre = nombre,
        id_zona = id_zona, 
        id_especialidad = id_especialidad,
        comision = comision,
        beneficio = beneficio,
        habilita = habilita
    
    WHERE vendedor.id_vendedor = id_vendedor;
    
END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertZona` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `insertZona`(IN id_zona INT,
                                                    nombre VARCHAR(45),
                                                    habilita VARCHAR(1))
BEGIN

IF (id_zona = 0) THEN

    INSERT INTO zona (nombre, habilita)
        VALUES (nombre, habilita);
        
ELSE

    UPDATE zona SET
        nombre = nombre,
        habilita = habilita
        
    WHERE zona.id_zona = id_zona;

END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `liquidaMayorProfesional` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `liquidaMayorProfesional`(IN id_mayorProfesional INT,
                                                                id_liquidacion INT,
                                                                id_empresa INT,
                                                                usuario VARCHAR(45))
BEGIN

UPDATE mayorProfesional SET
    mayorProfesional.liquidacion = id_liquidacion,
    mayorProfesional.usuario = usuario,
    mayorProfesional.fechaReal = CURRENT_TIMESTAMP
    
WHERE mayorProfesional.id_mayorProfesional = id_mayorProfesional;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `liquidaMayorTecnico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `liquidaMayorTecnico`(IN id_mayorTecnico INT,
                                                                usuario VARCHAR(45),
                                                                id_empresa INT)
BEGIN

DECLARE numeroLiquidacion INT;

SELECT tecnico INTO numeroLiquidacion 
FROM numeracionLiquidaciones 
WHERE numeracionLiquidaciones.id_empresa = id_empresa;

UPDATE mayortecnico SET
    mayortecnico.liquidacion = numeroLiquidacion,
    mayortecnico.usuario = usuario,
    mayortecnico.fechaReal = CURRENT_TIMESTAMP
WHERE mayortecnico.id_mayorTecnico = id_mayorTecnico;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `listaPresupuesto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `listaPresupuesto`(IN id_empresa INT(11), meses INT(2))
BEGIN
SELECT id_presupuesto as numero, DATE_FORMAT(fecha,'%d/%m/%Y') AS fecha, IFNULL(entidad.nombre,'') as entidad, IFNULL(lugar.nombre,'') as lugar,
    IFNULL(profesional.nombre,'') as profesional, paciente, presupuesto.dni as dni, presupuesto.total as monto, ifnull(zona.nombre, '') as zona,
    IFNULL(tipooperacion.nombre, '') as operacion, estado, rm1, rm2, rm3, costo_venta
    
    FROM presupuesto
    
    LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad
    LEFT JOIN entidad lugar ON lugar.id_entidad = presupuesto.id_lugarCirugia    
    LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
    LEFT JOIN zona ON presupuesto.id_zona = zona.id_zona
    LEFT JOIN tipoOperacion ON tipoOperacion.id_tipoOperacion = presupuesto.id_tipoOperacion
    
    WHERE 
        presupuesto.id_empresa = id_empresa AND
        presupuesto.fecha >= DATE_SUB(CURDATE(), interval meses month)
        
    ORDER BY numero DESC; 
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `logSistema` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `logSistema`(IN usuario VARCHAR(45),
															equipo VARCHAR(255),
															log TEXT)
BEGIN

insert into logsistema (usuario, equipo, log)
				VALUES (usuario, equipo, log);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `mapaConfirmaTurno` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `mapaConfirmaTurno`(IN id_empresa INT)
BEGIN

SELECT cajaasignada.id_presupuesto as presupuesto, cajadeposito.codigo as codigo, caja.nombre as caja, 
        DATE_FORMAT(presupuesto.fechaCirugia,'%d/%m/%Y') AS fechaCirugia, entidad.nombre as lugarCirugia, 
        zona.nombre as zona
        
FROM cajaAsignada

LEFT JOIN presupuesto ON presupuesto.id_presupuesto = cajaasignada.id_presupuesto
LEFT JOIN cajadeposito ON cajadeposito.id_cajadeposito = cajaasignada.id_cajadeposito
LEFT JOIN caja ON caja.id_caja = cajadeposito.id_caja
LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad
LEFT JOIN zona ON zona.id_zona = presupuesto.id_zona

WHERE cajaasignada.id_presupuesto IN (SELECT presupuesto.id_presupuesto
                                        FROM presupuesto
                                        WHERE presupuesto.estado = 'C' AND
                                            presupuesto.id_empresa = id_empresa)
                                            
ORDER BY fechaCirugia DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `modificaRemitoConsumido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `modificaRemitoConsumido`(IN id_remito INT,
                                                        observaciones TEXT,
                                                        usuario VARCHAR(45))
BEGIN

UPDATE remito SET
    
    remito.observaciones = observaciones,
    remito.usuario = usuario,
    remito.fechaReal = CURRENT_TIMESTAMP
    
WHERE remito.id_remito = id_remito;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `nuevoInicial` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `nuevoInicial`(IN id_empresa INT,
                                                                                ids TEXT,
                                                                                desde DATE,
                                                                                hasta DATE,
                                                                                lote VARCHAR(45),
                                                                                id_zona INT)
BEGIN
SELECT ifnull(producto.codigo, '') as codigo, ifnull(producto.gtin, '') as gtin, ifnull(producto.nombre, '') as producto, '' as dc, -1 as cantidad, '' as pm, '' as lote, null as vencimiento,
	'' as observaciones, ifnull(desde, '1990-01-01') as fecha,
	if(desde is null, 0, sum(if(dc = 'D', cantidad, -cantidad))) as saldoInicial
    
    FROM stockdetalle
    left join producto on producto.id_producto = stockdetalle.id_producto
    
    WHERE is_id_in_ids(ids, stockdetalle.id_producto) and
			(stockdetalle.id_stock IN (SELECT id_stock 
                                    FROM stock 
                                    WHERE stock.id_empresa = id_empresa AND
                                    IF(desde IS NULL, true, stock.fecha < desde) AND
                                    IF(id_zona = 0, true, stock.id_zona = id_zona)) OR
                                    
        stockdetalle.id_remito IN (SELECT id_remito
                                    FROM remito
                                    WHERE remito.id_empresa = id_empresa AND
                                    IF(desde IS NULL, true, remito.fecha < desde) AND
                                    IF(id_zona = 0, true, remito.id_zona = id_zona)) OR
                                    
        stockdetalle.id_ajusteStock IN (SELECT id_ajusteStock
                                    FROM ajusteStock
                                    WHERE ajusteStock.id_empresa = id_empresa AND
                                    IF(desde IS NULL, true, ajusteStock.fecha < desde) AND
                                    IF(id_zona = 0, true, ajusteStock.id_zona = id_zona)))
	GROUP BY stockdetalle.id_producto
    ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `nuevoPresu` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `nuevoPresu`(IN id_presupuesto INT,
									id_zona INT,
									usuario VARCHAR(45))
BEGIN

INSERT INTO presupuesto 

		VALUES (id_presupuesto, 'P', CURRENT_DATE(), id_zona, 0, 
					0, 'N', null, '', '',
                    '','XXXXX', 'N', '99999999', '',
                    '', 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 
                    '', 0, '', 0, '',
                    '', '', '', 0, '',
                    null, 0, 0, 0, null,
                    null, '', '', 'N', 1,
                    current_timestamp(), usuario); 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `productosForecast` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `productosForecast`(IN anio INT,
																id_vendedor INT,
                                                                id_forecastGrupo INT)
BEGIN

select productoFact.codigo, productoFact.nombre, sum(cantidad) as cantidad, forecast.id_productoFact
from forecast
left join productoFact on productoFact.id_productoFact = forecast.id_productoFact
where forecast.anio = anio AND
		forecast.id_vendedor = id_vendedor AND
        IF(id_forecastGrupo = 0, true, forecast.id_productoFact IN (SELECT productoFact.id_productoFact
																		FROM productoFact
                                                                        WHERE productoFact.id_forecastGrupo = id_forecastGrupo))
group by productoFact.codigo
order by productoFact.codigo;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `recibeIntersucursal` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `recibeIntersucursal`(IN id_remito INT,
											observaciones TEXT,
											usuario VARCHAR(45))
BEGIN

UPDATE intersucursal SET 
	intersucursal.recibido = 'S',
    intersucursal.usuario = usuario,
    intersucursal.fechaReal = CURRENT_TIMESTAMP

WHERE intersucursal.id_remito = id_remito;

UPDATE remito SET
	remito.observaciones = observaciones

WHERE remito.id_remito = id_remito;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `recibeRemito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `recibeRemito`(IN id_remito INT,
															usuario VARCHAR(45),
                                                            equipo VARCHAR(255))
BEGIN

DECLARE log TEXT;

UPDATE remito SET
	remito.recibido = "S",
    remito.fechaConsumido = CURRENT_DATE,
    remito.usuario = usuario,
    remito.fechaReal = CURRENT_TIMESTAMP
    
WHERE remito.id_remito = id_remito;

SET log = CONCAT("Recibe remito, id_remito = ", id_remito);

CALL logSistema(usuario, equipo, log);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `selectDepartamento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `selectDepartamento`()
BEGIN

SELECT *

FROM departamento

ORDER BY nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `selectEntidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `selectEntidad`(IN id_empresa INT,
                                                soloHabilitados BOOLEAN)
BEGIN

SELECT entidad.id_entidad, entidad.nombre, entidad.direccion, entidad.id_provincia, entidad.id_departamento,
        entidad.id_localidad,entidad.codPostal, entidad.email, entidad.telefono1, entidad.telefono2, 
        entidad.auditor, entidad.secretaria, entidad.id_zona, entidad.certImplante, entidad.recomendaciones, 
        entidad.id_formaPago, entidad.id_opcion, entidad.cuit, entidad.id_clasiEntidad, entidad.riesgoCredito, 
        entidad.reqFacturacion, entidad.observaciones, entidad.habilita, 
        
        provincia.nombre as provincia, departamento.nombre as departamento, localidad.nombre as localidad, zona.nombre as zona,
        formapago.nombre as formaPago, opcion.nombre as posicionIva, clasiEntidad.nombre as clasiEntidad,
        encargadocompras.nombre as comprasNombre, encargadocompras.telefono as comprasTelefono,
        encargadocompras.email as comprasEmail, encargadotesoreria.nombre as tesoreriaNombre, 
        encargadotesoreria.telefono as tesoreriaTelefono, encargadotesoreria.email as tesoreriaEmail, 
        encargadocontable.nombre as contableNombre, encargadocontable.telefono as contableTelefono,
        encargadocontable.email as contableEmail, encargadofarmacia.nombre as farmaciaNombre, 
        encargadofarmacia.telefono as farmaciaTelefono, encargadofarmacia.email as farmaciaEmail
        
FROM entidad

LEFT JOIN provincia ON provincia.id_provincia = entidad.id_provincia
LEFT JOIN departamento ON departamento.id_departamento = entidad.id_departamento
LEFT JOIN localidad ON localidad.id_localidad = entidad.id_localidad
LEFT JOIN zona ON zona.id_zona = entidad.id_zona
LEFT JOIN formapago ON formapago.id_formapago = entidad.id_formapago
LEFT JOIN opcion ON opcion.id_opcion = entidad.id_opcion
LEFT JOIN clasientidad ON clasientidad.id_clasientidad = entidad.id_clasientidad
LEFT JOIN encargadocompras ON encargadocompras.id_entidad = entidad.id_entidad
LEFT JOIN encargadotesoreria ON encargadotesoreria.id_entidad = entidad.id_entidad
LEFT JOIN encargadocontable ON encargadocontable.id_entidad = entidad.id_entidad
LEFT JOIN encargadofarmacia ON encargadofarmacia.id_entidad = entidad.id_entidad

WHERE entidad.id_empresa = id_empresa AND
    entidad.habilita <> IF(soloHabilitados, 'N', 'X')
    
ORDER BY entidad.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `selectLocalidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `selectLocalidad`()
BEGIN

SELECT *

FROM localidad

ORDER BY nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `selectProfesional` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `selectProfesional`(id_empresa INT,
                                                        soloHabilitados BOOLEAN)
BEGIN

SELECT profesional.id_profesional, profesional.nombre, profesional.direccion, profesional.id_provincia,profesional.id_departamento, 
        profesional.id_localidad, profesional.codPostal, profesional.fechaNac, profesional.dni, profesional.matricula, 
        profesional.contacto, profesional.perfil, profesional.email, profesional.telParticular, profesional.telMovil, 
        profesional.telOtros, profesional.telConsultorio, profesional.secretaria, profesional.dirConsultorio, profesional.id_especialidad, 
        profesional.id_subespecialidad, profesional.id_zona, profesional.id_vendedor, profesional.id_entidad, profesional.observaciones, 
        profesional.habilita, 
        
        IFNULL(provincia.nombre,'') as provincia, IFNULL(departamento.nombre,'') as departamento, IFNULL(localidad.nombre,'') as localidad, 
        IFNULL(especialidad.nombre,'') as especialidad, IFNULL(subespecialidad.nombre,'') as subespecialidad, 
        IFNULL(zona.nombre,'') as zona, IFNULL(vendedor.nombre,'') as vendedor, IFNULL(entidad.nombre,'') as entidad
        
FROM profesional

LEFT JOIN provincia ON provincia.id_provincia = profesional.id_provincia
LEFT JOIN departamento ON departamento.id_departamento = profesional.id_departamento
LEFT JOIN localidad ON localidad.id_localidad = profesional.id_localidad
LEFT JOIN especialidad ON especialidad.id_especialidad = profesional.id_especialidad
LEFT JOIN subespecialidad ON subespecialidad.id_subespecialidad = profesional.id_subespecialidad
LEFT JOIN zona ON zona.id_zona = profesional.id_zona
LEFT JOIN vendedor ON vendedor.id_vendedor = profesional.id_vendedor
LEFT JOIN entidad ON entidad.id_entidad = profesional.id_entidad

WHERE profesional.id_empresa = id_empresa AND
     IF(soloHabilitados, profesional.habilita = 'S', true)
    
ORDER BY profesional.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `selectSubespecialidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `selectSubespecialidad`(IN soloHabilitados BOOLEAN)
BEGIN

SELECT *

FROM subespecialidad

WHERE IF(soloHabilitados, habilita = 'S', true)

ORDER BY nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `selectVendedor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `selectVendedor`(IN id_empresa INT,
                                            soloHabilitados BOOLEAN)
BEGIN

SELECT vendedor.id_vendedor, vendedor.nombre, vendedor.id_zona, vendedor.id_especialidad, vendedor.comision, 
        vendedor.beneficio, vendedor.habilita, 
        
        zona.nombre AS zona, especialidad.nombre AS especialidad
    
FROM vendedor

LEFT JOIN zona ON zona.id_zona = vendedor.id_zona
LEFT JOIN especialidad ON especialidad.id_especialidad = vendedor.id_especialidad

WHERE vendedor.id_empresa = id_empresa AND
    IF(soloHabilitados, vendedor.habilita = 'S', true)
    
ORDER BY vendedor.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `stockCajas` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `stockCajas`(IN id_zona INT,
															id_caja INT,
                                                            id_empresa INT)
BEGIN

SELECT caja.nombre as caja, cajadeposito.codigo as codigo, zona.nombre as zona

FROM cajadeposito

left join caja on caja.id_caja = cajadeposito.id_caja
left join zona on zona.id_zona = cajadeposito.id_zona

where IF(id_zona = 0, true, cajadeposito.id_zona = id_zona) AND
		IF(id_caja = 0, true, cajadeposito.id_caja = id_caja) AND
		cajadeposito.id_empresa = id_empresa AND
		cajadeposito.habilita = 'S'
        
order by caja.nombre, zona.nombre, cajadeposito.codigo;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `stockSaldoInicial` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `stockSaldoInicial`(IN id_zona INT,
                                                            id_producto INT,
                                                            desde DATE,
                                                            id_empresa INT)
BEGIN

SELECT SUM(IF(stockdetalle.dc = 'D', stockdetalle.cantidad, -stockdetalle.cantidad)) AS saldo

FROM stockdetalle

WHERE stockdetalle.id_producto = id_producto AND
    IF(stockdetalle.id_remito = 0, true, stockdetalle.id_remito IN (SELECT remito.id_remito
                                                                    FROM remito
                                                                    WHERE remito.fecha < desde AND
                                                                        (remito.id_destino = id_zona OR remito.id_zona = id_zona) AND
                                                                        remito.id_empresa = id_empresa)) AND
    stockdetalle.id_stock IN (SELECT stock.id_stock
                                FROM stock
                                WHERE stock.fecha < desde AND
                                    stock.id_zona = id_zona AND
                                    stock.id_empresa = id_empresa);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `suspendeAnulaPresupuesto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `suspendeAnulaPresupuesto`(IN id_presupuesto INT,
												estado VARCHAR(1),
                                                id_motivo INT,
                                                motivo TEXT,
                                                usuario VARCHAR(45),
                                                equipo VARCHAR(255))
BEGIN

DECLARE log TEXT;
DECLARE accion VARCHAR(10);

UPDATE presupuesto SET
	presupuesto.estado = estado,
    presupuesto.id_motivo = id_motivo,
    presupuesto.motivo = motivo,
    presupuesto.usuario = usuario,
    presupuesto.fechaReal = current_timestamp
    
WHERE presupuesto.id_presupuesto = id_presupuesto;

CASE estado
	WHEN 'N' THEN SET accion = 'Anula';
    WHEN 'S' THEN SET accion = 'Suspende';
END CASE;

SET log = CONCAT(accion, " turno ", id_presupuesto);

CALL logSistema(usuario, equipo, log);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traeAccesos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traeAccesos`(IN id_usuario INT,
                                                    id_empresa INT)
BEGIN

SELECT menu.nombre, IF(acceso.id_acceso IS NULL, 'NO', 'SI') as habilita, menu.id_menu, menu.posicion

FROM menu

LEFT JOIN acceso ON acceso.id_menu = menu.id_menu AND 
                    acceso.id_usuario = id_usuario AND 
                    acceso.id_empresa = id_empresa

WHERE menu.accion > 0 AND
        menu.id_empresa = id_empresa AND
        menu.habilita = 'S'
        
ORDER BY menu.posicion;
    

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traeAltaStock` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traeAltaStock`(IN id_stock INT, 
                                                        id_empresa INT)
BEGIN

SELECT stock.fecha, tipocomp.abreviatura as tipoComp, stock.numComp, proveedor.nombre as proveedor, 
        zona.nombre as zona, stock.observaciones
        
FROM stock

LEFT JOIN tipocomp ON tipocomp.id_tipoComp = stock.id_tipoComp
LEFT JOIN proveedor ON proveedor.id_proveedor = stock.id_proveedor
LEFT JOIN zona ON zona.id_zona = stock.id_zona

WHERE stock.id_stock = id_stock AND 
        stock.id_empresa = id_empresa;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traeCajaZona` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traeCajaZona`(IN id_zona INT,
                                                    id_empresa INT)
BEGIN

SELECT cajadeposito.codigo, caja.nombre as caja, zona.nombre as zona, cajadeposito.id_cajadeposito, cajadeposito.id_caja

FROM cajadeposito

LEFT JOIN caja ON caja.id_caja = cajadeposito.id_caja
LEFT JOIN zona ON zona.id_zona = cajadeposito.id_zona

WHERE cajadeposito.id_zona = id_zona AND
        cajadeposito.id_empresa = id_empresa AND
        cajadeposito.habilita = 'S'
        
ORDER BY cajadeposito.codigo;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traeEntidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traeEntidad`(IN id_entidad INT)
BEGIN

SELECT entidad.id_entidad, entidad.nombre, entidad.direccion, entidad.codPostal, entidad.email,
        entidad.telefono1, entidad.telefono2, entidad.auditor, entidad.secretaria, entidad.certImplante,
        entidad.recomendaciones, entidad.cuit, entidad.riesgoCredito, entidad.reqFacturacion,
        entidad.observaciones, entidad.habilita, provincia.nombre as provincia,
        departamento.nombre as departamento, localidad.nombre as localidad, zona.nombre as zona,
        formapago.nombre as formaPago, opcion.nombre as posicionIva, clasiEntidad.nombre as clasiEntidad,
        encargadocompras.nombre as comprasNombre, encargadocompras.telefono as comprasTelefono,
        encargadocompras.email as comprasEmail, encargadotesoreria.nombre as tesoreriaNombre, 
        encargadotesoreria.telefono as tesoreriaTelefono, encargadotesoreria.email as tesoreriaEmail, 
        encargadocontable.nombre as contableNombre, encargadocontable.telefono as contableTelefono,
        encargadocontable.email as contableEmail, encargadofarmacia.nombre as farmaciaNombre, 
        encargadofarmacia.telefono as farmaciaTelefono, encargadofarmacia.email as farmaciaEmail
        
FROM entidad

LEFT JOIN provincia ON provincia.id_provincia = entidad.id_provincia
LEFT JOIN departamento ON departamento.id_departamento = entidad.id_departamento
LEFT JOIN localidad ON localidad.id_localidad = entidad.id_localidad
LEFT JOIN zona ON zona.id_zona = entidad.id_zona
LEFT JOIN formapago ON formapago.id_formapago = entidad.id_formapago
LEFT JOIN opcion ON opcion.id_opcion = entidad.id_opcion
LEFT JOIN clasientidad ON clasientidad.id_clasientidad = entidad.id_clasientidad
LEFT JOIN encargadocompras ON encargadocompras.id_entidad = entidad.id_entidad
LEFT JOIN encargadotesoreria ON encargadotesoreria.id_entidad = entidad.id_entidad
LEFT JOIN encargadocontable ON encargadocontable.id_entidad = entidad.id_entidad
LEFT JOIN encargadofarmacia ON encargadofarmacia.id_entidad = entidad.id_entidad

WHERE entidad.id_entidad = id_entidad;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traeLiquidacionTecnico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traeLiquidacionTecnico`(IN nroLiquidacion INT,
                                                                id_empresa INT)
BEGIN

DECLARE numero INT;

IF nroLiquidacion = 0 THEN
    SELECT tecnico INTO numero 
        FROM numeracionLiquidaciones 
        WHERE numeracionLiquidaciones.id_empresa = id_empresa;
ELSE
    SET numero = nroLiquidacion;
END IF;

SELECT presupuesto.id_presupuesto, presupuesto.fechaCirugia,
        profesional.nombre as profesional, presupuesto.paciente, mayortecnico.importe as importe,
        tecnico.nombre as tecnico, mayortecnico.liquidacion as liquidacion
        
FROM presupuesto

LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
LEFT JOIN mayortecnico ON mayortecnico.id_presupuesto = presupuesto.id_presupuesto
LEFT JOIN tecnico ON tecnico.id_tecnico = presupuesto.id_tecnico

WHERE presupuesto.id_presupuesto IN (SELECT mayortecnico.id_presupuesto
                                        FROM mayortecnico
                                        WHERE mayortecnico.liquidacion = numero AND
                                            mayortecnico.id_empresa = id_empresa)
                                            
ORDER BY tecnico.nombre, presupuesto.id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traePagosLiquidacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traePagosLiquidacion`(IN id_liquidacion INT)
BEGIN

SELECT liquidaciondetalle.banco, liquidaciondetalle.cheque, 
    IFNULL(DATE_FORMAT(liquidaciondetalle.vencimiento, '%d/%m/%Y'), '') as vencimiento,
    liquidaciondetalle.importe
    
FROM liquidaciondetalle

WHERE liquidaciondetalle.id_liquidacion = id_liquidacion;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traePorcentaje` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traePorcentaje`(IN id_grupoPorcentaje INT,
                                                        id_especialidad INT,
                                                        id_zona INT,
                                                        id_empresa INT)
BEGIN

SELECT porcentaje.valor

FROM porcentaje

WHERE IF(id_grupoPorcentaje = 0, true, porcentaje.id_grupoPorcentaje = id_grupoPorcentaje) AND
      IF(id_especialidad = 0, true, porcentaje.id_especialidad = id_especialidad) AND
      IF(id_zona = 0, true, porcentaje.id_zona = id_zona) AND
      porcentaje.id_empresa = id_empresa;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traePresupuesto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traePresupuesto`(IN id_presupuesto INT(11))
BEGIN

SELECT presupuesto.id_presupuesto, presupuesto.estado, presupuesto.fecha, IFNULL(zona.nombre,'') as zona, 
        IFNULL(entidad.nombre, '') as entidad, IFNULL(lugar.nombre,'') as lugar, presupuesto.acompañamiento, 
        presupuesto.fechaApertura, presupuesto.horaApertura, presupuesto.expediente, presupuesto.contratacion, 
        presupuesto.paciente, presupuesto.vip, presupuesto.dni, presupuesto.direccion, presupuesto.telefono, presupuesto.total,
        IFNULL(profesional1.nombre,'') as profesional1, presupuesto.rm1, 
        IFNULL(profesional2.nombre,'') as profesional2, presupuesto.rm2, 
        IFNULL(profesional3.nombre,'') as profesional3, presupuesto.rm3, 
        IFNULL(vendedor.nombre,'') as vendedor, IFNULL(tipoOperacion.nombre,'') as tipoOperacion,
        IFNULL(formaPago.nombre,'') as formaPago, presupuesto.formaPagoOtro,
        IFNULL(plazo.nombre,'') as plazo, presupuesto.plazoOtro,
        IFNULL(mantenimiento.nombre,'') as mantenimiento, presupuesto.mantenimientoOtro,
        presupuesto.notaPresu, presupuesto.observaciones, IFNULL(motivo.nombre,'') as id_motivo, presupuesto.motivo,
        presupuesto.notaExtra, presupuesto.costo_venta
        
FROM presupuesto

LEFT JOIN zona ON zona.id_zona = presupuesto.id_zona
LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad
LEFT JOIN entidad lugar ON lugar.id_entidad = presupuesto.id_lugarCirugia
LEFT JOIN profesional profesional1 ON profesional1.id_profesional = presupuesto.id_profesional1
LEFT JOIN profesional profesional2 ON profesional2.id_profesional = presupuesto.id_profesional2
LEFT JOIN profesional profesional3 ON profesional3.id_profesional = presupuesto.id_profesional3
LEFT JOIN vendedor ON vendedor.id_vendedor = presupuesto.id_vendedor
LEFT JOIN tipoOperacion ON tipoOperacion.id_tipoOperacion = presupuesto.id_tipoOperacion
LEFT JOIN formaPago ON formaPago.id_formaPago = presupuesto.id_formaPago
LEFT JOIN plazo ON plazo.id_plazo = presupuesto.id_plazo
LEFT JOIN mantenimiento ON mantenimiento.id_mantenimiento = presupuesto.id_mantenimiento
LEFT JOIN motivo ON motivo.id_motivo = presupuesto.id_motivo

WHERE presupuesto.id_presupuesto = id_presupuesto;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traePresupuestoProd` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traePresupuestoProd`(IN id_presupuesto INT(11))
BEGIN

SELECT presupuestoProd.cantidad, IFNULL(productoFact.codigo,'') as codigo, IFNULL(productoFact.nombre,'') as producto, 
        presupuestoProd.observaciones, presupuestoProd.pxUnit, presupuestoProd.id_productoFact, IFNULL(proveedor.nombre, '') as proveedor,
        presupuestoProd.id_proveedor
        
FROM presupuestoprod

LEFT JOIN productoFact ON productoFact.id_productoFact = presupuestoProd.id_productoFact
LEFT JOIN proveedor ON proveedor.id_proveedor = presupuestoProd.id_proveedor

WHERE presupuestoProd.id_presupuesto = id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traeProductoCodigo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traeProductoCodigo`(IN codigo VARCHAR(15),
                                                            id_empresa INT)
BEGIN

SELECT producto.id_producto, producto.codigo, producto.gtin, producto.nombre, producto.costo, producto.stockMinimo,
        producto.pm, producto.habilita, CONCAT(codconsumo.nombre, ' (', codconsumo.codigo, ')') as codConsumo,
        moneda.simbolo as moneda, clasiproducto.nombre as clasiProducto, proveedor.nombre as proveedor,
        costoPesos(producto.id_producto) as costoPesos
        
FROM producto

LEFT JOIN codconsumo ON codconsumo.id_codConsumo = producto.id_codConsumo
LEFT JOIN moneda ON moneda.id_moneda = producto.id_moneda
LEFT JOIN clasiproducto ON clasiproducto.id_clasiProducto = producto.id_clasiProducto
LEFT JOIN proveedor ON proveedor.id_proveedor = producto.id_proveedor

WHERE producto.codigo = codigo AND
        producto.id_empresa = id_empresa AND
        producto.habilita = 'S'
        
ORDER BY producto.codigo;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traeProductoId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traeProductoId`(IN id_producto INT,
                                                            id_empresa INT)
BEGIN

SELECT producto.id_producto, producto.codigo, producto.gtin, producto.nombre, producto.costo, producto.stockMinimo,
        producto.pm, producto.habilita, CONCAT(codconsumo.nombre, ' (', codconsumo.codigo, ')') as codConsumo,
        moneda.simbolo as moneda, clasiproducto.nombre as clasiProducto, proveedor.nombre as proveedor,
        costoPesos(producto.id_producto) as costoPesos
        
FROM producto

LEFT JOIN codconsumo ON codconsumo.id_codConsumo = producto.id_codConsumo
LEFT JOIN moneda ON moneda.id_moneda = producto.id_moneda
LEFT JOIN clasiproducto ON clasiproducto.id_clasiProducto = producto.id_clasiProducto
LEFT JOIN proveedor ON proveedor.id_proveedor = producto.id_proveedor

WHERE producto.id_producto = id_producto AND
        producto.habilita = 'S'
        
ORDER BY producto.codigo;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traeProfesional` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traeProfesional`(IN id_profesional INT)
BEGIN

SELECT profesional.id_profesional, profesional.nombre, profesional.direccion, profesional.codPostal, 
        profesional.fechaNac, profesional.dni, profesional.matricula, profesional.contacto, profesional.perfil,
        profesional.email, profesional.telParticular, profesional.telMovil, profesional.telOtros, 
        profesional.telConsultorio, profesional.secretaria, profesional.dirConsultorio, profesional.observaciones, 
        profesional.habilita, provincia.nombre as provincia, departamento.nombre as departamento, 
        localidad.nombre as localidad, especialidad.nombre as especialidad, subespecialidad.nombre as subespecialidad, 
        zona.nombre as zona, vendedor.nombre as vendedor, entidad.nombre as entidad
        
FROM profesional

LEFT JOIN provincia ON provincia.id_provincia = profesional.id_provincia
LEFT JOIN departamento ON departamento.id_departamento = profesional.id_departamento
LEFT JOIN localidad ON localidad.id_localidad = profesional.id_localidad
LEFT JOIN especialidad ON especialidad.id_especialidad = profesional.id_especialidad
LEFT JOIN subespecialidad ON subespecialidad.id_subespecialidad = profesional.id_subespecialidad
LEFT JOIN zona ON zona.id_zona = profesional.id_zona
LEFT JOIN vendedor ON vendedor.id_vendedor = profesional.id_vendedor
LEFT JOIN entidad ON entidad.id_entidad = profesional.id_entidad

WHERE profesional.id_profesional = id_profesional;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traeRemito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traeRemito`(IN id_remito INT,
                                                    numComp VARCHAR(12),
                                                    id_empresa INT)
BEGIN

SELECT remito.id_remito, DATE_FORMAT(remito.fecha, '%d/%m/%y') as fecha, remito.numComp, remito.observaciones,
    tipocomp.abreviatura as tipoComp, entidad.nombre as entidad, zona.nombre as zona
    
FROM remito

LEFT JOIN tipocomp ON tipocomp.id_tipoComp = remito.id_tipoComp
LEFT JOIN entidad ON entidad.id_entidad = remito.id_entidad
LEFT JOIN zona ON zona.id_zona = remito.id_zona

WHERE IF(id_remito > 0, remito.id_remito = id_remito, true)  AND
        IF(numComp <> '', remito.numComp = numComp, true) AND
        remito.id_empresa = id_empresa
        
ORDER BY remito.fecha ASC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traeRemitoCaja` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traeRemitoCaja`(IN id_remito INT)
BEGIN

SELECT cajaDeposito.codigo as codigo, caja.nombre as nombre, intersucursalCaja.id_cajaDeposito

FROM intersucursalCaja

LEFT JOIN cajadeposito ON cajaDeposito.id_cajaDeposito = intersucursalCaja.id_cajaDeposito
LEFT JOIN caja ON caja.id_caja = cajaDeposito.id_caja

WHERE intersucursalCaja.id_intersucursal IN (SELECT intersucursal.id_intersucursal
												FROM intersucursal
                                                WHERE intersucursal.id_remito = id_remito)
                                                
ORDER BY cajaDeposito.codigo;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traeRemitoDetalle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traeRemitoDetalle`(IN id_remito INT)
BEGIN

SELECT stockdetalle.id_stockdetalle, stockdetalle.cantidad, producto.codigo as codigo, producto.gtin as gtin, producto.nombre as nombre, stockdetalle.costoPesos as costo,
    stockdetalle.id_producto, stockDetalle.lote, stockDetalle.serie, stockdetalle.pm, IFNULL(DATE_FORMAT(stockdetalle.vencimiento, '%d/%m/%Y'), '') as vencimiento,
    stockdetalle.id_moneda, IFNULL(clasiProducto.nombre, '') as clasiProducto, stockDetalle.id_zona

FROM stockdetalle

LEFT JOIN producto ON producto.id_producto = stockdetalle.id_producto
LEFT JOIN clasiProducto ON clasiProducto.id_clasiProducto = producto.id_clasiProducto

WHERE stockdetalle.id_remito = id_remito AND stockdetalle.dc = 'C' AND (stockdetalle.observaciones LIKE 'RM-R%' OR stockdetalle.observaciones LIKE 'Intersucursal%')

ORDER BY clasiProducto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traeRmPresupuesto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traeRmPresupuesto`(IN id_presupuesto INT)
BEGIN

SELECT presupuesto.id_profesional1, presupuesto.rm1, 
        presupuesto.id_profesional2, presupuesto.rm2, 
        presupuesto.id_profesional3, presupuesto.rm3

FROM presupuesto

WHERE presupuesto.id_presupuesto = id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traeRubros` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traeRubros`(IN id_empresa INT)
BEGIN

SELECT menu.posicion, menu.nombre

FROM menu

WHERE menu.id_empresa = id_empresa AND
        menu.habilita = 'S' AND
        LENGTH(menu.posicion) = 1
        
ORDER BY menu.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traeStockDetalle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traeStockDetalle`(IN id_stock INT)
BEGIN

SELECT stockdetalle.id_producto, producto.codigo as codigo, producto.gtin as gtin, producto.nombre as nombre,
        stockdetalle.cantidad, stockdetalle.lote, stockdetalle.serie, stockdetalle.pm, stockdetalle.vencimiento,
        producto.costo as costoPesos, clasiproducto.nombre as clasiproducto, stockdetalle.id_stockDetalle, 
        producto.id_producto as id_producto

FROM stockdetalle

LEFT JOIN producto ON producto.id_producto = stockdetalle.id_producto
LEFT JOIN clasiproducto ON clasiproducto.id_clasiProducto = producto.id_clasiProducto

WHERE stockdetalle.id_stock = id_stock;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traeTurno` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traeTurno`(IN id_presupuesto INT)
BEGIN

SELECT presupuesto.id_presupuesto, presupuesto.fechaCirugia, presupuesto.fecha, presupuesto.paciente,
    presupuesto.telefono, presupuesto.dni, presupuesto.observaciones, presupuesto.id_entidad, presupuesto.id_zona,
    entidad.nombre AS entidad, lugar.nombre AS lugarCirugia, lugar.direccion AS dirLugarCirugia, 
    lugar.reqFacturacion AS reqFacturacion, prestacion.nombre AS prestacion, tecnico.nombre AS tecnico, 
    zona.nombre AS zona, profesional.nombre AS profesional, profesional.matricula AS matricula, 
    profesional.perfil AS perfil, vendedor.nombre as vendedor, tipoOperacion.nombre as tipoOperacion,
    especialidad.nombre as especialidad
    
FROM presupuesto

LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad
LEFT JOIN entidad lugar ON lugar.id_entidad = presupuesto.id_lugarCirugia
LEFT JOIN prestacion ON prestacion.id_prestacion = presupuesto.id_prestacion
LEFT JOIN tecnico ON tecnico.id_tecnico = presupuesto.id_tecnico
LEFT JOIN zona ON zona.id_zona = presupuesto.id_zona
LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
LEFT JOIN vendedor ON presupuesto.id_vendedor = vendedor.id_vendedor
LEFT JOIN tipoOperacion ON tipoOperacion.id_tipoOperacion = presupuesto.id_tipoOperacion
LEFT JOIN especialidad ON especialidad.id_especialidad = profesional.id_especialidad

WHERE presupuesto.id_presupuesto = id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `traeTurnosLiquidacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `traeTurnosLiquidacion`(IN id_liquidacion INT)
BEGIN

SELECT mayorprofesional.id_presupuesto, IF(mayorprofesional.id_presupuesto = 0, "Anticipo", presupuesto.paciente) as paciente, mayorprofesional.importe

FROM mayorprofesional

LEFT JOIN presupuesto ON presupuesto.id_presupuesto = mayorprofesional.id_presupuesto

WHERE mayorprofesional.liquidacion = id_liquidacion

ORDER BY mayorprofesional.id_presupuesto DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateComparativa` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `updateComparativa`(IN id_presupuesto INT,
                                                        competencia TEXT,
                                                        lobby TEXT)
BEGIN

UPDATE presupuesto SET
    presupuesto.competencia = competencia,
    presupuesto.lobby = lobby
    
WHERE presupuesto.id_presupuesto = id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateObservacionesPresupuesto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `updateObservacionesPresupuesto`(IN id_presupuesto INT,
													observaciones TEXT)
BEGIN

UPDATE presupuesto SET
	presupuesto.observaciones = observaciones
    
WHERE presupuesto.id_presupuesto = id_presupuesto;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateObservacionesStock` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `updateObservacionesStock`(IN id_stock INT,
													observaciones TEXT)
BEGIN

UPDATE stock SET
	stock.observaciones = observaciones
    
WHERE stock.id_stock = id_stock;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updatePresupuesto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `updatePresupuesto`(IN estado VARchar(1),
                                                        in fecha date,
                                                        in id_zona int(11),
                                                        in id_entidad int(11),
                                                        in id_lugarCirugia int(11),
                                                        in acompañamiento varchar(1),
                                                        in fechaApertura date,
                                                        in horaApertura varchar(4),
                                                        in expediente varchar(45),
                                                        in contratacion varchar(45),
                                                        in paciente varchar(45),
                                                        in vip varchar(1),
                                                        in dni varchar(15),
                                                        in direccion varchar(100),
                                                        in telefono varchar(45),
                                                        in total decimal(9,2),
                                                        in id_profesional1 int (11),
                                                        in rm1 decimal(7,2),
                                                        in id_profesional2 int (11),
                                                        in rm2 decimal(7,2),
                                                        in id_profesional3 int (11),
                                                        in rm3 decimal(7,2),
                                                        in id_vendedor int(11),
                                                        in id_tipoOperacion int(11),
                                                        in id_formaPago int(11),
                                                        in formaPagoOtro varchar(45),
                                                        in id_plazo int(11),
                                                        in plazoOtro varchar(45),
                                                        in id_mantenimiento int(11),
                                                        in mantenimientoOtro varchar(45),
                                                        in notaPresu text,
                                                        in observaciones text,
                                                        in id_motivo int(11),
                                                        in motivo text,
                                                        in id_presupuesto int(11),
                                                        in usuario varchar(45))
BEGIN

UPDATE `matera`.`presupuesto`
SET
`estado` = estado,
`fecha` = fecha,
`id_zona` = id_zona,
`id_entidad` = id_entidad,
`id_lugarCirugia` = id_lugarCirugia,
`acompañamiento` = acompañamiento,
`fechaApertura` = fechaApertura,
`horaApertura` = horaApertura,
`expediente` = expediente,
`contratacion` = contratacion,
`paciente` = paciente,
`vip` = vip,
`dni` = dni,
`direccion` = direccion,
`telefono` = telefono,
`total` = total,
`id_profesional1` = id_profesional1,
`rm1` = rm1,
`id_profesional2` = id_profesional2,
`rm2` = rm2,
`id_profesional3` = id_profesional3,
`rm3` = rm3,
`id_vendedor` = id_vendedor,
`id_tipoOperacion` = id_tipoOperacion,
`id_formaPago` = id_formaPago,
`formaPagoOtro` = formaPagoOtro,
`id_plazo` = id_plazo,
`plazoOtro` = plazoOtro,
`id_mantenimiento` = id_mantenimiento,
`mantenimientoOtro` = mantenimientoOtro,
`notaPresu` = notaPresu,
`observaciones` = observaciones,
`id_motivo` = id_motivo,
`motivo` = motivo,
`usuario` = usuario
WHERE `id_presupuesto` = id_presupuesto;


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateRemitoDetalle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `updateRemitoDetalle`(IN id_remito INT,
											id_producto INT,
											lote VARCHAR(45),
											serie VARCHAR(255),
                                            pm VARCHAR(45),
                                            vencimiento DATE,
                                            id_stockdetalle INT)
BEGIN

UPDATE stockdetalle set
	stockdetalle.lote = lote, 
	stockdetalle.serie = serie,
    stockdetalle.pm = pm,
    stockdetalle.vencimiento = vencimiento
    
WHERE stockdetalle.id_remito = id_remito AND
		stockdetalle.id_producto = id_producto AND
        stockdetalle.id_stockdetalle = id_stockdetalle;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `usuarioAdministrador` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `usuarioAdministrador`(IN id_usuario INT, 
                                                    id_empresa INT)
BEGIN

DELETE 
    FROM acceso 
    WHERE acceso.id_usuario = id_usuario AND 
            acceso.id_empresa = id_empresa;

INSERT INTO acceso (id_usuario, id_menu, id_empresa)

SELECT id_usuario, menu.id_menu, menu.id_empresa
    FROM menu
    WHERE menu.id_empresa = id_empresa AND
            menu.habilita = 'S';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `zonaSistema` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera_testing`@`%` PROCEDURE `zonaSistema`(IN id_usuario INT,
									id_zonaSistema int)
BEGIN

UPDATE usuario SET
	usuario.id_zonaSistema = id_zonaSistema
    
WHERE usuario.id_usuario = id_usuario;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-23  0:11:19
