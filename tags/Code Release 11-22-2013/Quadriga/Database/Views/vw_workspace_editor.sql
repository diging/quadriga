/*******************************************
Name          : vw_workspace_editor

Description   : retrieves associated network details of workspace

Called By     : 

Create By     : Kiran Kumar Batna

Modified Date : 10/30/2013

********************************************/
DROP VIEW IF EXISTS vw_workspace_editor;

CREATE VIEW vw_workspace_editor(workspaceid,user)
AS
     SELECT workspaceid,owner
      FROM tbl_workspace_editor;