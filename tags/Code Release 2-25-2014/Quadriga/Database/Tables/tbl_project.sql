/*******************************************
Name          : tbl_project

Description   : Stores the project details.

Called By     : sp_getProjectsList

Create By     : Kiran Kumar Batna

Modified Date : 05/24/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_project
(
  projectname     VARCHAR(50)   NOT NULL,
  description     TEXT          NULL,
  unixname        VARCHAR(50)   NOT NULL,
  projectid       VARCHAR(100)  NOT NULL,
  projectowner    VARCHAR(50)   NOT NULL,
  accessibility   VARCHAR(50)   NOT NULL,
  updatedby       VARCHAR(10)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(10)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(projectid),UNIQUE KEY(unixname) 
)