/*
SQLyog Ultimate v8.71 
MySQL - 5.5.25a : Database - iesdigital
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `configuracio` */

CREATE TABLE `configuracio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(255) DEFAULT NULL,
  `property` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `castTo` varchar(20) DEFAULT NULL,
  `description` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;

/*Table structure for table `fitxa_alumne_curs` */

CREATE TABLE `fitxa_alumne_curs` (
  `Exp_FK_ID` int(10) unsigned NOT NULL DEFAULT '0',
  `IdCurs_FK_ID` varchar(45) NOT NULL DEFAULT '',
  `Estudis` varchar(45) NOT NULL DEFAULT '',
  `Grup` varchar(45) NOT NULL DEFAULT '',
  `Any_academic` varchar(45) NOT NULL DEFAULT '',
  `Ensenyament` varchar(45) NOT NULL DEFAULT '',
  `Professor` varchar(45) NOT NULL DEFAULT '',
  `Observacions` longtext NOT NULL,
  `DerivatORI` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Si/No',
  `MotiuDerivacioORI` longtext NOT NULL,
  `NumMateriesSuspJuny` int(10) unsigned NOT NULL DEFAULT '0',
  `NotaMitjaFinal` float NOT NULL DEFAULT '0',
  `TEST` int(10) unsigned NOT NULL DEFAULT '0',
  `Sancions` longtext COMMENT 'Expedients i/o expulsions',
  `NExpDisciplina` int(10) unsigned NOT NULL DEFAULT '0',
  `Programes` varchar(255) NOT NULL DEFAULT '',
  `DataCreacio` varchar(50) NOT NULL DEFAULT '',
  `Modificat` varchar(255) NOT NULL DEFAULT '',
  `DataModificacio` varchar(50) NOT NULL DEFAULT '',
  `FA_1A` int(10) unsigned NOT NULL DEFAULT '0',
  `FA_2A` int(10) unsigned NOT NULL DEFAULT '0',
  `FA_3A` int(10) unsigned NOT NULL DEFAULT '0',
  `FJ_1A` int(10) unsigned NOT NULL DEFAULT '0',
  `FJ_2A` int(10) unsigned NOT NULL DEFAULT '0',
  `FJ_3A` int(10) unsigned NOT NULL DEFAULT '0',
  `RE_1A` int(10) unsigned NOT NULL DEFAULT '0',
  `RE_2A` int(10) unsigned NOT NULL DEFAULT '0',
  `RE_3A` int(10) unsigned NOT NULL DEFAULT '0',
  `RJ_1A` int(10) unsigned NOT NULL DEFAULT '0',
  `RJ_2A` int(10) unsigned NOT NULL DEFAULT '0',
  `RJ_3A` int(10) unsigned NOT NULL DEFAULT '0',
  `AL_1A` int(10) unsigned NOT NULL DEFAULT '0',
  `AL_2A` int(10) unsigned NOT NULL DEFAULT '0',
  `AL_3A` int(10) unsigned NOT NULL DEFAULT '0',
  `AG_1A` int(10) unsigned NOT NULL DEFAULT '0',
  `AG_2A` int(10) unsigned NOT NULL DEFAULT '0',
  `AG_3A` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`Exp_FK_ID`,`IdCurs_FK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `fitxa_alumne_deleted` */

CREATE TABLE `fitxa_alumne_deleted` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Exp_FK_ID` int(10) unsigned NOT NULL DEFAULT '0',
  `IdCurs_FK_ID` varchar(45) NOT NULL DEFAULT '',
  `Estudis` varchar(45) NOT NULL DEFAULT '',
  `Grup` varchar(45) NOT NULL DEFAULT '',
  `Any_academic` varchar(45) NOT NULL DEFAULT '',
  `Ensenyament` varchar(45) NOT NULL DEFAULT '',
  `Professor` varchar(45) NOT NULL DEFAULT '',
  `Observacions` longtext NOT NULL,
  `DerivatORI` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Si/No',
  `MotiuDerivacioORI` varchar(45) NOT NULL DEFAULT '',
  `NumMateriesSuspJuny` int(10) unsigned NOT NULL DEFAULT '0',
  `NotaMitjaFinal` float NOT NULL DEFAULT '0',
  `NumAL_1rTRI` int(10) unsigned NOT NULL DEFAULT '0',
  `NumAL_2nTRI` int(10) unsigned NOT NULL DEFAULT '0',
  `NumAL_3rTRI` int(10) unsigned NOT NULL DEFAULT '0',
  `NumAG_1rTRI` int(10) unsigned NOT NULL DEFAULT '0',
  `NumAG_2nTRI` int(10) unsigned NOT NULL DEFAULT '0',
  `NumAG_3rTRI` int(10) unsigned NOT NULL DEFAULT '0',
  `F_1rTRI` int(10) unsigned NOT NULL DEFAULT '0',
  `FJ_1rTRI` int(10) unsigned NOT NULL DEFAULT '0',
  `F_2nTRI` int(10) unsigned NOT NULL DEFAULT '0',
  `FJ_2nTRI` int(10) unsigned NOT NULL DEFAULT '0',
  `F_3rTRI` int(10) unsigned NOT NULL DEFAULT '0',
  `FJ_3rTRI` int(10) unsigned NOT NULL DEFAULT '0',
  `Ret_1rTRI` int(10) unsigned NOT NULL DEFAULT '0',
  `Ret_2nTRI` int(10) unsigned NOT NULL DEFAULT '0',
  `Ret_3rTRI` int(10) unsigned NOT NULL DEFAULT '0',
  `Sancions` longtext COMMENT 'Expedients i/o expulsions',
  `NExpDisciplina` int(10) unsigned NOT NULL DEFAULT '0',
  `Programes` varchar(255) NOT NULL DEFAULT '',
  `DataCreacio` varchar(50) NOT NULL DEFAULT '',
  `Modificat` varchar(255) NOT NULL DEFAULT '',
  `DataModificacio` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Table structure for table `javaversion` */

CREATE TABLE `javaversion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(25) DEFAULT NULL,
  `netbios` varchar(25) DEFAULT NULL,
  `java` varchar(25) DEFAULT NULL,
  `os` varchar(50) DEFAULT NULL,
  `arch` varchar(50) DEFAULT NULL,
  `processors` int(11) DEFAULT NULL,
  `totalMemory` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_medicaments` */

CREATE TABLE `sig_medicaments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcio` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_medicaments_alumnes` */

CREATE TABLE `sig_medicaments_alumnes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exp2` int(11) DEFAULT NULL,
  `idMedicament` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1081 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_medicaments_observ` */

CREATE TABLE `sig_medicaments_observ` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exp2` int(11) DEFAULT NULL,
  `observacions` longtext,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=latin1;

/*Table structure for table `xes_alta` */

CREATE TABLE `xes_alta` (
  `Exp2` int(11) NOT NULL,
  `dataAlta` date DEFAULT NULL,
  PRIMARY KEY (`Exp2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `xes_alumne` */

CREATE TABLE `xes_alumne` (
  `Exp2` int(11) NOT NULL,
  `Permisos` varchar(255) NOT NULL DEFAULT '',
  `Llinatge1` varchar(255) NOT NULL DEFAULT '',
  `Llinatge2` varchar(255) DEFAULT NULL,
  `Nom1` varchar(255) DEFAULT NULL,
  `Centre` varchar(11) DEFAULT NULL,
  `NumRep` int(11) DEFAULT NULL,
  `Repetidor` tinyint(1) DEFAULT '0',
  `Sexe` varchar(255) DEFAULT NULL,
  `Edat` int(11) DEFAULT NULL,
  `DataNaixement` date DEFAULT NULL,
  `Nacionalitat` varchar(255) DEFAULT NULL,
  `PaisNaixement` varchar(255) DEFAULT NULL,
  `ProvinciaNaixement` varchar(255) DEFAULT NULL,
  `LocalitatNaixement` varchar(255) DEFAULT NULL,
  `DNI` varchar(255) DEFAULT NULL,
  `TargetaSanitaria` varchar(255) DEFAULT NULL,
  `Adreca` varchar(255) DEFAULT NULL,
  `Municipi` varchar(255) DEFAULT NULL,
  `Localitat` varchar(255) DEFAULT NULL,
  `CP` varchar(255) DEFAULT NULL,
  `ultim8` varchar(15) DEFAULT NULL,
  `pwd` varchar(25) DEFAULT NULL,
  `Taquilla` varchar(255) DEFAULT NULL,
  `anee` longtext COMMENT 'text catalogat nese',
  PRIMARY KEY (`Exp2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `xes_alumne_detall` */

CREATE TABLE `xes_alumne_detall` (
  `Exp_FK_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Foto` longblob,
  `EdatPare` int(10) unsigned NOT NULL DEFAULT '0',
  `EdatMare` int(10) unsigned NOT NULL DEFAULT '0',
  `ProfessioPare` varchar(150) NOT NULL DEFAULT '',
  `ProfessioMare` varchar(150) NOT NULL DEFAULT '',
  PRIMARY KEY (`Exp_FK_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9553 DEFAULT CHARSET=latin1;

/*Table structure for table `xes_alumne_historic` */

CREATE TABLE `xes_alumne_historic` (
  `Exp2` int(10) unsigned NOT NULL,
  `AnyAcademic` varchar(255) NOT NULL DEFAULT '',
  `Ensenyament` varchar(255) NOT NULL DEFAULT '',
  `Estudis` varchar(255) NOT NULL DEFAULT '',
  `Grup` varchar(255) NOT NULL DEFAULT '',
  `ProfTutor` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`Exp2`,`AnyAcademic`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `xes_dades_pares` */

CREATE TABLE `xes_dades_pares` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Exp2` int(10) unsigned NOT NULL DEFAULT '0',
  `Telefon` varchar(255) NOT NULL DEFAULT '',
  `Email` varchar(255) NOT NULL DEFAULT '',
  `Relatiu1` varchar(255) NOT NULL DEFAULT '',
  `Tutor` varchar(255) NOT NULL DEFAULT '',
  `Relatiu2` varchar(255) NOT NULL DEFAULT '',
  `TelefonTutor` varchar(255) NOT NULL DEFAULT '',
  `Relatiu3` varchar(255) NOT NULL DEFAULT '',
  `EmailTutor` varchar(255) NOT NULL DEFAULT '',
  `Edat` int(255) NOT NULL,
  `Professio` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7289 DEFAULT CHARSET=latin1;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
