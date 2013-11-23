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
  IN  inworkspaceid  VARCHAR(150),
  IN  inuser         VARCHAR(30),
  OUT errmsg         VARCHAR(255)        
)
BEGIN
  -- the error handler for any sql exception
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
   SET errmsg = "SQLException occurred";

   IF(inworkspaceid IS NULL OR inworkspaceid = "")
     THEN SET errmsg = "Workspace id cannot be empty.";
   END IF;

   IF NOT EXISTS(SELECT 1 FROM vw_workspace WHERE workspaceid = inworkspaceid)
     THEN SET errmsg = "Workspace id is invalid.";
   END IF;

  IF (inuser IS NULL OR inuser = "")
   THEN SET errmsg = "User name is invalid";
  END IF;

  IF NOT EXISTS(SELECT 1 FROM vw_quadriga_user WHERE username = inuser)
    THEN SET errmsg = "User name is invalid";
  END IF;
 
  IF(errmsg IS NULL)
   THEN SET errmsg = "";
  END IF;

  IF EXISTS(SELECT 1 FROM vw_project WHERE projectowner = inuser)
   THEN 
      SELECT workspacename,description,workspaceowner,workspaceid FROM vw_workspace
       WHERE workspaceid = inworkspaceid;
    ELSE
        SELECT workspacename,description,workspaceowner,workspaceid FROM vw_workspace
       WHERE workspaceid = inworkspaceid
         AND workspaceowner = inuser;
  END IF;
END$$
DELIMITER ;