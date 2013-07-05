/*******************************************
Name          : tbl_project_dictionary

Description   : Stores the project details.

Called By     : sp_getProjectsList

Create By     : Kiran Kumar Batna

Modified Date : 05/24/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_project_dictionary
(
  projectid       VARCHAR(50)   NOT NULL,
  dictionaryid   int   NOT NULL,
  updatedby       VARCHAR(20)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(20)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(projectid,dictionaryid)
)