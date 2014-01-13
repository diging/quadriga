DROP FUNCTION IF EXISTS fn_checkIsWorkspaceAssociated;
DELIMITER $$
CREATE FUNCTION fn_checkIsWorkspaceAssociated
(
   inowner  VARCHAR(50)
)
RETURNS boolean
BEGIN
    IF EXISTS (SELECT 1 FROM vw_workspace WHERE workspaceowner = inowner )
    THEN RETURN true;
    ELSE RETURN false;
    END IF;
END$$
DELIMITER ;
