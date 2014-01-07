/*******************************************
Name          : vw_project_editor

Description   : retrieves associated network details of project

Called By     : 

Create By     : Kiran Kumar Batna

Modified Date : 10/30/2013

********************************************/
DROP VIEW IF EXISTS vw_project_editor;

CREATE VIEW vw_project_editor(projectid,editor)
AS
     SELECT projectid,editor
      FROM tbl_project_editor;