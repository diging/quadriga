DROP VIEW IF EXISTS vw_projectDetails;

CREATE VIEW vw_projectDetails(projectname,description,projectid,projectowner,accessibility)
AS
SELECT projectname,description,projectid,projectowner,accessibility
FROM tbl_project;