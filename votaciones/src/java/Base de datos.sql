-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.1.16-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win32
-- HeidiSQL Versión:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para votar
DROP DATABASE IF EXISTS `votar`;
CREATE DATABASE IF NOT EXISTS `votar` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish2_ci */;
USE `votar`;

-- Volcando estructura para tabla votar.candidato_votacion
DROP TABLE IF EXISTS `candidato_votacion`;
CREATE TABLE IF NOT EXISTS `candidato_votacion` (
  `CONS_CANDIDATO` int(11) NOT NULL AUTO_INCREMENT,
  `VOTACION_CONS_VOTACION` int(11) NOT NULL,
  `CONS_USUARIO_VOTACION` int(11) NOT NULL,
  `CANTIDAD_VOTOS` int(11) DEFAULT NULL,
  PRIMARY KEY (`CONS_CANDIDATO`),
  KEY `fk_CANDIDATO_VOTACION_VOTACION1_idx` (`VOTACION_CONS_VOTACION`),
  KEY `fk_CANDIDATO_VOTACION_USUARIO1_idx` (`CONS_USUARIO_VOTACION`),
  CONSTRAINT `fk_CANDIDATO_VOTACION_USUARIO1` FOREIGN KEY (`CONS_USUARIO_VOTACION`) REFERENCES `usuario` (`CONS_USUARIO`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CANDIDATO_VOTACION_VOTACION1` FOREIGN KEY (`VOTACION_CONS_VOTACION`) REFERENCES `votacion` (`CONS_VOTACION`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Volcando datos para la tabla votar.candidato_votacion: ~2 rows (aproximadamente)
DELETE FROM `candidato_votacion`;

-- Volcando estructura para tabla votar.estado_votacion
DROP TABLE IF EXISTS `estado_votacion`;
CREATE TABLE IF NOT EXISTS `estado_votacion` (
  `CONS_ESTADO_VOTACION` int(11) NOT NULL AUTO_INCREMENT,
  `ACTIVO` char(1) COLLATE utf8_spanish2_ci NOT NULL,
  `FECHA_REGISTRO` date NOT NULL,
  `ID_TIPO_ESTADO_VOTACION` int(11) NOT NULL,
  `CONS_VOTACION` int(11) NOT NULL,
  PRIMARY KEY (`CONS_ESTADO_VOTACION`),
  KEY `fk_ESTADO_VOTACION_TIPO_ESTADO_VOTACION1_idx` (`ID_TIPO_ESTADO_VOTACION`),
  KEY `fk_ESTADO_VOTACION_VOTACION1_idx` (`CONS_VOTACION`),
  CONSTRAINT `fk_ESTADO_VOTACION_TIPO_ESTADO_VOTACION1` FOREIGN KEY (`ID_TIPO_ESTADO_VOTACION`) REFERENCES `tipo_estado_votacion` (`ID_TIPO_ESTADO_VOTACION`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ESTADO_VOTACION_VOTACION1` FOREIGN KEY (`CONS_VOTACION`) REFERENCES `votacion` (`CONS_VOTACION`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Volcando datos para la tabla votar.estado_votacion: ~0 rows (aproximadamente)
DELETE FROM `estado_votacion`;
/*!40000 ALTER TABLE `estado_votacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `estado_votacion` ENABLE KEYS */;

-- Volcando estructura para tabla votar.persona
DROP TABLE IF EXISTS `persona`;
CREATE TABLE IF NOT EXISTS `persona` (
  `CONS_PERSONA` int(11) NOT NULL AUTO_INCREMENT,
  `CONS_USUARIO` int(11) NOT NULL,
  `ID_TIPO_DOCUMENTO` int(11) NOT NULL,
  `CONS_PERSONA_ASOCIADA` int(11) DEFAULT NULL,
  `IMAGEN_PERFIL` varchar(150) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `SEGUNDO_APELLIDO` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `PRIMER_NOMBRE` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `SEGUNDO_NOMBRE` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `PRIMER_APELLIDO` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `NOMBRE_EMPRESA` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `CORREO` varchar(150) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `NUMERO_DOCUMENTO` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL,
  PRIMARY KEY (`CONS_PERSONA`),
  KEY `fk_PERSONA_TIPO_DOCUMENTO1_idx` (`ID_TIPO_DOCUMENTO`),
  KEY `fk_PERSONA_USUARIO1` (`CONS_USUARIO`),
  CONSTRAINT `fk_PERSONA_TIPO_DOCUMENTO1` FOREIGN KEY (`ID_TIPO_DOCUMENTO`) REFERENCES `tipo_documento` (`ID_TIPO_DOCUMENTO`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_PERSONA_USUARIO1` FOREIGN KEY (`CONS_USUARIO`) REFERENCES `usuario` (`CONS_USUARIO`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Volcando datos para la tabla votar.persona: ~12 rows (aproximadamente)
DELETE FROM `persona`;

-- Volcando estructura para tabla votar.tipo_documento
DROP TABLE IF EXISTS `tipo_documento`;
CREATE TABLE IF NOT EXISTS `tipo_documento` (
  `ID_TIPO_DOCUMENTO` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE_DOCUMENTO` varchar(45) COLLATE utf8_spanish2_ci DEFAULT NULL,
  PRIMARY KEY (`ID_TIPO_DOCUMENTO`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Volcando datos para la tabla votar.tipo_documento: ~4 rows (aproximadamente)
DELETE FROM `tipo_documento`;
/*!40000 ALTER TABLE `tipo_documento` DISABLE KEYS */;
INSERT INTO `tipo_documento` (`ID_TIPO_DOCUMENTO`, `NOMBRE_DOCUMENTO`) VALUES
	(1, 'Cedula'),
	(2, 'Nit'),
	(3, 'Cedula de extranjeria');
/*!40000 ALTER TABLE `tipo_documento` ENABLE KEYS */;

-- Volcando estructura para tabla votar.tipo_estado_votacion
DROP TABLE IF EXISTS `tipo_estado_votacion`;
CREATE TABLE IF NOT EXISTS `tipo_estado_votacion` (
  `ID_TIPO_ESTADO_VOTACION` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE_ESTADO` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL,
  PRIMARY KEY (`ID_TIPO_ESTADO_VOTACION`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Volcando datos para la tabla votar.tipo_estado_votacion: ~1 rows (aproximadamente)
DELETE FROM `tipo_estado_votacion`;

-- Volcando estructura para tabla votar.tipo_usuario
DROP TABLE IF EXISTS `tipo_usuario`;
CREATE TABLE IF NOT EXISTS `tipo_usuario` (
  `ID_TIPO_USUARIO` int(11) NOT NULL COMMENT 'Hace referencia al id del tipo de usuario',
  `DESCRIPCION` varchar(50) COLLATE utf8_spanish2_ci NOT NULL COMMENT 'Lleva el nombre del usuario como : Votante,Empresa y Administrador',
  `PUBLICO` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`ID_TIPO_USUARIO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Volcando datos para la tabla votar.tipo_usuario: ~3 rows (aproximadamente)
DELETE FROM `tipo_usuario`;

/*!40000 ALTER TABLE `tipo_usuario` DISABLE KEYS */;
INSERT INTO `tipo_usuario` (`ID_TIPO_USUARIO`, `DESCRIPCION`, `PUBLICO`) VALUES
	(1, 'Administrador', b'0'),
	(2, 'Empresa', b'1'),
	(3, 'Empleado', b'1');
/*!40000 ALTER TABLE `tipo_usuario` ENABLE KEYS */;


-- Volcando estructura para tabla votar.tipo_votacion
DROP TABLE IF EXISTS `tipo_votacion`;
CREATE TABLE IF NOT EXISTS `tipo_votacion` (
  `ID_TIPO_VOTACION` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE_TIPO_VOTACION` varchar(100) COLLATE utf8_spanish2_ci DEFAULT '0',
  `ID_PERSONA` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_TIPO_VOTACION`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Volcando datos para la tabla votar.tipo_votacion: ~1 rows (aproximadamente)
DELETE FROM `tipo_votacion`;

-- Volcando estructura para tabla votar.tokens_accion
DROP TABLE IF EXISTS `tokens_accion`;
CREATE TABLE IF NOT EXISTS `tokens_accion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asunto` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `token` varchar(350) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `id_autor` int(11) DEFAULT '0',
  `fecha_creacion` timestamp NULL DEFAULT NULL,
  `actualizado` timestamp NULL DEFAULT NULL,
  `estado` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci COMMENT='Solo lo usará el sistema como método de verificación de acceso a algunos módulos tales como la recuperación de clave.';

-- Volcando datos para la tabla votar.tokens_accion: ~3 rows (aproximadamente)
DELETE FROM `tokens_accion`;

-- Volcando estructura para tabla votar.usuario
DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `CONS_USUARIO` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Este campo hace referencia al consecutivo del usuario',
  `ID_TIPO_USUARIO` int(11) NOT NULL COMMENT 'Contiene el identificador del tipo de usuario que puede tener el usuario administrador, votante y empresa.',
  `NOMBRE_USUARIO` varchar(50) COLLATE utf8_spanish2_ci NOT NULL COMMENT 'Contiene el nombre del usuario que se muestra en el sistema',
  `CONTRASENA` varchar(350) COLLATE utf8_spanish2_ci NOT NULL COMMENT 'Contraseña de maximo 50 caracteres que tiene el usuario.',
  `ACTIVO` char(1) COLLATE utf8_spanish2_ci NOT NULL COMMENT 'Contiene los valores S= si o N = no para saber el estado en el que se encuentra el usuario.',
  `ULTIMO_INGRESO` timestamp NULL DEFAULT NULL,
  `FECHA_REGISTRO` timestamp NULL DEFAULT NULL,
  `FECHA_ACTUALIZACION` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`CONS_USUARIO`),
  KEY `fk_USUARIO_TIPO_USUARIO_idx` (`ID_TIPO_USUARIO`),
  CONSTRAINT `fk_USUARIO_TIPO_USUARIO` FOREIGN KEY (`ID_TIPO_USUARIO`) REFERENCES `tipo_usuario` (`ID_TIPO_USUARIO`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Volcando datos para la tabla votar.usuario: ~13 rows (aproximadamente)
DELETE FROM `usuario`;

-- Volcando estructura para tabla votar.votacion
DROP TABLE IF EXISTS `votacion`;
CREATE TABLE IF NOT EXISTS `votacion` (
  `CONS_VOTACION` int(11) NOT NULL AUTO_INCREMENT,
  `FECHA_INICIO_INSCRIPCION` datetime NOT NULL,
  `FECHA_FIN_INSCRIPCION` datetime DEFAULT NULL,
  `ID_TIPO_VOTACION` int(11) NOT NULL,
  `FECHA_INICIO_VOTACION` datetime DEFAULT NULL,
  `FECHA_FIN_VOTACION` datetime DEFAULT NULL,
  `CONS_USUARIO_CREACION` int(11) NOT NULL,
  `ESTADO_VOTACION` char(1) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `CANTIDAD_VOTOS` int(11) DEFAULT '0',
  `CANTIDAD_CANDIDATOS` int(11) DEFAULT '0',
  PRIMARY KEY (`CONS_VOTACION`),
  KEY `fk_VOTACION_USUARIO1_idx` (`CONS_USUARIO_CREACION`),
  KEY `fk_TIPO_VOTACION_VOTACION` (`ID_TIPO_VOTACION`),
  CONSTRAINT `fk_TIPO_VOTACION_VOTACION` FOREIGN KEY (`ID_TIPO_VOTACION`) REFERENCES `tipo_votacion` (`ID_TIPO_VOTACION`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_VOTACION_USUARIO1` FOREIGN KEY (`CONS_USUARIO_CREACION`) REFERENCES `usuario` (`CONS_USUARIO`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Volcando datos para la tabla votar.votacion: ~2 rows (aproximadamente)
DELETE FROM `votacion`;

-- Volcando estructura para tabla votar.votacion_usuario_candidato
DROP TABLE IF EXISTS `votacion_usuario_candidato`;
CREATE TABLE IF NOT EXISTS `votacion_usuario_candidato` (
  `CONS_USUARIO_CANDIDATO` int(11) NOT NULL AUTO_INCREMENT,
  `CANDIDATO_VOTACION_CONS_CANDIDATO` int(11) NOT NULL,
  `USUARIO_CONS_USUARIO` int(11) NOT NULL,
  PRIMARY KEY (`CONS_USUARIO_CANDIDATO`,`CANDIDATO_VOTACION_CONS_CANDIDATO`,`USUARIO_CONS_USUARIO`),
  KEY `fk_VOTACION_USUARIO_CANDIDATO_CANDIDATO_VOTACION1_idx` (`CANDIDATO_VOTACION_CONS_CANDIDATO`),
  KEY `fk_VOTACION_USUARIO_CANDIDATO_USUARIO1_idx` (`USUARIO_CONS_USUARIO`),
  CONSTRAINT `fk_VOTACION_USUARIO_CANDIDATO_CANDIDATO_VOTACION1` FOREIGN KEY (`CANDIDATO_VOTACION_CONS_CANDIDATO`) REFERENCES `candidato_votacion` (`CONS_CANDIDATO`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_VOTACION_USUARIO_CANDIDATO_USUARIO1` FOREIGN KEY (`USUARIO_CONS_USUARIO`) REFERENCES `usuario` (`CONS_USUARIO`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- Volcando datos para la tabla votar.votacion_usuario_candidato: ~0 rows (aproximadamente)
DELETE FROM `votacion_usuario_candidato`;
/*!40000 ALTER TABLE `votacion_usuario_candidato` DISABLE KEYS */;
/*!40000 ALTER TABLE `votacion_usuario_candidato` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
