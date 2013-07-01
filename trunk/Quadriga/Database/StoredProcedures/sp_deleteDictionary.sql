/*******************************************
Name          : sp_deleteDictionary

Description   : Delete the dictionary from
				tbl_dictionary table

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 07/01/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_deleteDictionary;
DELIMITER $$
CREATE PROCEDURE sp_deleteDictionary	
(
  IN  inuser varchar(200),
  IN  indictionaryid varchar(200),
  OUT errmsg           VARCHAR(255)    
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
    IF(indictionaryid IS NULL OR indictionaryid = "")
      THEN SET errmsg = "dictionary id cannot be empty.";
    END IF;
    
     IF(inuser IS NULL OR inuser = "")
      THEN SET errmsg = "user name cannot be empty.";
    END IF;

  
    
    IF NOT EXISTS(SELECT 1 FROM vw_dictionary    WHERE id = indictionaryid and dictionaryowner = inuser) 
      THEN SET errmsg = "User don't have access to this dictionary";
    END IF; 
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
			DELETE FROM
			tbl_dictionary_items WHERE id = indictionaryid;
			DELETE FROM
			tbl_dictionary WHERE id = indictionaryid and dictionaryowner = inuser;
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









