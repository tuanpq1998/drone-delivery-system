CREATE DATABASE  IF NOT EXISTS `drone_manager` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `drone_manager`;
-- MySQL dump 10.13  Distrib 8.0.30, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: drone_manager
-- ------------------------------------------------------
-- Server version	8.0.30-0ubuntu0.20.04.2

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
-- Table structure for table `tbl_account`
--

DROP TABLE IF EXISTS `tbl_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fullname` varchar(200) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(65) DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `tbl_account_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `tbl_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_account`
--

LOCK TABLES `tbl_account` WRITE;
/*!40000 ALTER TABLE `tbl_account` DISABLE KEYS */;
INSERT INTO `tbl_account` VALUES (1,'admin','No 1 Dai Co Viet Road, HN','0987654123','admin@admin.com','admin','$2a$10$2rKiOvjptkFuLhfmLsxGZef4yxeEyLDPgEHZzneyDyKRtjTi8xiSW',1),(2,'Nguyen Anh Minh','No 55 Giai Phong, Dong Tam, HN','0123456789','tuan@gmail.com','tuan','$2a$10$XRhFfaq1ucE5LE5.6gLxqe1bmW9DyGi8AqMEUaLt7iYGw0hJujPXG',2),(3,'admin2','Dai hoc Bach Khoa Ha Noi','0123654987','Tuan@gmail.com','tuan2','$2a$10$XRhFfaq1ucE5LE5.6gLxqe1bmW9DyGi8AqMEUaLt7iYGw0hJujPXG',1);
/*!40000 ALTER TABLE `tbl_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_drone`
--

DROP TABLE IF EXISTS `tbl_drone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_drone` (
  `id` int NOT NULL,
  `serial_no` varchar(80) DEFAULT NULL,
  `drone_name` varchar(50) DEFAULT NULL,
  `model_name` varchar(45) DEFAULT NULL,
  `max_weight_package_delivery` int DEFAULT NULL,
  `max_speed` int DEFAULT NULL,
  `max_height` int DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `port` int DEFAULT NULL,
  `active_mission_id` int DEFAULT NULL,
  `location_longitude` decimal(10,5) DEFAULT NULL,
  `location_latitude` decimal(10,5) DEFAULT NULL,
  `location_altitude` decimal(10,5) DEFAULT NULL,
  `last_updated` datetime DEFAULT NULL,
  `step_delivery` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `active_mission_id` (`active_mission_id`),
  CONSTRAINT `tbl_drone_ibfk_1` FOREIGN KEY (`active_mission_id`) REFERENCES `tbl_mission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_drone`
--

LOCK TABLES `tbl_drone` WRITE;
/*!40000 ALTER TABLE `tbl_drone` DISABLE KEYS */;
INSERT INTO `tbl_drone` VALUES (1,'IF7MF9IPDD4V','Tuan drone','DJI Mavic',10,10,10,'2022-08-01 15:54:25','localhost',14540,NULL,105.84262,21.00743,0.03200,'2022-08-08 18:50:46',NULL),(2,'MEC8LIYDUYW4','Tuan drone 2','DJI Mavic',12,10,10,'2022-08-01 10:54:25','localhost',14541,NULL,105.84262,21.00743,0.02900,'2022-08-08 18:50:45',NULL),(3,'1ES51UGMNMPF','Tuandrone3','DJI Mavic',10,15,10,'2022-08-01 01:54:25','localhost',14542,NULL,105.84262,21.00743,0.01200,'2022-08-01 20:10:18',NULL),(4,'MPFSE151UGMN','Tuan drone 4','DJI Mini',4,4,4,'2022-08-01 01:54:25','localhost',14543,NULL,NULL,NULL,NULL,NULL,NULL),(14,'GK5RLL2USTFS','Tuan drone 5','DJI',10,10,10,'2022-08-04 01:07:04','localhost',14544,NULL,NULL,NULL,NULL,NULL,NULL),(15,'E7EBGGGESRDS','Tuandrone 6','Mavic',10,12,10,'2022-08-04 01:08:36','localhost',14545,NULL,NULL,NULL,NULL,NULL,NULL),(16,'E7EBGGGESRDS','Tuandrone 6','DJI',10,12,10,'2022-08-04 01:09:37','localhost',14545,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tbl_drone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_home`
--

DROP TABLE IF EXISTS `tbl_home`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_home` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `altitude` decimal(10,5) DEFAULT NULL,
  `latitude` decimal(10,5) DEFAULT NULL,
  `longitude` decimal(10,5) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_home`
--

LOCK TABLES `tbl_home` WRITE;
/*!40000 ALTER TABLE `tbl_home` DISABLE KEYS */;
INSERT INTO `tbl_home` VALUES (1,0.00000,21.00743,105.84262,'No1 Dai Co Viet Road, HN');
/*!40000 ALTER TABLE `tbl_home` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_mission`
--

DROP TABLE IF EXISTS `tbl_mission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_mission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mission_identifier` varchar(20) DEFAULT NULL,
  `package_name` varchar(100) DEFAULT NULL,
  `size` varchar(15) DEFAULT NULL,
  `weight` varchar(10) DEFAULT NULL,
  `price` varchar(10) DEFAULT NULL,
  `note` varchar(200) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `seller_id` int DEFAULT NULL,
  `drone_id` int DEFAULT NULL,
  `holding_time` decimal(4,1) NOT NULL DEFAULT '60.0',
  `speed_ms` decimal(4,1) NOT NULL DEFAULT '10.0',
  `flying_altitude` decimal(10,5) NOT NULL,
  `status` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_started` tinyint(1) DEFAULT '0',
  `tracking_start` int NOT NULL DEFAULT '0',
  `is_finished` tinyint(1) DEFAULT '0',
  `receiver_name` varchar(20) DEFAULT NULL,
  `receiver_tel` varchar(20) DEFAULT NULL,
  `receiver_address` varchar(200) DEFAULT NULL,
  `receiver_location_latitude` decimal(10,5) DEFAULT NULL,
  `receiver_location_altitude` decimal(10,5) NOT NULL DEFAULT '50.00000',
  `receiver_location_longitude` decimal(10,5) DEFAULT NULL,
  `sender_address` varchar(45) DEFAULT NULL,
  `sender_location_latitude` decimal(10,5) DEFAULT NULL,
  `sender_location_longitude` varchar(20) DEFAULT NULL,
  `sender_location_altitude` decimal(10,5) NOT NULL DEFAULT '50.00000',
  `password` varchar(65) DEFAULT NULL,
  `logs` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mission_identifier_UNIQUE` (`mission_identifier`),
  KEY `drone_id` (`drone_id`),
  KEY `seller_id` (`seller_id`),
  CONSTRAINT `tbl_mission_ibfk_1` FOREIGN KEY (`drone_id`) REFERENCES `tbl_drone` (`id`),
  CONSTRAINT `tbl_mission_ibfk_2` FOREIGN KEY (`seller_id`) REFERENCES `tbl_account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_mission`
--

LOCK TABLES `tbl_mission` WRITE;
/*!40000 ALTER TABLE `tbl_mission` DISABLE KEYS */;
INSERT INTO `tbl_mission` VALUES (2,'9lsZSx1S2mMLxgV','Tai nghe gaming','100x100x100','5',NULL,NULL,'2022-06-08 16:53:56',2,1,12.0,12.0,55.00000,'Drone returned.','2022-08-08 18:44:37',1,0,1,'Tuan','0123456789','Thong Nhat Park, Le Duan',21.00751,50.00000,105.84319,'No 55 Giai Phong, Dong Tam, HN',21.00627,'105.84319',50.00000,'eGQ6rhqoDh/Isa2sir3SrQ==','2022-8-08 18:40:54:: Starting ...|2022-8-08 18:41:33:: Take off from home, going to sender.|2022-8-08 18:41:55:: Arrived at sender, getting the package.|2022-8-08 18:42:22:: Got the package, going to receiver.|2022-8-08 18:42:43:: Arrived at receiver, dropping package.|2022-8-08 18:43:10:: Mission done, returning to home...|2022-8-08 18:44:37:: Drone returned.|'),(4,'XL4Pr42wCRHyq9r','Op dien thoai iPhone','100x150x100','2kg','100000','','2022-06-08 16:58:44',2,NULL,60.0,10.0,0.00000,'DI LAY HANG','2022-08-01 19:47:02',0,0,0,'QTuan','0123456789','1 Duong Dai Co Viet, Hai Ba Trung, HN',NULL,50.00000,NULL,NULL,NULL,NULL,0.00000,'8o2WAYQSUsWH7THj2bj1NQ==',NULL),(5,'tDgT9VvlTUei5bm','Mo hinh Pokemon mini','100x150x100','2kg','100000','','2022-06-08 17:00:00',2,NULL,60.0,10.0,0.00000,'DI LAY HANG',NULL,0,0,0,'QTuan','0123456789','1 Duong Dai Co Viet, Hai Ba Trung, HN',NULL,50.00000,NULL,NULL,NULL,NULL,0.00000,'8o2WAYQSUsWH7THj2bj1NQ==',NULL),(6,'0gSlCmCyMUSz6QI','Giay gau truc','100x150x100','2kg','100000','','2022-06-08 17:01:17',2,NULL,60.0,10.0,0.00000,'DI LAY HANG',NULL,0,0,0,'QTuan','0123456789','1 Duong Dai Co Viet, Hai Ba Trung, HN',NULL,50.00000,NULL,NULL,NULL,NULL,0.00000,'eGQ6rhqoDh/Isa2sir3SrQ==',NULL),(20,'NZrT7XSYfwL3Xv4','Chuot Razer','50x50x50','1','500000','Hang de vo xin nhe tay','2022-08-04 23:45:48',2,NULL,0.0,0.0,100.00000,NULL,NULL,0,0,0,'Dang Minh Tuan','0987654321','Cong vien Thong Nhat, HN',21.01138,50.00000,105.84344,NULL,21.00363,'105.84332323055709',50.00000,'ZL2d6SF/zUFSUI1rIswcdw==',NULL),(21,'7sYyzagTam70z5x','Sim dien thoai 4G','20x20x20','1kg','20000','','2022-08-05 22:51:36',2,NULL,0.0,0.0,100.00000,NULL,NULL,0,0,0,'Pham Tuan Viet','0987423651','Ngõ 78 Đường Giải Phóng, Hanoi, Hanoi',21.00229,50.00000,105.84015,'No 55 Giai Phong, Dong Tam, HN',21.00363,'105.84332323055709',50.00000,'iT78sYtKC8c9z9PpFFc/mw==',NULL),(22,'xufj8VSb0v9XiYI','Do choi Lego','20x20x20','2kg','100000','','2022-08-05 23:06:17',2,2,12.0,12.0,55.00000,'Drone returned.','2022-08-08 18:47:27',1,0,1,'Anh Minh','0123654987','Đường Đại Cồ Việt, Hanoi, Hanoi',21.00816,50.00000,105.84745,'No 55 Giai Phong, Dong Tam, HN',21.00363,'105.84159',50.00000,'gHxWk4lSHFDwzfbeODdfVw==','2022-8-08 18:41:14:: Starting ...|2022-8-08 18:41:52:: Take off from home, going to sender.|2022-8-08 18:42:45:: Arrived at sender, getting the package.|2022-8-08 18:43:12:: Got the package, going to receiver.|2022-8-08 18:44:43:: Arrived at receiver, dropping package.|2022-8-08 18:45:10:: Mission done, returning to home...|2022-8-08 18:47:27:: Drone returned.|');
/*!40000 ALTER TABLE `tbl_mission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_role`
--

DROP TABLE IF EXISTS `tbl_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_role`
--

LOCK TABLES `tbl_role` WRITE;
/*!40000 ALTER TABLE `tbl_role` DISABLE KEYS */;
INSERT INTO `tbl_role` VALUES (1,'admin',NULL),(2,'seller',NULL),(3,'guest',NULL);
/*!40000 ALTER TABLE `tbl_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-09 19:59:03
