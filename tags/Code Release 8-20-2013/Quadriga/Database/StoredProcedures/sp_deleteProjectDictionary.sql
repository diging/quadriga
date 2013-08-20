/*******************************************
Name          : sp_deleteProjectDictionary

Description   : Delete the dictionary from
				tbl_project_dictionary table

Called By     : UI (DBConnectionProjectDictionary.java)

Create By     : Lohith Dwaraka

Modified Date : 07/01/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_deleteProjectDictionary;
DELIMITER $$
CREATE PROCEDURE sp_deleteProjectDictionary	
(
  IN  inuser varchar(200),
  IN  indictionaryid varchar(200),
  IN  inprojectid varchar(200),
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
    
    IF(inprojectid IS NULL OR inprojectid = "")
      THEN SET errmsg = "Project id cannot be empty.";
    END IF;
    
     IF(inuser IS NULL OR inuser = "")
      THEN SET errmsg = "user name cannot be empty.";
    END IF;

  
    
    IF NOT EXISTS(SELECT 1 FROM tbl_project_dictionary    WHERE dictionaryid = indictionaryid and projectid = inprojectid) 
      THEN SET errmsg = "Dictionary is not present in the project";
    END IF;
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
			DELETE FROM
			tbl_project_dictionary WHERE projectid = inprojectid and dictionaryid= indictionaryid;
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









