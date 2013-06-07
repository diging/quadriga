

INSERT tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,collaboratorrolename,updatedby,updateddate,createdby,createddate)
VALUES ('1','test','collaborator_role1,collaborator_role4','ADMIN,EDITOR',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,collaboratorrolename,updatedby,updateddate,createdby,createddate)
VALUES ('1','jdoe','collaborator_role3','CONTRIBUTOR',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,collaboratorrolename,updatedby,updateddate,createdby,createddate)
VALUES ('1','leonard','collaborator_role2','PROJECT_ADMIN',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,collaboratorrolename,updatedby,updateddate,createdby,createddate)
VALUES ('1','dexter','collaborator_role4','EDITOR',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,collaboratorrolename,updatedby,updateddate,createdby,createddate)
VALUES ('1','penny','collaborator_role2','PROJECT_ADMIN',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,collaboratorrolename,updatedby,updateddate,createdby,createddate)
VALUES ('2','dexter','collaborator_role3','CONTRIBUTOR',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,collaboratorrolename,updatedby,updateddate,createdby,createddate)
VALUES ('2','deb','collaborator_role1','ADMIN',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,collaboratorrolename,updatedby,updateddate,createdby,createddate)
VALUES ('3','leonard','collaborator_role1,collaborator_role4','ADMIN,EDITOR',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,collaboratorrolename,updatedby,updateddate,createdby,createddate)
VALUES ('3','test','collaborator_role3','CONTRIBUTOR',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,collaboratorrolename,updatedby,updateddate,createdby,createddate)
VALUES ('4','jdoe','collaborator_role2','PROJECT_ADMIN',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());

INSERT tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,collaboratorrolename,updatedby,updateddate,createdby,createddate)
VALUES ('4','penny','collaborator_role1','ADMIN',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());
