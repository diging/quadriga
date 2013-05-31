/*******************************************
Name          : tbl_workspace_collaborator

Description   : Stores the workspace collaborator details.

Called By     : 

Create By     : Kiran Kumar Batna

Modified Date : 05/30/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_workspace_collaborator
(
  id       INT   REFERENCES tbl_workspace(id),
  username VARCHAR(20) REFERENCES tbl_quadriga_user(username),
  collaboratorrole VARCHAR(20),
  updatedby       VARCHAR(10)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(10)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(id,username,collaboratorrole)
)