/*******************************************
Name          : sp_getWorkspaceDetails

Description   : Retrieve the workspace details

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 06/25/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getWorkspaceDetails;

DELIMITER $$
CREATE PROCEDURE sp_getWorkspaceDetails
(
  IN  inworkspaceid  BIGINT,
  OUT errmsg         VARCHAR(255)        
)
BEGIN
  -- the error handler for any sql exception
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
   SET errmsg = "SQLException occurred";

   IF(inworkspaceid IS NULL)
     THEN SET errmsg = "Workspace id cannot be empty.";
   END IF;

   IF NOT EXISTS(SELECT 1 FROM vw_workspace WHERE workspaceid = inworkspaceid)
     THEN SET errmsg = "Workspace id is invalid.";
   END IF;

   IF(errmsg IS NULL)
     THEN SET errmsg = "";
     SELECT workspacename,description,workspaceowner,workspaceid FROM vw_workspace
       WHERE workspaceid = inworkspaceid;
    END IF;
END$$
DELIMITER ;