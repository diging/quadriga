/*******************************************
Name          : tbl_project_collaborator

Description   : Stores the collaborator roles for each project

Called By     : sp_getProjectDetails

Create By     : Kiran Kumar Batna

Modified Date : 05/24/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_project_collaborator
(
   projectid           VARCHAR(50)   NOT NULL,
   collaboratoruser    VARCHAR(20)   NOT NULL,
   collaboratorrole    VARCHAR(100)  NOT NULL,
   updatedby           VARCHAR(10)   NOT NULL,
   updateddate         TIMESTAMP     NOT NULL,
   createdby           VARCHAR(10)   NOT NULL,
   createddate         DATETIME      NOT NULL,
   PRIMARY KEY(projectid,collaboratoruser,collaboratorrole)
)