/***********************************************************************************************************************
  File: V1473790400__session_create_table_r_student_key.sql.sql

  Desc: Create the r_studentkey table that is leveraged by the student application

***********************************************************************************************************************/

USE session;

DROP TABLE IF EXISTS r_studentkeyid;
CREATE TABLE r_studentkeyid (
  studentkey bigint(20) NOT NULL AUTO_INCREMENT,
  studentid varchar(50) NOT NULL,
  statecode varchar(7) NOT NULL,
  clientname varchar(100) NOT NULL,
  PRIMARY KEY (studentkey),
  UNIQUE KEY ix_studentid_statecode (studentid,statecode)
);