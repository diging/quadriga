/*******************************************
Name          : sp_updateDictionaryItems

Description   : Update the dictionary items details to
				tbl_dictionary_items table from the rest

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/10/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_updateDictionaryItems;
DELIMITER $$
CREATE PROCEDURE sp_updateDictionaryItems	
(
  IN  indictionaryid    VARCHAR(100),
  IN  intermid    VARCHAR(200),
  IN  interm    VARCHAR(50),
  IN  inpos    VARCHAR(50),
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

    IF (intermid IS NULL OR intermid = "")
	 THEN SET errmsg = "Items cannot be empty";
	END IF;
	
	IF (interm IS NULL OR interm = "")
	 THEN SET errmsg = "id cannot be empty";
	END IF;
	
	IF (inpos IS NULL OR inpos = "")
	 THEN SET errmsg = "id cannot be empty";
	END IF;
    
    IF NOT EXISTS(SELECT 1 FROM vw_dictionary_items
				   WHERE id = indictionaryid and termid =intermid)
     
      THEN SET errmsg = "Item doesnot exists in this dictionary";
    END IF; 
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
			UPDATE 
			tbl_dictionary_items SET term=interm,pos=inpos WHERE id=indictionaryid and termid =intermid;
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









