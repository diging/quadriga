/*******************************************
Name          : fn_checkDuplicateUnixName

Description   : Check if the unix name is unique

Called By     : UI (DBConnectionDspaceManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 07/16/2013

********************************************/
DROP FUNCTION IF EXISTS fn_checkDuplicateUnixName;
DELIMITER $$
CREATE FUNCTION fn_checkDuplicateUnixName
(
  inUnixName    VARCHAR(50)
)
RETURNS boolean
BEGIN
   IF EXISTS (SELECT 1 FROM vw_project WHERE unixname = inUnixName)
    THEN RETURN TRUE;
   ELSE RETURN FALSE;
   END IF;
END$$
DELIMITER ;