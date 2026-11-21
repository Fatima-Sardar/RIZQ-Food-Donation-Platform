CREATE DATABASE IF NOT EXISTS rizq_db;
USE rizq_db;

-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: rizq_db
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `claims`
--

DROP TABLE IF EXISTS `claims`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `claims` (
  `id` int NOT NULL AUTO_INCREMENT,
  `donation_id` int NOT NULL,
  `claimant_id` int NOT NULL,
  `status` varchar(30) DEFAULT 'ACCEPTED',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `donation_id` (`donation_id`),
  KEY `claimant_id` (`claimant_id`),
  CONSTRAINT `claims_ibfk_1` FOREIGN KEY (`donation_id`) REFERENCES `donation_posts` (`id`),
  CONSTRAINT `claims_ibfk_2` FOREIGN KEY (`claimant_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `claims`
--

LOCK TABLES `claims` WRITE;
/*!40000 ALTER TABLE `claims` DISABLE KEYS */;
INSERT INTO `claims` VALUES (1,1,6,'ACCEPTED','2026-11-11 19:40:00'),(2,2,9,'ACCEPTED','2026-11-11 20:01:52');
/*!40000 ALTER TABLE `claims` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donation_posts`
--

DROP TABLE IF EXISTS `donation_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation_posts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `donor_id` int NOT NULL,
  `title` varchar(160) NOT NULL,
  `description` text,
  `quantity` int NOT NULL,
  `expiry_at` datetime NOT NULL,
  `pickup_address` varchar(255) NOT NULL,
  `status` enum('AVAILABLE','ACCEPTED','COLLECTED','DELIVERED','REMOVED') DEFAULT 'AVAILABLE',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `donor_id` (`donor_id`),
  CONSTRAINT `donation_posts_ibfk_1` FOREIGN KEY (`donor_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donation_posts`
--

LOCK TABLES `donation_posts` WRITE;
/*!40000 ALTER TABLE `donation_posts` DISABLE KEYS */;
INSERT INTO `donation_posts` VALUES (1,1,'Fresh Food','Fresh food for needy families',20,'2026-11-15 18:00:00','Sukkur IBA University','ACCEPTED','2026-11-11 19:31:08'),(2,1,'Fresh Vegetables','Fresh vegetables for needy families',15,'2026-11-15 18:00:00','Rohri Main Market','ACCEPTED','2026-11-11 19:59:18'),(3,75,'Demo Fresh Food','Fresh food for needy families',25,'2026-12-31 18:00:00','Sukkur City Center','AVAILABLE','2026-11-11 20:11:19'),(4,75,'Biryani','Fresh food for needy families',15,'2026-12-20 18:00:00','Clifton Block 5, Karachi','AVAILABLE','2026-11-11 20:41:15');
/*!40000 ALTER TABLE `donation_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `id` int NOT NULL AUTO_INCREMENT,
  `donation_id` int NOT NULL,
  `user_id` int NOT NULL,
  `rating` int NOT NULL,
  `comments` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `donation_id` (`donation_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`donation_id`) REFERENCES `donation_posts` (`id`),
  CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (1,1,6,5,'Excellent food quality. The donation process was smooth and helpful.','2026-11-11 20:32:44'),(2,1,6,4,'Very good initiative. It helps families who need food support.','2026-11-11 20:33:06'),(3,1,6,3,'Good service, but pickup coordination and communication can improve.','2026-11-11 20:33:09'),(4,1,6,5,'Good food donation service. Thank you RIZQ.','2026-11-11 20:33:42'),(5,1,6,5,'Excellent food quality and helpful service.','2026-11-11 20:33:42'),(6,2,9,4,'Very good experience. The donation process was easy.','2026-11-11 20:33:42'),(7,1,6,5,'Good food donation service. Thank you RIZQ.','2026-11-11 20:34:02'),(8,1,6,5,'Excellent food quality and helpful service.','2026-11-11 20:34:02'),(9,2,9,4,'Very good experience. The donation process was easy.','2026-11-11 20:34:02'),(10,1,6,5,'Good food donation service. Thank you RIZQ.','2026-11-11 20:35:50'),(11,1,6,5,'Excellent food quality and helpful service.','2026-11-11 20:35:50'),(12,2,9,4,'Very good experience. The donation process was easy.','2026-11-11 20:35:50'),(13,1,6,5,'Good food donation service. Thank you RIZQ.','2026-11-11 20:35:54'),(14,1,6,5,'Excellent food quality and helpful service.','2026-11-11 20:35:54'),(15,2,9,4,'Very good experience. The donation process was easy.','2026-11-11 20:35:54');
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `message` varchar(500) NOT NULL,
  `is_read` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(120) NOT NULL,
  `email` varchar(160) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `role` enum('DONOR','VOLUNTEER','WELFARE_ORG','ADMIN') NOT NULL,
  `organization_name` varchar(180) DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Test Donor 2','donor2@test.com','Donor123','03001234567','DONOR',NULL,1,'2026-11-11 18:58:01'),(2,'Ali Ahmed','ali@test.com','Ali123','03001111111','DONOR',NULL,1,'2026-11-11 19:01:02'),(3,'Fatima Khan','fatima@test.com','Fatima123','03002222222','DONOR',NULL,1,'2026-11-11 19:01:02'),(4,'Usman Malik','usman@test.com','Usman123','03003333333','DONOR',NULL,1,'2026-11-11 19:01:02'),(5,'Ayesha Noor','ayesha@test.com','Ayesha123','03004444444','DONOR',NULL,1,'2026-11-11 19:01:02'),(6,'Hassan Raza','hassan@test.com','Hassan123','03005555555','VOLUNTEER',NULL,1,'2026-11-11 19:01:02'),(7,'Sana Ahmed','sana@test.com','Sana123','03006666666','VOLUNTEER',NULL,1,'2026-11-11 19:01:02'),(8,'Bilal Shah','bilal@test.com','Bilal123','03007777777','VOLUNTEER',NULL,1,'2026-11-11 19:01:02'),(9,'Noor Foundation','noor@test.com','Noor123','03008888888','WELFARE_ORG','Noor Foundation',1,'2026-11-11 19:01:02'),(10,'Hope Welfare','hope@test.com','Hope123','03009999999','WELFARE_ORG','Hope Welfare Organization',1,'2026-11-11 19:01:02'),(74,'RIZQ Admin','admin@rizq.com','Admin123','03000000000','ADMIN',NULL,1,'2026-11-11 20:11:19'),(75,'Demo Donor','demo.donor@rizq.com','Demo123','03001111111','DONOR',NULL,1,'2026-11-11 20:11:19'),(76,'Demo Volunteer','demo.volunteer@rizq.com','Demo123','03002222222','VOLUNTEER',NULL,1,'2026-11-11 20:11:19'),(77,'Demo Welfare','demo.welfare@rizq.com','Demo123','03003333333','WELFARE_ORG','Demo Welfare Foundation',1,'2026-11-11 20:11:19');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-12  2:10:37
