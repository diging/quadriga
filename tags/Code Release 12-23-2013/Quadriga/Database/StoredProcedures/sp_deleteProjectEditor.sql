/*******************************************
Name          : sp_deleteProjectEditor

Description   : deletes the editor roles to user details to
				owner for a project

Called By     : UI (DBConnectionModifyProjectManager.java)

Create By     : Lohith Dwaraka

Modified Date : 08/14/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_deleteProjectEditor;
DELIMITER $$
CREATE PROCEDURE sp_deleteProjectEditor
(
  IN  inprojectid    VARCHAR(100),
  IN  inowner    VARCHAR(100),
  OUT errmsg           VARCHAR(255)    
)
BEGIN

	DECLARE uniqueId  BIGINT;
    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
    IF(inprojectid IS NULL OR inprojectid = "")
	  THEN SET errmsg = "Project id cannot be empty.";
    END IF;
    
    IF(inowner IS NULL OR inowner = "")
	  THEN SET errmsg = "Project owner name cannot be empty.";
    END IF;
    
	 IF NOT EXISTS(SELECT 1 FROM tbl_project_editor
                WHERE projectid = inprojectid and editor = inowner)
      THEN SET errmsg = "Owner don't exist";
	END IF;
	
    -- Inserting the record into the tbl_project_editor table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;

            DELETE 
              FROM tbl_project_editor
			 WHERE projectid = inprojectid and editor =inowner;
			 
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









