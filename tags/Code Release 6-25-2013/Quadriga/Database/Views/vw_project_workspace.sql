/*******************************************
Name          : vw_project_workspace

Description   : Retrieves the project and workspace details.

Called By     : 

Create By     : Kiran Kumar Batna

Modified Date : 05/30/2013

********************************************/
DROP VIEW IF EXISTS vw_project_workspace;

CREATE VIEW vw_project_workspace(projectid,workspaceid)
AS
  SELECT projectid,workspaceid
    FROM tbl_project_workspace;

