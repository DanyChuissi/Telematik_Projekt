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
-- Table structure for table `medicamentstatement`
--

DROP TABLE IF EXISTS `medicamentstatement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicamentstatement` (
  `statID` int(11) NOT NULL AUTO_INCREMENT,
  `PID` int(11) NOT NULL,
  `MeID` int(11) NOT NULL,
  `taken` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `periode` varchar(255) DEFAULT NULL,
  `dosage` varchar(255) DEFAULT NULL,
  idServer varchar(255),
  PRIMARY KEY (`statID`),
  KEY `medicamentstatement_ibfk_1` (`PID`),
  KEY `medicamentstatement_ibfk_2` (`MeID`),
  CONSTRAINT `medicamentstatement_ibfk_1` FOREIGN KEY (`PID`) REFERENCES `patient` (`identifier`) ON DELETE CASCADE,
  CONSTRAINT `medicamentstatement_ibfk_2` FOREIGN KEY (`MeID`) REFERENCES `medicament` (`MeID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicamentstatement`
--

LOCK TABLES `medicamentstatement` WRITE;
/*!40000 ALTER TABLE `medicamentstatement` DISABLE KEYS */;
INSERT INTO `medicamentstatement` VALUES (3,5,1,'y','Nach dem Essen','active','2018-11-08 bis 2018-11-10','2 x täglich'),(4,7,5,'unk','Prevention','on-hold','2018-11-06 bis 2018-11-06','1 x täglich'),(5,7,6,'n','','active','2018-11-06 bis 2018-11-06','1 x täglich'),(6,6,3,'y','','completed','2018-11-06 bis 2018-11-09','1 x täglich'),(7,8,2,'na','','on-hold','2018-11-06 bis 2018-11-16','3 x täglich');
/*!40000 ALTER TABLE `medicamentstatement` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-06 10:45:33
