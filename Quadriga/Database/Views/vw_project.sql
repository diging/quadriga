/*******************************************
Name          : vw_project

Description   : View to retrieve details of a project

Called By     : sp_getProjectList

Create By     : kiran kumar batna

Modified Date : 05/28/2013

********************************************/
DROP VIEW IF EXISTS vw_project;

CREATE VIEW vw_project
AS
SELECT projectname,
       description,
	   projectid,
       id,
       projectowner,
	   accessibility
FROM tbl_project;