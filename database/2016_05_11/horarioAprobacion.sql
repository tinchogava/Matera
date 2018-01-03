-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: matera
-- ------------------------------------------------------
-- Server version	5.7.10

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
-- Dumping routines for database 'matera'
--
DELIMITER ;
/*!50003 DROP PROCEDURE IF EXISTS `consultaConfirmaTurno` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera`@`%` PROCEDURE `consultaConfirmaTurno`(IN desde DATE,
                                                                hasta DATE,
                                                                id_zona INT,
                                                                id_empresa INT,
                                                                tipoBusqueda INT)
BEGIN

SELECT presupuesto.id_presupuesto, presupuesto.paciente, presupuesto.telefono, presupuesto.observaciones,
    fechaAgenda, date_format(log_evento_presupuesto.timestamp,'%h:%i %d-%m-%Y') AS fechaAprobacion, IFNULL(entidad.nombre, '') as entidad, 
    IFNULL(lugar.nombre, '') as lugar, IFNULL(profesional.nombre, '') as profesional, presupuesto.id_zona
    
FROM presupuesto

LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad
LEFT JOIN entidad lugar ON lugar.id_entidad = presupuesto.id_lugarCirugia
LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
LEFT JOIN log_evento_presupuesto ON presupuesto.id_presupuesto = log_evento_presupuesto.id_presupuesto

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
/*!50003 DROP PROCEDURE IF EXISTS `consultaConfirmaTurnoHoy` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera`@`%` PROCEDURE `consultaConfirmaTurnoHoy`(IN id_zona INT,
                                                id_empresa INT)
BEGIN

SELECT presupuesto.id_presupuesto, presupuesto.paciente, presupuesto.telefono, presupuesto.observaciones,
    fechaAgenda, date_format(log_evento_presupuesto.timestamp,'%h:%i %d-%m-%Y') AS fechaAprobacion, IFNULL(entidad.nombre, '') as entidad, 
    IFNULL(lugar.nombre, '') as lugar, IFNULL(profesional.nombre, '') as profesional, presupuesto.id_zona
    
FROM presupuesto

LEFT JOIN entidad ON entidad.id_entidad = presupuesto.id_entidad
LEFT JOIN entidad lugar ON lugar.id_entidad = presupuesto.id_lugarCirugia
LEFT JOIN profesional ON profesional.id_profesional = presupuesto.id_profesional1
LEFT JOIN log_evento_presupuesto ON presupuesto.id_presupuesto = log_evento_presupuesto.id_presupuesto

WHERE presupuesto.fechaAprobacion = CURRENT_DATE AND
	presupuesto.fechaAgenda IS NULL AND
    IF(id_zona = 0, true, presupuesto.id_zona = id_zona) AND
    (presupuesto.estado = 'A' OR presupuesto.estado = 'S') AND
    presupuesto.id_empresa = id_empresa
    
ORDER BY presupuesto.id_presupuesto DESC;

END ;;

DELIMITER ;
/*!50003 DROP PROCEDURE IF EXISTS `traeTurno` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`matera`@`%` PROCEDURE `traeTurno`(IN id_presupuesto INT)
BEGIN

SELECT presupuesto.id_presupuesto, presupuesto.fechaCirugia, presupuesto.fecha, fechaAgenda, date_format(log_evento_presupuesto.timestamp,'%h:%i %d-%m-%Y') AS fechaAprobacion, presupuesto.paciente,
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
LEFT JOIN log_evento_presupuesto ON presupuesto.id_presupuesto = log_evento_presupuesto.id_presupuesto

WHERE presupuesto.id_presupuesto = id_presupuesto;

END ;;
DELIMITER ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-11 11:11:22
