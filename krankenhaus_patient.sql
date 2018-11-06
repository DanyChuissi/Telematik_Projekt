-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: krankenhaus
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient` (
  `identifier` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `vorname` varchar(255) DEFAULT NULL,
  `gender` varchar(30) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `telefon` varchar(30) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `deseased` tinyint(1) DEFAULT NULL,
  `Street` varchar(255) DEFAULT NULL,
  `Housenumber` int(11) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `postalcode` int(11) DEFAULT NULL,
  `aufnahmeDatum` date DEFAULT NULL,
  `entlassungsDatum` date DEFAULT NULL,
  `entlassungStatus` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`identifier`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (5,'Max ','Musterman','male',NULL,'017623928382','2010-11-18',0,'Allestr',2,'Dortmund',44227,'2018-11-06',NULL,0),(6,'Paul','Müller','male',NULL,'01539090989','2014-07-23',0,'Emil-Figge',4,'Dortmund',44567,'2018-11-06',NULL,0),(7,'Elsa','Edda','female',NULL,'023523339383','2014-01-17',0,'Brüxstr',34,'Essen',33245,'2018-11-06',NULL,0),(8,'Hanz','Peter','unknown',NULL,'025698494849','2014-11-06',1,'Bornstr',88,'Köln',45674,'2018-11-06',NULL,0);
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-06 10:45:32
