/*******************************************
Name          : sp_deleteWorkspaceCC

Description   : Delete the Concept collection from
				tbl_workspace_conceptcollection table

Called By     : UI (DBConnectionProjectDictionary.java)

Create By     : Lohith Dwaraka

Modified Date : 07/11/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_deleteWorkspaceCC;
DELIMITER $$
CREATE PROCEDURE sp_deleteWorkspaceCC	
(
  IN  inuser varchar(200),
  IN  inccid varchar(200),
  IN  inworkspaceid varchar(200),
  OUT errmsg    VARCHAR(255)    
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
    IF(inccid IS NULL OR inccid = "")
      THEN SET errmsg = "Concept collection id cannot be empty.";
    END IF;
    
    IF(inworkspaceid IS NULL OR inworkspaceid = "")
      THEN SET errmsg = "Workspace id cannot be empty.";
    END IF;
    
     IF(inuser IS NULL OR inuser = "")
      THEN SET errmsg = "user name cannot be empty.";
    END IF;

  
    
    IF NOT EXISTS(SELECT 1 FROM tbl_workspace_conceptcollection    WHERE conceptcollectionid = inccid and workspaceid = inworkspaceid) 
      THEN SET errmsg = "Concept collection is not present in the Workspace";
    END IF;
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
			DELETE FROM
			tbl_workspace_conceptcollection WHERE workspaceid = inworkspaceid and conceptcollectionid = inccid;
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









