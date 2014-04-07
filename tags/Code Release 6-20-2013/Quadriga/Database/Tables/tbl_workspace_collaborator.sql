/*******************************************
Name          : tbl_workspace_collaborator

Description   : Stores the workspace collaborator details.

Called By     : 

Create By     : Kiran Kumar Batna

Modified Date : 05/30/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_workspace_collaborator
(
  id       INT   ,
  username VARCHAR(20) ,
  collaboratorrole VARCHAR(20),
  updatedby       VARCHAR(10)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(10)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(id,username,collaboratorrole) 
)