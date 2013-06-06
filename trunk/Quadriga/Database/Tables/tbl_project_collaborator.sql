/*******************************************
Name          : tbl_project_collaborator

Description   : Stores the collaborator roles for each project

Called By     : sp_getProjectDetails

Create By     : Kiran Kumar Batna

Modified Date : 05/24/2013

********************************************/
CREATE TABLE IF NOT EXISTS tbl_project_collaborator
(
   projectid           VARCHAR(50) ,
   collaboratoruser    VARCHAR(20),
   collaboratorrole    VARCHAR(100),
   updatedby           VARCHAR(10)   NOT NULL,
   updateddate         TIMESTAMP     NOT NULL,
   createdby           VARCHAR(10)   NOT NULL,
   createddate         DATETIME      NOT NULL,
   PRIMARY KEY(projectid,collaboratoruser,collaboratorrole),
   FOREIGN KEY(projectid) REFERENCES tbl_project(projectid)
   FOREIGN KEY(collaboratoruser)  REFERENCES tbl_quadriga_user(username)  
)