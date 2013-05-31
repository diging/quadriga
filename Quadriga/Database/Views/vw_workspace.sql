/*******************************************
Name          : vw_workspace

Description   : View on workspace details.

Called By     : 

Create By     : Kiran Kumar Batna

Modified Date : 05/30/2013

********************************************/
DROP VIEW IF EXISTS vw_workspace;

CREATE VIEW vw_workspace(workspacename,description,id,workspaceowner)
AS
  SELECT workspacename,description,id,workspaceowner
    FROM tbl_workspace;

