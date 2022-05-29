-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: electronic_shoe
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `bill`
--
use heroku_f01b639d6f12b30;

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `bill` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `totalprice` decimal(18,2) DEFAULT NULL,
  `bill_type` int DEFAULT NULL,
  `paying` int DEFAULT NULL COMMENT '0 - trạng thái bình thường\n1 - trạng thái đang thanh toán',
  `status` varchar(15) COLLATE utf8_general_ci DEFAULT NULL,
  `payment` varchar(4) COLLATE utf8_general_ci DEFAULT NULL COMMENT 'onl - thanh toán qua ví điện tử\noff - thanh toán khi nhận hàng',
  `address` varchar(100) COLLATE utf8_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `modified_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_bill_idx` (`user_id`),
  CONSTRAINT `fk_user_bill` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (1,6,7000000.00,1,0,'finished',NULL,'Hà Nội-Huyện Phú Xuyên-Xã Chuyên Mỹ-Đồng Vinh','2021-11-29 17:00:00','2022-05-22 05:48:09'),(13,1,14000000.00,1,0,'waiting',NULL,'Hà Nội-Huyện Phú Xuyên-Xã Chuyên Mỹ-Đồng Vinh','2021-01-05 17:00:00','2022-05-12 14:39:45'),(16,7,7000000.00,1,0,'waiting',NULL,'Hà Nội-Huyện Thanh Trì-Xã Tân Triều-Yên Xá','2022-04-05 17:00:00','2022-04-08 17:00:00'),(17,6,31500000.00,1,0,'finished',NULL,'Hà Nội-Huyện Phú Xuyên-Xã Chuyên Mỹ-Đồng Vinh','2022-01-05 17:00:00','2022-05-12 14:39:50'),(18,6,3500000.00,1,0,'finished',NULL,'Hà Nội-Huyện Phú Xuyên-Xã Chuyên Mỹ-Đồng Vinh','2022-01-05 17:00:00','2022-01-05 17:00:00'),(19,6,24500000.00,1,0,'finished',NULL,'Hà Nội-Huyện Phú Xuyên-Xã Chuyên Mỹ-Đồng Vinh','2022-02-05 17:00:00','2022-02-05 17:00:00'),(20,6,6700000.00,1,0,'finished',NULL,'Hà Nội-Huyện Phú Xuyên-Xã Chuyên Mỹ-Đồng Vinh','2021-02-05 17:00:00','2021-02-05 17:00:00'),(22,6,3500000.00,1,0,'delivering',NULL,'Hà Nội-Huyện Phú Xuyên-Xã Chuyên Mỹ-Đồng Vinh','2022-03-05 17:00:00','2022-04-08 17:00:00'),(23,6,3500000.00,1,0,'finished',NULL,'Hà Nội-Huyện Phú Xuyên-Xã Chuyên Mỹ-Đồng Vinh','2022-03-05 17:00:00','2022-03-05 17:00:00'),(24,6,3500000.00,1,0,'finished',NULL,'Ninh Thuận-Huyện Thuận Nam-Xã Phước Nam-14','2022-03-12 17:00:00','2022-03-12 17:00:00'),(26,1,6449000.00,1,0,'waiting',NULL,'Hà Nội-Huyện Phú Xuyên-Xã Chuyên Mỹ-Đồng Vinh','2022-05-07 10:17:18','2022-05-07 10:17:18'),(28,9,1900000.00,1,0,'delivering',NULL,'Hà Nội-Huyện Thanh Trì-Xã Tân Triều-Yên Xá','2021-03-10 17:00:00','2021-03-10 17:00:00'),(30,9,1900000.00,1,0,'finished',NULL,'Hà Nội-Huyện Thanh Trì-Xã Tân Triều-Yên Xá','2022-04-10 17:00:00','2022-04-10 17:00:00'),(31,9,4699000.00,1,0,'waiting',NULL,'Hà Nội-Huyện Thanh Trì-Xã Tân Triều-Yên Xá','2022-04-10 17:00:00','2022-04-10 17:00:00'),(32,9,18496000.00,1,0,'waiting',NULL,'Hà Nội-Huyện Thanh Trì-Xã Tân Triều-Yên Xá','2022-02-10 17:00:00','2022-02-10 17:00:00'),(33,7,13178000.00,1,0,'waiting',NULL,'Hà Nội-Huyện Thanh Oai-Xã Xuân Dương-1','2022-03-10 17:00:00','2022-03-10 17:00:00'),(34,7,9098000.00,1,0,'waiting',NULL,'Hà Nội-Huyện Thanh Trì-Xã Tân Triều-Yên Xá','2022-01-10 17:00:00','2022-01-10 17:00:00'),(52,8,3900000.00,1,0,'waiting',NULL,'Hà Nội-Huyện Thanh Trì-Xã Tân Triều-241 Yên Xá','2022-05-26 14:05:55','2022-05-26 14:05:55'),(53,14,NULL,0,0,NULL,NULL,NULL,'2022-05-17 14:56:53','2022-05-27 16:14:28'),(56,6,3500000.00,0,1,NULL,NULL,'Hà Nội-Huyện Thanh Trì-Xã Tân Triều-Yên Xá','2022-05-28 04:37:06','2022-05-28 04:37:45'),(57,8,6000000.00,0,0,NULL,NULL,'Hà Nội-Huyện Thanh Trì-Xã Tân Triều-Yên Xá','2022-05-29 02:09:25','2022-05-29 02:24:36');
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `brand` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_general_ci DEFAULT NULL,
  `slug` varchar(45) COLLATE utf8_general_ci DEFAULT NULL,
  `banner` varchar(45) COLLATE utf8_general_ci DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `modified_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (1,'Adidas','adidas','8f428146-5491-47ad-afdf-b12f9e10134c.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/banner%2F8f428146-5491-47ad-afdf-b12f9e10134c.jpg?alt=media',NULL,'2022-05-04 19:08:52'),(2,'Converse','converse','a57125cc-a1db-4cc6-9578-57c5bce2db01.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/banner%2Fa57125cc-a1db-4cc6-9578-57c5bce2db01.jpg?alt=media',NULL,'2022-04-25 17:00:00'),(3,'Nike','nike','fd94ae45-3667-434b-8879-d43da036f92a.gif','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/banner%2Ffd94ae45-3667-434b-8879-d43da036f92a.gif?alt=media',NULL,'2022-04-25 17:00:00');
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `color`
--

DROP TABLE IF EXISTS `color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `color` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_general_ci DEFAULT NULL,
  `slug` varchar(45) COLLATE utf8_general_ci DEFAULT NULL,
  `code` varchar(10) COLLATE utf8_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `modified_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `slug_UNIQUE` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `color`
--

LOCK TABLES `color` WRITE;
/*!40000 ALTER TABLE `color` DISABLE KEYS */;
INSERT INTO `color` VALUES (1,'Đen','den','#0a0a0a',NULL,'2022-03-29 17:00:00'),(2,'Trắng','trang','#ffffff',NULL,'2022-03-28 17:00:00'),(3,'Vàng','vang','#f6ff00',NULL,'2022-04-24 17:00:00'),(4,'Nâu','nau','#5c2d2d',NULL,'2022-03-28 17:00:00'),(5,'Đỏ','do','#ff0000',NULL,'2022-03-28 17:00:00'),(6,'Xanh dương','xanh-duong','#0049f5',NULL,'2022-03-28 17:00:00'),(7,'Xanh lá cây','xanh-la-cay','#00b303',NULL,'2022-03-28 17:00:00'),(8,'black red','black-red','#050505',NULL,'2022-03-27 17:00:00'),(9,'Xám','xam','#8c8c8c',NULL,'2022-03-28 17:00:00'),(10,'red blue','red-blue','#0084ff',NULL,'2022-03-27 17:00:00'),(11,'Bạc','bac','#cecaca','2022-03-29 17:00:00','2022-03-29 17:00:00'),(12,'Tím','tim','#9900ff','2022-03-29 17:00:00','2022-03-29 17:00:00');
/*!40000 ALTER TABLE `color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `title` varchar(45) COLLATE utf8_general_ci DEFAULT NULL,
  `content` varchar(255) COLLATE utf8_general_ci DEFAULT NULL,
  `star` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `modified_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_prod_comments_idx` (`product_id`),
  KEY `fk_user_comments_idx` (`user_id`),
  CONSTRAINT `fk_prod_comments` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `fk_user_comments` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,6,3,'Good','Sản phẩm rất đẹp, đi rất thoải mái',4,'2021-12-06 17:00:00','2021-12-06 17:00:00'),(2,6,1,'Good','sản phẩm tốt, đi rất vừa chân................',5,'2022-01-05 17:00:00','2022-01-05 17:00:00'),(3,6,2,'Good','Sản phẩm vừa vặn, êm chân .................................',5,'2022-01-05 17:00:00','2022-01-05 17:00:00'),(4,6,5,'Good','giá cả hợp lý, giày đẹp.......................',5,'2022-01-05 17:00:00','2022-01-05 17:00:00'),(5,1,3,'Nice','giày đẹp, đi êm chân ...............................',4,'2022-01-05 17:00:00','2022-01-05 17:00:00'),(6,7,3,'Nice','giày đẹp, đi êm chân ..............................',5,'2022-01-05 17:00:00','2022-01-05 17:00:00');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `discount` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `event_id` bigint DEFAULT NULL,
  `discount` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_discount_event_idx` (`event_id`),
  CONSTRAINT `fk_discount_event` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount`
--

LOCK TABLES `discount` WRITE;
/*!40000 ALTER TABLE `discount` DISABLE KEYS */;
INSERT INTO `discount` VALUES (18,11,10),(19,11,20),(20,12,10),(21,12,20);
/*!40000 ALTER TABLE `discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `events` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_general_ci DEFAULT NULL,
  `slug` varchar(45) COLLATE utf8_general_ci DEFAULT NULL,
  `start_at` timestamp NULL DEFAULT NULL,
  `end_at` timestamp NULL DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_general_ci DEFAULT NULL,
  `show_web` int DEFAULT NULL,
  `style` varchar(5) COLLATE utf8_general_ci DEFAULT NULL,
  `banner` varchar(45) COLLATE utf8_general_ci DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `modified_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES (11,'Tết','tet','2022-05-24 09:29:00','2022-05-31 09:29:00','Tết',1,'dark','211950d7-3754-4748-878b-86c3f381ebb3.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/banner%2F211950d7-3754-4748-878b-86c3f381ebb3.jpg?alt=media','2022-05-22 09:29:48','2022-05-22 09:29:48'),(12,'Dọn kho','don-kho','2022-05-22 09:34:00','2022-06-16 09:34:00','Dọn kho',0,'dark','c9b43c28-f0e0-46c3-a193-56f0f49cbf51.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/banner%2Fc9b43c28-f0e0-46c3-a193-56f0f49cbf51.jpg?alt=media','2022-05-22 09:35:03','2022-05-22 09:53:43');
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `images` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL,
  `color_id` bigint NOT NULL,
  `parent` int DEFAULT NULL,
  `image` varchar(45) COLLATE utf8_general_ci DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `modified_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=487 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` VALUES (1,1,6,1,'2f2c0054-6eba-4dd6-bf5b-1d383c17d1e6.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F2f2c0054-6eba-4dd6-bf5b-1d383c17d1e6.jpg?alt=media',NULL,'2022-04-16 17:00:00'),(2,1,7,1,'b8ae1992-ed97-4634-8718-e655dd44bfc9.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fb8ae1992-ed97-4634-8718-e655dd44bfc9.jpg?alt=media',NULL,'2022-04-16 17:00:00'),(3,2,1,1,'4171c6b0-e193-4b14-8f75-9b6d92041cbc.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F4171c6b0-e193-4b14-8f75-9b6d92041cbc.jpg?alt=media',NULL,'2022-04-16 17:00:00'),(4,2,2,1,'a32682ca-6345-44dc-a00f-81b89b17eea5.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fa32682ca-6345-44dc-a00f-81b89b17eea5.jpg?alt=media',NULL,'2022-04-16 17:00:00'),(5,3,1,1,'9754902e-190d-49f1-8234-1e165bb77973.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F9754902e-190d-49f1-8234-1e165bb77973.jpg?alt=media',NULL,'2022-04-16 17:00:00'),(6,3,2,1,'f8946229-0f8c-469b-b669-2eaa051a029c.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Ff8946229-0f8c-469b-b669-2eaa051a029c.jpg?alt=media',NULL,'2022-04-16 17:00:00'),(7,3,9,1,'56ba2176-dd23-4f6a-8f9a-0361e27bc8cc.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F56ba2176-dd23-4f6a-8f9a-0361e27bc8cc.jpg?alt=media',NULL,'2022-04-16 17:00:00'),(8,4,8,1,'97bf1497-d9d1-411c-b968-7934b6a28a83.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F97bf1497-d9d1-411c-b968-7934b6a28a83.jpg?alt=media',NULL,'2022-04-16 17:00:00'),(9,4,2,1,'c8e3996d-d10a-4131-86fc-819ad4c961f4.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fc8e3996d-d10a-4131-86fc-819ad4c961f4.jpg?alt=media',NULL,'2022-04-16 17:00:00'),(10,5,1,1,'9c08f964-296f-4d6a-8c72-1d56349a676d.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F9c08f964-296f-4d6a-8c72-1d56349a676d.jpg?alt=media',NULL,'2022-04-16 17:00:00'),(11,5,2,1,'81ee8634-c8c8-410c-b1e9-9c0efbe5e378.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F81ee8634-c8c8-410c-b1e9-9c0efbe5e378.jpg?alt=media',NULL,'2022-04-16 17:00:00'),(44,4,1,1,'d1e9578b-0221-48d4-86fa-5cc41aaccc13.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fd1e9578b-0221-48d4-86fa-5cc41aaccc13.jpg?alt=media','2022-02-07 17:00:00','2022-04-16 17:00:00'),(50,4,6,1,'30ca7806-957f-47d0-8e18-2397d70f0ec2.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F30ca7806-957f-47d0-8e18-2397d70f0ec2.jpg?alt=media','2022-02-07 17:00:00','2022-04-16 17:00:00'),(171,6,2,1,'93b47c7b-9a7f-4d0c-aa38-7eda6b35e0cf.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F93b47c7b-9a7f-4d0c-aa38-7eda6b35e0cf.jpg?alt=media','2022-03-17 17:00:00','2022-04-16 17:00:00'),(175,6,1,1,'f86efe1f-35c1-4f0a-9511-339846d08546.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Ff86efe1f-35c1-4f0a-9511-339846d08546.jpg?alt=media','2022-03-17 17:00:00','2022-04-16 17:00:00'),(179,6,6,1,'3298cf52-fdcd-4f3c-900e-f298414ccaaf.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F3298cf52-fdcd-4f3c-900e-f298414ccaaf.jpg?alt=media','2022-03-17 17:00:00','2022-04-16 17:00:00'),(183,7,7,1,'8ab6b228-8e6a-4b0c-8167-b282e2215c5f.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F8ab6b228-8e6a-4b0c-8167-b282e2215c5f.jpg?alt=media','2022-03-17 17:00:00','2022-04-16 17:00:00'),(187,7,1,1,'1edfc318-3874-4ee0-b0c0-5aec23923659.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F1edfc318-3874-4ee0-b0c0-5aec23923659.jpg?alt=media','2022-03-17 17:00:00','2022-04-16 17:00:00'),(196,7,3,1,'9d38058a-3ecf-40db-bafe-48bf3244f947.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F9d38058a-3ecf-40db-bafe-48bf3244f947.jpg?alt=media','2022-03-17 17:00:00','2022-04-16 17:00:00'),(200,8,3,1,'00ebbddc-4a8c-4b30-b149-75cbca8bf205.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F00ebbddc-4a8c-4b30-b149-75cbca8bf205.jpg?alt=media','2022-03-17 17:00:00','2022-04-16 17:00:00'),(204,9,1,1,'c14ee7ee-30af-4015-9e2e-2f7cc402c82c.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fc14ee7ee-30af-4015-9e2e-2f7cc402c82c.jpg?alt=media','2022-03-17 17:00:00','2022-04-16 17:00:00'),(208,10,2,1,'3fb57692-1519-40ad-aa83-63e1bf1dee9d.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F3fb57692-1519-40ad-aa83-63e1bf1dee9d.jpg?alt=media','2022-03-18 17:00:00','2022-04-16 17:00:00'),(214,10,1,1,'a0b6e2ab-daf2-4213-9e7b-4e933d57f243.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fa0b6e2ab-daf2-4213-9e7b-4e933d57f243.jpg?alt=media','2022-03-18 17:00:00','2022-04-16 17:00:00'),(220,11,1,1,'1ddf3f16-6f8f-4bdb-9658-8dfb593eb6dc.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F1ddf3f16-6f8f-4bdb-9658-8dfb593eb6dc.jpg?alt=media','2022-03-18 17:00:00','2022-04-16 17:00:00'),(227,11,2,1,'af44af5b-38eb-4dc2-9781-4bae8b5606c6.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Faf44af5b-38eb-4dc2-9781-4bae8b5606c6.jpg?alt=media','2022-03-18 17:00:00','2022-04-16 17:00:00'),(233,12,4,1,'02f3956b-8594-4b18-bae6-e7a3878e2fb0.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F02f3956b-8594-4b18-bae6-e7a3878e2fb0.jpg?alt=media','2022-03-18 17:00:00','2022-04-16 17:00:00'),(239,13,1,1,'c4ca2153-e5ec-4ae5-8211-bc0c45b925b8.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fc4ca2153-e5ec-4ae5-8211-bc0c45b925b8.jpg?alt=media','2022-03-18 17:00:00','2022-04-16 17:00:00'),(245,13,9,1,'2c85fe11-e0b1-4dfe-b452-3d919e2ce986.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F2c85fe11-e0b1-4dfe-b452-3d919e2ce986.jpg?alt=media','2022-03-18 17:00:00','2022-04-16 17:00:00'),(251,13,10,1,'f52cfc36-dc06-4993-8f57-adf8a7f8d114.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Ff52cfc36-dc06-4993-8f57-adf8a7f8d114.jpg?alt=media','2022-03-18 17:00:00','2022-04-16 17:00:00'),(291,1,6,0,'f036a8e8-2c2a-4519-b3d6-d3f914b6c424.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Ff036a8e8-2c2a-4519-b3d6-d3f914b6c424.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(292,1,6,0,'a77371e3-1967-4578-a41d-0d5a318b743e.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fa77371e3-1967-4578-a41d-0d5a318b743e.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(293,1,6,0,'8c78b06b-dbf4-4e69-b822-c0009317c10b.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F8c78b06b-dbf4-4e69-b822-c0009317c10b.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(294,1,6,0,'d46f543c-52ee-4a68-8e66-4dcd063e838d.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fd46f543c-52ee-4a68-8e66-4dcd063e838d.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(295,1,7,0,'2e48f68c-c6c8-4998-88da-df884dd389fb.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F2e48f68c-c6c8-4998-88da-df884dd389fb.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(296,1,7,0,'6c3d8936-5942-42f9-bbe6-086fe6367311.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F6c3d8936-5942-42f9-bbe6-086fe6367311.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(297,1,7,0,'45710665-58c0-4850-a1f9-cc85c0eaca8c.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F45710665-58c0-4850-a1f9-cc85c0eaca8c.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(298,1,7,0,'c2f5c22b-8285-43ad-974e-7dcc70d276e8.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fc2f5c22b-8285-43ad-974e-7dcc70d276e8.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(299,2,1,0,'5c28787e-57f9-4fe8-a621-dcf97d1fe090.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F5c28787e-57f9-4fe8-a621-dcf97d1fe090.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(300,2,1,0,'36c74b60-f23d-4273-8eeb-589492fc2481.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F36c74b60-f23d-4273-8eeb-589492fc2481.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(301,2,1,0,'370b5d82-1351-4dda-b74f-a66a61a238ee.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F370b5d82-1351-4dda-b74f-a66a61a238ee.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(302,2,1,0,'ddc7600f-6014-45b1-83c5-96279ba1932e.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fddc7600f-6014-45b1-83c5-96279ba1932e.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(303,2,2,0,'46f8d93c-f601-4f7c-ab35-ebc386d5b2ee.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F46f8d93c-f601-4f7c-ab35-ebc386d5b2ee.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(304,2,2,0,'71d8d7d2-17e7-417d-8c3f-7391a4cb712d.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F71d8d7d2-17e7-417d-8c3f-7391a4cb712d.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(305,2,2,0,'5492ffcb-df69-41f6-9b5e-fa206becd043.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F5492ffcb-df69-41f6-9b5e-fa206becd043.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(306,2,2,0,'c476e202-b87c-4fc4-bcb0-e9026aafcd16.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fc476e202-b87c-4fc4-bcb0-e9026aafcd16.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(307,2,2,0,'1c714b8b-1860-45de-85ae-33faef9309c2.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F1c714b8b-1860-45de-85ae-33faef9309c2.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(308,4,1,0,'b4194698-d37b-4288-a01a-8e6390589cbb.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fb4194698-d37b-4288-a01a-8e6390589cbb.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(309,4,1,0,'187aa785-289d-4f86-b9a6-dc31737558c4.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F187aa785-289d-4f86-b9a6-dc31737558c4.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(310,4,1,0,'278074e9-e027-434a-b0d3-c6bf41d0b3f5.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F278074e9-e027-434a-b0d3-c6bf41d0b3f5.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(311,4,1,0,'f7e2ef75-567f-4987-8d82-003bc849ddbd.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Ff7e2ef75-567f-4987-8d82-003bc849ddbd.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(312,4,1,0,'83153e0f-6b4b-43b6-97a8-4ba3f01ba535.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F83153e0f-6b4b-43b6-97a8-4ba3f01ba535.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(313,4,6,0,'27290e91-09b4-4856-8dde-620c50578dab.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F27290e91-09b4-4856-8dde-620c50578dab.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(314,4,6,0,'57f729f2-8861-4cbe-8ba1-4a23309cd68c.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F57f729f2-8861-4cbe-8ba1-4a23309cd68c.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(315,4,6,0,'cd5bad7a-c716-477f-a487-d28e905da923.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fcd5bad7a-c716-477f-a487-d28e905da923.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(316,4,6,0,'78eadfad-826d-41f0-9209-eaeed0842e19.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F78eadfad-826d-41f0-9209-eaeed0842e19.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(317,4,6,0,'d0bd9a84-f5fa-4bf7-9c79-fd599205219d.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fd0bd9a84-f5fa-4bf7-9c79-fd599205219d.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(318,4,2,0,'0acd73e9-455f-4a4a-b051-a4461b84ba71.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F0acd73e9-455f-4a4a-b051-a4461b84ba71.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(319,4,2,0,'034d6f93-b58d-48a2-8c2a-5d49e5608592.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F034d6f93-b58d-48a2-8c2a-5d49e5608592.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(320,4,2,0,'f6c813f7-bafc-4121-bcde-f6632a27674c.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Ff6c813f7-bafc-4121-bcde-f6632a27674c.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(321,4,2,0,'80809b56-39d5-48b7-b682-67ed32f903ba.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F80809b56-39d5-48b7-b682-67ed32f903ba.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(322,4,2,0,'9d8e744d-7e51-4ee5-a52b-ed16d68bd071.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F9d8e744d-7e51-4ee5-a52b-ed16d68bd071.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(323,4,8,0,'bd714c3a-2e20-43f9-92e4-3c23e219acda.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fbd714c3a-2e20-43f9-92e4-3c23e219acda.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(324,4,8,0,'c44da05f-61d1-47d3-8bc4-66ff324db9ef.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fc44da05f-61d1-47d3-8bc4-66ff324db9ef.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(325,4,8,0,'ec5dff2a-d189-4e31-9664-0944b0ccb588.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fec5dff2a-d189-4e31-9664-0944b0ccb588.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(326,4,8,0,'ee32997d-7979-4e29-934c-77709cb22bbb.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fee32997d-7979-4e29-934c-77709cb22bbb.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(327,4,8,0,'ddc17432-9cb5-4abc-86b3-8eba2b8c6157.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fddc17432-9cb5-4abc-86b3-8eba2b8c6157.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(328,5,1,0,'e14fd46c-4c68-4b1c-b0b2-e824a368e70f.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fe14fd46c-4c68-4b1c-b0b2-e824a368e70f.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(329,5,1,0,'1e6819cd-2c09-4f2b-89f9-0ddbfcee30b6.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F1e6819cd-2c09-4f2b-89f9-0ddbfcee30b6.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(330,5,1,0,'b1e586c7-f87e-4f4f-a0de-6bf1ec07d756.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fb1e586c7-f87e-4f4f-a0de-6bf1ec07d756.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(331,5,1,0,'1ea0f3eb-9dcd-4746-b7c0-c57d9ec732f5.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F1ea0f3eb-9dcd-4746-b7c0-c57d9ec732f5.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(332,5,1,0,'c107a3f1-ecf1-4ffa-84e4-7a5019b2b5c8.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fc107a3f1-ecf1-4ffa-84e4-7a5019b2b5c8.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(333,5,2,0,'8a8e682e-a97f-475c-a407-3d2286b9243b.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F8a8e682e-a97f-475c-a407-3d2286b9243b.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(334,5,2,0,'970bbd5d-8801-4a82-8e2e-24b46e7f8e68.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F970bbd5d-8801-4a82-8e2e-24b46e7f8e68.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(335,5,2,0,'3ec62597-62b1-4bc8-a47e-77e48465e1a5.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F3ec62597-62b1-4bc8-a47e-77e48465e1a5.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(336,5,2,0,'8347d9fe-533b-4785-8515-aaf5fdb9153a.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F8347d9fe-533b-4785-8515-aaf5fdb9153a.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(337,5,2,0,'82e50491-c820-4f28-a1d4-0dc446cdea4a.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F82e50491-c820-4f28-a1d4-0dc446cdea4a.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(338,6,1,0,'972e7c84-3e57-491b-adbc-f2500b39d6ed.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F972e7c84-3e57-491b-adbc-f2500b39d6ed.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(339,6,1,0,'2bc9e3ec-b2ff-4cc8-9806-66af8d5def58.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F2bc9e3ec-b2ff-4cc8-9806-66af8d5def58.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(340,6,1,0,'bd86f41f-faae-42c7-8bb2-2bfdc3f4c9dc.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fbd86f41f-faae-42c7-8bb2-2bfdc3f4c9dc.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(341,6,2,0,'c5f59cb5-c4ec-4d3d-b27a-939bec83bd13.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fc5f59cb5-c4ec-4d3d-b27a-939bec83bd13.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(342,6,2,0,'9b5f8555-8afb-4bbc-8e33-8e5f53655195.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F9b5f8555-8afb-4bbc-8e33-8e5f53655195.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(343,6,2,0,'0fbc676d-8cd3-4026-90ff-e87fe93c1bd5.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F0fbc676d-8cd3-4026-90ff-e87fe93c1bd5.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(344,6,6,0,'5bf06659-8a56-4b4f-8c49-bbe16f41c8f3.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F5bf06659-8a56-4b4f-8c49-bbe16f41c8f3.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(345,6,6,0,'07e5fd6c-e460-46b1-bf84-93155574b5ca.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F07e5fd6c-e460-46b1-bf84-93155574b5ca.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(346,6,6,0,'064cb4ed-ed87-4384-8d60-e36fd00afb02.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F064cb4ed-ed87-4384-8d60-e36fd00afb02.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(347,7,1,0,'2df10e71-d131-47bc-9e15-adb81b61eb41.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F2df10e71-d131-47bc-9e15-adb81b61eb41.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(348,7,1,0,'acec3023-a70f-48ca-8ba1-714d5aadcad4.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Facec3023-a70f-48ca-8ba1-714d5aadcad4.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(349,7,1,0,'72ebe7f5-49ca-4a62-b6ce-d7530a19b86a.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F72ebe7f5-49ca-4a62-b6ce-d7530a19b86a.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(350,7,1,0,'6def5157-3a3b-4bd5-8031-c50c1eb6a661.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F6def5157-3a3b-4bd5-8031-c50c1eb6a661.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(351,7,1,0,'6e14b50c-aa19-4c5f-b764-9584da562c16.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F6e14b50c-aa19-4c5f-b764-9584da562c16.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(352,7,3,0,'f4a618b2-873f-4082-984c-4c2f86061373.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Ff4a618b2-873f-4082-984c-4c2f86061373.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(353,7,3,0,'e2f6ffd4-1bc0-4679-ae83-91621ccb56b6.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fe2f6ffd4-1bc0-4679-ae83-91621ccb56b6.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(354,7,3,0,'2dd2789a-78a9-4414-889b-c1d83ba4c847.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F2dd2789a-78a9-4414-889b-c1d83ba4c847.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(355,7,7,0,'04f793c5-672d-413c-bee8-57558088c2e0.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F04f793c5-672d-413c-bee8-57558088c2e0.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(356,7,7,0,'85f62dd5-b801-4535-87e1-9433a58e8046.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F85f62dd5-b801-4535-87e1-9433a58e8046.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(357,7,7,0,'9da8a6da-3d2e-4f55-afb3-d30083557032.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F9da8a6da-3d2e-4f55-afb3-d30083557032.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(358,8,3,0,'79fc94c5-3d6c-44a7-9ba9-634aead86154.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F79fc94c5-3d6c-44a7-9ba9-634aead86154.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(359,8,3,0,'07a017e9-b4dd-4926-b80d-7cef3c332517.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F07a017e9-b4dd-4926-b80d-7cef3c332517.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(360,8,3,0,'d4dd522b-23fd-4582-9c7e-52cf58686df3.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fd4dd522b-23fd-4582-9c7e-52cf58686df3.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(361,9,1,0,'9b1ed476-a4c3-424a-ae96-ec81571ccad2.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F9b1ed476-a4c3-424a-ae96-ec81571ccad2.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(362,9,1,0,'312e57d6-62b1-4eff-abae-90e8e38c17b4.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F312e57d6-62b1-4eff-abae-90e8e38c17b4.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(363,9,1,0,'9683b3ba-9928-4371-b130-73c5bbe0df54.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F9683b3ba-9928-4371-b130-73c5bbe0df54.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(404,3,2,0,'544aa3d3-226c-4c0d-a3d6-1197488d1eba.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F544aa3d3-226c-4c0d-a3d6-1197488d1eba.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(405,3,2,0,'4182812b-26eb-4883-b47a-fcbbbe5df85f.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F4182812b-26eb-4883-b47a-fcbbbe5df85f.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(406,3,2,0,'3ee0e174-453c-41b2-a796-3b12b7cd233b.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F3ee0e174-453c-41b2-a796-3b12b7cd233b.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(407,3,2,0,'ada21919-dce8-4250-9ec3-89dc69bd70af.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fada21919-dce8-4250-9ec3-89dc69bd70af.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(408,3,1,0,'de5f7d97-19e4-48b7-9208-7ee3721feb94.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fde5f7d97-19e4-48b7-9208-7ee3721feb94.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(409,3,1,0,'74ddeb96-9204-4517-9f0a-123aeb8bd7ab.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F74ddeb96-9204-4517-9f0a-123aeb8bd7ab.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(410,3,1,0,'38daf209-783e-43d5-b176-a6d71c415634.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F38daf209-783e-43d5-b176-a6d71c415634.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(411,3,1,0,'8e7f2853-8b29-4fa2-be93-505d958ba655.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F8e7f2853-8b29-4fa2-be93-505d958ba655.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(412,3,9,0,'645501b0-bf69-4247-a504-52b54059b7cd.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F645501b0-bf69-4247-a504-52b54059b7cd.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(413,3,9,0,'ce01cc43-5738-4af4-a93f-ee7387deef3b.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fce01cc43-5738-4af4-a93f-ee7387deef3b.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(414,3,9,0,'f0966374-223b-4c0a-a6f9-5b995621b0a2.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Ff0966374-223b-4c0a-a6f9-5b995621b0a2.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(415,3,9,0,'b20656b8-977d-4565-947e-8a888cc39201.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fb20656b8-977d-4565-947e-8a888cc39201.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(416,3,9,0,'902516bb-fd23-4dfa-84c6-d93353d9999b.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F902516bb-fd23-4dfa-84c6-d93353d9999b.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(417,12,4,0,'eb254a3c-97f3-4434-b70d-0e15d31ae9cd.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Feb254a3c-97f3-4434-b70d-0e15d31ae9cd.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(418,12,4,0,'b3abf464-9ff2-4d25-95a0-41294db2159f.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fb3abf464-9ff2-4d25-95a0-41294db2159f.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(419,12,4,0,'f7f95103-930d-4a54-b844-3464d114ffe0.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Ff7f95103-930d-4a54-b844-3464d114ffe0.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(420,12,4,0,'1294a707-df5e-4e1d-8f33-6e1cdd1da26b.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F1294a707-df5e-4e1d-8f33-6e1cdd1da26b.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(421,12,4,0,'8df86520-53a4-463d-97d2-7643c2ec58a0.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F8df86520-53a4-463d-97d2-7643c2ec58a0.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(422,10,1,0,'7906ea7a-a858-473a-883e-3cc1ec1516d2.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F7906ea7a-a858-473a-883e-3cc1ec1516d2.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(423,10,1,0,'1f285084-92ac-46f5-b3a7-586f8b7f7f97.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F1f285084-92ac-46f5-b3a7-586f8b7f7f97.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(424,10,1,0,'453907e0-ca8f-4a05-8780-7214434cc6c7.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F453907e0-ca8f-4a05-8780-7214434cc6c7.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(425,10,1,0,'3ebfc506-7a86-42f6-9035-5ea2032c807c.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F3ebfc506-7a86-42f6-9035-5ea2032c807c.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(426,10,1,0,'6c8c9edf-c39d-4e78-aa5b-0b6c9712e338.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F6c8c9edf-c39d-4e78-aa5b-0b6c9712e338.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(427,10,2,0,'306abca6-a0fb-4479-8f75-8d79d7774f71.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F306abca6-a0fb-4479-8f75-8d79d7774f71.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(428,10,2,0,'2b5b046b-18b6-421c-964c-e2e2f866cd71.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F2b5b046b-18b6-421c-964c-e2e2f866cd71.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(429,10,2,0,'43ca8a80-5f84-45fb-9e12-29f7153d56a2.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F43ca8a80-5f84-45fb-9e12-29f7153d56a2.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(430,10,2,0,'4f9bfa3f-8a76-4cac-aeeb-150b2f0f7e04.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F4f9bfa3f-8a76-4cac-aeeb-150b2f0f7e04.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(431,10,2,0,'8bae7712-e7bf-42a6-8278-1d46e2418b09.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F8bae7712-e7bf-42a6-8278-1d46e2418b09.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(432,11,1,0,'521da27a-25b5-43b3-b31d-c9da72094a19.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F521da27a-25b5-43b3-b31d-c9da72094a19.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(433,11,1,0,'50b31e2b-4dfb-43bd-934a-f878ab357a93.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F50b31e2b-4dfb-43bd-934a-f878ab357a93.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(434,11,1,0,'2c24fc83-1bc4-4e44-b62b-e7f69a3e7cfe.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F2c24fc83-1bc4-4e44-b62b-e7f69a3e7cfe.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(435,11,1,0,'9768ac6e-cdad-4f56-ae4c-61e5b505d5eb.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F9768ac6e-cdad-4f56-ae4c-61e5b505d5eb.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(436,11,1,0,'c8ca38ec-226a-425c-8825-0d0ce4a07313.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fc8ca38ec-226a-425c-8825-0d0ce4a07313.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(437,11,2,0,'2545ea01-b2ef-4edb-ab8c-b71edc06625d.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F2545ea01-b2ef-4edb-ab8c-b71edc06625d.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(438,11,2,0,'ba8da82b-6fe8-4a2e-b6b5-44e242f89b1e.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fba8da82b-6fe8-4a2e-b6b5-44e242f89b1e.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(439,11,2,0,'3c2e03c4-51b4-4677-9cfe-d6af04ab822e.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F3c2e03c4-51b4-4677-9cfe-d6af04ab822e.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(440,11,2,0,'274d381a-a1b2-4bae-8264-f763c5f17e7c.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F274d381a-a1b2-4bae-8264-f763c5f17e7c.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(441,11,2,0,'4f86466c-3ba7-48dd-8ea1-e73ed11696d1.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F4f86466c-3ba7-48dd-8ea1-e73ed11696d1.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(442,13,10,0,'3835af08-447c-4977-ba80-b25ece2ad87b.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F3835af08-447c-4977-ba80-b25ece2ad87b.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(443,13,10,0,'89985c3a-9732-41cf-93b5-4639b3d682a9.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F89985c3a-9732-41cf-93b5-4639b3d682a9.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(444,13,10,0,'d172fcf8-c916-4f29-a544-1f41552a0a53.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fd172fcf8-c916-4f29-a544-1f41552a0a53.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(445,13,10,0,'627bfbab-14f0-4021-9faa-cb91bac285b9.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F627bfbab-14f0-4021-9faa-cb91bac285b9.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(446,13,10,0,'92d80f3f-2892-4cd2-98fa-61649dfdf989.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F92d80f3f-2892-4cd2-98fa-61649dfdf989.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(447,13,1,0,'d706dbab-10a6-47d8-89a6-b32bdfb62472.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fd706dbab-10a6-47d8-89a6-b32bdfb62472.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(448,13,1,0,'e484f181-769a-40be-84fd-226b8e055351.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fe484f181-769a-40be-84fd-226b8e055351.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(449,13,1,0,'16c353af-d36b-49f6-ac24-c7873c098973.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F16c353af-d36b-49f6-ac24-c7873c098973.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(450,13,1,0,'4b23a8fc-d43f-47b8-bdc0-87848b497843.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F4b23a8fc-d43f-47b8-bdc0-87848b497843.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(451,13,1,0,'00108729-a8eb-4f90-8fc7-dcc7f41a77f6.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F00108729-a8eb-4f90-8fc7-dcc7f41a77f6.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(452,13,9,0,'a15350e4-1290-4937-859d-098cb9e9f153.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fa15350e4-1290-4937-859d-098cb9e9f153.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(453,13,9,0,'7513fdaf-1bd2-479b-aa5d-67c9c9253e74.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F7513fdaf-1bd2-479b-aa5d-67c9c9253e74.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(454,13,9,0,'ce81ec06-e317-4cd7-b38f-9c639d104a67.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fce81ec06-e317-4cd7-b38f-9c639d104a67.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(455,13,9,0,'862e50bb-187a-4294-bd2f-585481ddacf0.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F862e50bb-187a-4294-bd2f-585481ddacf0.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(456,13,9,0,'047790e2-8f11-4d4a-90b2-ef9ca607c3d9.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F047790e2-8f11-4d4a-90b2-ef9ca607c3d9.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(457,14,6,0,'062c2402-59b9-4e95-9318-0e6e5626268a.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F062c2402-59b9-4e95-9318-0e6e5626268a.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(458,14,6,0,'ff5b10d0-aea8-4c1a-af8a-3832da798249.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fff5b10d0-aea8-4c1a-af8a-3832da798249.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(459,14,6,0,'079df67c-1197-4370-b348-13be1e5d277e.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F079df67c-1197-4370-b348-13be1e5d277e.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(460,14,6,0,'5c3f6988-4bef-4f5a-a52b-e194494f0f0f.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F5c3f6988-4bef-4f5a-a52b-e194494f0f0f.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(461,14,6,0,'89916c12-74cf-49c2-a6bb-819dc74d65c8.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F89916c12-74cf-49c2-a6bb-819dc74d65c8.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(462,14,6,1,'e163a1a7-ec5e-4153-94f1-aa292ecffa92.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fe163a1a7-ec5e-4153-94f1-aa292ecffa92.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(463,14,7,0,'4b860f0d-509e-4b8d-96ca-6ebca3757d9a.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F4b860f0d-509e-4b8d-96ca-6ebca3757d9a.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(464,14,7,0,'ebc5e07e-58c4-47a6-9286-09c1916fb3cb.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Febc5e07e-58c4-47a6-9286-09c1916fb3cb.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(465,14,7,0,'2bc28eb5-fc8c-4293-9354-6692821b63f0.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F2bc28eb5-fc8c-4293-9354-6692821b63f0.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(466,14,7,0,'66b372dd-3065-467c-a8aa-bf224b9e1eb5.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F66b372dd-3065-467c-a8aa-bf224b9e1eb5.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(467,14,7,0,'b840a4da-cbcf-4f71-b423-583a7bc86b9e.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fb840a4da-cbcf-4f71-b423-583a7bc86b9e.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(468,14,7,1,'bbb53de0-88e5-4aca-b541-b809c85f3004.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fbbb53de0-88e5-4aca-b541-b809c85f3004.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(469,15,1,1,'b2237c22-98b1-4f87-9427-e051be1d10ee.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fb2237c22-98b1-4f87-9427-e051be1d10ee.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(470,15,1,0,'5073d80e-6bf5-40d3-93e1-14fa0ac1c314.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F5073d80e-6bf5-40d3-93e1-14fa0ac1c314.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(471,15,1,0,'c93611b1-5dc6-49be-960e-a15b17ee1295.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fc93611b1-5dc6-49be-960e-a15b17ee1295.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(472,15,1,0,'3a70b4ae-9c56-4b71-a276-2f672a6d6010.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F3a70b4ae-9c56-4b71-a276-2f672a6d6010.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(473,15,1,0,'e59ee941-22e2-4345-94c2-663798182169.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fe59ee941-22e2-4345-94c2-663798182169.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(474,15,1,0,'f1e3d7a3-818e-4e3b-9da9-537102253f4a.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Ff1e3d7a3-818e-4e3b-9da9-537102253f4a.jpg?alt=media','2022-04-16 17:00:00','2022-04-16 17:00:00'),(475,20,4,0,'ac90469e-9387-4c34-813e-ebcf12a04822.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fac90469e-9387-4c34-813e-ebcf12a04822.jpg?alt=media','2022-05-12 14:34:44','2022-05-12 14:34:44'),(476,20,4,0,'19420125-c7e2-4072-b686-506b21a3b0e2.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F19420125-c7e2-4072-b686-506b21a3b0e2.jpg?alt=media','2022-05-12 14:34:45','2022-05-12 14:34:45'),(477,20,4,0,'10183334-de08-48e4-9b74-c27600521964.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F10183334-de08-48e4-9b74-c27600521964.jpg?alt=media','2022-05-12 14:34:45','2022-05-12 14:34:45'),(478,20,4,0,'a09a4184-9467-4ac5-b364-118c1fb4bb3c.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fa09a4184-9467-4ac5-b364-118c1fb4bb3c.jpg?alt=media','2022-05-12 14:34:45','2022-05-12 14:34:45'),(479,20,4,0,'b228c834-4b7c-44ca-9d2e-702be1f453b9.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fb228c834-4b7c-44ca-9d2e-702be1f453b9.jpg?alt=media','2022-05-12 14:34:46','2022-05-12 14:34:46'),(480,20,4,1,'23eeecf0-195a-434f-bd6c-61bf2a3f3124.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F23eeecf0-195a-434f-bd6c-61bf2a3f3124.jpg?alt=media','2022-05-12 14:34:46','2022-05-12 14:34:46'),(481,20,1,0,'9e21d28b-c12a-40c4-9549-6077121a2c2b.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F9e21d28b-c12a-40c4-9549-6077121a2c2b.jpg?alt=media','2022-05-15 09:34:29','2022-05-15 09:34:29'),(482,20,1,0,'96a9d1cb-0d24-4a1e-9191-743370401b68.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F96a9d1cb-0d24-4a1e-9191-743370401b68.jpg?alt=media','2022-05-15 09:34:29','2022-05-15 09:34:29'),(483,20,1,0,'51b7af85-436a-4113-abe6-f8bb89dfc752.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F51b7af85-436a-4113-abe6-f8bb89dfc752.jpg?alt=media','2022-05-15 09:34:29','2022-05-15 09:34:29'),(484,20,1,0,'c6e5da57-b43b-4ad5-bb7e-5933051db37e.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2Fc6e5da57-b43b-4ad5-bb7e-5933051db37e.jpg?alt=media','2022-05-15 09:34:30','2022-05-15 09:34:30'),(485,20,1,0,'51fc8d43-0ead-4294-b555-19c2bde45153.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F51fc8d43-0ead-4294-b555-19c2bde45153.jpg?alt=media','2022-05-15 09:34:30','2022-05-15 09:34:30'),(486,20,1,1,'71a86b0d-eee9-4ff7-ae0a-841acb429830.jpg','https://firebasestorage.googleapis.com/v0/b/spring-boot-shoe.appspot.com/o/shoe%2F71a86b0d-eee9-4ff7-ae0a-841acb429830.jpg?alt=media','2022-05-15 09:34:30','2022-05-15 09:34:30');
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_bill`
--

DROP TABLE IF EXISTS `product_bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `product_bill` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_detail_id` bigint DEFAULT NULL,
  `bill_id` bigint DEFAULT NULL,
  `price` decimal(18,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `modified_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_bill_prod_idx` (`bill_id`),
  KEY `fk_prod_bill_idx` (`product_detail_id`),
  CONSTRAINT `fk_bill_prod` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`id`),
  CONSTRAINT `fk_prod_bill` FOREIGN KEY (`product_detail_id`) REFERENCES `product_detail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_bill`
--

LOCK TABLES `product_bill` WRITE;
/*!40000 ALTER TABLE `product_bill` DISABLE KEYS */;
INSERT INTO `product_bill` VALUES (11,21,1,3500000.00,1,'2021-12-01 17:00:00','2021-12-01 17:00:00'),(16,17,1,3500000.00,1,'2021-12-01 17:00:00','2021-12-01 17:00:00'),(28,17,17,3500000.00,9,'2021-12-09 17:00:00','2022-01-05 17:00:00'),(31,19,13,3500000.00,4,'2021-12-10 17:00:00','2021-12-10 17:00:00'),(45,18,18,3500000.00,1,'2022-01-05 17:00:00','2022-01-05 17:00:00'),(46,18,16,3500000.00,1,'2022-01-05 17:00:00','2022-01-05 17:00:00'),(47,18,19,3500000.00,7,'2022-01-05 17:00:00','2022-01-05 17:00:00'),(49,2,20,2500000.00,1,'2022-01-05 17:00:00','2022-01-05 17:00:00'),(50,15,20,2500000.00,1,'2022-01-05 17:00:00','2022-01-05 17:00:00'),(51,32,20,1700000.00,1,'2022-01-05 17:00:00','2022-01-05 17:00:00'),(52,21,16,3500000.00,1,'2022-01-05 17:00:00','2022-01-05 17:00:00'),(54,20,22,3500000.00,1,'2022-03-05 17:00:00','2022-03-05 17:00:00'),(55,20,23,3500000.00,1,'2022-03-05 17:00:00','2022-03-05 17:00:00'),(56,20,24,3500000.00,1,'2022-03-12 17:00:00','2022-03-12 17:00:00'),(62,47,28,1900000.00,1,'2022-04-10 17:00:00','2022-04-10 17:00:00'),(64,53,30,1900000.00,1,'2022-04-10 17:00:00','2022-04-10 17:00:00'),(65,71,31,4699000.00,1,'2022-04-10 17:00:00','2022-04-10 17:00:00'),(66,61,32,2649000.00,2,'2022-04-10 17:00:00','2022-04-10 17:00:00'),(67,71,32,4699000.00,2,'2022-04-10 17:00:00','2022-04-10 17:00:00'),(68,47,32,1900000.00,2,'2022-04-10 17:00:00','2022-04-10 17:00:00'),(69,38,33,1450000.00,2,'2022-04-10 17:00:00','2022-04-10 17:00:00'),(70,74,33,3239000.00,2,'2022-04-10 17:00:00','2022-04-10 17:00:00'),(71,54,33,1900000.00,2,'2022-04-10 17:00:00','2022-04-10 17:00:00'),(72,55,34,1900000.00,2,'2022-04-10 17:00:00','2022-04-10 17:00:00'),(73,65,34,2649000.00,2,'2022-04-10 17:00:00','2022-04-10 17:00:00'),(75,38,26,1450000.00,1,'2022-04-11 17:00:00','2022-04-11 17:00:00'),(79,67,26,4999000.00,1,'2022-05-07 08:59:31','2022-05-07 08:59:31'),(147,57,53,1160000.00,1,'2022-05-17 14:56:53','2022-05-17 14:56:53'),(153,7,52,2000000.00,1,'2022-05-26 14:04:29','2022-05-26 14:04:57'),(156,47,52,1900000.00,1,'2022-05-26 14:05:12','2022-05-26 14:05:12'),(157,11,53,NULL,2,'2022-05-27 16:13:24','2022-05-27 16:13:24'),(159,18,56,3500000.00,1,'2022-05-28 03:45:31','2022-05-28 03:45:31'),(160,7,57,2000000.00,3,'2022-05-29 01:34:42','2022-05-29 01:57:08');
/*!40000 ALTER TABLE `product_bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_detail`
--

DROP TABLE IF EXISTS `product_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `product_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `color_id` bigint DEFAULT NULL,
  `size_id` bigint DEFAULT NULL,
  `discount_id` bigint DEFAULT NULL,
  `status` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `modified_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_prod_color_idx` (`color_id`),
  KEY `fk_prod_size_idx` (`size_id`),
  KEY `fk_prod_feature_idx` (`product_id`),
  CONSTRAINT `fk_prod_color` FOREIGN KEY (`color_id`) REFERENCES `color` (`id`),
  CONSTRAINT `fk_prod_feature` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `fk_prod_size` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=196 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_detail`
--

LOCK TABLES `product_detail` WRITE;
/*!40000 ALTER TABLE `product_detail` DISABLE KEYS */;
INSERT INTO `product_detail` VALUES (1,1,6,5,18,1,NULL,'2022-05-22 09:30:16'),(2,1,6,4,18,1,NULL,'2022-05-22 09:30:16'),(3,1,6,6,18,1,NULL,'2022-05-22 09:30:16'),(4,1,6,7,18,1,NULL,'2022-05-22 09:30:16'),(5,1,6,8,18,1,NULL,'2022-05-22 09:30:16'),(6,1,7,4,19,1,NULL,'2022-05-22 09:30:17'),(7,1,7,5,19,1,NULL,'2022-05-22 09:30:17'),(8,1,7,6,19,1,NULL,'2022-05-22 09:30:17'),(9,1,7,7,19,1,NULL,'2022-05-22 09:30:17'),(10,1,7,8,19,1,NULL,'2022-05-22 09:30:17'),(11,2,1,5,0,1,NULL,'2022-05-22 07:36:43'),(12,2,1,6,0,1,NULL,'2022-05-15 10:20:00'),(13,2,1,7,0,1,NULL,'2022-05-15 10:20:00'),(14,2,2,5,0,1,NULL,'2022-05-22 07:36:43'),(15,2,2,6,0,1,NULL,'2022-05-22 07:36:43'),(16,2,2,8,0,1,NULL,'2022-05-22 07:36:43'),(17,3,1,5,0,-1,NULL,'2022-05-04 19:21:31'),(18,3,1,6,0,1,NULL,'2022-05-15 04:28:32'),(19,3,1,7,0,1,NULL,'2022-05-15 04:28:32'),(20,3,2,5,0,-1,NULL,'2022-05-04 19:21:31'),(21,3,2,6,0,1,NULL,'2022-05-15 04:28:32'),(22,3,2,7,0,1,NULL,'2022-05-15 04:28:32'),(23,3,9,5,0,1,NULL,'2022-05-15 04:28:32'),(24,3,9,6,0,1,NULL,'2022-05-15 04:28:32'),(25,3,9,7,0,1,NULL,'2022-05-15 04:28:32'),(26,4,8,5,0,1,NULL,'2022-05-15 04:28:32'),(27,4,8,6,0,1,NULL,'2022-05-15 04:28:32'),(28,4,8,7,0,1,NULL,'2022-05-15 04:28:32'),(29,4,2,5,0,1,NULL,'2022-05-15 04:28:32'),(30,4,2,6,0,1,NULL,'2022-05-15 04:28:32'),(31,4,2,7,0,1,NULL,'2022-05-15 04:28:32'),(32,5,1,5,0,1,NULL,'2022-05-15 04:28:32'),(33,5,1,6,0,1,NULL,'2022-05-15 04:28:32'),(34,5,1,7,0,1,NULL,'2022-05-15 04:28:32'),(35,5,2,5,0,1,NULL,'2022-05-15 04:28:32'),(36,5,2,6,0,1,NULL,'2022-05-15 04:28:32'),(37,5,2,7,0,1,NULL,'2022-05-15 04:28:32'),(38,6,1,5,21,1,NULL,'2022-05-22 09:35:46'),(39,6,1,6,21,1,NULL,'2022-05-22 09:35:46'),(40,6,1,7,21,1,NULL,'2022-05-22 09:35:46'),(41,6,2,5,21,1,NULL,'2022-05-22 09:35:46'),(42,6,2,6,21,1,NULL,'2022-05-22 09:35:46'),(43,6,2,7,21,1,NULL,'2022-05-22 09:35:46'),(44,6,6,5,0,1,NULL,'2022-05-04 19:21:31'),(45,6,6,6,0,1,NULL,'2022-05-04 19:21:31'),(46,6,6,7,0,1,NULL,'2022-05-04 19:21:32'),(47,7,1,5,0,1,NULL,'2022-05-04 19:21:32'),(48,7,1,6,0,1,NULL,'2022-05-04 19:21:32'),(49,7,3,5,0,1,NULL,'2022-05-04 19:21:32'),(50,7,3,6,0,1,NULL,'2022-05-04 19:21:32'),(51,7,7,5,0,1,NULL,'2022-05-04 19:21:32'),(52,7,7,7,0,1,NULL,'2022-05-04 19:21:32'),(53,8,3,5,0,1,NULL,'2022-05-04 19:21:32'),(54,8,3,6,0,1,NULL,'2022-05-04 19:21:32'),(55,8,3,7,0,1,NULL,'2022-05-04 19:21:32'),(56,8,3,8,0,1,'2022-01-07 17:00:00','2022-05-04 19:21:32'),(57,9,1,5,21,1,'2022-01-07 17:00:00','2022-05-22 09:35:46'),(58,9,1,6,21,1,'2022-01-07 17:00:00','2022-05-22 09:35:46'),(59,9,1,7,21,1,NULL,'2022-05-22 09:35:46'),(60,9,1,8,21,1,NULL,'2022-05-22 09:35:46'),(61,10,1,5,20,1,NULL,'2022-05-22 09:35:46'),(62,10,1,6,20,1,'2022-02-03 17:00:00','2022-05-22 09:35:46'),(63,10,1,7,20,1,'2022-02-03 17:00:00','2022-05-22 09:35:46'),(64,10,2,5,20,1,'2022-02-03 17:00:00','2022-05-22 09:35:46'),(65,10,2,6,20,1,NULL,'2022-05-22 09:35:46'),(66,10,2,7,20,1,NULL,'2022-05-22 09:35:46'),(67,11,1,5,0,1,NULL,'2022-05-15 04:28:32'),(68,11,1,6,0,1,NULL,'2022-05-15 04:28:32'),(69,11,1,7,0,1,'2022-02-07 17:00:00','2022-05-15 04:28:32'),(70,11,1,8,0,1,'2022-02-07 17:00:00','2022-05-15 04:28:32'),(71,12,4,5,0,0,'2022-02-07 17:00:00','2022-05-04 19:21:32'),(72,12,4,6,0,1,'2022-02-07 17:00:00','2022-05-04 19:21:32'),(73,12,4,7,0,1,'2022-02-07 17:00:00','2022-05-04 19:21:32'),(74,13,10,6,0,1,'2022-02-07 17:00:00','2022-05-04 19:21:32'),(75,13,10,7,0,1,'2022-02-07 17:00:00','2022-05-04 19:21:32'),(76,13,1,5,0,1,'2022-02-07 17:00:00','2022-05-04 19:21:32'),(77,13,1,6,0,1,'2022-02-07 17:00:00','2022-05-04 19:21:32'),(78,13,1,7,0,1,'2022-02-07 17:00:00','2022-05-04 19:21:32'),(79,13,1,8,0,1,'2022-02-07 17:00:00','2022-05-04 19:21:32'),(80,13,9,6,0,1,'2022-02-07 17:00:00','2022-05-04 19:21:32'),(87,4,1,1,0,1,'2022-02-07 17:00:00','2022-05-15 04:28:32'),(88,4,1,3,0,1,'2022-02-07 17:00:00','2022-05-15 04:28:32'),(89,4,1,4,0,1,'2022-02-07 17:00:00','2022-05-15 04:28:32'),(90,4,1,5,0,1,'2022-02-07 17:00:00','2022-05-15 04:28:32'),(93,4,6,1,0,1,'2022-02-07 17:00:00','2022-05-15 04:28:32'),(94,4,6,3,0,1,'2022-02-07 17:00:00','2022-05-15 04:28:32'),(95,4,6,4,0,1,'2022-02-07 17:00:00','2022-05-15 04:28:32'),(152,11,2,2,0,1,'2022-03-18 17:00:00','2022-05-15 04:28:32'),(153,11,2,3,0,1,'2022-03-18 17:00:00','2022-05-15 04:28:32'),(154,11,2,4,0,1,'2022-03-18 17:00:00','2022-05-15 04:28:32'),(155,11,2,5,0,1,'2022-03-18 17:00:00','2022-05-15 04:28:32'),(156,11,2,6,0,1,'2022-03-18 17:00:00','2022-05-15 04:28:32'),(157,15,1,3,0,1,'2022-03-18 17:00:00','2022-05-15 04:28:32'),(158,15,1,4,0,1,'2022-03-18 17:00:00','2022-05-15 04:28:32'),(159,15,1,5,0,1,'2022-03-18 17:00:00','2022-05-15 04:28:32'),(160,15,1,6,0,1,'2022-03-18 17:00:00','2022-05-15 04:28:32'),(161,15,1,7,0,1,'2022-03-18 17:00:00','2022-05-15 04:28:32'),(177,14,6,3,0,1,'2022-04-16 17:00:00','2022-05-15 04:28:32'),(178,14,6,4,0,1,'2022-04-16 17:00:00','2022-05-15 04:28:32'),(179,14,6,5,0,1,'2022-04-16 17:00:00','2022-05-15 04:28:32'),(180,14,6,6,0,1,'2022-04-16 17:00:00','2022-05-15 04:28:32'),(181,14,6,7,0,1,'2022-04-16 17:00:00','2022-05-15 04:28:32'),(182,14,6,8,0,1,'2022-04-16 17:00:00','2022-05-15 04:28:32'),(183,14,7,4,0,1,'2022-04-16 17:00:00','2022-05-15 04:28:32'),(184,14,7,5,0,1,'2022-04-16 17:00:00','2022-05-15 04:28:32'),(185,14,7,6,0,1,'2022-04-16 17:00:00','2022-05-15 04:28:32'),(186,14,7,7,0,1,'2022-04-16 17:00:00','2022-05-15 04:28:32'),(187,14,7,8,0,1,'2022-04-16 17:00:00','2022-05-15 04:28:32'),(188,20,4,3,0,1,'2022-05-12 14:34:44','2022-05-12 14:34:44'),(189,20,4,4,0,1,'2022-05-12 14:34:44','2022-05-12 14:34:44'),(190,20,4,5,0,1,'2022-05-12 14:34:44','2022-05-12 14:34:44'),(191,20,4,6,0,1,'2022-05-12 14:34:44','2022-05-12 14:34:44'),(192,20,4,7,0,1,'2022-05-12 14:34:44','2022-05-12 14:34:44'),(193,20,1,5,0,1,'2022-05-15 09:34:28','2022-05-15 09:34:28'),(194,20,1,7,0,1,'2022-05-15 09:34:28','2022-05-15 09:34:28'),(195,20,1,8,0,1,'2022-05-15 09:34:28','2022-05-15 09:34:28');
/*!40000 ALTER TABLE `product_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_receipt`
--

DROP TABLE IF EXISTS `product_receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `product_receipt` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `receipt_id` bigint DEFAULT NULL,
  `product_detail_id` bigint DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `modified_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_receipt_prod_idx` (`receipt_id`),
  KEY `fk_prod_receipt_idx` (`product_detail_id`),
  CONSTRAINT `fk_prod_receipt` FOREIGN KEY (`product_detail_id`) REFERENCES `product_detail` (`id`),
  CONSTRAINT `fk_receipt_prod` FOREIGN KEY (`receipt_id`) REFERENCES `receipt` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_receipt`
--

LOCK TABLES `product_receipt` WRITE;
/*!40000 ALTER TABLE `product_receipt` DISABLE KEYS */;
INSERT INTO `product_receipt` VALUES (1,1,1,10,NULL,NULL),(2,1,2,10,NULL,NULL),(3,1,3,10,NULL,NULL),(4,1,4,10,NULL,NULL),(5,1,5,10,NULL,NULL),(6,1,6,10,NULL,NULL),(7,1,7,10,NULL,NULL),(8,1,8,10,NULL,NULL),(9,1,9,10,NULL,NULL),(10,1,10,10,NULL,NULL),(11,1,11,10,NULL,NULL),(12,1,12,10,NULL,NULL),(13,1,13,10,NULL,NULL),(14,1,14,10,NULL,NULL),(15,1,15,10,NULL,NULL),(16,1,16,10,NULL,NULL),(17,1,17,10,NULL,NULL),(18,1,18,10,NULL,NULL),(19,1,19,10,NULL,NULL),(20,1,20,10,NULL,NULL),(21,1,21,10,NULL,NULL),(22,1,22,10,NULL,NULL),(23,1,23,10,NULL,NULL),(24,1,24,10,NULL,NULL),(25,1,25,10,NULL,NULL),(26,1,26,10,NULL,NULL),(27,1,27,10,NULL,NULL),(28,1,28,10,NULL,NULL),(29,1,29,10,NULL,NULL),(30,1,30,10,NULL,NULL),(31,1,31,10,NULL,NULL),(32,1,32,10,NULL,NULL),(33,1,33,10,NULL,NULL),(34,1,34,10,NULL,NULL),(35,1,35,10,NULL,NULL),(36,1,36,10,NULL,NULL),(37,1,37,10,NULL,NULL),(38,1,38,10,NULL,NULL),(39,1,39,10,NULL,NULL),(40,1,40,10,NULL,NULL),(41,1,41,10,NULL,NULL),(42,1,42,10,NULL,NULL),(43,1,43,10,NULL,NULL),(44,1,44,10,NULL,NULL),(45,1,45,10,NULL,NULL),(46,1,46,10,NULL,NULL),(47,1,47,10,NULL,NULL),(48,1,48,10,NULL,NULL),(49,1,49,10,NULL,NULL),(50,1,50,10,NULL,NULL),(51,1,51,10,NULL,NULL),(52,1,52,10,NULL,NULL),(53,1,53,10,NULL,NULL),(54,1,54,10,NULL,NULL),(55,1,55,10,NULL,NULL),(56,1,56,10,NULL,NULL),(57,1,57,10,NULL,NULL),(58,1,58,10,NULL,NULL),(59,1,59,10,NULL,NULL),(60,1,60,10,NULL,NULL),(61,1,61,10,NULL,NULL),(62,1,62,10,NULL,NULL),(63,1,63,10,NULL,NULL),(64,1,64,10,NULL,NULL),(65,1,65,10,NULL,NULL),(66,1,66,10,NULL,NULL),(67,1,67,10,NULL,NULL),(68,1,68,10,NULL,NULL),(69,1,69,10,NULL,NULL),(70,1,70,10,NULL,NULL),(71,1,71,10,NULL,NULL),(72,1,72,10,NULL,NULL),(73,1,73,10,NULL,NULL),(74,1,74,10,NULL,NULL),(75,1,75,10,NULL,NULL),(76,1,76,10,NULL,NULL),(77,1,77,10,NULL,NULL),(78,1,78,10,NULL,NULL),(79,1,79,10,NULL,NULL),(80,1,80,10,NULL,NULL),(107,4,39,10,'2022-05-23 16:11:54','2022-05-23 16:11:54'),(108,4,40,10,'2022-05-23 16:11:54','2022-05-23 16:11:54'),(109,4,38,10,'2022-05-23 16:11:54','2022-05-23 16:11:54'),(110,4,41,10,'2022-05-23 16:11:54','2022-05-23 16:11:54'),(111,4,42,10,'2022-05-23 16:11:54','2022-05-23 16:11:54'),(112,4,43,10,'2022-05-23 16:11:54','2022-05-23 16:11:54'),(113,4,44,15,'2022-05-23 16:11:54','2022-05-23 16:11:54'),(114,4,45,15,'2022-05-23 16:11:54','2022-05-23 16:11:54'),(115,4,46,15,'2022-05-23 16:11:54','2022-05-23 16:11:54'),(116,8,1,10,'2022-05-23 16:14:47','2022-05-23 16:14:47'),(117,8,2,10,'2022-05-23 16:14:47','2022-05-23 16:14:47'),(118,8,3,10,'2022-05-23 16:14:47','2022-05-23 16:14:47'),(119,8,4,10,'2022-05-23 16:14:47','2022-05-23 16:14:47'),(120,8,5,10,'2022-05-23 16:14:47','2022-05-23 16:14:47'),(121,8,11,10,'2022-05-23 16:14:47','2022-05-23 16:14:47'),(122,8,12,10,'2022-05-23 16:14:47','2022-05-23 16:14:47'),(123,8,13,10,'2022-05-23 16:14:47','2022-05-23 16:14:47'),(124,8,14,10,'2022-05-23 16:14:47','2022-05-23 16:14:47'),(125,8,15,10,'2022-05-23 16:14:47','2022-05-23 16:14:47'),(126,8,16,10,'2022-05-23 16:14:47','2022-05-23 16:14:47'),(127,7,38,10,'2022-05-23 16:15:11','2022-05-23 16:15:11'),(128,7,39,10,'2022-05-23 16:15:11','2022-05-23 16:15:11'),(129,7,57,15,'2022-05-23 16:15:11','2022-05-23 16:15:11'),(130,7,58,15,'2022-05-23 16:15:11','2022-05-23 16:15:11'),(131,7,59,15,'2022-05-23 16:15:11','2022-05-23 16:15:11'),(132,7,60,15,'2022-05-23 16:15:11','2022-05-23 16:15:11'),(133,9,53,10,'2022-05-23 16:15:42','2022-05-23 16:15:42'),(134,9,54,10,'2022-05-23 16:15:42','2022-05-23 16:15:42'),(135,9,55,10,'2022-05-23 16:15:42','2022-05-23 16:15:42'),(136,9,56,10,'2022-05-23 16:15:42','2022-05-23 16:15:42'),(137,9,49,10,'2022-05-23 16:15:42','2022-05-23 16:15:42'),(138,9,50,10,'2022-05-23 16:15:42','2022-05-23 16:15:42'),(139,9,51,10,'2022-05-23 16:15:42','2022-05-23 16:15:42'),(140,9,52,10,'2022-05-23 16:15:42','2022-05-23 16:15:42'),(141,5,61,10,'2022-05-23 16:16:23','2022-05-23 16:16:23'),(142,5,62,10,'2022-05-23 16:16:23','2022-05-23 16:16:23'),(143,5,64,10,'2022-05-23 16:16:23','2022-05-23 16:16:23'),(144,5,65,10,'2022-05-23 16:16:23','2022-05-23 16:16:23'),(145,5,74,10,'2022-05-23 16:16:23','2022-05-23 16:16:23'),(146,5,75,10,'2022-05-23 16:16:23','2022-05-23 16:16:23'),(147,5,76,10,'2022-05-23 16:16:23','2022-05-23 16:16:23'),(148,5,77,10,'2022-05-23 16:16:23','2022-05-23 16:16:23'),(149,5,78,10,'2022-05-23 16:16:23','2022-05-23 16:16:23'),(150,5,79,10,'2022-05-23 16:16:23','2022-05-23 16:16:23'),(151,5,80,10,'2022-05-23 16:16:23','2022-05-23 16:16:23'),(152,6,188,10,'2022-05-23 16:17:17','2022-05-23 16:17:17'),(153,6,189,10,'2022-05-23 16:17:17','2022-05-23 16:17:17'),(154,6,190,10,'2022-05-23 16:17:17','2022-05-23 16:17:17'),(155,6,191,10,'2022-05-23 16:17:17','2022-05-23 16:17:17'),(156,6,192,10,'2022-05-23 16:17:17','2022-05-23 16:17:17'),(157,6,177,10,'2022-05-23 16:17:17','2022-05-23 16:17:17'),(158,6,178,10,'2022-05-23 16:17:17','2022-05-23 16:17:17'),(159,6,179,10,'2022-05-23 16:17:17','2022-05-23 16:17:17'),(160,6,180,10,'2022-05-23 16:17:17','2022-05-23 16:17:17'),(161,6,181,10,'2022-05-23 16:17:17','2022-05-23 16:17:17'),(162,6,182,10,'2022-05-23 16:17:17','2022-05-23 16:17:17'),(163,6,183,10,'2022-05-23 16:17:17','2022-05-23 16:17:17'),(164,6,184,10,'2022-05-23 16:17:17','2022-05-23 16:17:17'),(165,6,185,10,'2022-05-23 16:17:17','2022-05-23 16:17:17'),(166,6,186,10,'2022-05-23 16:17:17','2022-05-23 16:17:17'),(167,6,187,10,'2022-05-23 16:17:17','2022-05-23 16:17:17');
/*!40000 ALTER TABLE `product_receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_general_ci DEFAULT NULL,
  `slug` varchar(45) COLLATE utf8_general_ci DEFAULT NULL,
  `price` decimal(18,2) DEFAULT NULL,
  `brand_id` bigint DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_general_ci DEFAULT NULL,
  `content` text COLLATE utf8_general_ci,
  `status` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `modified_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_prod_brand_idx` (`brand_id`),
  CONSTRAINT `fk_prod_brand` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Stan Smith','stan-smith',2500000.00,1,'STAN SMITH, MÃI MÃI ĐẬM CHẤT BIỂU TƯỢNG GIỜ ĐÂY CÀNG BỀN VỮNG HƠN','Vẻ đẹp kinh điển. Phong cách vốn dĩ. Đa năng hàng ngày. Suốt hơn 50 năm qua và chưa dừng ở đó, giày adidas Stan Smith luôn giữ vững vị trí là một biểu tượng. Đôi giày này là phiên bản cải biên mới mẻ, là một phần cam kết của adidas hướng tới chỉ sử dụng polyester tái chế bắt đầu từ năm 2024. Với thân giày vegan và đế ngoài làm từ cao su phế liệu, đôi giày này vẫn mang phong cách đầy tính biểu tượng, đồng thời thân thiện với môi trường.\r\n\r\nGiày sử dụng chất liệu vegan thay cho thành phần hoặc chất liệu có nguồn gốc từ động vật. Sản phẩm này may bằng vải công nghệ Primegreen, thuộc dòng chất liệu tái chế hiệu năng cao. Thân giày chứa 50% thành phần tái chế. Không sử dụng polyester nguyên sinh',1,'2022-05-05 19:08:52','2022-05-04 19:08:52'),(2,'Superstar','superstar',2500000.00,1,'TƯNG BỪNG ĐÓN TẾT CÙNG ĐÔI GIÀY ĐẦY TÍNH BIỂU TƯỢNG NÀY','Năm mới. Cơ hội mới. Đón mừng năm mới đầy thăng hoa với đôi giày adidas Superstar này. Tái hiện mẫu giày trainer bóng rổ quen thuộc của thập niên 70 trong phiên bản mới dành cho năm con Hổ, đôi giày này có thân giày bằng da trơn láng với điểm nhấn là họa tiết rực rỡ mang cảm hứng Tết Nguyên Đán. Ngay cả miếng lót giày cũng ngập tràn không khí Tết, cùng bạn lan tỏa vận may trên từng sải bước.',1,'2022-04-04 19:08:52','2022-05-04 19:08:52'),(3,'Pureboost 21','pureboost-21',3500000.00,1,'ĐÔI GIÀY CHẠY BỘ ĐÀN HỒI CHO BẠN CHUYỂN ĐỘNG TỰ NHIÊN','Kế hoạch tập luyện chỉ có hiệu quả khi bạn nỗ lực thực hiện. Đôi giày chạy bộ adidas này ủng hộ quyết tâm của bạn với công nghệ Boost hoàn trả năng lượng tuyệt vời. Thân giày làm từ chất liệu tái chế, mang đến cho bạn buổi chạy thật tuyệt vời về nhiều mặt.\r\n\r\nSản phẩm này may bằng vải công nghệ Primeblue, chất liệu tái chế hiệu năng cao có sử dụng sợi Parley Ocean Plastic. 50% thân giày làm bằng vải dệt, 75% vải dệt bằng sợi Primeblue. Không sử dụng polyester nguyên sinh',1,'2022-04-09 19:08:52','2022-05-04 19:08:52'),(4,'Alphabounce','alphabounce',3000000.00,1,'ĐÔI GIÀY CHẠY BỘ ĐA NĂNG GIÚP BẠN BỨT TỐC THEO MỌI HƯỚNG','Lợi thế cạnh tranh mà bạn đang tìm kiếm? Đương nhiên là đến từ thói quen tập luyện của bạn rồi. Đôi giày chạy bộ adidas này giúp bạn luôn thoải mái khi tập gym cũng như chạy bộ cự ly ngắn đến trung bình. Với thiết kế nâng đỡ mà linh hoạt, đôi giày này mang lại cảm giác ổn định trong các bài tập cần chuyển động sang hai bên như trượt băng. Lớp đệm Bounce đàn hồi dưới lòng bàn chân',1,'2022-05-07 19:08:52','2022-05-04 19:08:52'),(5,'Duramo SL','duramo-sl',1700000.00,1,'ĐÔI GIÀY CHẠY BỘ THOẢI MÁI CHO SẢI BƯỚC NHẸ NHÀNG','Quyết tâm tăng tốc sau mỗi buổi chạy. Thân giày bằng vải lưới và gót giày bằng cao su neoprene mềm mại đem đến cho đôi giày chạy bộ này vẻ ngoài đậm chất kỹ thuật và độ ôm thoải mái, nâng đỡ. Khi bạn đã sẵn sàng bứt tốc, đế giữa Lightmotion siêu nhẹ cũng sẵn sàng đáp ứng',1,'2022-03-04 19:08:52','2022-05-04 19:08:52'),(6,'Chuck Taylor Classic Low','chuck-taylor-classic-low',1450000.00,2,'dòng giày truyền thống của Converse',' Converse Classic mang đến cho bạn sự trẻ trung và đầy cá tính. Với tông màu đen - trắng quen thuộc cùng logo Chuck Taylor All Star nằm bên hông thân giày mang đến sự nổi bật cho người mang. Đặc biệt là khả năng mix&match cực phong cách. Vì vậy đây là dòng luôn được restock liên tục tại cửa hàng Drake.',1,'2022-03-19 19:08:52','2022-01-26 17:00:00'),(7,'Chuck Taylor 1970s Low','chuck-taylor-1970s-low',1900000.00,2,'huyền thoại trở lại đầy ngoạn mục','Là 1 trong những dòng sản phẩm bán chạy nhất của Converse. Phần đế màu trắng ngà vintage được phủ 1 lớp bóng bên ngoài là điểm nhấn riêng cho dòng 1970s, dễ vệ sinh hơn',1,'2022-03-04 19:08:52','2022-01-26 17:00:00'),(8,'Chuck Taylor 1970s High','chuck-taylor-1970s-high',1900000.00,2,'huyền thoại trở lại đầy ngoạn mục','Là 1 trong những dòng sản phẩm bán chạy nhất của Converse. Phần đế màu trắng ngà vintage được phủ 1 lớp bóng bên ngoài là điểm nhấn riêng cho dòng 1970s, dễ vệ sinh hơn.',1,'2022-02-04 19:08:52','2022-01-26 17:00:00'),(9,'Chuck Taylor Classic High','chuck-taylor-classic-high',1450000.00,2,'dòng giày truyền thống của Converse',' Converse Classic mang đến cho bạn sự trẻ trung và đầy cá tính. Với tông màu đen - trắng quen thuộc cùng logo Chuck Taylor All Star nằm bên hông thân giày mang đến sự nổi bật cho người mang. Đặc biệt là khả năng mix&match cực phong cách. Vì vậy đây là dòng luôn được restock liên tục tại cửa hàng Drake.',1,'2022-05-01 19:08:52','2022-01-26 17:00:00'),(10,'Nike Air Force 1 \'07','nike-air-force-1-07',2649000.00,3,'PHONG CÁCH HUYỀN THOẠI ĐÃ TÁI TẠO','Sự rạng rỡ tồn tại trong Nike Air Force 1 \'07, quả bóng b-ball OG mang đến một sự thay đổi mới mẻ về những gì bạn biết rõ nhất: các lớp phủ được khâu đẹp, kết thúc sạch sẽ và lượng đèn flash hoàn hảo để khiến bạn tỏa sáng',1,'2022-01-04 19:08:52','2022-05-04 19:08:52'),(11,'Nike Air Max 97','nike-air-max-97',4999000.00,3,'đôi giày khác biệt nhất từ trước tới nay.','Được sản xuất bằng những nguyên liệu và công nghệ cao cấp nhất của Nike tạo cho sản phẩm sự thoải mái, thông thoáng và sang trọng, kiểu dáng thời trang lịch lãm và tinh tế dễ phối đồ\r\n\r\nAir Max là một trong những công nghệ giày thành công nhất hiện nay. Đối với các tín đồ mê phong cách thể thao thì những đôi giày Nike Air Max luôn mang đến một sức hút không thể chối cãi',1,'2022-04-04 19:08:52','2022-05-04 19:08:52'),(12,'Air Jordan 1 Retro High OG','air-jordan-1-retro-high-og',4699000.00,3,'Không khí bên trong, da sang trọng, cupsole cổ điển','Quen thuộc nhưng luôn mới mẻ, Air Jordan 1 mang tính biểu tượng được làm lại cho văn hóa sneakerhead ngày nay. Phiên bản Retro High OG này hoàn toàn đi kèm với chất liệu da cao cấp, đệm thoải mái và các chi tiết thiết kế cổ điển',1,'2022-04-19 19:08:52','2022-01-26 17:00:00'),(13,'Nike Air Max Dawn','nike-air-max-dawn',3239000.00,3,'RETRO DELIGHT','Bắt nguồn từ DNA của môn thể thao, Air Max Dawn được làm từ ít nhất 20% vật liệu tái chế tính theo trọng lượng. Da lộn tổng hợp và các vật liệu khác pha trộn cảm giác chạy cổ điển với các chi tiết mớ',1,'2022-01-09 19:08:52','2022-01-26 17:00:00'),(14,'ZX 5K BOOST','zx-5k-boost',4200000.00,1,'ĐÔI GIÀY TRAINER THANH THOÁT, ĐẬM CHẤT TƯƠNG LAI CHO CẢM GIÁC THOẢI MÁI MỖI NGÀY','Bạn đã sẵn sàng cho tương lai? Chắc chắn rồi, khi đã có đôi giày adidas ZX 5K Boost này đồng hành cùng bạn. Mang đến công nghệ hiện đại nhất cho dòng ZX không ngừng biến hóa, đôi giày này tiếp thêm năng lượng cho từng sải bước và giúp bạn tỏa sáng phong cách. Lớp đệm Boost đảm bảo yếu tố thoải mái. Các chi tiết trong mờ và phản quang giúp đôi chân bạn luôn mới mẻ.',1,'2022-01-26 17:00:00','2022-05-04 19:08:52'),(15,'RETROPY E5','retropy-e5',3300000.00,1,'ĐÔI GIÀY PHONG CÁCH CHẠY BỘ CHO CẢM GIÁC THOẢI MÁI XUYÊN SUỐT TRONG MỌI CHUYỂN ĐỘNG.','Cuộc sống chẳng có vẻ gì là sẽ chậm lại. Vậy tại sao bạn không chọn lấy một đôi giày có khả năng bắt kịp nhịp sống ấy? Chẳng hạn như đôi giày Retropy E5 này. Mô phỏng mẫu giày trainer adidas Racing 1 trứ danh, đôi giày này dựa trên nền tảng của sự thoải mái và tốc độ để tạo nên một diện mạo hiện đại sẵn sàng cho nhịp sống hối hả thường ngày. Lớp đệm Boost hoàn trả năng lượng giúp nâng tầm cảm giác thoải mái.',1,'2022-01-26 17:00:00','2022-05-04 19:08:52'),(20,'duongtuanhieu','duongtuanhieu',10000000.00,1,NULL,NULL,1,'2022-05-12 14:33:27','2022-05-12 14:33:27');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt`
--

DROP TABLE IF EXISTS `receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `receipt` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `brand_id` bigint DEFAULT NULL,
  `totalprice` decimal(18,2) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `modified_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_receipt_idx` (`user_id`),
  KEY `fk_brand_receipt_idx` (`brand_id`),
  CONSTRAINT `fk_brand_receipt` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `fk_user_receipt` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt`
--

LOCK TABLES `receipt` WRITE;
/*!40000 ALTER TABLE `receipt` DISABLE KEYS */;
INSERT INTO `receipt` VALUES (1,8,1,2102100000.00,'2022-03-26 17:00:00','2022-03-26 17:00:00'),(4,8,2,152250000.00,'2022-03-26 17:00:00','2022-05-23 16:11:54'),(5,8,3,332690000.00,'2022-03-26 17:00:00','2022-05-23 16:16:23'),(6,8,1,962000000.00,'2022-05-12 14:36:06','2022-05-23 16:17:17'),(7,8,2,116000000.00,'2022-05-12 14:38:05','2022-05-23 16:15:11'),(8,8,1,275000000.00,'2022-05-22 07:06:10','2022-05-23 16:14:47'),(9,8,2,152000000.00,'2022-05-22 17:56:08','2022-05-23 16:15:42');
/*!40000 ALTER TABLE `receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(10) COLLATE utf8_general_ci DEFAULT NULL,
  `name` varchar(45) COLLATE utf8_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `modified_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN','Quản trị',NULL,NULL),(2,'ROLE_USER','Khách hàng',NULL,NULL);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size`
--

DROP TABLE IF EXISTS `size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `size` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `modified_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size`
--

LOCK TABLES `size` WRITE;
/*!40000 ALTER TABLE `size` DISABLE KEYS */;
INSERT INTO `size` VALUES (1,34,NULL,'2022-03-28 17:00:00'),(2,36,NULL,NULL),(3,37,NULL,NULL),(4,38,NULL,NULL),(5,39,NULL,NULL),(6,40,NULL,NULL),(7,41,NULL,NULL),(8,42,NULL,NULL),(9,43,NULL,NULL),(10,44,NULL,NULL),(11,45,NULL,NULL),(12,46,'2022-03-29 17:00:00','2022-03-29 17:00:00'),(13,33,'2022-03-29 17:00:00','2022-03-29 17:00:00'),(14,47,'2022-03-31 17:00:00','2022-03-31 17:00:00');
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `fk_user_role_idx` (`user_id`),
  KEY `fk_role_user_idx` (`role_id`),
  CONSTRAINT `fk_role_user` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,2),(6,2),(7,2),(8,1),(9,2),(10,2),(11,2),(12,2),(14,2),(15,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(45) COLLATE utf8_general_ci NOT NULL,
  `firstname` varchar(20) COLLATE utf8_general_ci DEFAULT NULL,
  `lastname` varchar(20) COLLATE utf8_general_ci DEFAULT NULL,
  `fullname` varchar(45) COLLATE utf8_general_ci DEFAULT NULL,
  `slug` varchar(45) COLLATE utf8_general_ci DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `address` varchar(100) COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(15) COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_general_ci NOT NULL,
  `verification_token` varchar(255) COLLATE utf8_general_ci DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `provider` varchar(45) COLLATE utf8_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `modified_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'tuanhieu3420002@gmail.com','Hiếu','Dương Tuấn','Tuấn Hiếu','tuan-hieu',1,'Hà Nội-Huyện Phú Xuyên-Xã Chuyên Mỹ-Đồng Vinh','0953284856','$2a$10$qx/4Iu05Y8xzebM5SjgkaO525kkimIIqJTWO9YWXy2I1sPDUH8HGa',NULL,_binary '',1,NULL,'2021-11-25 17:00:00','2022-05-06 18:37:21'),(6,'tuanhieu342k1@gmail.com','Hiếu','Dương Tuấn','Dương Tuấn Hiếu','hieu-duong',1,'Hà Nội-Huyện Phú Xuyên-Xã Chuyên Mỹ-Đồng Vinh','0834633431','$2a$10$PUq9VnDwihqHe2D1delvoOitAcH.IxDUuXi4Paxbjw2thjpmT8SK.',NULL,_binary '',1,'google','2021-11-28 17:00:00','2022-05-28 04:37:06'),(7,'duongth34001@gmail.com','Tuan Hieu','Duong','Hieu Duong','hieu-duong',1,NULL,'0834633431','$2a$10$dPUnwDsHkvID8bCSP/TFHeGP47SlBbX3jUH5qLPq4QJljaHayeZJa',NULL,_binary '',1,'facebook','2021-11-29 17:00:00','2022-05-06 17:27:58'),(8,'admin@gmail.com','Hieu','Duong','Duong Hieu','duong-hieu',1,NULL,'0834633431','$2a$10$dPUnwDsHkvID8bCSP/TFHeGP47SlBbX3jUH5qLPq4QJljaHayeZJa',NULL,_binary '',1,NULL,NULL,'2022-05-29 02:09:25'),(9,'tuanhieu@gmail.com','Hiếu','Dương','Dương Hiếu',NULL,1,NULL,NULL,'$2a$10$hcsoQdexphuqdSgzLWZvd.5AWp0mPOgnt04mvQUdXXgPxtMESzQBS',NULL,_binary '',1,'local','2022-03-18 17:00:00','2022-05-06 17:27:58'),(10,'huyen@gmail.com','Huyền','Dương','Dương Huyền','duong-huyen',0,NULL,NULL,'$2a$10$eUrNoQmgY0EPdLZn1yQ46Ok/AZ5DpzcF0LZ6eSIxMOFjF1DPWGxNq',NULL,_binary '',1,'local','2022-03-19 17:00:00','2022-05-06 17:27:58'),(11,'tuanhieu3420001@gmail.com','Hiếu','Dương','Dương Hiếu','duong-hieu',1,NULL,NULL,'$2a$10$qSPka7Ij3evhOMDWrXcibOc4FDXp0LxE9fOUA9tWs0.IEzK1erTju',NULL,_binary '',1,'local','2022-05-06 17:17:17','2022-05-06 17:18:02'),(12,'tuanhieu3420003@gmail.com','Tuấn','Hiếu','Tuấn Hiếu','tuan-hieu',NULL,NULL,NULL,'$2a$10$JvBDkmo2SDGMQVi4I5dtKuTqQ4H255tKBqBLxj5k/wQX6kdSMW526',NULL,_binary '',1,'google','2022-05-11 15:46:29','2022-05-11 15:46:29'),(14,'tuanhieu3420004@gmail.com','Tuấn','Hiếu','Tuấn Hiếu','tuan-hieu',NULL,NULL,NULL,'$2a$10$/Ag6/A3RU4GgW5NKOstv4eiYor.9FaCq5n2c6erfvVqsfIyZFI23W',NULL,_binary '',1,'google','2022-05-17 14:56:48','2022-05-17 14:56:48'),(15,'tuanhieu3420005@gmail.com','Tuấn Hiếu','Dương','Dương Tuấn Hiếu','duong-tuan-hieu',1,NULL,NULL,'$2a$10$6/IpFgrE2qSqDazdVt8izO11I7itWIN34hFnELMSX9fD0/rbeNzCe','vzZQ7ayfuEMyix9KhIgzt52GRuJTq0fVn49dImnZikMYE26uVFxuPCQ9QTrZvvzJ',_binary '',1,'local','2022-05-28 06:34:41','2022-05-28 06:34:41');
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

-- Dump completed on 2022-05-29 10:30:26
