-- MySQL dump 10.13  Distrib 8.0.34, for macos13 (arm64)
--
-- Host: cs-dblab01.uwg.westga.edu    Database: cs3230f24b
-- ------------------------------------------------------
-- Server version	8.4.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `address_id` int NOT NULL AUTO_INCREMENT,
  `street` varchar(100) NOT NULL,
  `city` varchar(50) NOT NULL,
  `state` varchar(50) NOT NULL,
  `zip_code` varchar(10) NOT NULL,
  `address_one` varchar(50) DEFAULT NULL,
  `address_two` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'123 Elm St','Springfield','IL','62704',NULL,NULL),(2,'456 Oak St','Austin','TX','73301',NULL,NULL),(3,'789 Pine St','Madison','WI','53703',NULL,NULL),(4,'101 Maple St','Tampa','FL','33602',NULL,NULL),(5,'202 Cedar St','Salem','OR','97301',NULL,NULL),(19,'192 Meadowcliff trail','Carrollton','GA','30116',NULL,NULL),(20,'123 Elm street','color','CT','30116',NULL,NULL),(21,'101 Elm St','Atlanta','GA','30304',NULL,NULL),(22,'101 Elm St','Atlanta','GA','30304',NULL,NULL),(23,'143 Elm Street','Madison','WI','12345',NULL,NULL),(24,'189 Elm Street','Home','WV','87654',NULL,NULL),(25,'992 Elm Street','Madison','WA','09801',NULL,NULL),(26,'8 Hidden Lakes Drive','Carrollton','GA','30116',NULL,NULL),(27,'120 Plum Ave','Orge','AL','25250',NULL,NULL);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` int NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `gender` enum('male','female') NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin1','pass123','Admin','One','male');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int NOT NULL,
  `doctor_id` int NOT NULL,
  `date` datetime NOT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `status` enum('scheduled','completed','cancelled') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `patient_id` (`patient_id`),
  KEY `doctor_id` (`doctor_id`),
  CONSTRAINT `appointment_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,12,4,'2024-11-03 10:20:00','Do not feel good','scheduled'),(2,13,3,'2024-11-17 11:00:00','Flu symptoms','completed'),(3,14,4,'2024-10-18 14:00:00','Back pain','scheduled'),(4,15,5,'2024-10-19 10:00:00','Diabetes follow-up','cancelled'),(5,16,6,'2024-10-20 16:00:00','Blood pressure check','scheduled'),(45,28,6,'2024-11-20 08:40:00','Not feeling well','completed');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diagnosis`
--

DROP TABLE IF EXISTS `diagnosis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diagnosis` (
  `id` int NOT NULL AUTO_INCREMENT,
  `visit_id` int NOT NULL,
  `initial_diagnosis` varchar(255) NOT NULL,
  `final_diagnosis` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `visit_id` (`visit_id`),
  CONSTRAINT `diagnosis_ibfk_1` FOREIGN KEY (`visit_id`) REFERENCES `visit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diagnosis`
--

LOCK TABLES `diagnosis` WRITE;
/*!40000 ALTER TABLE `diagnosis` DISABLE KEYS */;
INSERT INTO `diagnosis` VALUES (1,1,'Flu-like symptoms','Influenza'),(23,128,'','Pas');
/*!40000 ALTER TABLE `diagnosis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `id` int NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `dob` date NOT NULL,
  `gender` enum('male','female') NOT NULL,
  `address_id` int DEFAULT NULL,
  `phone` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `address_id` (`address_id`),
  CONSTRAINT `doctor_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`),
  CONSTRAINT `doctor_ibfk_2` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (2,'Smith','John','1980-01-01','male',1,'111-555-1234'),(3,'Doe','Jane','1982-03-14','female',2,'111-555-5678'),(4,'Kim','James','1975-07-22','male',3,'111-555-9012'),(5,'Lee','Anna','1986-11-30','female',4,'111-555-3456'),(6,'Carter','David','1990-05-10','male',5,'111-555-7890');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lab_test`
--

DROP TABLE IF EXISTS `lab_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lab_test` (
  `labe_code` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `unit_measurement` varchar(20) NOT NULL,
  `low_value` decimal(10,2) NOT NULL,
  `high_value` decimal(10,2) NOT NULL,
  PRIMARY KEY (`labe_code`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lab_test`
--

LOCK TABLES `lab_test` WRITE;
/*!40000 ALTER TABLE `lab_test` DISABLE KEYS */;
INSERT INTO `lab_test` VALUES (1,'Flu','mIU/L',0.00,1.00),(6,'White Blood Cell (WBC)','mIU/L',2.00,10.00),(7,'Low Density Lipoproteins (LDL)','mIU/L',2.00,10.00),(8,'Hepatitis A','mIU/L',2.00,10.00),(9,'Hepatitis B','mIU/L',2.00,10.00);
/*!40000 ALTER TABLE `lab_test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nurse`
--

DROP TABLE IF EXISTS `nurse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nurse` (
  `id` int NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `dob` date NOT NULL,
  `gender` enum('male','female') NOT NULL,
  `address_id` int DEFAULT NULL,
  `phone` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `address_id` (`address_id`),
  CONSTRAINT `nurse_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`),
  CONSTRAINT `nurse_ibfk_2` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nurse`
--

LOCK TABLES `nurse` WRITE;
/*!40000 ALTER TABLE `nurse` DISABLE KEYS */;
INSERT INTO `nurse` VALUES (7,'Ray','Emily','1991-04-21','female',1,'555-8765'),(11,'Brown','Daniel','1992-02-25','male',5,'555-9876'),(12,'Rivera','Daniel','2000-10-27','male',22,'678-621-3892');
/*!40000 ALTER TABLE `nurse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `last_name` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `dob` date NOT NULL,
  `gender` enum('male','female') NOT NULL,
  `address_id` int DEFAULT NULL,
  `phone` varchar(15) NOT NULL,
  `status` enum('active','inactive','discharged') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `address_id` (`address_id`),
  CONSTRAINT `patient_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (12,'Johnson','John','1990-05-11','male',1,'890-555-0001','active'),(13,'Doe','Jane','1993-11-26','female',2,'111-555-0002','inactive'),(14,'Williams','Sam','1985-03-25','male',3,'555-0003','discharged'),(15,'Taylor','Alex','1987-12-15','female',4,'555-0004','active'),(16,'Brown','Chris','1995-01-30','male',5,'555-0005','active'),(24,'rivera','daniel','2001-11-26','male',19,'678-621-3892','active'),(25,'rivera','daniel','2017-11-26','male',20,'678-621-3892','active'),(26,'eds','dillon','2000-11-08','female',23,'123-456-7890','active'),(27,'santos','vitor','1996-11-22','female',24,'876-543-1234','active'),(28,'Hanks','Tom','1988-02-13','male',25,'098-987-8765','active'),(30,'Gaines','Jeffrey','2003-05-06','male',26,'770-624-4895','active'),(31,'Fury','Tommy','2024-11-04','male',27,'770-624-4895','active');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_result`
--

DROP TABLE IF EXISTS `test_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_result` (
  `id` int NOT NULL AUTO_INCREMENT,
  `visit_id` int NOT NULL,
  `lab_code` int NOT NULL,
  `date_performed` varchar(255) NOT NULL,
  `result` varchar(255) DEFAULT NULL,
  `is_abnormal` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `visit_id` (`visit_id`),
  KEY `lab_code` (`lab_code`),
  CONSTRAINT `test_result_ibfk_1` FOREIGN KEY (`visit_id`) REFERENCES `visit` (`id`),
  CONSTRAINT `test_result_ibfk_2` FOREIGN KEY (`lab_code`) REFERENCES `lab_test` (`labe_code`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_result`
--

LOCK TABLES `test_result` WRITE;
/*!40000 ALTER TABLE `test_result` DISABLE KEYS */;
INSERT INTO `test_result` VALUES (1,1,1,'2024-10-16 12:00:00','Positive for flu',1),(73,128,1,'2024-11-20 12:30:57','8.0',1);
/*!40000 ALTER TABLE `test_result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('admin','doctor','nurse','patient') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin1','-792095615','admin'),(2,'docsmith','1828752713','doctor'),(3,'nurseray','-2015850322','nurse'),(4,'tempadmin','1971651579','admin'),(5,'tempdoc','-1428111132','doctor'),(6,'tempnur','-1428101321','nurse'),(7,'michaelbrooks','-1483965482','nurse'),(11,'daniel','1450575459','nurse'),(12,'daniel','1450575459','nurse');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visit`
--

DROP TABLE IF EXISTS `visit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `appointment_id` int NOT NULL,
  `nurse_id` int NOT NULL,
  `doctor_id` int NOT NULL,
  `patient_id` int NOT NULL,
  `diagnosis_id` int DEFAULT NULL,
  `testresults_id` int DEFAULT NULL,
  `date` datetime NOT NULL,
  `systolic_bp` int DEFAULT NULL,
  `diastolic_bp` int DEFAULT NULL,
  `temp` decimal(5,3) DEFAULT NULL,
  `pulse` int DEFAULT NULL,
  `height` decimal(5,2) DEFAULT NULL,
  `weight` decimal(5,2) DEFAULT NULL,
  `symptoms` text,
  `is_editable` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `appointment_id` (`appointment_id`),
  KEY `nurse_id` (`nurse_id`),
  KEY `doctor_id` (`doctor_id`),
  KEY `patient_id` (`patient_id`),
  KEY `diagnosis_id` (`diagnosis_id`),
  KEY `testresults_id` (`testresults_id`),
  CONSTRAINT `visit_ibfk_1` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`id`),
  CONSTRAINT `visit_ibfk_2` FOREIGN KEY (`nurse_id`) REFERENCES `nurse` (`id`),
  CONSTRAINT `visit_ibfk_3` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`),
  CONSTRAINT `visit_ibfk_5` FOREIGN KEY (`diagnosis_id`) REFERENCES `diagnosis` (`id`),
  CONSTRAINT `visit_ibfk_6` FOREIGN KEY (`testresults_id`) REFERENCES `test_result` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visit`
--

LOCK TABLES `visit` WRITE;
/*!40000 ALTER TABLE `visit` DISABLE KEYS */;
INSERT INTO `visit` VALUES (1,1,7,2,12,1,1,'2024-10-16 09:30:00',120,80,98.600,72,5.80,160.00,'Cough, fever',1),(128,45,12,6,28,NULL,NULL,'2024-11-20 08:40:00',60,60,60.000,60,606.00,60.00,'cee',1);
/*!40000 ALTER TABLE `visit` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-28 22:27:58
