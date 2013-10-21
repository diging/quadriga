/*******************************************
Name          : sp_deleteDictionaryItemsCollab

Description   : Delete the dictionary items details to
				tbl_dictionary_items table

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/10/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_deleteDictionaryItemsCollab;
DELIMITER $$
CREATE PROCEDURE sp_deleteDictionaryItemsCollab	
(
  IN  indictionaryid varchar(200),
  IN  intermid		varchar(200),
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

    IF (intermid IS NULL OR intermid = "")
	 THEN SET errmsg = "Term cannot be empty";
	END IF;
	

    
    IF NOT EXISTS(SELECT 1 FROM vw_dictionary_items    WHERE id IN( select id from tbl_dictionary where id=indictionaryid 
    ) and termid =intermid) 
     
      THEN SET errmsg = "Item doesnot exists in this dictionary";
    END IF; 
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
			DELETE FROM
			tbl_dictionary_items WHERE id IN ( select id from tbl_dictionary where id=indictionaryid)
				and termid =intermid;
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









