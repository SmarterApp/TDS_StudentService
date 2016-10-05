/***********************************************************************************************************************
  File: V1475687021__session_create_r_studentpackage.sql

  Desc: Creates the r_studentpackage table.

***********************************************************************************************************************/
USE session;

DROP TABLE IF EXISTS `r_studentpackage`;
CREATE TABLE `r_studentpackage` (
  `_key` bigint(20) NOT NULL AUTO_INCREMENT,
  `studentkey` bigint(20) NOT NULL,
  `clientname` varchar(100) NOT NULL,
  `package` blob NOT NULL,
  `version` varchar(4) NOT NULL,
  `iscurrent` bit(1) DEFAULT b'1',
  `datecreated` datetime(3) NOT NULL,
  `dateend` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`_key`),
  UNIQUE KEY `ix_r_studentpackage_studentkey_clientname` (`studentkey`,`clientname`),
  CONSTRAINT `fk_r_studentpackage_r_studentkeyid` FOREIGN KEY (`studentkey`) REFERENCES `r_studentkeyid` (`studentkey`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;