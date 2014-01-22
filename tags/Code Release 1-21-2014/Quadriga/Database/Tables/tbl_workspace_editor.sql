/*******************************************
Name          : tbl_workspace_editor

Description   : Stores the editor roles for owner based on request

Called By     : tbl_workspace_editor

Create By     : Lohith Dwaraka

Modified Date : 08/14/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_workspace_editor
(
   workspaceid         VARCHAR(150)   NOT NULL,
   editor    		   VARCHAR(50)    NOT NULL,
   updatedby           VARCHAR(20)    NOT NULL,
   updateddate         TIMESTAMP      NOT NULL,
   createdby           VARCHAR(20)    NOT NULL,
   createddate         DATETIME       NOT NULL,
   PRIMARY KEY(workspaceid,editor)
)