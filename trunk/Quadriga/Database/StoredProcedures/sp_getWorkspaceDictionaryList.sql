/*******************************************
Name          : sp_getWorkspaceDictionaryList

Description   : retrieves the dictionary details
				in a workspace

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getWorkspaceDictionaryList;

DELIMITER $$
CREATE PROCEDURE sp_getWorkspaceDictionaryList
(
  IN  inworkspaceid  VARCHAR(50),
  IN inuserid VARCHAR(50),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(inworkspaceid IS NULL OR inworkspaceid = "")
     THEN SET errmsg = "Workspace id cannot be empty.";
    END IF;
    
    IF(inuserid IS NULL OR inuserid = "")
     THEN SET errmsg = "Workspace owner name cannot be empty.";
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM vw_workspace
                     WHERE workspaceowner = inuserid)
      THEN SET errmsg = "Workspace owner name is invalid.";
    END IF;

    IF NOT EXISTS(SELECT 1 FROM vw_quadriga_user
				   WHERE username = inuserid)
      THEN SET errmsg = "Invalid user.Please enter the correct value.";
    END IF; 
    
    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the dictionary details
	 SELECT dictionaryname,description,id,dictionaryowner,accessibility
       FROM vw_dictionary
	   WHERE id IN ( select dictionaryid from tbl_workspace_dictionary where workspaceid=inworkspaceid );
	END IF;
END$$
DELIMITER ;