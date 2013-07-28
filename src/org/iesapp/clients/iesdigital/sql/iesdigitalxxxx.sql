/*
SQLyog Ultimate v8.71 
MySQL - 5.5.25a : Database - iesdigital2012
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `avaluacions` */

CREATE TABLE `avaluacions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ensenyament` varchar(255) NOT NULL,
  `estudis` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Table structure for table `avaluacionsdetall` */

CREATE TABLE `avaluacionsdetall` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idAvaluacions` int(11) DEFAULT NULL,
  `fechaInicio` date DEFAULT NULL,
  `fechaFin` date DEFAULT NULL,
  `valorExportable` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

/*Table structure for table `evaluacionesdetalle` */

CREATE TABLE `evaluacionesdetalle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idEvaluaciones` int(11) NOT NULL DEFAULT '0',
  `idTipoEvaluacionesDetalle` int(11) NOT NULL DEFAULT '0',
  `fechaInicio` date DEFAULT NULL,
  `fechaFin` date DEFAULT NULL,
  `fechaInicioReal` date DEFAULT NULL,
  `fechaFinReal` date DEFAULT NULL,
  `pasaLista` char(1) NOT NULL DEFAULT '',
  `publicarSGDWeb` char(1) NOT NULL DEFAULT '',
  `fechaSGDWeb` date DEFAULT NULL,
  `idRecuperacionDe` int(11) DEFAULT NULL,
  `valorExportable` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idEvaluaciones` (`idEvaluaciones`,`idTipoEvaluacionesDetalle`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Table structure for table `fitxa_permisos` */

CREATE TABLE `fitxa_permisos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `grup` varchar(255) DEFAULT NULL COMMENT 'Grup d''usuari',
  `dadesPersonals_edit` tinyint(1) DEFAULT NULL COMMENT 'Permet editar la fitxa de dades personals',
  `dadesPersonals_view` tinyint(1) DEFAULT NULL COMMENT 'Permet veure la fitxa de dades personals',
  `dadesPrimaria_view` tinyint(1) DEFAULT NULL COMMENT 'Permet veure les dades de primaria',
  `fitxaActual_edit` tinyint(1) DEFAULT NULL COMMENT 'Permet editar la fitxa del curs actual',
  `fitxaActual_view` tinyint(1) DEFAULT NULL COMMENT 'Permet veure la fitxa del curs actual',
  `fitxaAnterior_edit` tinyint(1) DEFAULT NULL COMMENT 'Permet editar les fitxes de cursos anteriors',
  `fitxaAnterior_view` tinyint(1) DEFAULT NULL COMMENT 'Permet veure les fitxes de cursos anteriors',
  `informeResumFitxa_gen` tinyint(1) DEFAULT NULL COMMENT 'Permet generar un resum de les fitxes de tutoria',
  `informePasswords_gen` tinyint(1) DEFAULT NULL COMMENT 'Permet generar informe amb passwords',
  `informeFitxaTutoria_gen` tinyint(1) DEFAULT NULL COMMENT 'Permet generar una fitxa completa de l''alumne',
  `informeAccions` tinyint(1) DEFAULT NULL COMMENT 'Permet generar informes d''accions de tutoria',
  `informeSancions` tinyint(1) DEFAULT NULL COMMENT 'Permet generar informe de sancions',
  `informeSGD_gen` tinyint(1) DEFAULT NULL,
  `fitxesCtrl_crear` tinyint(1) DEFAULT NULL COMMENT 'Permet crear fitxes',
  `fitxesCtrl_esborrar` tinyint(1) DEFAULT NULL COMMENT 'Permet esborrar fitxes',
  `accions_view` tinyint(1) DEFAULT NULL COMMENT 'Permetre que es pugui accedir a les tasques de tutoria',
  `accions_fullEdit` tinyint(1) DEFAULT NULL COMMENT 'Permet editar les accions en mode tutor=1 o en mode admin = 2',
  `accions_tancar` tinyint(1) DEFAULT NULL COMMENT 'Permet tancar les accions',
  `accions_esborrar` tinyint(1) DEFAULT NULL COMMENT 'Permet esborrar les accions',
  `imported_edit` tinyint(1) DEFAULT NULL COMMENT 'Permet editar els camps que son importats de l''SGD',
  `cerca_mostraAvancada` tinyint(1) DEFAULT NULL COMMENT 'La finestra de cerca avanÃ§ada s''obri per defecte si >0',
  `cerca_permetAccions` tinyint(1) DEFAULT NULL COMMENT 'Els tabs de cerca per accions estan enabled si >0',
  `medicaments_edit` tinyint(1) DEFAULT NULL COMMENT 'Permet editar els medicaments',
  `medicaments_view` tinyint(1) DEFAULT NULL COMMENT 'Permet veure els medicaments',
  `medicaments_give` tinyint(1) DEFAULT NULL COMMENT 'Permet donar medicaments',
  `informeMedicaments` tinyint(1) DEFAULT '0',
  `nese_view` tinyint(1) DEFAULT NULL COMMENT 'Veure dades nese',
  `nese_edit` tinyint(1) DEFAULT NULL COMMENT 'Edicio de dades nese',
  `entrevistaPares_view` tinyint(1) DEFAULT NULL COMMENT 'Permet veure la informacio de les entrevistes',
  `entrevistaPares_edit` tinyint(1) DEFAULT NULL COMMENT 'Permet editar i demanar entrevista amb pares',
  `justificarFaltes` tinyint(1) DEFAULT NULL COMMENT 'Permet justificar faltes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Table structure for table `fitxa_programes` */

CREATE TABLE `fitxa_programes` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `curt` varchar(50) DEFAULT NULL,
  `llarg` varchar(255) DEFAULT NULL,
  `disponible` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Table structure for table `sgd_anotaciones` */

CREATE TABLE `sgd_anotaciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idClase` int(11) DEFAULT NULL,
  `idProfesor` int(11) DEFAULT NULL,
  `horaClase` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `anotacion` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sgd_conceptos` */

CREATE TABLE `sgd_conceptos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idProfesores` int(11) DEFAULT NULL,
  `idClase` int(11) DEFAULT NULL,
  `nombreConcepto` varchar(20) DEFAULT NULL,
  `colorConcepto` varchar(15) DEFAULT NULL,
  `porcentajeConcepto` int(11) DEFAULT NULL,
  `textoActividad` varchar(255) DEFAULT NULL,
  `evaluableActividad` enum('S','N') NOT NULL DEFAULT 'N',
  `webActividad` enum('S','N') NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Table structure for table `sgd_conceptosactividades` */

CREATE TABLE `sgd_conceptosactividades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idActividad` int(11) DEFAULT NULL,
  `idConcepto` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1;

/*Table structure for table `sgd_faltascheck` */

CREATE TABLE `sgd_faltascheck` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idProfesor` int(11) DEFAULT NULL,
  `idIncidencia` int(11) DEFAULT NULL,
  `fecha` timestamp NULL DEFAULT NULL,
  `fixed` enum('N','Y','F') DEFAULT 'N',
  `newIdIncidencia` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=244 DEFAULT CHARSET=latin1;

/*Table structure for table `sgd_mensajes_attachments` */

CREATE TABLE `sgd_mensajes_attachments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idmensajes` int(11) DEFAULT NULL,
  `attachment` longtext,
  `size` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Table structure for table `sgd_mensajes_richtext` */

CREATE TABLE `sgd_mensajes_richtext` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idMensajes` int(11) DEFAULT NULL,
  `richText` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Table structure for table `sig_data` */

CREATE TABLE `sig_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=367 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_diari_guardies` */

CREATE TABLE `sig_diari_guardies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` date DEFAULT NULL,
  `dia_setmana` int(11) DEFAULT NULL,
  `hora` int(11) DEFAULT NULL,
  `profe_falta` varchar(10) NOT NULL DEFAULT '',
  `profe_guarda` varchar(10) NOT NULL DEFAULT '',
  `on_guarda` varchar(50) NOT NULL DEFAULT '',
  `grup` varchar(30) NOT NULL DEFAULT '',
  `feina` tinyint(1) NOT NULL DEFAULT '0',
  `comentaris` varchar(200) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3320 DEFAULT CHARSET=latin1 COMMENT='(NO MODIFICAR)registres de les guardies que fa cada professo';

/*Table structure for table `sig_espais` */

CREATE TABLE `sig_espais` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aula` varchar(255) DEFAULT NULL,
  `descripcio` varchar(255) DEFAULT NULL,
  `zona_guardia` varchar(255) DEFAULT NULL,
  `utilizable_guardia` int(11) NOT NULL DEFAULT '1',
  `reservable` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

/*Table structure for table `sig_faltes_previstes` */

CREATE TABLE `sig_faltes_previstes` (
  `id_fp` int(11) NOT NULL AUTO_INCREMENT,
  `abrev` varchar(20) DEFAULT NULL,
  `desde` date DEFAULT NULL,
  `fins` date DEFAULT NULL,
  `comment` longtext,
  `commentg` longtext,
  `feina` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id_fp`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_festius` */

CREATE TABLE `sig_festius` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `desde` date NOT NULL,
  `fins` date NOT NULL,
  `commentari` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_grant` */

CREATE TABLE `sig_grant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `moduleId` varchar(255) NOT NULL,
  `key` varchar(255) NOT NULL,
  `description` longtext,
  `defaultValue` int(11) NOT NULL,
  `acceptedValues` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_grant_roles` */

CREATE TABLE `sig_grant_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) NOT NULL,
  `description` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_grant_values` */

CREATE TABLE `sig_grant_values` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idGrant` int(11) NOT NULL,
  `idRole` int(11) NOT NULL,
  `value` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sig_grant_values` (`idGrant`)
) ENGINE=InnoDB AUTO_INCREMENT=588 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_guardies` */

CREATE TABLE `sig_guardies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abrev` varchar(255) DEFAULT NULL,
  `lloc` varchar(15) NOT NULL DEFAULT '',
  `dia` int(11) DEFAULT NULL,
  `hora` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=283 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_guardies_zones` */

CREATE TABLE `sig_guardies_zones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lloc` varchar(11) DEFAULT NULL,
  `descripcio` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_horaris` */

CREATE TABLE `sig_horaris` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prof` varchar(255) NOT NULL DEFAULT '',
  `asig` varchar(255) NOT NULL DEFAULT '',
  `curso` varchar(255) NOT NULL DEFAULT '',
  `nivel` varchar(255) NOT NULL DEFAULT '',
  `grupo` varchar(255) NOT NULL DEFAULT '',
  `aula` varchar(255) NOT NULL DEFAULT '',
  `dia` int(11) NOT NULL DEFAULT '0',
  `hora` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3540 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_hores_classe` */

CREATE TABLE `sig_hores_classe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idTipoHoras` smallint(6) DEFAULT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `inicio` time DEFAULT NULL,
  `fin` time DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1 COMMENT='Horas de comenÃ§ament de cada classe i cada pati.';

/*Table structure for table `sig_justificat` */

CREATE TABLE `sig_justificat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abrev` varchar(20) NOT NULL DEFAULT '',
  `data` date DEFAULT NULL,
  `hora` int(11) NOT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_llista_pati` */

CREATE TABLE `sig_llista_pati` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `profe` varchar(50) DEFAULT NULL,
  `dia_setmana` varchar(10) DEFAULT NULL,
  `numero` varchar(1) DEFAULT NULL,
  `lloc` varchar(30) DEFAULT NULL,
  `signatura` datetime DEFAULT NULL,
  `data` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='(NO MODIFICAR)dades de les guardies de pati de cada dia';

/*Table structure for table `sig_lloc_guardies_pati` */

CREATE TABLE `sig_lloc_guardies_pati` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dia` varchar(12) DEFAULT NULL,
  `pati` varchar(1) DEFAULT NULL,
  `lloc` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='relaciÃ³ de llocs per guardar als patis';

/*Table structure for table `sig_log` */

CREATE TABLE `sig_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `usua` varchar(25) NOT NULL DEFAULT '',
  `ip` varchar(25) DEFAULT '',
  `netbios` varchar(255) DEFAULT '',
  `domain` varchar(25) DEFAULT '',
  `tasca` varchar(255) NOT NULL DEFAULT '',
  `inici` datetime DEFAULT NULL,
  `fi` datetime DEFAULT NULL,
  `resultat` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22593 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_medicaments_reg` */

CREATE TABLE `sig_medicaments_reg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exp2` int(11) DEFAULT NULL,
  `data` timestamp NULL DEFAULT NULL,
  `idAutoritzat` varchar(25) DEFAULT NULL,
  `idMedicament` int(11) DEFAULT NULL,
  `observacions` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_missatgeria` */

CREATE TABLE `sig_missatgeria` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idEntrevista` int(10) DEFAULT NULL,
  `destinatari` varchar(50) DEFAULT NULL,
  `idMateria` int(11) DEFAULT NULL,
  `materia` varchar(255) DEFAULT NULL,
  `actitud` longtext,
  `notes` longtext,
  `feina` longtext,
  `comentaris` longtext,
  `dataContestat` datetime DEFAULT NULL,
  `idMensajeProfesor` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4158 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_missatgeria_items` */

CREATE TABLE `sig_missatgeria_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ordre` int(11) DEFAULT NULL,
  `item` varchar(255) DEFAULT NULL,
  `tipus` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_missatges` */

CREATE TABLE `sig_missatges` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `de` varchar(50) DEFAULT NULL,
  `para` longtext,
  `data` datetime DEFAULT NULL,
  `missatge` longtext,
  `instantani` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=latin1 COMMENT='missatges que es carreguen quan l''usuari clicka';

/*Table structure for table `sig_perfils_guardies` */

CREATE TABLE `sig_perfils_guardies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `perfil` varchar(255) DEFAULT NULL,
  `abrev` varchar(255) DEFAULT NULL,
  `lloc` varchar(255) DEFAULT NULL,
  `dia` int(11) DEFAULT NULL,
  `hora` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_perfils_horaris` */

CREATE TABLE `sig_perfils_horaris` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `perfil` varchar(255) DEFAULT NULL,
  `prof` varchar(255) NOT NULL DEFAULT '',
  `asig` varchar(255) NOT NULL DEFAULT '',
  `curso` varchar(255) NOT NULL DEFAULT '',
  `nivel` varchar(255) NOT NULL DEFAULT '',
  `grupo` varchar(255) NOT NULL DEFAULT '',
  `aula` varchar(255) NOT NULL DEFAULT '',
  `dia` int(11) NOT NULL DEFAULT '0',
  `hora` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3821 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_professorat` */

CREATE TABLE `sig_professorat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `abrev` varchar(255) DEFAULT NULL,
  `depart` varchar(255) DEFAULT NULL,
  `torn` int(11) NOT NULL DEFAULT '0',
  `idSGD` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_progtasques` */

CREATE TABLE `sig_progtasques` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hora` time DEFAULT NULL,
  `dia` int(11) DEFAULT NULL,
  `tipo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_reserves` */

CREATE TABLE `sig_reserves` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abrev` char(15) DEFAULT NULL,
  `tipus` int(11) DEFAULT NULL,
  `id_recurs` varchar(15) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `hora` int(11) DEFAULT NULL,
  `motiu` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=958 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_reserves_material` */

CREATE TABLE `sig_reserves_material` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `material` varchar(255) DEFAULT NULL,
  `ubicacio` varchar(255) DEFAULT NULL,
  `descripcio` longtext,
  `imatge` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_senseguardia` */

CREATE TABLE `sig_senseguardia` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item` varchar(10) DEFAULT NULL,
  `descripcio` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=latin1 COMMENT='abreviatures de sessions que no precisen guardar alumnes';

/*Table structure for table `sig_signatures` */

CREATE TABLE `sig_signatures` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abrev` varchar(255) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `h1` int(11) NOT NULL DEFAULT '0',
  `h1_t` datetime DEFAULT NULL,
  `h2` int(11) NOT NULL DEFAULT '0',
  `h2_t` datetime DEFAULT NULL,
  `h3` int(11) NOT NULL DEFAULT '0',
  `h3_t` datetime DEFAULT NULL,
  `h4` int(11) NOT NULL DEFAULT '0',
  `h4_t` datetime DEFAULT NULL,
  `h5` int(11) NOT NULL DEFAULT '0',
  `h5_t` datetime DEFAULT NULL,
  `h6` int(11) NOT NULL DEFAULT '0',
  `h6_t` datetime DEFAULT NULL,
  `h7` int(11) NOT NULL DEFAULT '0',
  `h7_t` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28557 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_signatures_comentaris` */

CREATE TABLE `sig_signatures_comentaris` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abrev` varchar(25) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=369 DEFAULT CHARSET=utf8;

/*Table structure for table `sig_signatures_tarda` */

CREATE TABLE `sig_signatures_tarda` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abrev` varchar(255) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `h1` int(11) NOT NULL DEFAULT '0',
  `h1_t` datetime DEFAULT NULL,
  `h2` int(11) NOT NULL DEFAULT '0',
  `h2_t` datetime DEFAULT NULL,
  `h3` int(11) NOT NULL DEFAULT '0',
  `h3_t` datetime DEFAULT NULL,
  `h4` int(11) NOT NULL DEFAULT '0',
  `h4_t` datetime DEFAULT NULL,
  `h5` int(11) NOT NULL DEFAULT '0',
  `h5_t` datetime DEFAULT NULL,
  `h6` int(11) NOT NULL DEFAULT '0',
  `h6_t` datetime DEFAULT NULL,
  `h7` int(11) NOT NULL DEFAULT '0',
  `h7_t` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2027 DEFAULT CHARSET=latin1;

/*Table structure for table `sig_simbolsincidencies` */

CREATE TABLE `sig_simbolsincidencies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `simbols4Falta` varchar(255) DEFAULT NULL,
  `simbols4FaltaJ` varchar(255) DEFAULT NULL,
  `simbols4Retard` varchar(255) DEFAULT NULL,
  `simbols4RetardJ` varchar(255) DEFAULT NULL,
  `simbols4AmonLleu` varchar(255) DEFAULT NULL,
  `simbols4AmonGreu` varchar(255) DEFAULT NULL,
  `simbols4AmonHist` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Table structure for table `tuta_actuacions` */

CREATE TABLE `tuta_actuacions` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador de l''actuacio/rule',
  `actuacio` varchar(255) NOT NULL DEFAULT '' COMMENT 'Descripcio breu de l''actuacio/rule',
  `descripcio` varchar(255) NOT NULL DEFAULT '' COMMENT 'Descripció llarga de l''actuacio/rule',
  `tipus` varchar(25) NOT NULL DEFAULT '' COMMENT 'tipus d''actuacio CONVIVENCIA O ASSISTENCIA',
  `nomesAdmin` tinyint(11) NOT NULL DEFAULT '0' COMMENT '1/0: 1=actuacio nomes disponible per a ADMIN O PREFECTURA',
  `roles` varchar(255) NOT NULL DEFAULT '''*''' COMMENT 'Roles for which this rule is applicable',
  `simbol` varchar(25) NOT NULL DEFAULT '' COMMENT 'FA/RE/AG: Simbol de la incidencia que ha de contar',
  `threshold` int(11) NOT NULL DEFAULT '0' COMMENT 'Llindar d''incidencies tipus SIMBOL per que s''activi l''actuacio',
  `vrepeticio` int(11) NOT NULL DEFAULT '0' COMMENT 'Cada quan s''ha de repetir l''actuacio',
  `vmax` int(11) NOT NULL DEFAULT '0' COMMENT 'Max incidencies perque sigui avisable l''actuacio',
  `alertActuacionsPendents` enum('N','S') NOT NULL DEFAULT 'N' COMMENT 'Quan s''activa, alerta com a actuacio pendent',
  `repetir` enum('S','N') NOT NULL DEFAULT 'N' COMMENT 'Cal que es torni a activar la incidencia a multiples de threshold',
  `autoTancar` enum('N','S') NOT NULL DEFAULT 'N' COMMENT 'L''actuacio es tanca automaticament despres de crear-se',
  `equivalencies` varchar(255) NOT NULL DEFAULT '' COMMENT 'Ids de les actuacions que son equivalent a aquesta i que quan es faci una desapareixeran la resta',
  `requireClosed` varchar(255) NOT NULL DEFAULT '' COMMENT 'Per poder dur a terme aquesta actuacio requereix que totes amb ids especificades aqui estiguin tancades',
  `requireCreated` varchar(255) NOT NULL DEFAULT '' COMMENT 'Per poder dur a terme aquesta actuacio s''ha d''haver creat les actuacions amb ids especificades',
  `instruccions` longtext COMMENT 'Instruccions per dur a terme l''actuacio',
  `reglament` longtext COMMENT 'Reglament ROF referent a l''actuacio',
  `alert` longtext COMMENT 'Missatge d''alerta sota la descripcio',
  `class` varchar(255) DEFAULT NULL COMMENT 'Nom de la classe encarregada per representar el formulari de l''actuacio',
  `form` longtext COMMENT 'unused',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=latin1;

/*Table structure for table `tuta_actuacions_fields` */

CREATE TABLE `tuta_actuacions_fields` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idRule` int(11) DEFAULT NULL COMMENT 'id de l''actuacio',
  `ambits` varchar(255) DEFAULT NULL COMMENT 'ESO/PQPI/BAT/GM/GS: ambits en que s''apliquen aquests camps',
  `estudis` varchar(255) NOT NULL DEFAULT '*' COMMENT 'Nivell educatiu',
  `idFieldSet` int(11) DEFAULT NULL COMMENT 'id del conjunt de camps que s''utilitza pel formulari',
  `minAge` int(11) NOT NULL DEFAULT '0' COMMENT 'Edat minima per aplicar la regla',
  `maxAge` int(11) NOT NULL DEFAULT '100' COMMENT 'Edat maxima per aplicar la regla',
  `enviamentCarta` varchar(255) NOT NULL DEFAULT 'N' COMMENT 'N=no, S=si, #fieldName (si esta activat aquest camp), Especifica si per tancar l''accio necessita esperar l''enviament d''una carta',
  `enviamentSMS` varchar(255) NOT NULL DEFAULT 'N' COMMENT 'N=no, S=si, #fieldName (si esta activat aquest camp),Especifica si per tancar l''accio necessita esperar l''enviament d''un SMS',
  `instancesPolicy` enum('SINGLE','MULTIPLE','MULTIPLE_WAIT','MULTIPLE_WARNING') NOT NULL DEFAULT 'MULTIPLE' COMMENT 'SINGLE:nomes una instancia, MULTIPLE_WAIT:multiples si la resta estan tancades, MULTIPLE_WARNING:multiple amb un avis',
  `registerInFitxaAlumne` enum('N','S') NOT NULL DEFAULT 'N' COMMENT 'Deixa constancia a la fitxa de l''alumne que s''ha duit a terme aquesta actuacio tal dia',
  `xmlForm` longtext COMMENT 'formulari en xml',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;

/*Table structure for table `tuta_actuacions_reports` */

CREATE TABLE `tuta_actuacions_reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `idSubRule` int(11) DEFAULT NULL COMMENT 'id de la SubRegla',
  `idRule` int(11) DEFAULT NULL COMMENT 'id de la regla',
  `ambit` varchar(255) DEFAULT NULL COMMENT 'ESO/PQPI/BAT/GM/GS',
  `estudis` varchar(255) NOT NULL DEFAULT '*' COMMENT '*=tots, e.g. 2N BAT nomes 2n de batxillerat',
  `reportPath` varchar(255) DEFAULT NULL COMMENT 'Ruta relativa des de reports/ per trobar el informe (sense extensio .jasper)',
  `reportDescription` varchar(255) DEFAULT NULL COMMENT 'Descripcio del que hi ha al document',
  `lang` enum('CA','ES','EN','DE') NOT NULL DEFAULT 'CA' COMMENT 'Idioma del report',
  `includeSubReport` enum('N','F','A','R') NOT NULL DEFAULT 'N' COMMENT 'incloure subreport N=no, F=de faltes, A=d''amonestacions, R=de retards. Es passa l''objecte db_ds2 a jasper',
  `limitInc` int(11) DEFAULT NULL COMMENT 'limita el nombre d''incidencies que es mostren en el subreport',
  `popupInstructions` varchar(255) DEFAULT NULL COMMENT 'Quan es nova, mostra per primera volta les instruccions del que fer amb el document',
  `important` enum('N','S') NOT NULL DEFAULT 'S' COMMENT 'La marca com document principal i el col.loca en primer lloc',
  `visibilitat` enum('P','*') NOT NULL DEFAULT '*' COMMENT 'Deixa veure aquest document a P=prefectura, *=Prefectura&Tutor',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=latin1;

/*Table structure for table `tuta_dies_sancions` */

CREATE TABLE `tuta_dies_sancions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exp2` int(11) DEFAULT NULL,
  `idActuacio` int(11) DEFAULT NULL,
  `desde` date DEFAULT NULL,
  `fins` date DEFAULT NULL,
  `tipus` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=716 DEFAULT CHARSET=latin1;

/*Table structure for table `tuta_entrevistes` */

CREATE TABLE `tuta_entrevistes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exp2` int(11) DEFAULT NULL,
  `abrev` varchar(50) DEFAULT NULL,
  `dia` date DEFAULT NULL,
  `dataEnviat` date DEFAULT NULL,
  `sms` tinyint(1) DEFAULT NULL,
  `para` longtext,
  `acords` longtext,
  `observacions` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=463 DEFAULT CHARSET=latin1;

/*Table structure for table `tuta_incidenciessgd` */

CREATE TABLE `tuta_incidenciessgd` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idCas` int(11) DEFAULT NULL,
  `idSgd` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=925 DEFAULT CHARSET=utf8;

/*Table structure for table `tuta_instructors` */

CREATE TABLE `tuta_instructors` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `idCas` int(10) DEFAULT NULL,
  `idInstructor` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;

/*Table structure for table `tuta_reg_actuacions` */

CREATE TABLE `tuta_reg_actuacions` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `iniciatper` varchar(50) NOT NULL DEFAULT '' COMMENT 'qui inicia aquesta accio',
  `exp2` int(10) DEFAULT NULL COMMENT 'num expedient alumne',
  `data1` date DEFAULT NULL COMMENT 'Data de solicitud de l''actuacio',
  `idActuacio` int(11) NOT NULL COMMENT 'Tipus d''actuacio que es recomana',
  `data2` date DEFAULT NULL COMMENT 'Data de la resoluciÃ³',
  `resolucio` varchar(255) NOT NULL DEFAULT '' COMMENT 'Tipus de resoluciÃ³',
  `document` longtext COMMENT 'Parametres per la generaciÃ³ del document',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2709 DEFAULT CHARSET=latin1;

/*Table structure for table `tuta_reg_actuacions_deleted` */

CREATE TABLE `tuta_reg_actuacions_deleted` (
  `id` int(10) unsigned NOT NULL,
  `iniciatper` varchar(50) NOT NULL DEFAULT '' COMMENT 'qui inicia aquesta accio',
  `exp2` int(10) DEFAULT NULL COMMENT 'num expedient alumne',
  `data1` date DEFAULT NULL COMMENT 'Data de solicitud de l''actuacio',
  `idActuacio` int(11) NOT NULL COMMENT 'Tipus d''actuacio que es recomana',
  `data2` date DEFAULT NULL COMMENT 'Data de la resoluciÃ³',
  `resolucio` varchar(255) NOT NULL DEFAULT '' COMMENT 'Tipus de resoluciÃ³',
  `document` longtext COMMENT 'Parametres per la generaciÃ³ del document'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `tuta_visitestutors` */

CREATE TABLE `tuta_visitestutors` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `abrev` varchar(25) DEFAULT NULL,
  `dia` smallint(6) DEFAULT NULL,
  `hora` smallint(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=latin1;

/*Table structure for table `usu_usuari` */

CREATE TABLE `usu_usuari` (
  `IdUsuari` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Nom` varchar(45) NOT NULL DEFAULT '' COMMENT 'Abreviatura del professor p.e. MA4',
  `usuari` varchar(45) NOT NULL DEFAULT '' COMMENT 'username',
  `Contrasenya` varchar(45) NOT NULL DEFAULT '' COMMENT 'Podria esser la de PDA o la que s''estableixi',
  `GrupFitxes` varchar(45) DEFAULT NULL COMMENT 'El grup de permisos al que pertany pel programa de fixes',
  `bloquejat` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0=compte accessible; 1=compte bloquejat',
  `preferences_pdaweb` longtext,
  PRIMARY KEY (`IdUsuari`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
