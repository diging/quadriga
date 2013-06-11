/*******************************************
Name          : sp_deleteDictionaryItems

Description   : Delete the dictionary items details to
				tbl_dictionary_items table

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/10/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_deleteDictionaryItems;
DELIMITER $$
CREATE PROCEDURE sp_deleteDictionaryItems	
(
  IN  indictionaryid    VARCHAR(100),
  IN  initems    VARCHAR(50),
  OUT errmsg           VARCHAR(255)    
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
    IF(indictionaryid IS NULL OR indictionaryid = "")
      THEN SET errmsg = "dictionaryid cannot be empty.";
    END IF;

    IF (initems IS NULL OR initems = "")
	 THEN SET errmsg = "Items cannot be empty";
	END IF;
	
    
    IF NOT EXISTS(SELECT 1 FROM vw_dictionary_items
				   WHERE dictionaryid = indictionaryid and items =initems)
     
      THEN SET errmsg = "Item doesnot exists in this dictionary";
    END IF; 
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
			DELETE FROM
			tbl_dictionary_items WHERE dictionaryid=indictionaryid and items =initems;
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









