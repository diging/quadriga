/*******************************************
Name          : tbl_workspace

Description   : Stores the workspace details.

Called By     : 

Create By     : Kiran Kumar Batna

Modified Date : 05/30/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_workspace
(
  workspacename   VARCHAR(50)   NOT NULL,
  description     TEXT          NULL,
  workspaceid     INT           NOT NULL AUTO_INCREMENT,
  workspaceowner  VARCHAR(50) NOT NULL,
  updatedby       VARCHAR(10)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(10)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(workspaceid),UNIQUE KEY(workspacename)
)