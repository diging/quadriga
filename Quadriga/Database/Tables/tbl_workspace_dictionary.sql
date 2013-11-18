/*******************************************
Name          : tbl_workspace_dictionary

Description   : Adds dictionary to the workspace details.

Called By     : 

Create By     : Lohith Dwaraka

Modified Date : 07/11/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_workspace_dictionary
(
  workspaceid       VARCHAR(150)   NOT NULL,
  dictionaryid   varchar(50)   NOT NULL,
  updatedby       VARCHAR(20)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(20)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(workspaceid,dictionaryid)
)