CREATE TABLE IF NOT EXISTS tbl_project_collaboratorRoles
(
   collaboratorRoleName  	VARCHAR(50) ,
   collaboratorRoleId  		VARCHAR(20) PRIMARY KEY, 
   updatedby           		VARCHAR(10)   NOT NULL,
   updateddate         		TIMESTAMP     NOT NULL,
   createdby           		VARCHAR(10)   NOT NULL,
   createddate         		DATETIME      NOT NULL
)