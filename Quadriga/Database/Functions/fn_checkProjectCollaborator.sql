DROP FUNCTION IF EXISTS fn_checkProjectCollaborator;
DELIMITER $$
CREATE FUNCTION fn_checkProjectCollaborator
(
  inusername    VARCHAR(50),
  incollaboratorrole VARCHAR(100)
)
RETURNS boolean
BEGIN
    IF(( SELECT COUNT(DISTINCT projectid) FROM tbl_project_collaborator
        WHERE collaboratoruser = inusername AND collaboratorrole = incollaboratorrole)>0)
    THEN RETURN true;
    ELSE RETURN false;
    END if;    
END$$
DELIMITER ;