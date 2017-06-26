CREATE DATABASE  IF NOT EXISTS `PoliTweets` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `PoliTweets`;
-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: localhost    Database: PoliTweets
-- ------------------------------------------------------
-- Server version	5.7.18-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--
-- ORDER BY:  `id`

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','admin');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conglomerado`
--

DROP TABLE IF EXISTS `conglomerado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conglomerado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `cuentaTwitter` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`),
  UNIQUE KEY `cuentaTwitter` (`cuentaTwitter`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conglomerado`
--
-- ORDER BY:  `id`

LOCK TABLES `conglomerado` WRITE;
/*!40000 ALTER TABLE `conglomerado` DISABLE KEYS */;
INSERT INTO `conglomerado` VALUES (1,'Chile Vamos','Chile_Vamos_'),(2,'Nueva Mayoría','NuevaMayoriacl'),(3,'Yo Marco por el Cambio',NULL),(4,'Frente Amplio','elfrente_amplio'),(5,'Sentido Futuro','Sentidofuturo'),(6,'Alternativa Democrática',NULL),(7,'Independiente',NULL);
/*!40000 ALTER TABLE `conglomerado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conglomerado_keyword`
--

DROP TABLE IF EXISTS `conglomerado_keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conglomerado_keyword` (
  `idconglomerado` int(11) NOT NULL,
  `idkeyword` int(11) NOT NULL,
  PRIMARY KEY (`idconglomerado`,`idkeyword`),
  KEY `idconglomerado` (`idconglomerado`),
  KEY `idkeyword` (`idkeyword`),
  CONSTRAINT `conglomerado_keyword_ibfk_1` FOREIGN KEY (`idconglomerado`) REFERENCES `conglomerado` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `conglomerado_keyword_ibfk_2` FOREIGN KEY (`idkeyword`) REFERENCES `keyword` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conglomerado_keyword`
--
-- ORDER BY:  `idconglomerado`,`idkeyword`

LOCK TABLES `conglomerado_keyword` WRITE;
/*!40000 ALTER TABLE `conglomerado_keyword` DISABLE KEYS */;
/*!40000 ALTER TABLE `conglomerado_keyword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conglomerado_metrica`
--

DROP TABLE IF EXISTS `conglomerado_metrica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conglomerado_metrica` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idconglomerado` int(11) NOT NULL,
  `idmetrica` int(11) NOT NULL,
  `valor` decimal(10,0) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `lugar` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idconglomerado` (`idconglomerado`),
  KEY `idmetrica` (`idmetrica`),
  CONSTRAINT `conglomerado_metrica_ibfk_1` FOREIGN KEY (`idconglomerado`) REFERENCES `conglomerado` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `conglomerado_metrica_ibfk_2` FOREIGN KEY (`idmetrica`) REFERENCES `metrica` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conglomerado_metrica`
--
-- ORDER BY:  `id`

LOCK TABLES `conglomerado_metrica` WRITE;
/*!40000 ALTER TABLE `conglomerado_metrica` DISABLE KEYS */;
/*!40000 ALTER TABLE `conglomerado_metrica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `keyword`
--

DROP TABLE IF EXISTS `keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keyword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `value` (`value`)
) ENGINE=InnoDB AUTO_INCREMENT=1087 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keyword`
--
-- ORDER BY:  `id`

LOCK TABLES `keyword` WRITE;
/*!40000 ALTER TABLE `keyword` DISABLE KEYS */;
INSERT INTO `keyword` VALUES (1,'@SenadoresUDI'),(2,'@DiputadosUDI'),(3,'@SenadoresRN'),(4,'@DiputadosRN'),(5,'UDI'),(6,'RN'),(7,'@SenadoresDC'),(8,'@DiputadosDC'),(9,'DC'),(10,'PRI'),(11,'@ppddiputados'),(12,'PPD'),(13,'@SenadoresPS'),(14,'@bancadaPSchile'),(15,'PS'),(16,'@Diputados_PC'),(17,'PC'),(18,'PRSD'),(19,'PRO'),(20,'rev. democratica'),(21,'RD'),(22,'MIR'),(23,'Evopoli'),(24,'MASRegión'),(26,'IC'),(27,'@BancadaPCIC'),(28,'ME-O'),(29,'@min_interior'),(30,'@PrensaMINDEF'),(31,'@segegob'),(32,'@MintrabChile'),(33,'@mop_chile'),(34,'@MinDeSalud'),(35,'@MinMineriaCL'),(36,'@mtt_chile'),(37,'@MinisterioBBNN'),(38,'@MMAChile'),(39,'@MindepChile'),(40,'@consejocultura'),(41,'Arica'),(42,'Caleta Camarones'),(43,'Caleta Vítor'),(44,'Camarones'),(45,'Chitita'),(46,'Codpa'),(47,'Cuya'),(48,'Esquiña'),(49,'Guañacagua'),(50,'Las Maitas'),(51,'Molinos'),(52,'Poconchile'),(53,'San Miguel de Azapa'),(54,'Sora'),(55,'Villa Frontera'),(56,'Ancuta'),(57,'Belén'),(58,'Caquena'),(59,'Chapiquiña'),(60,'Chucuyo'),(61,'Cosapilla'),(62,'Guallatire'),(63,'Pachama'),(64,'Parinacota'),(65,'Putre'),(66,'Saxamar'),(67,'Socoroma'),(68,'Tignamar'),(69,'Visviri'),(70,'Alto Hospicio'),(71,'Iquique'),(72,'Punta Patache'),(73,'Pisagua‎'),(74,'Ancuaque'),(75,'Arabilla'),(76,'Caleta Buena'),(77,'Camiña'),(78,'Cancosa'),(79,'Cariquima'),(80,'Chapiquilta'),(81,'Chiapa'),(82,'Chijo'),(83,'Colchane'),(84,'Collacagua'),(85,'Enquelga'),(86,'Fuerte Baquedano'),(87,'Huara'),(88,'Huatacondo'),(89,'Huaviña'),(90,'Isluga'),(91,'La Huayca'),(92,'La Tirana'),(93,'Lirima'),(94,'Mamiña'),(95,'Mauque'),(96,'Mocha'),(97,'Moquella'),(98,'Pica'),(99,'Pintados'),(100,'Pozo Almonte'),(101,'San Antonio de Zapiga'),(102,'San Lorenzo de Tarapacá'),(103,'Sibaya'),(104,'Sotoca'),(105,'Usmagama'),(106,'Antofagasta'),(107,'Caleta Michilla'),(108,'Caracoles'),(109,'Cobija'),(110,'Hornitos'),(111,'Mejillones'),(112,'Paposo'),(113,'Pampa Unión'),(114,'Sierra Gorda (Chile)'),(115,'Taltal'),(116,'Calama‎'),(117,'Ayquina'),(118,'Caspana'),(119,'Catarpe'),(120,'Lasana'),(121,'Ollagüe'),(122,'San Francisco de Chiu Chiu'),(123,'San Pedro de Atacama'),(124,'Socaire'),(125,'Toconao'),(126,'María Elena'),(127,'Oficina salitrera Pedro de Valdivia'),(128,'Tocopilla'),(129,'Chañaral‎'),(130,'Diego de Almagro'),(131,'El Salado'),(132,'El Salvador'),(133,'Inca de Oro'),(134,'Mina San Pedro Nolasco'),(135,'Caldera'),(136,'Copiapó'),(137,'Bahía Inglesa'),(138,'Conurbación Copiapó'),(139,'Tierra Amarilla'),(140,'Loreto'),(141,'Los Loros'),(142,'Puerto Viejo'),(143,'Albaricoque'),(144,'Algarrobal'),(145,'Cachiyuyo'),(146,'Caleta Chañaral'),(147,'Canto de Agua'),(148,'Carrizal Bajo'),(149,'Cerro Blanco'),(150,'Chanchoquín Chico'),(151,'Chanchoquín Grande'),(152,'Chigüinto'),(153,'Chollay'),(154,'Conay'),(155,'Domeyko'),(156,'El Berraco'),(157,'El Churcal'),(158,'El Corral'),(159,'El Corral de Valeriano'),(160,'El Corral de San Félix'),(161,'El Maitén'),(162,'El Pedregal'),(163,'El Rosario'),(164,'El Sombrío'),(165,'El Tránsito'),(166,'Freirina'),(167,'Huasco Bajo'),(168,'Incahuasi'),(169,'Juntas de Valeriano'),(170,'Junta de Valeriano'),(171,'La Angostura'),(172,'La Arena'),(173,'La Fragua'),(174,'La Higuerita'),(175,'La Huerta'),(176,'La Junta'),(177,'La Majada'),(178,'La Marquesa'),(179,'La Mesilla'),(180,'La Pampa'),(181,'La Placeta'),(182,'La Vega'),(183,'Las Breas'),(184,'Las Pircas'),(185,'Los Canales'),(186,'Los Perales'),(187,'Los Tambos'),(188,'Malaguín'),(189,'Pachuy'),(190,'Pastalito'),(191,'Piedras Juntas'),(192,'Punta Negra '),(193,'Quebrada de la Plata'),(194,'Quebrada de Pinte'),(195,'Ramadilla'),(196,'Retamo'),(197,'San Félix'),(198,'Caimanes'),(199,'Canela'),(200,'Canela Baja'),(201,'Huintil'),(202,'Illapel'),(203,'Los Vilos'),(204,'Mincha'),(205,'Cuncumén'),(206,'Guangualí'),(207,'Pichidangui'),(208,'Salamanca'),(209,'Coquimbo'),(210,'La Serena‎'),(211,'Andacollo'),(212,'Caleta Hornos'),(213,'Chungungo'),(214,'El Molle'),(215,'El Trapiche'),(216,'Gualliguaica'),(217,'Guanaqueros'),(218,'Guayacán'),(219,'Huachalalume'),(220,'La Higuera'),(221,'Lambert'),(222,'Las Tacas'),(223,'Paihuano'),(224,'Pisco Elqui'),(225,'La Unión'),(226,'La Greda'),(227,'Puerto Aldea'),(228,'Puerto Velero'),(229,'Punta Colorada'),(230,'Punta de Choros'),(231,'Quebrada de Pinto'),(232,'Tongoy'),(233,'Totoralillo'),(234,'Vicuña'),(235,'Ovalle'),(236,'Barraza'),(237,'Chañaral Alto'),(238,'Combarbalá'),(239,'Huatulame'),(240,'Lagunillas'),(241,'Mialqui'),(242,'Monte Patria'),(243,'Punitaqui'),(244,'Río Hurtado'),(245,'San Julián'),(246,'Sotaquí'),(247,'Tulahuén'),(248,'Hanga Roa'),(249,'Los Andes'),(250,'Calle Larga'),(251,'Pocuro'),(252,'Rinconada'),(253,'Saladillo'),(254,'San Esteban'),(255,'Quilpué'),(256,'Colliguay'),(257,'Limache'),(258,'Limache Viejo'),(259,'Olmué'),(260,'San Francisco de Limache'),(261,'Villa Alemana'),(262,'La Ligua'),(263,'Alicahue'),(264,'Cabildo'),(265,'Catapilco'),(266,'El Molino de Ingenio'),(267,'Los Molles'),(268,'Papudo'),(269,'Petorca'),(270,'Zapallar'),(271,'Quillota‎'),(272,'El Melón'),(273,'Hijuelas'),(274,'La Calera'),(275,'La Cruz'),(276,'Nogales'),(277,'Ocoa'),(278,'Purutun'),(279,'Quebrada del Ají'),(281,'Santo Domingo'),(282,'Algarrobo'),(283,'Cartagena'),(284,'El Quisco'),(285,'El Tabo'),(286,'El Totoral'),(287,'Isla Negra'),(288,'Las Cruces'),(289,'Leyda'),(290,'Llolleo'),(291,'San Antonio'),(292,'San Sebastián'),(293,'El Yeco'),(294,'San Felipe'),(295,'Catemu'),(296,'Curimón'),(297,'Llay-Llay'),(298,'Panquehue'),(299,'Putaendo'),(300,'Rinconada de Silva'),(301,'Santa María'),(302,'Concón'),(303,'Valparaíso‎'),(304,'Viña del Mar'),(305,'Bahía Laguna Verde'),(306,'Cachagua'),(307,'Campiche'),(308,'Casablanca'),(309,'Ciudad Abierta'),(310,'Curauma'),(311,'Las Docas'),(312,'Horcón'),(313,'La Chocota'),(314,'Las Dichas'),(315,'Las Ventanas'),(316,'Loncura'),(317,'Maitencillo'),(318,'Placilla de Peñuelas'),(319,'Puchuncaví'),(320,'Quintay'),(321,'Quintero'),(322,'Ritoque'),(323,'San Juan Bautista'),(324,'Valle Alegre'),(325,'Colina'),(326,'Lampa‎'),(327,'Batuco'),(328,'Caleu'),(329,'Chicauma'),(330,'Peldehue'),(331,'Rungue'),(332,'Baños Morales'),(333,'El Melocotón'),(334,'El Volcán'),(335,'La Obra'),(337,'Los Maitenes'),(338,'Pirque'),(339,'San Gabriel'),(340,'San José de Maipo'),(341,'El Toyo'),(342,'Las Vertientes'),(343,'Corral Quemado'),(344,'El Colorado'),(345,'Farellones'),(346,'La Ermita'),(347,'La Parva'),(348,'Valle Nevado'),(349,'Buin'),(350,'San Bernardo'),(351,'Alto Jahuel'),(352,'Angostura de Paine'),(353,'Calera de Tango'),(354,'Huelquén'),(355,'Linderos'),(356,'Maipo'),(357,'Paine'),(358,'Melipilla'),(359,'Curacaví'),(360,'Longovilo'),(361,'Mallarauco'),(362,'Pomaire'),(363,'Puangue'),(364,'San Pedro'),(365,'Villa Alhué'),(366,'Santiago de Chile'),(367,'Santiago'),(368,'Talagante'),(369,'El Monte'),(370,'Isla de Maipo'),(371,'Lonquén'),(372,'Malloco'),(373,'Peñaflor'),(374,'Machalí‎'),(375,'Rancagua'),(377,'Caletones'),(378,'Chanqueahue'),(379,'Chapa Verde'),(380,'Codegua'),(381,'Coinco'),(382,'Coltauco'),(383,'Colón'),(384,'Copequén'),(385,'Coya'),(386,'Doñihue'),(387,'El Carmen'),(388,'El Estero'),(389,'El Manzano'),(390,'El Rulo'),(391,'El Tambo'),(392,'Mina El Teniente'),(393,'Esmeralda'),(394,'Graneros'),(395,'Guacarhue'),(396,'Gultro'),(397,'Larmahue'),(398,'Las Balsas'),(399,'Las Cabras'),(400,'Las Nieves'),(401,'Llallauquén'),(402,'Lo Miranda'),(403,'Los Lirios'),(404,'Malloa'),(405,'Mostazal'),(406,'Olivar Alto'),(407,'Palmas de Cocalán'),(408,'Pangal'),(409,'Pataguas Orilla'),(410,'Pelequén'),(411,'Peumo'),(412,'Picarquín'),(413,'Pichidegua'),(414,'Popeta'),(415,'Quinta de Tilcoco'),(416,'Rengo'),(417,'Requínoa'),(418,'Rosario'),(419,'Salsipuedes'),(420,'San Francisco de Mostazal'),(421,'San Vicente de Tagua Tagua'),(422,'Santa Inés'),(423,'Sewell'),(424,'Termas de Cauquenes'),(425,'Tuniche'),(426,'Zúñiga'),(427,'Pichilemu'),(428,'Bucalemu'),(429,'Cáhuil'),(430,'Ciruelos'),(432,'La Boca'),(433,'La Estrella'),(434,'Litueche'),(435,'Marchigüe'),(436,'Matanzas'),(437,'Navidad'),(438,'Paredones'),(439,'Polcura'),(440,'Puertecillo'),(441,'Pupuya'),(442,'Rapel'),(443,'San Pedro de Alcántara'),(444,'San Rafael'),(445,'Topocalma'),(446,'Tumán'),(447,'San Fernando'),(448,'Santa Cruz'),(449,'Calleuque'),(450,'Chépica'),(451,'Chimbarongo'),(452,'Cunaco'),(453,'El Huique'),(454,'La Rufina'),(455,'Lolol'),(456,'Nancagua'),(457,'Palmilla'),(458,'Peor es Nada'),(459,'Peralillo'),(460,'Placilla'),(461,'Población'),(462,'Pumanque'),(463,'Roma'),(464,'Sierras de Bellavista'),(465,'Termas del Flaco'),(466,'Puente Negro'),(467,'Cauquenes'),(468,'Chanco'),(469,'Chovellén'),(470,'Curanipe'),(471,'Mariscadero'),(472,'Pelluhue'),(473,'Pilén'),(474,'Quilhuiné'),(475,'Sauzal'),(476,'Tregualemu'),(477,'Curicó'),(478,'Duao'),(479,'Hualañé'),(480,'Iloca'),(481,'Licantén'),(482,'Llico'),(483,'Lontué'),(484,'Lora'),(485,'Molina'),(486,'Peteroa'),(487,'Pichingal'),(488,'Potrero Grande'),(489,'Rauco'),(490,'Romeral'),(491,'Sagrada Familia'),(492,'Teno'),(493,'Vichuquén'),(494,'Villa Prat'),(495,'Linares'),(496,'Abránquil'),(497,'Bobadilla'),(498,'Caliboro'),(499,'Huerta de Maule'),(500,'Longaví'),(501,'Nirivilo'),(503,'Panimávida'),(504,'Parral'),(505,'Río Purapel'),(506,'Putagán'),(507,'Quinamávida'),(508,'Rari'),(509,'Retiro'),(510,'San Javier de Loncomilla'),(511,'Villa Alegre'),(512,'Yerbas Buenas'),(513,'Talca'),(514,'Constitución'),(515,'Cumpeo'),(516,'Curepto'),(517,'Empedrado'),(518,'Gualleco'),(519,'Maule'),(520,'Pelarco'),(521,'Putú'),(522,'San Clemente'),(524,'Santa Olga'),(525,'Vilches'),(526,'Cañete'),(527,'Lebu'),(528,'Antihuala'),(529,'Arauco'),(530,'Canihual'),(531,'Carampangue'),(532,'Cayucupil'),(533,'Cerro Alto'),(534,'Contulmo'),(535,'Curanilahue'),(536,'Laraquete'),(538,'Los Álamos'),(539,'Quiapo'),(540,'Quidico'),(541,'Tirúa'),(542,'Tranaquepe'),(543,'Antuco'),(544,'Cabrero'),(545,'Coihue'),(546,'Estación'),(547,'Huépil'),(548,'La Laja'),(549,'Los Ángeles'),(550,'Monte Águila'),(551,'Mulchén'),(552,'Nacimiento'),(553,'Negrete'),(555,'Quilaco'),(556,'Quilleco'),(557,'Quinel'),(558,'Rere'),(559,'Rihue'),(560,'Santa Bárbara'),(561,'Santa Fe'),(562,'Trupán'),(563,'Tucapel'),(564,'Villa Mercedes'),(565,'Yumbel'),(566,'Concepción'),(567,'Tomé‎'),(568,'Bellavista'),(569,'Caleta Lenga'),(570,'Caleta Lo Rojas'),(571,'Caleta Tumbes'),(572,'Chiguayante'),(573,'Chivilingo'),(574,'Cocholgüe'),(575,'Colcura'),(576,'Coliumo'),(577,'Coronel'),(578,'Dichato'),(579,'Florida'),(580,'Hualqui'),(581,'Lirquén'),(582,'Lloicura'),(583,'Lota'),(584,'Penco'),(585,'Pingueral'),(586,'Puente 7'),(587,'Punta de Parra'),(588,'Rafael'),(589,'Santa Juana'),(590,'Talcahuano'),(591,'Tanahuillín'),(592,'Pinto'),(593,'Agua Buena'),(594,'Almarza'),(595,'Buchupureo'),(596,'Buli'),(597,'Bulnes'),(598,'Bustamante'),(599,'Cachapoal'),(600,'Campanario'),(601,'Cerro Negro'),(602,'Chillán'),(603,'Chillán Viejo'),(604,'Cholguán'),(605,'Cobquecura'),(606,'Coelemu'),(607,'Coihueco'),(608,'Coleal'),(609,'Conurbación Chillán'),(610,'Culenar'),(612,'El Emboque'),(613,'El Guape'),(614,'Flor de Quihua'),(615,'General Cruz'),(616,'Guarilihue Bajo'),(617,'La Quinta'),(618,'La Viñita'),(619,'Las Mariposas'),(620,'Los Lleuques'),(621,'Minas del Prado'),(622,'Nahueltoro'),(623,'Nebuco'),(624,'Ninhue'),(625,'Ñipas'),(626,'Paso Ancho'),(627,'Perales'),(628,'Población 11 de septiembre'),(629,'Portezuelo'),(630,'Pueblo Seco'),(631,'Quillón'),(632,'Quilmo'),(633,'Quinchamalí'),(634,'Quinquegua'),(635,'Ranguelmo'),(636,'Ránquil'),(637,'Recinto'),(638,'Rucapequén'),(639,'San Carlos'),(640,'San Fabián de Alico'),(642,'San Gregorio de Ñiquén'),(643,'San Ignacio'),(644,'San Nicolás'),(645,'Santa Clara'),(646,'Talquipén'),(647,'Tanilvoro'),(648,'Termas de Chillán'),(649,'Treguaco'),(650,'Tres Esquinas'),(651,'Tres Esquinas de Cachapoal'),(652,'Tres Esquinas de Cato'),(653,'Vegas de Itata'),(654,'Villa El Copihue'),(655,'Virgüín'),(656,'Yungay'),(657,'Zemita'),(658,'Ñiquén'),(659,'La Imperial'),(660,'Lautaro'),(661,'Villarrica'),(662,'Barros Arana'),(663,'Boroa'),(664,'Cajón'),(665,'Carahue'),(666,'Catripulli'),(667,'Chile Nuestro'),(668,'Cholchol'),(670,'Cunco'),(671,'Curarrehue'),(672,'El Capricho'),(673,'Faja Maisan'),(674,'Freire'),(675,'Galvarino'),(676,'Gorbea'),(677,'Labranza'),(678,'Las Araucarias'),(679,'Lican Ray'),(680,'Loncoche'),(681,'Melipeuco'),(682,'Molco'),(683,'Nueva Imperial'),(684,'Perquenco'),(685,'Pitrufquén'),(686,'Pucón'),(687,'Queule'),(688,'Saavedra'),(689,'Temuco'),(690,'Teodoro Schmidt'),(691,'Toltén'),(692,'Trovolhue'),(693,'Vilcún'),(694,'Villa Almagro'),(695,'Villa Coihueco'),(696,'Curacautín‎'),(697,'Angol'),(698,'Capitán Pastene'),(699,'Curaco'),(700,'Ercilla'),(701,'Huequén'),(702,'Icalma'),(703,'Los Sauces'),(704,'Lumaco'),(705,'Malalcahuello'),(706,'Mininco'),(707,'Pailahueque'),(708,'Pemehue'),(709,'Pidima'),(710,'Purén'),(711,'Quino'),(712,'Renaico'),(713,'Temucuicui'),(714,'Traiguén'),(715,'Victoria'),(716,'Villa Esperanza'),(717,'Río Bueno'),(718,'Caleta Hueicolla'),(719,'Catamutún'),(720,'Chaichahuén'),(721,'Cocule'),(722,'Cudico'),(723,'Daglipulli'),(724,'Futrono'),(725,'Hueicolla'),(726,'Ignao'),(728,'Lago Ranco'),(729,'Llancacura'),(730,'Llifén'),(731,'Mashue'),(732,'Puerto Carrasco'),(733,'Puerto Ulloa'),(735,'Riñinahue'),(736,'Trumao'),(737,'Antilhue'),(738,'Arique'),(739,'Asque'),(740,'Cabo Blanco'),(741,'Cachín'),(742,'Calafquén'),(743,'Caricuicui'),(744,'Carirriñe'),(745,'Cayumapu'),(746,'Cayumapu (Valdivia)'),(747,'Centinela'),(748,'Chaihuín'),(750,'Chiguao'),(751,'Chonqui'),(752,'Chorocamayo'),(753,'Choshuenco'),(754,'Chunimpa'),(756,'Colegual'),(757,'Coñaripe'),(758,'Corcovado'),(759,'Corral'),(760,'Correltué'),(761,'Coz Coz'),(762,'Cruces'),(763,'Cudileufu'),(764,'Cuncún'),(765,'Curiñanco'),(766,'Cuyinhue'),(767,'Dollinco'),(768,'El Molino'),(769,'El Salto'),(770,'El Tesorito'),(771,'El Vergel'),(772,'Enco'),(773,'Flor del Lago'),(774,'Folilco'),(775,'Folilco (Máfil)'),(776,'Huellahue'),(777,'Huerquehue'),(778,'Huidif'),(779,'Huillicoihue'),(780,'Huillinco'),(781,'Huillines'),(782,'Huitag'),(783,'Huite'),(784,'Illahue'),(785,'Inipulli'),(786,'Isla Teja'),(787,'Iñaque'),(788,'Junco'),(789,'La Esperanza'),(790,'La Palma'),(791,'La Pellinada'),(792,'Lanco'),(793,'Las Huellas'),(794,'Lipingue'),(795,'Liquiñe'),(796,'Llancahue'),(797,'Llofe'),(798,'Llongahue'),(799,'Llonquén'),(800,'Los Bajos'),(801,'Los Lagos'),(803,'Mae'),(804,'Máfil'),(805,'Malchehue'),(806,'Malihue'),(807,'Mehuín'),(808,'Melefquén'),(809,'Millahuillín'),(810,'Nanihue'),(811,'Neltume'),(812,'Niebla'),(813,'Paico'),(814,'Paillaco'),(815,'Panguilelfún'),(816,'Panguipulli'),(817,'Pehuel'),(818,'Pelchuquín'),(819,'Pellaifa'),(820,'Pichipichoy'),(821,'Pichoy'),(822,'Pilinhue'),(823,'Pilolcura'),(824,'Pirehueico'),(825,'Pishuinco'),(826,'Pitrén'),(827,'Porvenir'),(828,'Puante'),(829,'Pucará'),(830,'Pucono'),(831,'Pucura'),(832,'Puerto Claro'),(833,'Puerto Enco'),(834,'Puerto Fuy'),(835,'Puerto Pirehueico'),(836,'Pullinque'),(837,'Punahue'),(838,'Punucapa'),(839,'Pupunahue'),(840,'Purey'),(841,'Putreguel'),(842,'Puyehue'),(843,'Puñaco'),(844,'Quilme'),(845,'Quinchilca'),(846,'Rañintuleufú'),(847,'Rebellín'),(848,'Reyehueico'),(849,'Riñihue'),(850,'Runca'),(851,'San Javier'),(852,'Santa Carla'),(854,'Tambillo'),(855,'Tomén'),(856,'Torobayo'),(857,'Trafún'),(858,'Trafún Chico'),(859,'Trafún Grande'),(860,'Traitraico'),(861,'Tralahuapi'),(862,'Tralcao'),(863,'Tralcán'),(864,'Tres Bocas'),(865,'Valdivia'),(866,'Ñancul'),(867,'Aldachildo'),(868,'Bahía de Caulín'),(869,'Castro'),(870,'Chacao'),(871,'Cucao'),(872,'Detif'),(873,'Nercón'),(874,'Quetalco'),(875,'Quíquel'),(876,'San Juan'),(877,'Tenaún'),(878,'Abtao'),(879,'Alerce'),(880,'Alto Puelo'),(881,'Angelmó'),(882,'Calbuco'),(883,'Caleta Chaparano'),(884,'Caleta La Arena'),(885,'Carelmapu'),(886,'Cascajal'),(887,'Chamiza'),(888,'Chayahué'),(889,'Cochamó'),(890,'Conurbación Puerto Montt-Puerto Varas'),(891,'Correntoso'),(892,'El Manso'),(893,'Ensenada'),(894,'Fresia'),(895,'Frutillar'),(897,'Las Gualas'),(898,'Llaguepe'),(899,'Llanada Grande'),(900,'Llanquihue'),(901,'Los Morros'),(902,'Los Muermos'),(903,'Los Riscos'),(904,'Maullín'),(905,'Nueva Braunau'),(906,'Pargua'),(907,'Paso El León'),(908,'Pelluco'),(909,'Petrohué'),(910,'Peulla'),(911,'Pocoihuén'),(912,'Primer Corral'),(913,'Pucheguín'),(914,'Puelo Bajo'),(915,'Puerto Montt'),(916,'Puerto Varas'),(917,'Punta Canelo'),(918,'Punta Maldonado'),(919,'Quenuir'),(920,'Ralún'),(921,'Río Puelo'),(922,'San Luis'),(923,'Segundo Corral'),(924,'Sotomó'),(925,'Steffen'),(926,'Torrentoso'),(927,'Valle El Frío'),(928,'Yates'),(929,'Bahía Mansa'),(930,'Caleta Cóndor'),(931,'Caleta Huellelhue'),(932,'Cancha Larga'),(933,'Cofalmo'),(934,'Currimahuida'),(935,'Forrahue'),(937,'Los Juncos'),(938,'Maicolpue'),(939,'Misión de Cuínco'),(940,'Misión de Rahue'),(941,'Pucatrihue'),(942,'Puerto Octay'),(943,'Puilo'),(944,'Purranque'),(946,'Quilacahuín'),(947,'Riachuelo'),(948,'Río Negro'),(949,'San Pablo'),(950,'Trinquicahuín'),(951,'Trome'),(953,'Ñochaco'),(954,'Ayacara'),(955,'Caleta Aulen'),(956,'Caleta Buill'),(957,'Caleta Cholgo'),(958,'Caleta Curamin'),(959,'Caleta El Manzano'),(960,'Caleta Fiordo Largo'),(961,'Caleta Gonzalo'),(962,'Caleta Leptepu'),(963,'Caleta Loncochalgua'),(964,'Caleta Pichanco'),(965,'Caleta Pichicolo'),(966,'Caleta Poyo'),(967,'Caleta Puelche'),(968,'Caleta Queten'),(969,'Caleta Quiaca'),(970,'Chaqueihua'),(971,'Chauchil'),(972,'Chumildén'),(973,'Ciudad de Chaitén'),(974,'Contao'),(975,'Futaleufú'),(976,'Hornopirén'),(977,'Hualaihué Puerto'),(978,'Huequi'),(979,'Huinay'),(980,'La Poza'),(982,'Llanchid'),(983,'Lleguimán'),(984,'Mañihueico'),(985,'Palena'),(986,'Pillán'),(987,'Puerto Bonito'),(988,'Puntilla Pichicolo'),(989,'Quildaco Bajo'),(990,'Reñihue'),(991,'Rolecha'),(993,'Tentelhue'),(994,'Termas de Cahuelmó'),(995,'Termas de Porcelana'),(996,'Termas Lago Cabrera'),(997,'Villa Santa Lucía'),(998,'Vodudahue'),(999,'Candelario Mancilla'),(1000,'Fachinal'),(1001,'Mañihuales'),(1002,'Caleta Andrade'),(1003,'Estero Copa'),(1005,'Melinka'),(1006,'Puerto Aguirre'),(1007,'Puerto Aysén'),(1008,'Puerto Chacabuco'),(1009,'Puerto Raúl Marín Balmaceda'),(1010,'Puyuhuapi'),(1012,'Chile Chico'),(1013,'Cochrane'),(1014,'Coyhaique'),(1015,'Caleta Austral'),(1018,'Entrada Baker'),(1019,'Entrada Mayer'),(1020,'Lago Brown'),(1021,'Los Ñadis'),(1022,'Villa O\'Higgins'),(1023,'Balmaceda'),(1025,'El Blanco'),(1026,'La Tapera'),(1027,'Lago Verde'),(1030,'Puerto Bertrand'),(1031,'Puerto Guadal'),(1032,'Puerto Ingeniero Ibáñez'),(1033,'Puerto Murta'),(1034,'Puerto Sánchez'),(1035,'Puerto Williams‎'),(1036,'Puerto Harris'),(1037,'Puerto Navarino'),(1038,'Puerto Toro'),(1039,'Villa Las Estrellas'),(1040,'Punta Arenas'),(1041,'Monte Aymond'),(1042,'Punta Delgada'),(1044,'Cameron'),(1045,'Cerro Sombrero'),(1046,'Puerto Natales‎'),(1047,'Puerto Alert'),(1048,'Puerto Bories'),(1049,'Puerto Edén'),(1050,'Puerto Henry'),(1051,'Puerto Riofrío'),(1052,'Villa Cerro Castillo'),(1053,'Cerrillos'),(1054,'La Reina'),(1055,'Pudahuel'),(1056,'Cerro Navia'),(1057,'Las Condes'),(1058,'Quilicura'),(1059,'Conchalí'),(1060,'Lo Barnechea'),(1061,'Quinta Normal'),(1062,'El Bosque'),(1063,'Lo Espejo'),(1064,'Recoleta'),(1065,'Estación Central'),(1066,'Lo Prado'),(1067,'Renca'),(1068,'Huechuraba'),(1069,'Macul'),(1070,'San Miguel'),(1071,'Independencia'),(1072,'Maipú'),(1073,'San Joaquin'),(1074,'La Cisterna'),(1075,'Ñuñoa'),(1076,'San Ramón'),(1077,'La Florida'),(1078,'Redro Aguirre Cerda'),(1079,'La Pintana'),(1080,'Peñalolén'),(1081,'Vitacura'),(1082,'La Granja'),(1083,'Providencia'),(1084,'Padre Hurtado'),(1086,'Puente Alto');
/*!40000 ALTER TABLE `keyword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `metrica`
--

DROP TABLE IF EXISTS `metrica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `metrica` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `metrica`
--
-- ORDER BY:  `id`

LOCK TABLES `metrica` WRITE;
/*!40000 ALTER TABLE `metrica` DISABLE KEYS */;
INSERT INTO `metrica` VALUES (1,'aprobacion'),(2,'sentimientoPositivo'),(3,'sentimientoNegativo'),(4,'sentimientoNeutro');
/*!40000 ALTER TABLE `metrica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partido`
--

DROP TABLE IF EXISTS `partido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idconglomerado` int(11) NOT NULL,
  `nombre` varchar(128) NOT NULL,
  `cuentaTwitter` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `idconglomerado` (`idconglomerado`),
  CONSTRAINT `partido_ibfk_1` FOREIGN KEY (`idconglomerado`) REFERENCES `conglomerado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partido`
--
-- ORDER BY:  `id`

LOCK TABLES `partido` WRITE;
/*!40000 ALTER TABLE `partido` DISABLE KEYS */;
INSERT INTO `partido` VALUES (1,1,'Unión Demócrata Independiente','udipopular'),(2,1,'Renovación Nacional','RNchile'),(3,2,'Democracia Cristiana','PDC_Chile'),(4,1,'Partido Regionalista Independiente',NULL),(5,2,'Partido por la Democracia','PPD_Chile'),(6,2,'Partido Socialista','pschile'),(7,2,'Partido Comunista','PCdeChile'),(8,2,'Partido Radical Socialdemócrata','PRSDChile'),(9,3,'Partido Progresista','LosPROgresistas'),(10,7,'Partido Liberal','Liberales_Chile'),(11,4,'Partido Humanista','phumanista'),(12,4,'Revolución Democrática','RDemocratica'),(13,4,'Izquierda Autónoma','izqautonoma'),(14,4,'Partido Igualdad','IgualdadPartido'),(15,7,'Movimiento de izquierda revolucionario','MIRdeChile'),(16,5,'Amplitud','AmplitudChile'),(17,1,'Evolución Política','evopoli'),(18,2,'Movimiento Amplio Social','PartidoMAS_'),(19,2,'Izquierda Ciudadana','izq_ciu'),(20,5,'Ciudadanos','CiudadanosCs'),(21,7,'Partido Amplio de Izquierda Socialista','IzqSoc'),(22,6,'Movimiento Independiente Regionalista Agrario y Social',NULL),(23,7,'Independiente',NULL),(24,3,'Democracia Regional Patagónica','DmRegional');
/*!40000 ALTER TABLE `partido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partido_keyword`
--

DROP TABLE IF EXISTS `partido_keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partido_keyword` (
  `idkeyword` int(11) NOT NULL,
  `idpartido` int(11) NOT NULL,
  PRIMARY KEY (`idpartido`,`idkeyword`),
  KEY `idkeyword` (`idkeyword`),
  KEY `idpartido` (`idpartido`),
  CONSTRAINT `partido_keyword_ibfk_1` FOREIGN KEY (`idpartido`) REFERENCES `partido` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `partido_keyword_ibfk_2` FOREIGN KEY (`idkeyword`) REFERENCES `keyword` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partido_keyword`
--
-- ORDER BY:  `idpartido`,`idkeyword`

LOCK TABLES `partido_keyword` WRITE;
/*!40000 ALTER TABLE `partido_keyword` DISABLE KEYS */;
INSERT INTO `partido_keyword` VALUES (1,1),(2,1),(5,1),(3,2),(4,2),(6,2),(7,3),(8,3),(9,3),(10,4),(11,5),(12,5),(13,6),(14,6),(15,6),(16,7),(17,7),(18,8),(19,9),(20,12),(21,12),(22,15),(23,17),(24,18),(26,19),(27,19);
/*!40000 ALTER TABLE `partido_keyword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partido_metrica`
--

DROP TABLE IF EXISTS `partido_metrica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partido_metrica` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idpartido` int(11) NOT NULL,
  `idmetrica` int(11) NOT NULL,
  `valor` decimal(10,0) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `lugar` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idpartido` (`idpartido`),
  KEY `idmetrica` (`idmetrica`),
  CONSTRAINT `partido_metrica_ibfk_1` FOREIGN KEY (`idmetrica`) REFERENCES `metrica` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `partido_metrica_ibfk_2` FOREIGN KEY (`idpartido`) REFERENCES `partido` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partido_metrica`
--
-- ORDER BY:  `id`

LOCK TABLES `partido_metrica` WRITE;
/*!40000 ALTER TABLE `partido_metrica` DISABLE KEYS */;
/*!40000 ALTER TABLE `partido_metrica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `politico`
--

DROP TABLE IF EXISTS `politico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `politico` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idpartido` int(11) NOT NULL,
  `idconglomerado` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `cuentaTwitter` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idpartido` (`idpartido`),
  KEY `idconglomerado` (`idconglomerado`),
  CONSTRAINT `politico_ibfk_1` FOREIGN KEY (`idpartido`) REFERENCES `partido` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `politico_ibfk_2` FOREIGN KEY (`idconglomerado`) REFERENCES `conglomerado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=197 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `politico`
--
-- ORDER BY:  `id`

LOCK TABLES `politico` WRITE;
/*!40000 ALTER TABLE `politico` DISABLE KEYS */;
INSERT INTO `politico` VALUES (1,5,7,'Ricardo','Lagos','RicardoLagos'),(2,2,7,'Sebastián','Piñera','sebastianpinera'),(3,9,7,'Marco','Enríquez-Ominami','marcoporchile'),(4,1,7,'Pablo','Longueira','Pablo_Longueira'),(5,3,7,'Eduardo','Frei',NULL),(6,6,7,'Michelle','Bachelet','PrensaMichelle'),(7,1,7,'Evelyn','Matthei','evelynmatthei'),(8,7,7,'Daniel','Jadue','danieljadue'),(9,7,7,'Cristian','Cuevas','ccuevasz'),(10,23,7,'Marcel','Claude','marcelclaude'),(11,23,7,'Franco','Parisi','Fr_parisi'),(12,14,7,'Roxana','Miranda','RoxanaEsPueblo'),(13,20,7,'Andres','Velasco','AndresVelasco'),(14,1,7,'Jovino','Novoa','Jovinonovoa'),(15,23,7,'Laurence','Golborne','lgolborne'),(16,3,7,'Jorge','Burgos','jburgosv'),(17,5,7,'Heraldo','Muñoz','HeraldoMunoz'),(18,8,7,'José','Gomez','jagomez'),(19,5,7,'Rodrigo','Valdés','Min_Hacienda'),(20,5,7,'Nicolás','Eyzaguirre','Segpres'),(21,6,7,'Marcelo','Diaz Diaz','marcelodiazd'),(22,3,7,'Luis','Céspedes','meconomia'),(23,7,7,'Marcos','Barraza','dsocial_gob'),(24,5,7,'Adriana','Delpiano','Mineduc'),(25,23,7,'Javiera','Blanco','MinjuChile'),(26,3,7,'Ximena','Rincón','ximerincon'),(27,3,7,'Alberto','Undurraga','aundurragav'),(28,23,7,'Carmen','Castillo Taucher','ministeriosalud'),(29,5,7,'Paulina','Saball','Minvu'),(30,6,7,'Carlos','Furche','MinagriCL'),(31,8,7,'Aurora','Williams','MinMineria_cl'),(32,5,7,'Andrés','Gómez-Lobo','AndresGomezlobo'),(33,19,7,'Victor','Osorio','VictorOsorioR'),(34,6,7,'Máximo','Pacheco','MinEnergia'),(35,3,7,'Pablo','Banedier','pbadenierm'),(36,18,7,'Natalia','Riffo','nataliariffo'),(37,7,7,'Claudia','Pascual','SernamChile'),(38,23,7,'Ernesto','Ottone','ErnestoOttoneR'),(39,2,7,'Andres','Allamand','allamand'),(40,6,7,'Isabel','Allende','iallendebussi'),(41,23,7,'Pedro','Araya','ArayaPedro'),(42,24,7,'Carlos','Bianchi','CarlitosBianchi'),(43,2,7,'Francisco','Chahuán','chahuan'),(44,1,7,'Juan','Coloma','jacoloma'),(45,6,7,'Alfonso','De Urresti','adeurresti'),(46,2,7,'Alberto','Espina','albertoespina'),(47,1,7,'Alejandro','García Huidobro','Senadoragh'),(48,2,7,'Jose','García Ruminot','PGarciaRuminot'),(49,5,7,'Guido','Girardi','guidogirardi'),(50,3,7,'Carolina','Goic','carolinagoic'),(51,23,7,'Alejandro','Guillier','SenadorGuillier'),(52,5,7,'Felipe','Harboe','felipeharboe'),(53,23,7,'Antonio','Horvath','Antoniohorvath'),(54,5,7,'Ricardo','Lagos weber','lagosweber'),(55,1,7,'Hernán','Larraín','HernanLarrainF'),(56,6,7,'Juan','Letelier','jplchile'),(57,3,7,'Manuel','Matta','SenadorMatta'),(58,6,7,'Carlos','Montes','carlosmontestwt'),(59,1,7,'Iván','Moreira','ivanmoreirab'),(60,5,7,'Adriana','Muñoz','_adrianamunoz'),(61,21,7,'Alejandro','Navarro','senadornavarro'),(62,23,7,'Jaime','Orpis','jaimeorpisb'),(63,23,7,'Manuel','Ossandón','mjossandon'),(64,16,7,'Lily','Pérez','lilyperez'),(65,1,7,'Victor','Perez Varela','senadorVPV'),(66,3,7,'Jorge','Pizarro','pizarrosenador'),(67,2,7,'Baldo','Prokurica','bprokurica'),(68,5,7,'Jaime','Quintana Leal','senadorquintana'),(69,6,7,'Rabindranath','Quinteros Lara','quinterosenador'),(70,23,7,'Fulvio','Rossi Ciocca','FulvioRossiC'),(71,5,7,'Eugenio','Tuma Zedán','SenadorTuma'),(72,1,7,'Jacqueline','Van Rysselberghe Herrera','jvanbiobio'),(73,1,7,'Ena','Von Baer Jahn','enavonbaer'),(74,3,7,'Ignacio','Walker Prieto','ignaciowalker'),(75,3,7,'Patricio','Walker Prieto','patriciowalker'),(76,3,7,'Andrés','Zaldívar Larraín','andreszaldivarl'),(77,23,7,'Sergio','Aguiló','aguilo_sergio'),(78,6,7,'Jenny','Álvarez','jennyalvarezv'),(79,1,7,'Pedro','Alvarez-Salamanca Ramírez','alvarezsalamanc'),(80,6,7,'Osvaldo','Andrade Lara','OsvaldoAndradeL'),(81,3,7,'Claudio','Arriagada Macaya','claudioarriagad'),(82,23,7,'Pepe','Auth Stewart','pepe_auth'),(83,1,7,'Ramón','Barros Montero','memobarros'),(84,2,7,'Germán','Becker Alvear','gbeckera'),(85,1,7,'Jaime','Bellolio Avaria','jbellolio'),(86,2,7,'Bernardo','Berger Fett','DiputadoBerger'),(87,23,7,'Gabriel','Boric Font','gabrielboric'),(88,16,7,'Pedro','Browne Urrejola','e_PedroBrowne'),(89,5,7,'Cristián','Campos Jara',NULL),(90,7,7,'Karol','Cariola Oliva','Karolcariola'),(91,7,7,'Lautaro','Carmona Soto','lautarocarmona'),(92,5,7,'Loreto','Carvajal Ambiado','loretodiputada'),(93,6,7,'Juan','Castro González','DoctorJLCastro'),(94,5,7,'Guillermo','Ceroni Fuentes','diputadoceroni'),(95,3,7,'Fuad','Chahin Valenzuela','fchahin'),(96,3,7,'Marcelo','Chávez Velásquez','marcelochavezv'),(97,6,7,'Daniella','Cicardini Milla','Dani_Cicardini'),(98,1,7,'Juan','Coloma Alamos','Tono_Coloma'),(99,3,7,'Aldo','Cornejo González','DiputadoCornejo'),(100,1,7,'Felipe','De Mussy Hiriart','DeMussy'),(101,23,7,'José','Edwards Silva','RojoEdwards'),(102,3,7,'Sergio','Espejo Yaksic','SergioEspejo'),(103,8,7,'Marcos','Espinosa Monardes','dipu_mespinosa'),(104,6,7,'Fidel','Espinoza Sandoval','fideldiputado'),(105,5,7,'Daniel','Farcas Guendelman','dfarcas'),(106,5,7,'Ramón','Farías Ponce','RamonFarias'),(107,6,7,'Maya','Fernández Allende','Mayafernandeza'),(108,3,7,'Iván','Flores García','ifloresdiputado'),(109,3,7,'Iván','Fuentes Castillo','Prensaifuentes'),(110,2,7,'Gonzalo','Fuenzalida Figueroa','gonzofuenza'),(111,1,7,'Sergio','Gahona Salazar','sergiogahona'),(112,2,7,'René','García García',NULL),(113,5,7,'Cristina','Girardi Lavín','cgirardidiputad'),(114,16,7,'Joaquín','Godoy Ibáñez',NULL),(115,5,7,'Rodrigo','González Torres',NULL),(116,7,7,'Hugo','Gutiérrez Gálvez','Hugo_Gutierrez_'),(117,1,7,'Romilio','Gutiérrez Pino','RomilioGP'),(118,1,7,'Gustavo','Hasbún Selume','gustavohasbun'),(119,1,7,'Javier','Hernández Hernández',NULL),(120,8,7,'Marcela','Hernando Pérez','MarcelaHernando'),(121,1,7,'María','Hoffmann Opazo','PepaHoffmann'),(122,23,7,'Miguel','Alvarado Ramírez','dipalvarado'),(123,23,7,'Giorgio','Jackson Drago','GiorgioJackson'),(124,5,7,'Enrique','Jaramillo Becker','ejaramilb'),(125,8,7,'Carlos','Jarpa Wevar','Oficinajarpa'),(126,5,7,'Tucapel','Jiménez Fuentes','tucapeljimenez'),(127,23,7,'José','Kast Rist','joseantoniokast'),(128,17,7,'Felipe','Kast Sommerhoff','felipekast'),(129,1,7,'Issa','Kort Garriga','issakortg'),(130,1,7,'Joaquín','Lavín León','LavinJoaquin'),(131,6,7,'Luis','Lemus Aracena','lemusdiputado'),(132,3,7,'Roberto','León Ramírez','diputadoleon'),(133,5,7,'Felipe','Letelier Norambuena','FelipeDiputado'),(134,3,7,'Pablo','Lorenzini Basso','pabloIorenzini'),(135,1,7,'Javier','Macaya Danús','javiermacaya'),(136,2,7,'Rosauro','Martínez Labbé',NULL),(137,1,7,'Patricio','Melero Abaroa',NULL),(138,6,7,'Daniel','Melo Contreras','Danielmelochile'),(139,8,7,'Fernando','Meza Moncada','Diputadofmeza'),(140,10,7,'Vlado','Mirosevic Verdugo','vladomirosevic'),(141,1,7,'Andrea','Molina Oliva',NULL),(142,2,7,'Cristián','Monckeberg Bruner','cmonckeberg'),(143,2,7,'Nicolás','Monckeberg Díaz','nmonckeberg'),(144,6,7,'Manuel','Monsalve Benavides','InfoDipMonsalve'),(145,1,7,'Celso','Morales Muñoz','Celsomorales'),(146,3,7,'Juan','Morano Cornejo','juanmorano'),(147,1,7,'Claudia','Nogueira Fernández','Nogueiradiputad'),(148,1,7,'Iván','Norambuena Farías',NULL),(149,7,7,'Daniel','Núñez Arancibia','daniel_nunez_a'),(150,5,7,'Marco','Núñez Lozano','marconunez'),(151,2,7,'Paulina','Núñez Urrutia','paulinanu'),(152,3,7,'Sergio','Ojeda Uribe','diputadoOjeda'),(153,3,7,'José','Ortiz Novoa','OrtizDiputado'),(154,6,7,'Clemira','Pacheco Rivas','clemirapachecor'),(155,6,7,'Denise','Pascal Allende','denisediputada'),(156,2,7,'Diego','Paulsen Kehr','diegopaulsen'),(157,8,7,'José','Pérez Arriagada','Dip_JosePerez'),(158,2,7,'Leopoldo','Pérez Lahsen','perezlahsen'),(159,3,7,'Jaime','Pilowsky Greene','PilowskyDip'),(160,23,7,'Roberto','Poblete Zapata','DiputadoPoblete'),(161,3,7,'Yasna','Provoste Campillay','ProvosteYasna'),(162,2,7,'Jorge','Rathgeb Schifferli','JorgeRathgeb'),(163,3,7,'Ricardo','Rincón González','RicardoRincon1'),(164,23,7,'Gaspar','Rivas Sánchez','GasparRivas'),(165,8,7,'Alberto','Robles Pantoja','diputadorobles'),(166,6,7,'Luis','Rocafull López','Rocafuldiputado'),(167,23,7,'Karla','Rubilar Barahona','KarlaEnAccion'),(168,3,7,'Jorge','Sabag Villalobos','DiputadoSabag'),(169,2,7,'Marcela','Sabat Fernández','MarceSabat'),(170,23,7,'René','Saffirio Espinoza','renesaffirio'),(171,6,7,'Raúl','Saldívar Auger','raulsaldivarps'),(172,1,7,'David','Sandoval Plaza','sandovalplaza'),(173,2,7,'Alejandro','Santana Tirachini','AleTirachini'),(174,6,7,'Marcelo','Schilling Rodríguez','dip_schilling'),(175,22,7,'Alejandra','Sepúlveda Orbenes','ale_sepulvedap'),(176,3,7,'Gabriel','Silber Romo','gabrielsilber'),(177,1,7,'Ernesto','Silva Méndez','ErnestoSilvaM'),(178,6,7,'Leonardo','Soto Ferrada','LeoSotoChile'),(179,1,7,'Arturo','Squella Ovalle','arturosquella'),(180,5,7,'Jorge','Tarud Daccarett','JorgeTarud'),(181,7,7,'Guillermo','Teillier Del Valle','gteillier'),(182,3,7,'Víctor','Torres Jeldes','DocVictorTorres'),(183,1,7,'Renzo','Trisotti Martínez','RenzoTrisotti'),(184,5,7,'Joaquín','Tuma Zedan',NULL),(185,1,7,'Marisol','Turres Figueroa','MarisolTurres'),(186,1,7,'Jorge','Ulloa Aguillón','jorge_ulloa'),(187,6,7,'Christian','Urízar Muñoz','UrizarDiputado'),(188,1,7,'Ignacio','Urrutia Bonilla',NULL),(189,1,7,'Osvaldo','Urrutia Soto','urrutiaosvaldo'),(190,7,7,'Camila','Vallejo Dowling','camila_vallejo'),(191,3,7,'Patricio','Vallespín López','pvallespin'),(192,1,7,'Enrique','Van Rysselberghe Herrera','vanrysselberghe'),(193,3,7,'Mario','Venegas Cárdenas',NULL),(194,23,7,'Germán','Verdugo Soto','prensagverdugo'),(195,3,7,'Matías','Walker Prieto','matiaswalkerp'),(196,1,7,'Felipe','Ward Edwards','Felipeward');
/*!40000 ALTER TABLE `politico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `politico_keyword`
--

DROP TABLE IF EXISTS `politico_keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `politico_keyword` (
  `idkeyword` int(11) NOT NULL,
  `idpolitico` int(11) NOT NULL,
  PRIMARY KEY (`idpolitico`,`idkeyword`),
  KEY `idkeyword` (`idkeyword`),
  KEY `idpolitico` (`idpolitico`),
  CONSTRAINT `politico_keyword_ibfk_1` FOREIGN KEY (`idpolitico`) REFERENCES `politico` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `politico_keyword_ibfk_2` FOREIGN KEY (`idkeyword`) REFERENCES `keyword` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `politico_keyword`
--
-- ORDER BY:  `idpolitico`,`idkeyword`

LOCK TABLES `politico_keyword` WRITE;
/*!40000 ALTER TABLE `politico_keyword` DISABLE KEYS */;
INSERT INTO `politico_keyword` VALUES (28,3),(29,16),(30,18),(31,21),(32,26),(33,27),(34,28),(35,31),(36,32),(37,33),(38,35),(39,36),(40,38);
/*!40000 ALTER TABLE `politico_keyword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `politico_metrica`
--

DROP TABLE IF EXISTS `politico_metrica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `politico_metrica` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idpolitico` int(11) NOT NULL,
  `idmetrica` int(11) NOT NULL,
  `valor` float DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `lugar` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idpolitico` (`idpolitico`),
  KEY `idmetrica` (`idmetrica`),
  CONSTRAINT `politico_metrica_ibfk_1` FOREIGN KEY (`idpolitico`) REFERENCES `politico` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `politico_metrica_ibfk_2` FOREIGN KEY (`idmetrica`) REFERENCES `metrica` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `politico_metrica`
--
-- ORDER BY:  `id`

LOCK TABLES `politico_metrica` WRITE;
/*!40000 ALTER TABLE `politico_metrica` DISABLE KEYS */;
/*!40000 ALTER TABLE `politico_metrica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `region`
--

DROP TABLE IF EXISTS `region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `region` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(5) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`),
  UNIQUE KEY `codigo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `region`
--
-- ORDER BY:  `id`

LOCK TABLES `region` WRITE;
/*!40000 ALTER TABLE `region` DISABLE KEYS */;
INSERT INTO `region` VALUES (1,'I','Tarapacá'),(2,'II','Antofagasta'),(3,'III','Atacama'),(4,'IV','Coquimbo'),(5,'V','Valparaíso'),(6,'VI','O\'Higgins'),(7,'VII','Maule'),(8,'VIII','Biobío'),(9,'IX','Araucanía'),(10,'X','Los Lagos'),(11,'XI','Aysén'),(12,'XII','Magallanes'),(13,'RM','Metropolitana de Santiago'),(14,'XIV','Los Ríos'),(15,'XV','Arica y Parinacota');
/*!40000 ALTER TABLE `region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `region_keyword`
--

DROP TABLE IF EXISTS `region_keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `region_keyword` (
  `idkeyword` int(11) NOT NULL,
  `idregion` int(11) NOT NULL,
  PRIMARY KEY (`idregion`,`idkeyword`),
  KEY `idkeyword` (`idkeyword`),
  KEY `idregion` (`idregion`),
  CONSTRAINT `region_keyword_ibfk_1` FOREIGN KEY (`idregion`) REFERENCES `partido` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `region_keyword_ibfk_2` FOREIGN KEY (`idkeyword`) REFERENCES `keyword` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `region_keyword`
--
-- ORDER BY:  `idregion`,`idkeyword`

LOCK TABLES `region_keyword` WRITE;
/*!40000 ALTER TABLE `region_keyword` DISABLE KEYS */;
INSERT INTO `region_keyword` VALUES (70,1),(71,1),(72,1),(73,1),(74,1),(75,1),(76,1),(77,1),(78,1),(79,1),(80,1),(81,1),(82,1),(83,1),(84,1),(85,1),(86,1),(87,1),(88,1),(89,1),(90,1),(91,1),(92,1),(93,1),(94,1),(95,1),(96,1),(97,1),(98,1),(99,1),(100,1),(101,1),(102,1),(103,1),(104,1),(105,1),(106,2),(107,2),(108,2),(109,2),(110,2),(111,2),(112,2),(113,2),(114,2),(115,2),(116,2),(117,2),(118,2),(119,2),(120,2),(121,2),(122,2),(123,2),(124,2),(125,2),(126,2),(127,2),(128,2),(129,3),(130,3),(131,3),(132,3),(133,3),(134,3),(135,3),(136,3),(137,3),(138,3),(139,3),(140,3),(141,3),(142,3),(143,3),(144,3),(145,3),(146,3),(147,3),(148,3),(149,3),(150,3),(151,3),(152,3),(153,3),(154,3),(155,3),(156,3),(157,3),(158,3),(159,3),(160,3),(161,3),(162,3),(163,3),(164,3),(165,3),(166,3),(167,3),(168,3),(169,3),(170,3),(171,3),(172,3),(173,3),(174,3),(175,3),(176,3),(177,3),(178,3),(179,3),(180,3),(181,3),(182,3),(183,3),(184,3),(185,3),(186,3),(187,3),(188,3),(189,3),(190,3),(191,3),(192,3),(193,3),(194,3),(195,3),(196,3),(197,3),(198,4),(199,4),(200,4),(201,4),(202,4),(203,4),(204,4),(205,4),(206,4),(207,4),(208,4),(209,4),(210,4),(211,4),(212,4),(213,4),(214,4),(215,4),(216,4),(217,4),(218,4),(219,4),(220,4),(221,4),(222,4),(223,4),(224,4),(225,4),(226,4),(227,4),(228,4),(229,4),(230,4),(231,4),(232,4),(233,4),(234,4),(235,4),(236,4),(237,4),(238,4),(239,4),(240,4),(241,4),(242,4),(243,4),(244,4),(245,4),(246,4),(247,4),(123,5),(248,5),(249,5),(250,5),(251,5),(252,5),(253,5),(254,5),(255,5),(256,5),(257,5),(258,5),(259,5),(260,5),(261,5),(262,5),(263,5),(264,5),(265,5),(266,5),(267,5),(268,5),(269,5),(270,5),(271,5),(272,5),(273,5),(274,5),(275,5),(276,5),(277,5),(278,5),(279,5),(281,5),(282,5),(283,5),(284,5),(285,5),(286,5),(287,5),(288,5),(289,5),(290,5),(291,5),(292,5),(293,5),(294,5),(295,5),(296,5),(297,5),(298,5),(299,5),(300,5),(301,5),(302,5),(303,5),(304,5),(305,5),(306,5),(307,5),(308,5),(309,5),(310,5),(311,5),(312,5),(313,5),(314,5),(315,5),(316,5),(317,5),(318,5),(319,5),(320,5),(321,5),(322,5),(323,5),(324,5),(352,6),(374,6),(375,6),(377,6),(378,6),(379,6),(380,6),(381,6),(382,6),(383,6),(384,6),(385,6),(386,6),(387,6),(388,6),(389,6),(390,6),(391,6),(392,6),(393,6),(394,6),(395,6),(396,6),(397,6),(398,6),(399,6),(400,6),(401,6),(402,6),(403,6),(404,6),(405,6),(406,6),(407,6),(408,6),(409,6),(410,6),(411,6),(412,6),(413,6),(414,6),(415,6),(416,6),(417,6),(418,6),(419,6),(420,6),(421,6),(422,6),(423,6),(424,6),(425,6),(426,6),(427,6),(428,6),(429,6),(430,6),(432,6),(433,6),(434,6),(435,6),(436,6),(437,6),(438,6),(439,6),(440,6),(441,6),(442,6),(443,6),(444,6),(445,6),(446,6),(447,6),(448,6),(449,6),(450,6),(451,6),(452,6),(453,6),(454,6),(455,6),(456,6),(457,6),(458,6),(459,6),(460,6),(461,6),(462,6),(463,6),(464,6),(465,6),(466,6),(444,7),(457,7),(467,7),(468,7),(469,7),(470,7),(471,7),(472,7),(473,7),(474,7),(475,7),(476,7),(477,7),(478,7),(479,7),(480,7),(481,7),(482,7),(483,7),(484,7),(485,7),(486,7),(487,7),(488,7),(489,7),(490,7),(491,7),(492,7),(493,7),(494,7),(495,7),(496,7),(497,7),(498,7),(499,7),(500,7),(501,7),(503,7),(504,7),(505,7),(506,7),(507,7),(508,7),(509,7),(510,7),(511,7),(512,7),(513,7),(514,7),(515,7),(516,7),(517,7),(518,7),(519,7),(520,7),(521,7),(522,7),(524,7),(525,7),(339,8),(387,8),(439,8),(482,8),(526,8),(527,8),(528,8),(529,8),(530,8),(531,8),(532,8),(533,8),(534,8),(535,8),(536,8),(538,8),(539,8),(540,8),(541,8),(542,8),(543,8),(544,8),(545,8),(546,8),(547,8),(548,8),(549,8),(550,8),(551,8),(552,8),(553,8),(555,8),(556,8),(557,8),(558,8),(559,8),(560,8),(561,8),(562,8),(563,8),(564,8),(565,8),(566,8),(567,8),(568,8),(569,8),(570,8),(571,8),(572,8),(573,8),(574,8),(575,8),(576,8),(577,8),(578,8),(579,8),(580,8),(581,8),(582,8),(583,8),(584,8),(585,8),(586,8),(587,8),(588,8),(589,8),(590,8),(591,8),(592,8),(593,8),(594,8),(595,8),(596,8),(597,8),(598,8),(599,8),(600,8),(601,8),(602,8),(603,8),(604,8),(605,8),(606,8),(607,8),(608,8),(609,8),(610,8),(612,8),(613,8),(614,8),(615,8),(616,8),(617,8),(618,8),(619,8),(620,8),(621,8),(622,8),(623,8),(624,8),(625,8),(626,8),(627,8),(628,8),(629,8),(630,8),(631,8),(632,8),(633,8),(634,8),(635,8),(636,8),(637,8),(638,8),(639,8),(640,8),(642,8),(643,8),(644,8),(645,8),(646,8),(647,8),(648,8),(649,8),(650,8),(651,8),(652,8),(653,8),(654,8),(655,8),(656,8),(657,8),(658,8),(607,9),(659,9),(660,9),(661,9),(662,9),(663,9),(664,9),(665,9),(666,9),(667,9),(668,9),(670,9),(671,9),(672,9),(673,9),(674,9),(675,9),(676,9),(677,9),(678,9),(679,9),(680,9),(681,9),(682,9),(683,9),(684,9),(685,9),(686,9),(687,9),(688,9),(689,9),(690,9),(691,9),(692,9),(693,9),(694,9),(695,9),(696,9),(697,9),(698,9),(699,9),(700,9),(701,9),(702,9),(703,9),(704,9),(705,9),(706,9),(707,9),(708,9),(709,9),(710,9),(711,9),(712,9),(713,9),(714,9),(715,9),(716,9),(176,10),(560,10),(736,10),(796,10),(842,10),(867,10),(868,10),(869,10),(870,10),(871,10),(872,10),(873,10),(874,10),(875,10),(876,10),(877,10),(878,10),(879,10),(880,10),(881,10),(882,10),(883,10),(884,10),(885,10),(886,10),(887,10),(888,10),(889,10),(890,10),(891,10),(892,10),(893,10),(894,10),(895,10),(897,10),(898,10),(899,10),(900,10),(901,10),(902,10),(903,10),(904,10),(905,10),(906,10),(907,10),(908,10),(909,10),(910,10),(911,10),(912,10),(913,10),(914,10),(915,10),(916,10),(917,10),(918,10),(919,10),(920,10),(921,10),(922,10),(923,10),(924,10),(925,10),(926,10),(927,10),(928,10),(929,10),(930,10),(931,10),(932,10),(933,10),(934,10),(935,10),(937,10),(938,10),(939,10),(940,10),(941,10),(942,10),(943,10),(944,10),(946,10),(947,10),(948,10),(949,10),(950,10),(951,10),(953,10),(954,10),(955,10),(956,10),(957,10),(958,10),(959,10),(960,10),(961,10),(962,10),(963,10),(964,10),(965,10),(966,10),(967,10),(968,10),(969,10),(970,10),(971,10),(972,10),(973,10),(974,10),(975,10),(976,10),(977,10),(978,10),(979,10),(980,10),(982,10),(983,10),(984,10),(985,10),(986,10),(987,10),(988,10),(989,10),(990,10),(991,10),(993,10),(994,10),(995,10),(996,10),(997,10),(998,10),(176,11),(999,11),(1000,11),(1001,11),(1002,11),(1003,11),(1005,11),(1006,11),(1007,11),(1008,11),(1009,11),(1010,11),(1012,11),(1013,11),(1014,11),(1015,11),(1018,11),(1019,11),(1020,11),(1021,11),(1022,11),(1023,11),(1025,11),(1026,11),(1027,11),(1030,11),(1031,11),(1032,11),(1033,11),(1034,11),(827,12),(1035,12),(1036,12),(1037,12),(1038,12),(1039,12),(1040,12),(1041,12),(1042,12),(1044,12),(1045,12),(1046,12),(1047,12),(1048,12),(1049,12),(1050,12),(1051,12),(1052,12),(240,13),(325,13),(326,13),(327,13),(328,13),(329,13),(330,13),(331,13),(332,13),(333,13),(334,13),(335,13),(337,13),(338,13),(339,13),(340,13),(341,13),(342,13),(343,13),(344,13),(345,13),(346,13),(347,13),(348,13),(349,13),(350,13),(351,13),(352,13),(353,13),(354,13),(355,13),(356,13),(357,13),(358,13),(359,13),(360,13),(361,13),(362,13),(363,13),(364,13),(365,13),(366,13),(367,13),(368,13),(369,13),(370,13),(371,13),(372,13),(373,13),(1053,13),(1054,13),(1055,13),(1056,13),(1057,13),(1058,13),(1059,13),(1060,13),(1061,13),(1062,13),(1063,13),(1064,13),(1065,13),(1066,13),(1067,13),(1068,13),(1069,13),(1070,13),(1071,13),(1072,13),(1073,13),(1074,13),(1075,13),(1076,13),(1077,13),(1078,13),(1079,13),(1080,13),(1081,13),(1082,13),(1083,13),(1084,13),(1086,13),(225,14),(337,14),(468,14),(524,14),(607,14),(717,14),(718,14),(719,14),(720,14),(721,14),(722,14),(723,14),(724,14),(725,14),(726,14),(728,14),(729,14),(730,14),(731,14),(732,14),(733,14),(735,14),(736,14),(737,14),(738,14),(739,14),(740,14),(741,14),(742,14),(743,14),(744,14),(745,14),(746,14),(747,14),(748,14),(750,14),(751,14),(752,14),(753,14),(754,14),(756,14),(757,14),(758,14),(759,14),(760,14),(761,14),(762,14),(763,14),(764,14),(765,14),(766,14),(767,14),(768,14),(769,14),(770,14),(771,14),(772,14),(773,14),(774,14),(775,14),(776,14),(777,14),(778,14),(779,14),(780,14),(781,14),(782,14),(783,14),(784,14),(785,14),(786,14),(787,14),(788,14),(789,14),(790,14),(791,14),(792,14),(793,14),(794,14),(795,14),(796,14),(797,14),(798,14),(799,14),(800,14),(801,14),(803,14),(804,14),(805,14),(806,14),(807,14),(808,14),(809,14),(810,14),(811,14),(812,14),(813,14),(814,14),(815,14),(816,14),(817,14),(818,14),(819,14),(820,14),(821,14),(822,14),(823,14),(824,14),(825,14),(826,14),(827,14),(828,14),(829,14),(830,14),(831,14),(832,14),(833,14),(834,14),(835,14),(836,14),(837,14),(838,14),(839,14),(840,14),(841,14),(842,14),(843,14),(844,14),(845,14),(846,14),(847,14),(848,14),(849,14),(850,14),(851,14),(852,14),(854,14),(855,14),(856,14),(857,14),(858,14),(859,14),(860,14),(861,14),(862,14),(863,14),(864,14),(865,14),(866,14),(41,15),(42,15),(43,15),(44,15),(45,15),(46,15),(47,15),(48,15),(49,15),(50,15),(51,15),(52,15),(53,15),(54,15),(55,15),(56,15),(57,15),(58,15),(59,15),(60,15),(61,15),(62,15),(63,15),(64,15),(65,15),(66,15),(67,15),(68,15),(69,15);
/*!40000 ALTER TABLE `region_keyword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `region_metrica`
--

DROP TABLE IF EXISTS `region_metrica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `region_metrica` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idregion` int(11) NOT NULL,
  `idmetrica` int(11) NOT NULL,
  `valor` decimal(10,0) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `lugar` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idregion` (`idregion`),
  KEY `idmetrica` (`idmetrica`),
  CONSTRAINT `region_metrica_ibfk_1` FOREIGN KEY (`idmetrica`) REFERENCES `metrica` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `region_metrica_ibfk_2` FOREIGN KEY (`idregion`) REFERENCES `region` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `region_metrica`
--
-- ORDER BY:  `id`

LOCK TABLES `region_metrica` WRITE;
/*!40000 ALTER TABLE `region_metrica` DISABLE KEYS */;
/*!40000 ALTER TABLE `region_metrica` ENABLE KEYS */;
UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-26  0:39:37
