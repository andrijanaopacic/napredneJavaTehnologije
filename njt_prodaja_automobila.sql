/*
SQLyog Community v13.1.5  (64 bit)
MySQL - 10.4.32-MariaDB : Database - njt_automobili_prodaja
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`njt_automobili_prodaja` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `njt_automobili_prodaja`;

/*Table structure for table `automobil` */

DROP TABLE IF EXISTS `automobil`;

CREATE TABLE `automobil` (
  `cena` double NOT NULL,
  `godiste` int(11) NOT NULL,
  `kilometraza` int(11) NOT NULL,
  `kubikaza` int(11) NOT NULL,
  `snaga` int(11) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `model_id` bigint(20) DEFAULT NULL,
  `slika` varchar(255) DEFAULT NULL,
  `gorivo` enum('BENZIN','DIZEL','METAN','STRUJA') DEFAULT NULL,
  `menjac` enum('AUTOMATIK','MANUELNI') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9x8ibhychee8niko57f14mupj` (`model_id`),
  CONSTRAINT `FK9x8ibhychee8niko57f14mupj` FOREIGN KEY (`model_id`) REFERENCES `model` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Table structure for table `korisnici` */

DROP TABLE IF EXISTS `korisnici`;

CREATE TABLE `korisnici` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(120) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `uloga` enum('ADMIN','KORISNIK') NOT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_korisnicko_ime` (`username`),
  UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Table structure for table `marka` */

DROP TABLE IF EXISTS `marka`;

CREATE TABLE `marka` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `marka` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Table structure for table `model` */

DROP TABLE IF EXISTS `model`;

CREATE TABLE `model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `marka_id` bigint(20) NOT NULL,
  `model` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcpr2h0sqy53erw4m315vrmcy` (`marka_id`),
  CONSTRAINT `FKcpr2h0sqy53erw4m315vrmcy` FOREIGN KEY (`marka_id`) REFERENCES `marka` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Table structure for table `oglas` */

DROP TABLE IF EXISTS `oglas`;

CREATE TABLE `oglas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `aktivan` bit(1) NOT NULL,
  `naslov` varchar(255) DEFAULT NULL,
  `opis` varchar(255) DEFAULT NULL,
  `ukupna_cena` double NOT NULL,
  `vreme_oglasavanja` datetime(6) DEFAULT NULL,
  `automobil_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtn7cyr9s7ydxbkuypjua2vtjv` (`automobil_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `FKtn7cyr9s7ydxbkuypjua2vtjv` FOREIGN KEY (`automobil_id`) REFERENCES `automobil` (`id`),
  CONSTRAINT `oglas_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `korisnici` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Table structure for table `password_reset_tokens` */

DROP TABLE IF EXISTS `password_reset_tokens`;

CREATE TABLE `password_reset_tokens` (
  `token` varchar(255) NOT NULL,
  `expires_at` datetime(6) NOT NULL,
  `used` bit(1) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`token`),
  KEY `FKe6n9ywcpd7fm3tphk29cl6mwo` (`user_id`),
  CONSTRAINT `FKe6n9ywcpd7fm3tphk29cl6mwo` FOREIGN KEY (`user_id`) REFERENCES `korisnici` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Table structure for table `sacuvani_oglas` */

DROP TABLE IF EXISTS `sacuvani_oglas`;

CREATE TABLE `sacuvani_oglas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `korisnik_id` bigint(20) DEFAULT NULL,
  `oglas_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7jsye7b17w8elq9qjuu2rnr9e` (`korisnik_id`),
  KEY `FKejj96mgj26ajhd485xr70cg6x` (`oglas_id`),
  CONSTRAINT `FK7jsye7b17w8elq9qjuu2rnr9e` FOREIGN KEY (`korisnik_id`) REFERENCES `korisnici` (`id`),
  CONSTRAINT `FKejj96mgj26ajhd485xr70cg6x` FOREIGN KEY (`oglas_id`) REFERENCES `oglas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Table structure for table `verification_token` */

DROP TABLE IF EXISTS `verification_token`;

CREATE TABLE `verification_token` (
  `token` varchar(255) NOT NULL,
  `expires_at` datetime(6) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`token`),
  UNIQUE KEY `UKq6jibbenp7o9v6tq178xg88hg` (`user_id`),
  CONSTRAINT `FKjy9l5g1tvjm1upw3851kmn5gv` FOREIGN KEY (`user_id`) REFERENCES `korisnici` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
