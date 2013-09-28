/*******************************************
Name          : sp_getProjectIdForWorkspace

Description   : retrieves the project id
				for a workspace

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getProjectIdForWorkspace;

DELIMITER $$
CREATE PROCEDURE sp_getProjectIdForWorkspace
(
  IN  inid  VARCHAR(100),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(inid IS NULL OR inid = "")
     THEN SET errmsg = "Workspace id cannot be empty.";
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM tbl_workspace
                     WHERE workspaceid = inid)
      THEN SET errmsg = "Workspace id is invalid.";
    END IF;

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the dictionary details
	 SELECT projectid
       FROM tbl_project_workspace
	   WHERE workspaceid = inid;
	END IF;
END$$
DELIMITER ;