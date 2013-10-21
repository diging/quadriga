DROP FUNCTION IF EXISTS fn_checkWorkspaceExists;
DELIMITER $$
CREATE FUNCTION fn_checkWorkspaceExists
(
  inworkspaceid   VARCHAR(100)
)
RETURNS boolean
BEGIN
    IF EXISTS (SELECT 1 FROM vw_workspace WHERE workspaceid = inworkspaceid)
      THEN RETURN TRUE;
    ELSE RETURN FALSE;
    END IF;
END$$
DELIMITER ;