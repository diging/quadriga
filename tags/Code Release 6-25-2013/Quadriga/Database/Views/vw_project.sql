/*******************************************
Name          : vw_project

Description   : View to retrieve details of a project

Called By     : sp_getProjectList

Create By     : kiran kumar batna

Modified Date : 05/28/2013

********************************************/
DROP VIEW IF EXISTS vw_project;

CREATE VIEW vw_project(projectname,description,unixname,projectid,projectowner,accessibility)
AS
SELECT projectname,
       description,
	   unixname,
       projectid,
       projectowner,
	   accessibility
FROM tbl_project;