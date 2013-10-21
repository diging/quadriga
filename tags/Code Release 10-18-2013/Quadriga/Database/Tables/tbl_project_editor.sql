/*******************************************
Name          : tbl_project_editor

Description   : Stores the editor roles for owner based on request

Called By     : sp_addProjectEditor

Create By     : Lohith Dwaraka

Modified Date : 08/14/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_project_editor
(
   projectid           VARCHAR(50)   NOT NULL,
   owner    		   VARCHAR(20)   NOT NULL,
   updatedby           VARCHAR(10)   NOT NULL,
   updateddate         TIMESTAMP     NOT NULL,
   createdby           VARCHAR(10)   NOT NULL,
   createddate         DATETIME      NOT NULL,
   PRIMARY KEY(projectid,owner)
)