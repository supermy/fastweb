-- MySQL dump 10.11
--
-- Host: localhost    Database: fastweb
-- ------------------------------------------------------
-- Server version	5.0.51a-3ubuntu5.4

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
-- Table structure for table `JBPM_ACTION`
--

DROP TABLE IF EXISTS `JBPM_ACTION`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_ACTION` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `class` char(1) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `ISPROPAGATIONALLOWED_` bit(1) default NULL,
  `ACTIONEXPRESSION_` varchar(255) default NULL,
  `ISASYNC_` bit(1) default NULL,
  `REFERENCEDACTION_` bigint(20) default NULL,
  `ACTIONDELEGATION_` bigint(20) default NULL,
  `EVENT_` bigint(20) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `TIMERNAME_` varchar(255) default NULL,
  `DUEDATE_` varchar(255) default NULL,
  `REPEAT_` varchar(255) default NULL,
  `TRANSITIONNAME_` varchar(255) default NULL,
  `TIMERACTION_` bigint(20) default NULL,
  `EXPRESSION_` varchar(4000) default NULL,
  `EVENTINDEX_` int(11) default NULL,
  `EXCEPTIONHANDLER_` bigint(20) default NULL,
  `EXCEPTIONHANDLERINDEX_` int(11) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_ACTION_ACTNDL` (`ACTIONDELEGATION_`),
  KEY `IDX_ACTION_PROCDF` (`PROCESSDEFINITION_`),
  KEY `IDX_ACTION_EVENT` (`EVENT_`),
  KEY `FK_ACTION_REFACT` (`REFERENCEDACTION_`),
  KEY `FK_CRTETIMERACT_TA` (`TIMERACTION_`),
  KEY `FK_ACTION_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_ACTION_EVENT` (`EVENT_`),
  KEY `FK_ACTION_ACTNDEL` (`ACTIONDELEGATION_`),
  KEY `FK_ACTION_EXPTHDL` (`EXCEPTIONHANDLER_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_ACTION`
--

LOCK TABLES `JBPM_ACTION` WRITE;
/*!40000 ALTER TABLE `JBPM_ACTION` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_ACTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_BYTEARRAY`
--

DROP TABLE IF EXISTS `JBPM_BYTEARRAY`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_BYTEARRAY` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `NAME_` varchar(255) default NULL,
  `FILEDEFINITION_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_BYTEARR_FILDEF` (`FILEDEFINITION_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_BYTEARRAY`
--

LOCK TABLES `JBPM_BYTEARRAY` WRITE;
/*!40000 ALTER TABLE `JBPM_BYTEARRAY` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_BYTEARRAY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_BYTEBLOCK`
--

DROP TABLE IF EXISTS `JBPM_BYTEBLOCK`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_BYTEBLOCK` (
  `PROCESSFILE_` bigint(20) NOT NULL,
  `BYTES_` blob,
  `INDEX_` int(11) NOT NULL,
  PRIMARY KEY  (`PROCESSFILE_`,`INDEX_`),
  KEY `FK_BYTEBLOCK_FILE` (`PROCESSFILE_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_BYTEBLOCK`
--

LOCK TABLES `JBPM_BYTEBLOCK` WRITE;
/*!40000 ALTER TABLE `JBPM_BYTEBLOCK` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_BYTEBLOCK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_COMMENT`
--

DROP TABLE IF EXISTS `JBPM_COMMENT`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_COMMENT` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `VERSION_` int(11) NOT NULL,
  `ACTORID_` varchar(255) default NULL,
  `TIME_` datetime default NULL,
  `MESSAGE_` varchar(4000) default NULL,
  `TOKEN_` bigint(20) default NULL,
  `TASKINSTANCE_` bigint(20) default NULL,
  `TASKINSTANCEINDEX_` int(11) default NULL,
  `TOKENINDEX_` int(11) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_COMMENT_TSK` (`TASKINSTANCE_`),
  KEY `IDX_COMMENT_TOKEN` (`TOKEN_`),
  KEY `FK_COMMENT_TOKEN` (`TOKEN_`),
  KEY `FK_COMMENT_TSK` (`TASKINSTANCE_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_COMMENT`
--

LOCK TABLES `JBPM_COMMENT` WRITE;
/*!40000 ALTER TABLE `JBPM_COMMENT` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_COMMENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_DECISIONCONDITIONS`
--

DROP TABLE IF EXISTS `JBPM_DECISIONCONDITIONS`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_DECISIONCONDITIONS` (
  `DECISION_` bigint(20) NOT NULL,
  `TRANSITIONNAME_` varchar(255) default NULL,
  `EXPRESSION_` varchar(255) default NULL,
  `INDEX_` int(11) NOT NULL,
  PRIMARY KEY  (`DECISION_`,`INDEX_`),
  KEY `FK_DECCOND_DEC` (`DECISION_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_DECISIONCONDITIONS`
--

LOCK TABLES `JBPM_DECISIONCONDITIONS` WRITE;
/*!40000 ALTER TABLE `JBPM_DECISIONCONDITIONS` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_DECISIONCONDITIONS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_DELEGATION`
--

DROP TABLE IF EXISTS `JBPM_DELEGATION`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_DELEGATION` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASSNAME_` varchar(4000) default NULL,
  `CONFIGURATION_` varchar(4000) default NULL,
  `CONFIGTYPE_` varchar(255) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_DELEG_PRCD` (`PROCESSDEFINITION_`),
  KEY `FK_DELEGATION_PRCD` (`PROCESSDEFINITION_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_DELEGATION`
--

LOCK TABLES `JBPM_DELEGATION` WRITE;
/*!40000 ALTER TABLE `JBPM_DELEGATION` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_DELEGATION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_EVENT`
--

DROP TABLE IF EXISTS `JBPM_EVENT`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_EVENT` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `EVENTTYPE_` varchar(255) default NULL,
  `TYPE_` char(1) default NULL,
  `GRAPHELEMENT_` bigint(20) default NULL,
  `TASK_` bigint(20) default NULL,
  `TRANSITION_` bigint(20) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `NODE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_EVENT_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_EVENT_TRANS` (`TRANSITION_`),
  KEY `FK_EVENT_NODE` (`NODE_`),
  KEY `FK_EVENT_TASK` (`TASK_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_EVENT`
--

LOCK TABLES `JBPM_EVENT` WRITE;
/*!40000 ALTER TABLE `JBPM_EVENT` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_EVENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_EXCEPTIONHANDLER`
--

DROP TABLE IF EXISTS `JBPM_EXCEPTIONHANDLER`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_EXCEPTIONHANDLER` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `EXCEPTIONCLASSNAME_` varchar(4000) default NULL,
  `TYPE_` char(1) default NULL,
  `GRAPHELEMENT_` bigint(20) default NULL,
  `TASK_` bigint(20) default NULL,
  `GRAPHELEMENTINDEX_` int(11) default NULL,
  `TRANSITION_` bigint(20) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `NODE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_EXCEPTIONHANDLER`
--

LOCK TABLES `JBPM_EXCEPTIONHANDLER` WRITE;
/*!40000 ALTER TABLE `JBPM_EXCEPTIONHANDLER` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_EXCEPTIONHANDLER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_JOB`
--

DROP TABLE IF EXISTS `JBPM_JOB`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_JOB` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DUEDATE_` datetime default NULL,
  `PROCESSINSTANCE_` bigint(20) default NULL,
  `TOKEN_` bigint(20) default NULL,
  `TASKINSTANCE_` bigint(20) default NULL,
  `ISSUSPENDED_` bit(1) default NULL,
  `ISEXCLUSIVE_` bit(1) default NULL,
  `LOCKOWNER_` varchar(255) default NULL,
  `LOCKTIME_` datetime default NULL,
  `EXCEPTION_` varchar(4000) default NULL,
  `RETRIES_` int(11) default NULL,
  `NAME_` varchar(255) default NULL,
  `REPEAT_` varchar(255) default NULL,
  `TRANSITIONNAME_` varchar(255) default NULL,
  `ACTION_` bigint(20) default NULL,
  `GRAPHELEMENTTYPE_` varchar(255) default NULL,
  `GRAPHELEMENT_` bigint(20) default NULL,
  `NODE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_JOB_TSKINST` (`TASKINSTANCE_`),
  KEY `IDX_JOB_TOKEN` (`TOKEN_`),
  KEY `IDX_JOB_PRINST` (`PROCESSINSTANCE_`),
  KEY `FK_JOB_PRINST` (`PROCESSINSTANCE_`),
  KEY `FK_JOB_ACTION` (`ACTION_`),
  KEY `FK_JOB_TOKEN` (`TOKEN_`),
  KEY `FK_JOB_NODE` (`NODE_`),
  KEY `FK_JOB_TSKINST` (`TASKINSTANCE_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_JOB`
--

LOCK TABLES `JBPM_JOB` WRITE;
/*!40000 ALTER TABLE `JBPM_JOB` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_JOB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_LOG`
--

DROP TABLE IF EXISTS `JBPM_LOG`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_LOG` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `INDEX_` int(11) default NULL,
  `DATE_` datetime default NULL,
  `TOKEN_` bigint(20) default NULL,
  `PARENT_` bigint(20) default NULL,
  `MESSAGE_` varchar(4000) default NULL,
  `EXCEPTION_` varchar(4000) default NULL,
  `ACTION_` bigint(20) default NULL,
  `CHILD_` bigint(20) default NULL,
  `TRANSITION_` bigint(20) default NULL,
  `NODE_` bigint(20) default NULL,
  `ENTER_` datetime default NULL,
  `LEAVE_` datetime default NULL,
  `DURATION_` bigint(20) default NULL,
  `SOURCENODE_` bigint(20) default NULL,
  `DESTINATIONNODE_` bigint(20) default NULL,
  `NEWLONGVALUE_` bigint(20) default NULL,
  `TASKINSTANCE_` bigint(20) default NULL,
  `TASKACTORID_` varchar(255) default NULL,
  `SWIMLANEINSTANCE_` bigint(20) default NULL,
  `TASKOLDACTORID_` varchar(255) default NULL,
  `VARIABLEINSTANCE_` bigint(20) default NULL,
  `OLDLONGIDCLASS_` varchar(255) default NULL,
  `OLDLONGIDVALUE_` bigint(20) default NULL,
  `NEWLONGIDCLASS_` varchar(255) default NULL,
  `NEWLONGIDVALUE_` bigint(20) default NULL,
  `OLDDATEVALUE_` datetime default NULL,
  `NEWDATEVALUE_` datetime default NULL,
  `OLDDOUBLEVALUE_` double default NULL,
  `NEWDOUBLEVALUE_` double default NULL,
  `OLDBYTEARRAY_` bigint(20) default NULL,
  `NEWBYTEARRAY_` bigint(20) default NULL,
  `OLDSTRINGVALUE_` varchar(4000) default NULL,
  `NEWSTRINGVALUE_` varchar(4000) default NULL,
  `OLDSTRINGIDCLASS_` varchar(255) default NULL,
  `OLDSTRINGIDVALUE_` varchar(255) default NULL,
  `NEWSTRINGIDCLASS_` varchar(255) default NULL,
  `NEWSTRINGIDVALUE_` varchar(255) default NULL,
  `OLDLONGVALUE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_LOG_SOURCENODE` (`SOURCENODE_`),
  KEY `FK_LOG_DESTNODE` (`DESTINATIONNODE_`),
  KEY `FK_LOG_TOKEN` (`TOKEN_`),
  KEY `FK_LOG_TRANSITION` (`TRANSITION_`),
  KEY `FK_LOG_TASKINST` (`TASKINSTANCE_`),
  KEY `FK_LOG_CHILDTOKEN` (`CHILD_`),
  KEY `FK_LOG_OLDBYTES` (`OLDBYTEARRAY_`),
  KEY `FK_LOG_SWIMINST` (`SWIMLANEINSTANCE_`),
  KEY `FK_LOG_NEWBYTES` (`NEWBYTEARRAY_`),
  KEY `FK_LOG_ACTION` (`ACTION_`),
  KEY `FK_LOG_VARINST` (`VARIABLEINSTANCE_`),
  KEY `FK_LOG_NODE` (`NODE_`),
  KEY `FK_LOG_PARENT` (`PARENT_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_LOG`
--

LOCK TABLES `JBPM_LOG` WRITE;
/*!40000 ALTER TABLE `JBPM_LOG` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_LOG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_MODULEDEFINITION`
--

DROP TABLE IF EXISTS `JBPM_MODULEDEFINITION`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_MODULEDEFINITION` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `NAME_` varchar(4000) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `STARTTASK_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_MODDEF_PROCDF` (`PROCESSDEFINITION_`),
  KEY `FK_MODDEF_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_TSKDEF_START` (`STARTTASK_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_MODULEDEFINITION`
--

LOCK TABLES `JBPM_MODULEDEFINITION` WRITE;
/*!40000 ALTER TABLE `JBPM_MODULEDEFINITION` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_MODULEDEFINITION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_MODULEINSTANCE`
--

DROP TABLE IF EXISTS `JBPM_MODULEINSTANCE`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_MODULEINSTANCE` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `PROCESSINSTANCE_` bigint(20) default NULL,
  `TASKMGMTDEFINITION_` bigint(20) default NULL,
  `NAME_` varchar(255) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_MODINST_PRINST` (`PROCESSINSTANCE_`),
  KEY `FK_MODINST_PRCINST` (`PROCESSINSTANCE_`),
  KEY `FK_TASKMGTINST_TMD` (`TASKMGMTDEFINITION_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_MODULEINSTANCE`
--

LOCK TABLES `JBPM_MODULEINSTANCE` WRITE;
/*!40000 ALTER TABLE `JBPM_MODULEINSTANCE` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_MODULEINSTANCE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_NODE`
--

DROP TABLE IF EXISTS `JBPM_NODE`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_NODE` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `DESCRIPTION_` varchar(4000) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `ISASYNC_` bit(1) default NULL,
  `ISASYNCEXCL_` bit(1) default NULL,
  `ACTION_` bigint(20) default NULL,
  `SUPERSTATE_` bigint(20) default NULL,
  `SIGNAL_` int(11) default NULL,
  `CREATETASKS_` bit(1) default NULL,
  `ENDTASKS_` bit(1) default NULL,
  `PARENTLOCKMODE_` varchar(255) default NULL,
  `SCRIPT_` bigint(20) default NULL,
  `SUBPROCNAME_` varchar(255) default NULL,
  `DECISIONEXPRESSION_` varchar(255) default NULL,
  `DECISIONDELEGATION` bigint(20) default NULL,
  `SUBPROCESSDEFINITION_` bigint(20) default NULL,
  `NODECOLLECTIONINDEX_` int(11) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_PSTATE_SBPRCDEF` (`SUBPROCESSDEFINITION_`),
  KEY `IDX_NODE_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `IDX_NODE_ACTION` (`ACTION_`),
  KEY `IDX_NODE_SUPRSTATE` (`SUPERSTATE_`),
  KEY `FK_DECISION_DELEG` (`DECISIONDELEGATION`),
  KEY `FK_NODE_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_NODE_ACTION` (`ACTION_`),
  KEY `FK_PROCST_SBPRCDEF` (`SUBPROCESSDEFINITION_`),
  KEY `FK_NODE_SCRIPT` (`SCRIPT_`),
  KEY `FK_NODE_SUPERSTATE` (`SUPERSTATE_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_NODE`
--

LOCK TABLES `JBPM_NODE` WRITE;
/*!40000 ALTER TABLE `JBPM_NODE` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_NODE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_POOLEDACTOR`
--

DROP TABLE IF EXISTS `JBPM_POOLEDACTOR`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_POOLEDACTOR` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `VERSION_` int(11) NOT NULL,
  `ACTORID_` varchar(255) default NULL,
  `SWIMLANEINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_TSKINST_SWLANE` (`SWIMLANEINSTANCE_`),
  KEY `IDX_PLDACTR_ACTID` (`ACTORID_`),
  KEY `FK_POOLEDACTOR_SLI` (`SWIMLANEINSTANCE_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_POOLEDACTOR`
--

LOCK TABLES `JBPM_POOLEDACTOR` WRITE;
/*!40000 ALTER TABLE `JBPM_POOLEDACTOR` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_POOLEDACTOR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_PROCESSDEFINITION`
--

DROP TABLE IF EXISTS `JBPM_PROCESSDEFINITION`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_PROCESSDEFINITION` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `DESCRIPTION_` varchar(4000) default NULL,
  `VERSION_` int(11) default NULL,
  `ISTERMINATIONIMPLICIT_` bit(1) default NULL,
  `STARTSTATE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_PROCDEF_STRTST` (`STARTSTATE_`),
  KEY `FK_PROCDEF_STRTSTA` (`STARTSTATE_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_PROCESSDEFINITION`
--

LOCK TABLES `JBPM_PROCESSDEFINITION` WRITE;
/*!40000 ALTER TABLE `JBPM_PROCESSDEFINITION` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_PROCESSDEFINITION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_PROCESSINSTANCE`
--

DROP TABLE IF EXISTS `JBPM_PROCESSINSTANCE`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_PROCESSINSTANCE` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `VERSION_` int(11) NOT NULL,
  `KEY_` varchar(255) default NULL,
  `START_` datetime default NULL,
  `END_` datetime default NULL,
  `ISSUSPENDED_` bit(1) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `ROOTTOKEN_` bigint(20) default NULL,
  `SUPERPROCESSTOKEN_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_PROCIN_SPROCTK` (`SUPERPROCESSTOKEN_`),
  KEY `IDX_PROCIN_ROOTTK` (`ROOTTOKEN_`),
  KEY `IDX_PROCIN_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `IDX_PROCIN_KEY` (`KEY_`),
  KEY `FK_PROCIN_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_PROCIN_ROOTTKN` (`ROOTTOKEN_`),
  KEY `FK_PROCIN_SPROCTKN` (`SUPERPROCESSTOKEN_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_PROCESSINSTANCE`
--

LOCK TABLES `JBPM_PROCESSINSTANCE` WRITE;
/*!40000 ALTER TABLE `JBPM_PROCESSINSTANCE` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_PROCESSINSTANCE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_RUNTIMEACTION`
--

DROP TABLE IF EXISTS `JBPM_RUNTIMEACTION`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_RUNTIMEACTION` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `VERSION_` int(11) NOT NULL,
  `EVENTTYPE_` varchar(255) default NULL,
  `TYPE_` char(1) default NULL,
  `GRAPHELEMENT_` bigint(20) default NULL,
  `PROCESSINSTANCE_` bigint(20) default NULL,
  `ACTION_` bigint(20) default NULL,
  `PROCESSINSTANCEINDEX_` int(11) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_RTACTN_ACTION` (`ACTION_`),
  KEY `IDX_RTACTN_PRCINST` (`PROCESSINSTANCE_`),
  KEY `FK_RTACTN_PROCINST` (`PROCESSINSTANCE_`),
  KEY `FK_RTACTN_ACTION` (`ACTION_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_RUNTIMEACTION`
--

LOCK TABLES `JBPM_RUNTIMEACTION` WRITE;
/*!40000 ALTER TABLE `JBPM_RUNTIMEACTION` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_RUNTIMEACTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_SWIMLANE`
--

DROP TABLE IF EXISTS `JBPM_SWIMLANE`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_SWIMLANE` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `NAME_` varchar(255) default NULL,
  `ACTORIDEXPRESSION_` varchar(255) default NULL,
  `POOLEDACTORSEXPRESSION_` varchar(255) default NULL,
  `ASSIGNMENTDELEGATION_` bigint(20) default NULL,
  `TASKMGMTDEFINITION_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_SWL_ASSDEL` (`ASSIGNMENTDELEGATION_`),
  KEY `FK_SWL_TSKMGMTDEF` (`TASKMGMTDEFINITION_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_SWIMLANE`
--

LOCK TABLES `JBPM_SWIMLANE` WRITE;
/*!40000 ALTER TABLE `JBPM_SWIMLANE` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_SWIMLANE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_SWIMLANEINSTANCE`
--

DROP TABLE IF EXISTS `JBPM_SWIMLANEINSTANCE`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_SWIMLANEINSTANCE` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `VERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `ACTORID_` varchar(255) default NULL,
  `SWIMLANE_` bigint(20) default NULL,
  `TASKMGMTINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_SWIMLINST_SL` (`SWIMLANE_`),
  KEY `FK_SWIMLANEINST_TM` (`TASKMGMTINSTANCE_`),
  KEY `FK_SWIMLANEINST_SL` (`SWIMLANE_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_SWIMLANEINSTANCE`
--

LOCK TABLES `JBPM_SWIMLANEINSTANCE` WRITE;
/*!40000 ALTER TABLE `JBPM_SWIMLANEINSTANCE` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_SWIMLANEINSTANCE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_TASK`
--

DROP TABLE IF EXISTS `JBPM_TASK`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_TASK` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `NAME_` varchar(255) default NULL,
  `DESCRIPTION_` varchar(4000) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `ISBLOCKING_` bit(1) default NULL,
  `ISSIGNALLING_` bit(1) default NULL,
  `CONDITION_` varchar(255) default NULL,
  `DUEDATE_` varchar(255) default NULL,
  `PRIORITY_` int(11) default NULL,
  `ACTORIDEXPRESSION_` varchar(255) default NULL,
  `POOLEDACTORSEXPRESSION_` varchar(255) default NULL,
  `TASKMGMTDEFINITION_` bigint(20) default NULL,
  `TASKNODE_` bigint(20) default NULL,
  `STARTSTATE_` bigint(20) default NULL,
  `ASSIGNMENTDELEGATION_` bigint(20) default NULL,
  `SWIMLANE_` bigint(20) default NULL,
  `TASKCONTROLLER_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_TASK_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `IDX_TASK_TSKNODE` (`TASKNODE_`),
  KEY `IDX_TASK_TASKMGTDF` (`TASKMGMTDEFINITION_`),
  KEY `FK_TASK_STARTST` (`STARTSTATE_`),
  KEY `FK_TASK_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_TASK_ASSDEL` (`ASSIGNMENTDELEGATION_`),
  KEY `FK_TASK_SWIMLANE` (`SWIMLANE_`),
  KEY `FK_TASK_TASKNODE` (`TASKNODE_`),
  KEY `FK_TASK_TASKMGTDEF` (`TASKMGMTDEFINITION_`),
  KEY `FK_TSK_TSKCTRL` (`TASKCONTROLLER_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_TASK`
--

LOCK TABLES `JBPM_TASK` WRITE;
/*!40000 ALTER TABLE `JBPM_TASK` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_TASK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_TASKACTORPOOL`
--

DROP TABLE IF EXISTS `JBPM_TASKACTORPOOL`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_TASKACTORPOOL` (
  `POOLEDACTOR_` bigint(20) NOT NULL,
  `TASKINSTANCE_` bigint(20) NOT NULL,
  PRIMARY KEY  (`TASKINSTANCE_`,`POOLEDACTOR_`),
  KEY `FK_TASKACTPL_TSKI` (`TASKINSTANCE_`),
  KEY `FK_TSKACTPOL_PLACT` (`POOLEDACTOR_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_TASKACTORPOOL`
--

LOCK TABLES `JBPM_TASKACTORPOOL` WRITE;
/*!40000 ALTER TABLE `JBPM_TASKACTORPOOL` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_TASKACTORPOOL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_TASKCONTROLLER`
--

DROP TABLE IF EXISTS `JBPM_TASKCONTROLLER`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_TASKCONTROLLER` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `TASKCONTROLLERDELEGATION_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_TSKCTRL_DELEG` (`TASKCONTROLLERDELEGATION_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_TASKCONTROLLER`
--

LOCK TABLES `JBPM_TASKCONTROLLER` WRITE;
/*!40000 ALTER TABLE `JBPM_TASKCONTROLLER` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_TASKCONTROLLER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_TASKINSTANCE`
--

DROP TABLE IF EXISTS `JBPM_TASKINSTANCE`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_TASKINSTANCE` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `DESCRIPTION_` varchar(4000) default NULL,
  `ACTORID_` varchar(255) default NULL,
  `CREATE_` datetime default NULL,
  `START_` datetime default NULL,
  `END_` datetime default NULL,
  `DUEDATE_` datetime default NULL,
  `PRIORITY_` int(11) default NULL,
  `ISCANCELLED_` bit(1) default NULL,
  `ISSUSPENDED_` bit(1) default NULL,
  `ISOPEN_` bit(1) default NULL,
  `ISSIGNALLING_` bit(1) default NULL,
  `ISBLOCKING_` bit(1) default NULL,
  `TASK_` bigint(20) default NULL,
  `TOKEN_` bigint(20) default NULL,
  `PROCINST_` bigint(20) default NULL,
  `SWIMLANINSTANCE_` bigint(20) default NULL,
  `TASKMGMTINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_TSKINST_TMINST` (`TASKMGMTINSTANCE_`),
  KEY `IDX_TSKINST_SLINST` (`SWIMLANINSTANCE_`),
  KEY `IDX_TASKINST_TOKN` (`TOKEN_`),
  KEY `IDX_TASK_ACTORID` (`ACTORID_`),
  KEY `IDX_TASKINST_TSK` (`TASK_`,`PROCINST_`),
  KEY `FK_TSKINS_PRCINS` (`PROCINST_`),
  KEY `FK_TASKINST_TMINST` (`TASKMGMTINSTANCE_`),
  KEY `FK_TASKINST_TOKEN` (`TOKEN_`),
  KEY `FK_TASKINST_SLINST` (`SWIMLANINSTANCE_`),
  KEY `FK_TASKINST_TASK` (`TASK_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_TASKINSTANCE`
--

LOCK TABLES `JBPM_TASKINSTANCE` WRITE;
/*!40000 ALTER TABLE `JBPM_TASKINSTANCE` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_TASKINSTANCE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_TOKEN`
--

DROP TABLE IF EXISTS `JBPM_TOKEN`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_TOKEN` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `VERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `START_` datetime default NULL,
  `END_` datetime default NULL,
  `NODEENTER_` datetime default NULL,
  `NEXTLOGINDEX_` int(11) default NULL,
  `ISABLETOREACTIVATEPARENT_` bit(1) default NULL,
  `ISTERMINATIONIMPLICIT_` bit(1) default NULL,
  `ISSUSPENDED_` bit(1) default NULL,
  `LOCK_` varchar(255) default NULL,
  `NODE_` bigint(20) default NULL,
  `PROCESSINSTANCE_` bigint(20) default NULL,
  `PARENT_` bigint(20) default NULL,
  `SUBPROCESSINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_TOKEN_PARENT` (`PARENT_`),
  KEY `IDX_TOKEN_PROCIN` (`PROCESSINSTANCE_`),
  KEY `IDX_TOKEN_NODE` (`NODE_`),
  KEY `IDX_TOKEN_SUBPI` (`SUBPROCESSINSTANCE_`),
  KEY `FK_TOKEN_SUBPI` (`SUBPROCESSINSTANCE_`),
  KEY `FK_TOKEN_PROCINST` (`PROCESSINSTANCE_`),
  KEY `FK_TOKEN_NODE` (`NODE_`),
  KEY `FK_TOKEN_PARENT` (`PARENT_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_TOKEN`
--

LOCK TABLES `JBPM_TOKEN` WRITE;
/*!40000 ALTER TABLE `JBPM_TOKEN` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_TOKEN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_TOKENVARIABLEMAP`
--

DROP TABLE IF EXISTS `JBPM_TOKENVARIABLEMAP`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_TOKENVARIABLEMAP` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `VERSION_` int(11) NOT NULL,
  `TOKEN_` bigint(20) default NULL,
  `CONTEXTINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_TKVVARMP_TOKEN` (`TOKEN_`),
  KEY `IDX_TKVARMAP_CTXT` (`CONTEXTINSTANCE_`),
  KEY `FK_TKVARMAP_TOKEN` (`TOKEN_`),
  KEY `FK_TKVARMAP_CTXT` (`CONTEXTINSTANCE_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_TOKENVARIABLEMAP`
--

LOCK TABLES `JBPM_TOKENVARIABLEMAP` WRITE;
/*!40000 ALTER TABLE `JBPM_TOKENVARIABLEMAP` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_TOKENVARIABLEMAP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_TRANSITION`
--

DROP TABLE IF EXISTS `JBPM_TRANSITION`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_TRANSITION` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `NAME_` varchar(255) default NULL,
  `DESCRIPTION_` varchar(4000) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `FROM_` bigint(20) default NULL,
  `TO_` bigint(20) default NULL,
  `CONDITION_` varchar(255) default NULL,
  `FROMINDEX_` int(11) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_TRANS_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `IDX_TRANSIT_FROM` (`FROM_`),
  KEY `IDX_TRANSIT_TO` (`TO_`),
  KEY `FK_TRANSITION_FROM` (`FROM_`),
  KEY `FK_TRANS_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_TRANSITION_TO` (`TO_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_TRANSITION`
--

LOCK TABLES `JBPM_TRANSITION` WRITE;
/*!40000 ALTER TABLE `JBPM_TRANSITION` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_TRANSITION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_VARIABLEACCESS`
--

DROP TABLE IF EXISTS `JBPM_VARIABLEACCESS`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_VARIABLEACCESS` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `VARIABLENAME_` varchar(255) default NULL,
  `ACCESS_` varchar(255) default NULL,
  `MAPPEDNAME_` varchar(255) default NULL,
  `TASKCONTROLLER_` bigint(20) default NULL,
  `INDEX_` int(11) default NULL,
  `SCRIPT_` bigint(20) default NULL,
  `PROCESSSTATE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_VARACC_PROCST` (`PROCESSSTATE_`),
  KEY `FK_VARACC_SCRIPT` (`SCRIPT_`),
  KEY `FK_VARACC_TSKCTRL` (`TASKCONTROLLER_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_VARIABLEACCESS`
--

LOCK TABLES `JBPM_VARIABLEACCESS` WRITE;
/*!40000 ALTER TABLE `JBPM_VARIABLEACCESS` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_VARIABLEACCESS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JBPM_VARIABLEINSTANCE`
--

DROP TABLE IF EXISTS `JBPM_VARIABLEINSTANCE`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `JBPM_VARIABLEINSTANCE` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `CONVERTER_` char(1) default NULL,
  `TOKEN_` bigint(20) default NULL,
  `TOKENVARIABLEMAP_` bigint(20) default NULL,
  `PROCESSINSTANCE_` bigint(20) default NULL,
  `BYTEARRAYVALUE_` bigint(20) default NULL,
  `STRINGIDCLASS_` varchar(255) default NULL,
  `STRINGVALUE_` varchar(4000) default NULL,
  `LONGIDCLASS_` varchar(255) default NULL,
  `LONGVALUE_` bigint(20) default NULL,
  `DATEVALUE_` datetime default NULL,
  `DOUBLEVALUE_` double default NULL,
  `TASKINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_VARINST_TK` (`TOKEN_`),
  KEY `IDX_VARINST_TKVARMP` (`TOKENVARIABLEMAP_`),
  KEY `IDX_VARINST_PRCINS` (`PROCESSINSTANCE_`),
  KEY `FK_VARINST_PRCINST` (`PROCESSINSTANCE_`),
  KEY `FK_VARINST_TKVARMP` (`TOKENVARIABLEMAP_`),
  KEY `FK_VARINST_TK` (`TOKEN_`),
  KEY `FK_BYTEINST_ARRAY` (`BYTEARRAYVALUE_`),
  KEY `FK_VAR_TSKINST` (`TASKINSTANCE_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `JBPM_VARIABLEINSTANCE`
--

LOCK TABLES `JBPM_VARIABLEINSTANCE` WRITE;
/*!40000 ALTER TABLE `JBPM_VARIABLEINSTANCE` DISABLE KEYS */;
/*!40000 ALTER TABLE `JBPM_VARIABLEINSTANCE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c_address`
--

DROP TABLE IF EXISTS `c_address`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `c_address` (
  `c_id` bigint(20) NOT NULL auto_increment,
  `c_create` datetime default NULL,
  `c_enabled` bit(1) default NULL,
  `msn` varchar(20) default NULL,
  `phone` varchar(20) default NULL,
  `qq` varchar(20) default NULL,
  `user_id` bigint(20) default NULL,
  PRIMARY KEY  (`c_id`),
  KEY `FK9E9E3018BBC88186` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `c_address`
--

LOCK TABLES `c_address` WRITE;
/*!40000 ALTER TABLE `c_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `c_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c_authors`
--

DROP TABLE IF EXISTS `c_authors`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `c_authors` (
  `c_id` bigint(20) NOT NULL auto_increment,
  `c_create` datetime default NULL,
  `c_enabled` bit(1) default NULL,
  `c_name` varchar(20) NOT NULL,
  `c_nickname` varchar(20) NOT NULL,
  PRIMARY KEY  (`c_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `c_authors`
--

LOCK TABLES `c_authors` WRITE;
/*!40000 ALTER TABLE `c_authors` DISABLE KEYS */;
INSERT INTO `c_authors` VALUES (1,NULL,'','AUTH_VIEW_USER','用户'),(2,NULL,'','AUTH_MODIFY_USER','用户修改'),(3,NULL,'','AUTH_VIEW_ROLE','角色'),(4,NULL,'','AUTH_MODIFY_ROLE','角色修改');
/*!40000 ALTER TABLE `c_authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c_files`
--

DROP TABLE IF EXISTS `c_files`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `c_files` (
  `c_id` bigint(20) NOT NULL auto_increment,
  `c_create` datetime default NULL,
  `c_enabled` bit(1) default NULL,
  `c_done` bit(1) default NULL,
  `c_filetype` varchar(80) NOT NULL,
  `c_name` varchar(120) NOT NULL,
  `c_path` varchar(240) NOT NULL,
  `c_type` varchar(30) NOT NULL,
  PRIMARY KEY  (`c_id`),
  UNIQUE KEY `c_name` (`c_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `c_files`
--

LOCK TABLES `c_files` WRITE;
/*!40000 ALTER TABLE `c_files` DISABLE KEYS */;
/*!40000 ALTER TABLE `c_files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c_role_auths`
--

DROP TABLE IF EXISTS `c_role_auths`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `c_role_auths` (
  `role_id` bigint(20) NOT NULL,
  `authority_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`role_id`,`authority_id`),
  KEY `FKA670541E169DBDA6` (`role_id`),
  KEY `FKA670541E2F09CAAE` (`authority_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `c_role_auths`
--

LOCK TABLES `c_role_auths` WRITE;
/*!40000 ALTER TABLE `c_role_auths` DISABLE KEYS */;
INSERT INTO `c_role_auths` VALUES (1,1),(1,2),(1,3),(1,4),(2,1),(2,3);
/*!40000 ALTER TABLE `c_role_auths` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c_roles`
--

DROP TABLE IF EXISTS `c_roles`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `c_roles` (
  `c_id` bigint(20) NOT NULL auto_increment,
  `c_create` datetime default NULL,
  `c_enabled` bit(1) default NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY  (`c_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `c_roles`
--

LOCK TABLES `c_roles` WRITE;
/*!40000 ALTER TABLE `c_roles` DISABLE KEYS */;
INSERT INTO `c_roles` VALUES (1,NULL,'','管理员'),(2,NULL,'','用户'),(3,NULL,'','员工'),(4,NULL,'','主管'),(5,NULL,'','副主管'),(6,NULL,'','经理'),(7,NULL,'','副经理');
/*!40000 ALTER TABLE `c_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c_user_roles`
--

DROP TABLE IF EXISTS `c_user_roles`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `c_user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`user_id`,`role_id`),
  KEY `FKADD3E2A5169DBDA6` (`role_id`),
  KEY `FKADD3E2A5BBC88186` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `c_user_roles`
--

LOCK TABLES `c_user_roles` WRITE;
/*!40000 ALTER TABLE `c_user_roles` DISABLE KEYS */;
INSERT INTO `c_user_roles` VALUES (1,1),(1,2),(2,2),(3,2),(4,2),(5,2),(6,2);
/*!40000 ALTER TABLE `c_user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c_users`
--

DROP TABLE IF EXISTS `c_users`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `c_users` (
  `c_id` bigint(20) NOT NULL auto_increment,
  `c_create` datetime default NULL,
  `c_enabled` bit(1) default NULL,
  `accountNonExpired` bit(1) default NULL,
  `accountNonLocked` bit(1) default NULL,
  `credentialsNonExpired` bit(1) default NULL,
  `email` varchar(80) NOT NULL,
  `c_intro` longtext,
  `c_name` varchar(20) NOT NULL,
  `c_passwd` varchar(32) NOT NULL,
  `c_salary` double default NULL,
  PRIMARY KEY  (`c_id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `c_name` (`c_name`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `c_users`
--

LOCK TABLES `c_users` WRITE;
/*!40000 ALTER TABLE `c_users` DISABLE KEYS */;
INSERT INTO `c_users` VALUES (1,NULL,'','','','','springclick@gmail.com',NULL,'Admin','e10adc3949ba59abbe56\ne057f20f883',NULL),(2,NULL,'','','','','user@super.com',NULL,'User','e10adc3949ba59abbe56\ne057f20f883',NULL),(3,NULL,'','','','','cat@super.com',NULL,'cat','e10adc3949ba59abbe56\ne057f20f883',NULL),(4,NULL,'','','','','dog@super.com',NULL,'dog','e10adc3949ba59abbe56\ne057f20f883',NULL),(5,NULL,'','','','','fish@super.com',NULL,'fish','e10adc3949ba59abbe56\ne057f20f883',NULL),(6,NULL,'','','','','pig@super.com',NULL,'pig','e10adc3949ba59abbe56\ne057f20f883',NULL);
/*!40000 ALTER TABLE `c_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `w_orig_appr`
--

DROP TABLE IF EXISTS `w_orig_appr`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `w_orig_appr` (
  `c_id` bigint(20) NOT NULL auto_increment,
  `c_create` datetime default NULL,
  `c_enabled` bit(1) default NULL,
  `remark` longtext,
  `name` varchar(180) NOT NULL,
  `start` datetime default NULL,
  `orig_id` bigint(20) default NULL,
  `user_id` bigint(20) default NULL,
  PRIMARY KEY  (`c_id`),
  KEY `FKD11C27E7BBC88186` (`user_id`),
  KEY `FKD11C27E7C9C3D213` (`orig_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `w_orig_appr`
--

LOCK TABLES `w_orig_appr` WRITE;
/*!40000 ALTER TABLE `w_orig_appr` DISABLE KEYS */;
/*!40000 ALTER TABLE `w_orig_appr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `w_orig_cert`
--

DROP TABLE IF EXISTS `w_orig_cert`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `w_orig_cert` (
  `c_id` bigint(20) NOT NULL auto_increment,
  `c_create` datetime default NULL,
  `c_enabled` bit(1) default NULL,
  `done` bit(1) default NULL,
  `remark` longtext,
  `start` datetime default NULL,
  `title` varchar(180) NOT NULL,
  `user_id` bigint(20) default NULL,
  PRIMARY KEY  (`c_id`),
  KEY `FKD11CE79ABBC88186` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `w_orig_cert`
--

LOCK TABLES `w_orig_cert` WRITE;
/*!40000 ALTER TABLE `w_orig_cert` DISABLE KEYS */;
/*!40000 ALTER TABLE `w_orig_cert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `w_orig_item`
--

DROP TABLE IF EXISTS `w_orig_item`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `w_orig_item` (
  `c_id` bigint(20) NOT NULL auto_increment,
  `c_create` datetime default NULL,
  `c_enabled` bit(1) default NULL,
  `money` double default NULL,
  `name` varchar(180) NOT NULL,
  `orig_id` bigint(20) default NULL,
  PRIMARY KEY  (`c_id`),
  KEY `FKD11FD889C9C3D213` (`orig_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `w_orig_item`
--

LOCK TABLES `w_orig_item` WRITE;
/*!40000 ALTER TABLE `w_orig_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `w_orig_item` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2009-04-17 12:32:59
