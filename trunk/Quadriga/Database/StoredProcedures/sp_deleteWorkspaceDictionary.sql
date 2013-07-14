/*******************************************
Name          : sp_deleteWorkspaceDictionary

Description   : Delete the dictionary from
				tbl_workspace_dictionary table

Called By     : UI (DBConnectionProjectDictionary.java)

Create By     : Lohith Dwaraka

Modified Date : 07/01/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_deleteWorkspaceDictionary;
DELIMITER $$
CREATE PROCEDURE sp_deleteWorkspaceDictionary	
(
  IN  inuser varchar(200),
  IN  indictionaryid varchar(200),
  IN  inworkspaceid varchar(200),
  OUT errmsg    VARCHAR(255)    
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
    IF(indictionaryid IS NULL OR indictionaryid = "")
      THEN SET errmsg = "dictionary id cannot be empty.";
    END IF;
    
    IF(inworkspaceid IS NULL OR inworkspaceid = "")
      THEN SET errmsg = "Workspace id cannot be empty.";
    END IF;
    
     IF(inuser IS NULL OR inuser = "")
      THEN SET errmsg = "user name cannot be empty.";
    END IF;

  
    
    IF NOT EXISTS(SELECT 1 FROM tbl_workspace_dictionary    WHERE dictionaryid = indictionaryid and workspaceid = inworkspaceid) 
      THEN SET errmsg = "Dictionary is not present in the project";
    END IF;
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
			DELETE FROM
			tbl_workspace_dictionary WHERE workspaceid = inworkspaceid and dictionaryid= indictionaryid;
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









