/*******************************************
Name          : vw_project_collaborator

Description   : Stores the collaborator roles for each project

Called By     : sp_getProjectDetails

Create By     : Kiran Kumar Batna

Modified Date : 05/24/2013

********************************************/
DROP VIEW IF EXISTS vw_project_collaborator;

CREATE VIEW vw_project_collaborator(projectid,collaboratoruser,collaboratorrole)
AS
   SELECT projectid,
          collaboratoruser,
          collaboratorrole
     FROM tbl_project_collaborator;
