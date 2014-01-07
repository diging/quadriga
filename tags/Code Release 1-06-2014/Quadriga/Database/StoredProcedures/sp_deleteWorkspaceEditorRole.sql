/*******************************************
Name          : sp_deleteWorkspaceEditorRole

Description   : Delete the editor roles to user details to
				owner for a project

Called By     : UI (DBConnectionModifyProjectManager.java)

Create By     : Lohith Dwaraka

Modified Date : 08/14/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_deleteWorkspaceEditorRole;
DELIMITER $$
CREATE PROCEDURE sp_deleteWorkspaceEditorRole
(
  IN  inworkspaceid    VARCHAR(150),
  IN  inowner    VARCHAR(100),
  OUT errmsg           VARCHAR(255)    
)
BEGIN

	DECLARE uniqueId  BIGINT;
    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
    IF(inworkspaceid IS NULL OR inworkspaceid = "")
	  THEN SET errmsg = "Workspace id cannot be empty.";
    END IF;
    
    IF(inowner IS NULL OR inowner = "")
	  THEN SET errmsg = "Workspace owner name cannot be empty.";
    END IF;
    
	 IF NOT EXISTS(SELECT 1 FROM tbl_workspace_editor
                WHERE workspaceid = inworkspaceid and editor = inowner)
      THEN SET errmsg = "Owner don't exist";
	END IF;
	
    -- Deleting the record from the tbl_workspace_editor table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;

            DELETE 
              FROM tbl_workspace_editor
			 WHERE workspaceid = inworkspaceid and editor = inowner;

			 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









