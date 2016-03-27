/*******************************************
Name          : tbl_project_editor

Description   : Stores the editor names associated for 
                a project

Called By     : sp_addProjectEditor

Create By     : Lohith Dwaraka

Modified Date : 08/14/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_project_editor
(
   projectid           VARCHAR(100)   NOT NULL,
   editor    		   VARCHAR(50)    NOT NULL,
   updatedby           VARCHAR(20)    NOT NULL,
   updateddate         TIMESTAMP      NOT NULL,
   createdby           VARCHAR(20)    NOT NULL,
   createddate         DATETIME       NOT NULL,
   PRIMARY KEY(projectid,editor)
)