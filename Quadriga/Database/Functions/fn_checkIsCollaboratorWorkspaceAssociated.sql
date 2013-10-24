DROP FUNCTION IF EXISTS fn_checkIsCollaboratorWorkspaceAssociated;
DELIMITER $$
CREATE FUNCTION fn_checkIsCollaboratorWorkspaceAssociated
(
   incollaborator  VARCHAR(50),
   incollaboratorrole VARCHAR(100)
)
RETURNS boolean
BEGIN
    IF EXISTS (SELECT 1 FROM vw_workspace_collaborator WHERE username = incollaborator
      AND collaboratorrole = incollaboratorrole)
    THEN RETURN true;
    ELSE RETURN false;
    END IF;
END$$
DELIMITER ;