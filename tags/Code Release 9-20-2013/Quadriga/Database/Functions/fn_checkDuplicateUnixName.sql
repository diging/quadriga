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
  inUnixName    VARCHAR(50),
  inProjectid   VARCHAR(100)
)
RETURNS boolean
BEGIN
   DECLARE tempprojectid VARCHAR(100);

   SELECT projectid INTO tempprojectid FROM vw_project
            WHERE unixname = inUnixName;

   IF ISNULL(COALESCE(inProjectid,tempprojectid))
      THEN RETURN FALSE;
      ELSE IF COALESCE(tempprojectid,inProjectid) = inProjectid
           THEN RETURN FALSE;
           ELSE RETURN TRUE;
           END IF;
   END IF;
END$$
DELIMITER ;