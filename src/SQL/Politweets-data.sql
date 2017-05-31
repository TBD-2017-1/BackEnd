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
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `conglomerado`
--

LOCK TABLES `conglomerado` WRITE;
/*!40000 ALTER TABLE `conglomerado` DISABLE KEYS */;
INSERT INTO `conglomerado` VALUES (1,'Chile Vamos','Chile_Vamos_'),(2,'Nueva Mayoría','NuevaMayoriacl'),(3,'Yo Marco por el Cambio','NULL'),(4,'Frente Amplio','elfrente_amplio'),(5,'Sentido Futuro','Sentidofuturo'),(6,'Alternativa Democrática',NULL),(7,'Independiente',NULL);
/*!40000 ALTER TABLE `conglomerado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `conglomerado_keyword`
--

LOCK TABLES `conglomerado_keyword` WRITE;
/*!40000 ALTER TABLE `conglomerado_keyword` DISABLE KEYS */;
/*!40000 ALTER TABLE `conglomerado_keyword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `conglomerado_metrica`
--

LOCK TABLES `conglomerado_metrica` WRITE;
/*!40000 ALTER TABLE `conglomerado_metrica` DISABLE KEYS */;
/*!40000 ALTER TABLE `conglomerado_metrica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `keyword`
--

LOCK TABLES `keyword` WRITE;
/*!40000 ALTER TABLE `keyword` DISABLE KEYS */;
INSERT INTO `keyword` VALUES (27,'@BancadaPCIC'),(14,'@bancadaPSchile'),(40,'@consejocultura'),(8,'@DiputadosDC'),(4,'@DiputadosRN'),(2,'@DiputadosUDI'),(16,'@Diputados_PC'),(39,'@MindepChile'),(34,'@MinDeSalud'),(37,'@MinisterioBBNN'),(35,'@MinMineriaCL'),(32,'@MintrabChile'),(29,'@min_interior'),(38,'@MMAChile'),(33,'@mop_chile'),(36,'@mtt_chile'),(11,'@ppddiputados'),(30,'@PrensaMINDEF'),(31,'@segegob'),(7,'@SenadoresDC'),(13,'@SenadoresPS'),(3,'@SenadoresRN'),(1,'@SenadoresUDI'),(9,'DC'),(23,'Evopoli'),(26,'IC'),(25,'MAS'),(24,'MAS Región'),(28,'ME-O'),(22,'MIR'),(41,'PAIS'),(17,'PC'),(12,'PPD'),(10,'PRI'),(19,'PRO'),(18,'PRSD'),(15,'PS'),(21,'RD'),(20,'rev. democratica'),(6,'RN'),(5,'UDI');
/*!40000 ALTER TABLE `keyword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `metrica`
--

LOCK TABLES `metrica` WRITE;
/*!40000 ALTER TABLE `metrica` DISABLE KEYS */;
INSERT INTO `metrica` VALUES (1,'aprobacion'),(2,'sentimientoPositivo'),(3,'sentimientoNegativo'),(4,'sentimientoNeutro');
/*!40000 ALTER TABLE `metrica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `partido`
--

LOCK TABLES `partido` WRITE;
/*!40000 ALTER TABLE `partido` DISABLE KEYS */;
INSERT INTO `partido` VALUES (1,1,'Unión Demócrata Independiente','udipopular'),(2,1,'Renovación Nacional','RNchile'),(3,2,'Democracia Cristiana','PDC_Chile'),(4,1,'Partido Regionalista Independiente','NULL'),(5,2,'Partido por la Democracia','PPD_Chile'),(6,2,'Partido Socialista','pschile'),(7,2,'Partido Comunista','PCdeChile'),(8,2,'Partido Radical Socialdemócrata','PRSDChile'),(9,3,'Partido Progresista','LosPROgresistas'),(10,7,'Partido Liberal','Liberales_Chile'),(11,4,'Partido Humanista','phumanista'),(12,4,'Revolución Democrática','RDemocratica'),(13,4,'Izquierda Autónoma','izqautonoma'),(14,4,'Partido Igualdad','IgualdadPartido'),(15,7,'Movimiento de izquierda revolucionario','MIRdeChile'),(16,5,'Amplitud','AmplitudChile'),(17,1,'Evolución Política','evopoli'),(18,2,'Movimiento Amplio Social','PartidoMAS_'),(19,2,'Izquierda Ciudadana','izq_ciu'),(20,5,'Ciudadanos','CiudadanosCs'),(21,7,'Partido Amplio de Izquierda Socialista','IzqSoc'),(22,6,'Movimiento Independiente Regionalista Agrario y Social',NULL),(23,7,'Independiente',NULL),(24,3,'Democracia Regional Patagónica','DmRegional');
/*!40000 ALTER TABLE `partido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `partido_keyword`
--

LOCK TABLES `partido_keyword` WRITE;
/*!40000 ALTER TABLE `partido_keyword` DISABLE KEYS */;
INSERT INTO `partido_keyword` VALUES (1,1),(2,1),(3,2),(4,2),(5,1),(6,2),(7,3),(8,3),(9,3),(10,4),(11,5),(12,5),(13,6),(14,6),(15,6),(16,7),(17,7),(18,8),(19,9),(20,12),(21,12),(22,15),(23,17),(24,18),(25,18),(26,19),(27,19),(41,21);
/*!40000 ALTER TABLE `partido_keyword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `partido_metrica`
--

LOCK TABLES `partido_metrica` WRITE;
/*!40000 ALTER TABLE `partido_metrica` DISABLE KEYS */;
/*!40000 ALTER TABLE `partido_metrica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `politico`
--

LOCK TABLES `politico` WRITE;
/*!40000 ALTER TABLE `politico` DISABLE KEYS */;
INSERT INTO `politico` VALUES (1,5,7,'Ricardo','Lagos','RicardoLagos'),(2,2,7,'Sebastián','Piñera','sebastianpinera'),(3,9,7,'Marco','Enríquez-Ominami','marcoporchile'),(4,1,7,'Pablo','Longueira','Pablo_Longueira'),(5,3,7,'Eduardo','Frei',NULL),(6,6,7,'Michelle','Bachelet','PrensaMichelle'),(7,1,7,'Evelyn','Matthei','evelynmatthei'),(8,7,7,'Daniel','Jadue','danieljadue'),(9,7,7,'Cristian','Cuevas','ccuevasz'),(10,23,7,'Marcel','Claude','marcelclaude'),(11,23,7,'Franco','Parisi','Fr_parisi'),(12,14,7,'Roxana','Miranda','RoxanaEsPueblo'),(13,20,7,'Andres','Velasco','AndresVelasco'),(14,1,7,'Jovino','Novoa','Jovinonovoa'),(15,23,7,'Laurence','Golborne','lgolborne'),(16,3,7,'Jorge','Burgos','jburgosv'),(17,5,7,'Heraldo','Muñoz','HeraldoMunoz'),(18,8,7,'José','Gomez','jagomez'),(19,5,7,'Rodrigo','Valdés','Min_Hacienda'),(20,5,7,'Nicolás','Eyzaguirre','Segpres'),(21,6,7,'Marcelo','Diaz Diaz','marcelodiazd'),(22,3,7,'Luis','Céspedes','meconomia'),(23,7,7,'Marcos','Barraza','dsocial_gob'),(24,5,7,'Adriana','Delpiano','Mineduc'),(25,23,7,'Javiera','Blanco','MinjuChile'),(26,3,7,'Ximena','Rincón','ximerincon'),(27,3,7,'Alberto','Undurraga','aundurragav'),(28,23,7,'Carmen','Castillo Taucher','ministeriosalud'),(29,5,7,'Paulina','Saball','Minvu'),(30,6,7,'Carlos','Furche','MinagriCL'),(31,8,7,'Aurora','Williams','MinMineria_cl'),(32,5,7,'Andrés','Gómez-Lobo','AndresGomezlobo'),(33,19,7,'Victor','Osorio','VictorOsorioR'),(34,6,7,'Máximo','Pacheco','MinEnergia'),(35,3,7,'Pablo','Banedier','pbadenierm'),(36,18,7,'Natalia','Riffo','nataliariffo'),(37,7,7,'Claudia','Pascual','SernamChile'),(38,23,7,'Ernesto','Ottone','ErnestoOttoneR'),(39,2,7,'Andres','Allamand','allamand'),(40,6,7,'Isabel','Allende','iallendebussi'),(41,23,7,'Pedro','Araya','ArayaPedro'),(42,24,7,'Carlos','Bianchi','CarlitosBianchi'),(43,2,7,'Francisco','Chahuán','chahuan'),(44,1,7,'Juan','Coloma','jacoloma'),(45,6,7,'Alfonso','De Urresti','adeurresti'),(46,2,7,'Alberto','Espina','albertoespina'),(47,1,7,'Alejandro','García Huidobro','Senadoragh'),(48,2,7,'Jose','García Ruminot','PGarciaRuminot'),(49,5,7,'Guido','Girardi','guidogirardi'),(50,3,7,'Carolina','Goic','carolinagoic'),(51,23,7,'Alejandro','Guillier','SenadorGuillier'),(52,5,7,'Felipe','Harboe','felipeharboe'),(53,23,7,'Antonio','Horvath','Antoniohorvath'),(54,5,7,'Ricardo','Lagos weber','lagosweber'),(55,1,7,'Hernán','Larraín','HernanLarrainF'),(56,6,7,'Juan','Letelier','jplchile'),(57,3,7,'Manuel','Matta','SenadorMatta'),(58,6,7,'Carlos','Montes','carlosmontestwt'),(59,1,7,'Iván','Moreira','ivanmoreirab'),(60,5,7,'Adriana','Muñoz','_adrianamunoz'),(61,21,7,'Alejandro','Navarro','senadornavarro'),(62,23,7,'Jaime','Orpis','jaimeorpisb'),(63,23,7,'Manuel','Ossandón','mjossandon'),(64,16,7,'Lily','Pérez','lilyperez'),(65,1,7,'Victor','Perez Varela','senadorVPV'),(66,3,7,'Jorge','Pizarro','pizarrosenador'),(67,2,7,'Baldo','Prokurica','bprokurica'),(68,5,7,'Jaime','Quintana Leal','senadorquintana'),(69,6,7,'Rabindranath','Quinteros Lara','quinterosenador'),(70,23,7,'Fulvio','Rossi Ciocca','FulvioRossiC'),(71,5,7,'Eugenio','Tuma Zedán','SenadorTuma'),(72,1,7,'Jacqueline','Van Rysselberghe Herrera','jvanbiobio'),(73,1,7,'Ena','Von Baer Jahn','enavonbaer'),(74,3,7,'Ignacio','Walker Prieto','ignaciowalker'),(75,3,7,'Patricio','Walker Prieto','patriciowalker'),(76,3,7,'Andrés','Zaldívar Larraín','andreszaldivarl'),(77,23,7,'Sergio','Aguiló','aguilo_sergio'),(78,6,7,'Jenny','Álvarez','jennyalvarezv'),(79,1,7,'Pedro','Alvarez-Salamanca Ramírez','alvarezsalamanc'),(80,6,7,'Osvaldo','Andrade Lara','OsvaldoAndradeL'),(81,3,7,'Claudio','Arriagada Macaya','claudioarriagad'),(82,23,7,'Pepe','Auth Stewart','pepe_auth'),(83,1,7,'Ramón','Barros Montero','memobarros'),(84,2,7,'Germán','Becker Alvear','gbeckera'),(85,1,7,'Jaime','Bellolio Avaria','jbellolio'),(86,2,7,'Bernardo','Berger Fett','DiputadoBerger'),(87,23,7,'Gabriel','Boric Font','gabrielboric'),(88,16,7,'Pedro','Browne Urrejola','e_PedroBrowne'),(89,5,7,'Cristián','Campos Jara',NULL),(90,7,7,'Karol','Cariola Oliva','Karolcariola'),(91,7,7,'Lautaro','Carmona Soto','lautarocarmona'),(92,5,7,'Loreto','Carvajal Ambiado','loretodiputada'),(93,6,7,'Juan','Castro González','DoctorJLCastro'),(94,5,7,'Guillermo','Ceroni Fuentes','diputadoceroni'),(95,3,7,'Fuad','Chahin Valenzuela','fchahin'),(96,3,7,'Marcelo','Chávez Velásquez','marcelochavezv'),(97,6,7,'Daniella','Cicardini Milla','Dani_Cicardini'),(98,1,7,'Juan','Coloma Alamos','Tono_Coloma'),(99,3,7,'Aldo','Cornejo González','DiputadoCornejo'),(100,1,7,'Felipe','De Mussy Hiriart','DeMussy'),(101,23,7,'José','Edwards Silva','RojoEdwards'),(102,3,7,'Sergio','Espejo Yaksic','SergioEspejo'),(103,8,7,'Marcos','Espinosa Monardes','dipu_mespinosa'),(104,6,7,'Fidel','Espinoza Sandoval','fideldiputado'),(105,5,7,'Daniel','Farcas Guendelman','dfarcas'),(106,5,7,'Ramón','Farías Ponce','RamonFarias'),(107,6,7,'Maya','Fernández Allende','Mayafernandeza'),(108,3,7,'Iván','Flores García','ifloresdiputado'),(109,3,7,'Iván','Fuentes Castillo','Prensaifuentes'),(110,2,7,'Gonzalo','Fuenzalida Figueroa','gonzofuenza'),(111,1,7,'Sergio','Gahona Salazar','sergiogahona'),(112,2,7,'René','García García',NULL),(113,5,7,'Cristina','Girardi Lavín','cgirardidiputad'),(114,16,7,'Joaquín','Godoy Ibáñez',NULL),(115,5,7,'Rodrigo','González Torres',NULL),(116,7,7,'Hugo','Gutiérrez Gálvez','Hugo_Gutierrez_'),(117,1,7,'Romilio','Gutiérrez Pino','RomilioGP'),(118,1,7,'Gustavo','Hasbún Selume','gustavohasbun'),(119,1,7,'Javier','Hernández Hernández',NULL),(120,8,7,'Marcela','Hernando Pérez','MarcelaHernando'),(121,1,7,'María','Hoffmann Opazo','PepaHoffmann'),(122,23,7,'Miguel','Alvarado Ramírez','dipalvarado'),(123,23,7,'Giorgio','Jackson Drago','GiorgioJackson'),(124,5,7,'Enrique','Jaramillo Becker','ejaramilb'),(125,8,7,'Carlos','Jarpa Wevar','Oficinajarpa'),(126,5,7,'Tucapel','Jiménez Fuentes','tucapeljimenez'),(127,23,7,'José','Kast Rist','joseantoniokast'),(128,17,7,'Felipe','Kast Sommerhoff','felipekast'),(129,1,7,'Issa','Kort Garriga','issakortg'),(130,1,7,'Joaquín','Lavín León','LavinJoaquin'),(131,6,7,'Luis','Lemus Aracena','lemusdiputado'),(132,3,7,'Roberto','León Ramírez','diputadoleon'),(133,5,7,'Felipe','Letelier Norambuena','FelipeDiputado'),(134,3,7,'Pablo','Lorenzini Basso','pabloIorenzini'),(135,1,7,'Javier','Macaya Danús','javiermacaya'),(136,2,7,'Rosauro','Martínez Labbé',NULL),(137,1,7,'Patricio','Melero Abaroa',NULL),(138,6,7,'Daniel','Melo Contreras','Danielmelochile'),(139,8,7,'Fernando','Meza Moncada','Diputadofmeza'),(140,10,7,'Vlado','Mirosevic Verdugo','vladomirosevic'),(141,1,7,'Andrea','Molina Oliva',NULL),(142,2,7,'Cristián','Monckeberg Bruner','cmonckeberg'),(143,2,7,'Nicolás','Monckeberg Díaz','nmonckeberg'),(144,6,7,'Manuel','Monsalve Benavides','InfoDipMonsalve'),(145,1,7,'Celso','Morales Muñoz','Celsomorales'),(146,3,7,'Juan','Morano Cornejo','juanmorano'),(147,1,7,'Claudia','Nogueira Fernández','Nogueiradiputad'),(148,1,7,'Iván','Norambuena Farías',NULL),(149,7,7,'Daniel','Núñez Arancibia','daniel_nunez_a'),(150,5,7,'Marco','Núñez Lozano','marconunez'),(151,2,7,'Paulina','Núñez Urrutia','paulinanu'),(152,3,7,'Sergio','Ojeda Uribe','diputadoOjeda'),(153,3,7,'José','Ortiz Novoa','OrtizDiputado'),(154,6,7,'Clemira','Pacheco Rivas','clemirapachecor'),(155,6,7,'Denise','Pascal Allende','denisediputada'),(156,2,7,'Diego','Paulsen Kehr','diegopaulsen'),(157,8,7,'José','Pérez Arriagada','Dip_JosePerez'),(158,2,7,'Leopoldo','Pérez Lahsen','perezlahsen'),(159,3,7,'Jaime','Pilowsky Greene','PilowskyDip'),(160,23,7,'Roberto','Poblete Zapata','DiputadoPoblete'),(161,3,7,'Yasna','Provoste Campillay','ProvosteYasna'),(162,2,7,'Jorge','Rathgeb Schifferli','JorgeRathgeb'),(163,3,7,'Ricardo','Rincón González','RicardoRincon1'),(164,23,7,'Gaspar','Rivas Sánchez','GasparRivas'),(165,8,7,'Alberto','Robles Pantoja','diputadorobles'),(166,6,7,'Luis','Rocafull López','Rocafuldiputado'),(167,23,7,'Karla','Rubilar Barahona','KarlaEnAccion'),(168,3,7,'Jorge','Sabag Villalobos','DiputadoSabag'),(169,2,7,'Marcela','Sabat Fernández','MarceSabat'),(170,23,7,'René','Saffirio Espinoza','renesaffirio'),(171,6,7,'Raúl','Saldívar Auger','raulsaldivarps'),(172,1,7,'David','Sandoval Plaza','sandovalplaza'),(173,2,7,'Alejandro','Santana Tirachini','AleTirachini'),(174,6,7,'Marcelo','Schilling Rodríguez','dip_schilling'),(175,22,7,'Alejandra','Sepúlveda Orbenes','ale_sepulvedap'),(176,3,7,'Gabriel','Silber Romo','gabrielsilber'),(177,1,7,'Ernesto','Silva Méndez','ErnestoSilvaM'),(178,6,7,'Leonardo','Soto Ferrada','LeoSotoChile'),(179,1,7,'Arturo','Squella Ovalle','arturosquella'),(180,5,7,'Jorge','Tarud Daccarett','JorgeTarud'),(181,7,7,'Guillermo','Teillier Del Valle','gteillier'),(182,3,7,'Víctor','Torres Jeldes','DocVictorTorres'),(183,1,7,'Renzo','Trisotti Martínez','RenzoTrisotti'),(184,5,7,'Joaquín','Tuma Zedan',NULL),(185,1,7,'Marisol','Turres Figueroa','MarisolTurres'),(186,1,7,'Jorge','Ulloa Aguillón','jorge_ulloa'),(187,6,7,'Christian','Urízar Muñoz','UrizarDiputado'),(188,1,7,'Ignacio','Urrutia Bonilla',NULL),(189,1,7,'Osvaldo','Urrutia Soto','urrutiaosvaldo'),(190,7,7,'Camila','Vallejo Dowling','camila_vallejo'),(191,3,7,'Patricio','Vallespín López','pvallespin'),(192,1,7,'Enrique','Van Rysselberghe Herrera','vanrysselberghe'),(193,3,7,'Mario','Venegas Cárdenas',NULL),(194,23,7,'Germán','Verdugo Soto','prensagverdugo'),(195,3,7,'Matías','Walker Prieto','matiaswalkerp'),(196,1,7,'Felipe','Ward Edwards','Felipeward');
/*!40000 ALTER TABLE `politico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `politico_keyword`
--

LOCK TABLES `politico_keyword` WRITE;
/*!40000 ALTER TABLE `politico_keyword` DISABLE KEYS */;
INSERT INTO `politico_keyword` VALUES (28,3),(29,16),(30,18),(31,21),(32,26),(33,27),(34,28),(35,31),(36,32),(37,33),(38,35),(39,36),(40,38);
/*!40000 ALTER TABLE `politico_keyword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `politico_metrica`
--

LOCK TABLES `politico_metrica` WRITE;
/*!40000 ALTER TABLE `politico_metrica` DISABLE KEYS */;
/*!40000 ALTER TABLE `politico_metrica` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-10 21:46:28
