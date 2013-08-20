/*******************************************
Name          : sp_addWorkspaceEditorRole

Description   : adds the editor roles to user details to
				owner for a project

Called By     : UI (DBConnectionModifyProjectManager.java)

Create By     : Lohith Dwaraka

Modified Date : 08/14/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_addWorkspaceEditorRole;
DELIMITER $$
CREATE PROCEDURE sp_addWorkspaceEditorRole
(
  IN  inworkspaceid    VARCHAR(100),
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
    
	 IF EXISTS(SELECT 1 FROM tbl_workspace_editor
                WHERE workspaceid = inworkspaceid and owner = inowner)
      THEN SET errmsg = "Owner already assigned as owner";
	END IF;
	
    -- Inserting the record into the tbl_project_editor table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;

            INSERT 
              INTO tbl_workspace_editor(workspaceid,owner,
                         updatedby,updateddate,createdby,createddate)
			 VALUES (inworkspaceid,inowner,
                     inowner,NOW(),inowner,NOW());	
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









