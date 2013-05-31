/*******************************************
Name          : tbl_project_workspace

Description   : Stores the project and workspace details.

Called By     : 

Create By     : Kiran Kumar Batna

Modified Date : 05/30/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_project_workspace
(
  projectid       VARCHAR(100)  NOT NULL REFERENCES tbl_project(projectid),
  workspaceid     INT           NOT NULL REFERENCES tbl_workspace(id),
  updatedby       VARCHAR(10)   NOT NULL,
  updateddate     TIMESTAMP     NOT NULL,
  createdby       VARCHAR(10)   NOT NULL,
  createddate     DATETIME      NOT NULL,
  PRIMARY KEY(projectid,workspaceid) 
)