/*******************************************
Name          : vw_workspace_collaborator

Description   : Retrieves the workspace collaborator details.

Called By     : 

Create By     : Kiran Kumar Batna

Modified Date : 05/30/2013

********************************************/
DROP VIEW IF EXISTS vw_workspace_collaborator;

CREATE VIEW vw_workspace_collaborator(workspaceid,collaboratoruser,collaboratorrole)
AS
  SELECT workspaceid,collaboratoruser,collaboratorrole
    FROM tbl_workspace_collaborator;

 
