/*******************************************
Name          : vw_workspace

Description   : View for workspace table.

Called By     : 

Create By     : Kiran Kumar Batna

Modified Date : 06/20/2013

********************************************/
DROP VIEW IF EXISTS vw_workspace;

CREATE VIEW vw_workspace(workspacename,description,workspaceid,
                         workspaceowner,isarchived,isdeactivated)
AS
SELECT workspacename,description,workspaceid,
       workspaceowner,isarchived,isdeactivated 
FROM tbl_workspace;
