/*******************************************
Name          : tbl_project_dictionary

Description   : Adds dictionary to the project details.

Called By     : 

Create By     : Lohith Dwaraka

Modified Date : 07/11/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_project_dictionary
(
  projectid       VARCHAR(50)   NOT NULL,
  dictionaryid   varchar(50)   NOT NULL,
  updatedby       VARCHAR(20)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(20)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(projectid,dictionaryid)
)