
INSERT tbl_project_collaboratorRoles(collaboratorRoleName,collaboratorRoleId,updatedby,updateddate,createdby,createddate)
VALUES ('ROLE_COLLABORATOR_ADMIN','collaborator_role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT tbl_project_collaboratorRoles(collaboratorRoleName,collaboratorRoleId,updatedby,updateddate,createdby,createddate)
VALUES ('ROLE_COLLABORATOR_PROJECT_ADMIN','collaborator_role2',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT tbl_project_collaboratorRoles(collaboratorRoleName,collaboratorRoleId,updatedby,updateddate,createdby,createddate)
VALUES ('ROLE_COLLABORATOR_CONTRIBUTOR','collaborator_role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT tbl_project_collaboratorRoles(collaboratorRoleName,collaboratorRoleId,updatedby,updateddate,createdby,createddate)
VALUES ('ROLE_COLLABORATOR_EDITOR','collaborator_role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

