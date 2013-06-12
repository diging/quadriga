/*******************************************
Name          : sp_addDictionaryItems

Description   : adds the dictionary items details to
				tbl_dictionary_items table

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_addDictionaryItems;
DELIMITER $$
CREATE PROCEDURE sp_addDictionaryItems	
(
  IN  indictionaryid    VARCHAR(100),
  IN  initems    VARCHAR(200),
  IN  inid    VARCHAR(200),
  IN  inpos    VARCHAR(50),
  IN indictionaryowner VARCHAR(50),
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
	
	IF (inid IS NULL OR inid = "")
	 THEN SET errmsg = "Word power id cannot be empty";
	END IF;
	
	IF (inpos IS NULL OR inpos = "")
	 THEN SET errmsg = "Word power Pos cannot be empty";
	END IF;
	
	  
	IF (indictionaryowner IS NULL OR indictionaryowner = "")
	 THEN SET errmsg = "dictionary owner cannot be empty";
	END IF;

    IF NOT EXISTS(SELECT 1 FROM vw_quadriga_user
				   WHERE username = indictionaryowner)
      THEN SET errmsg = "Invalid owner.Please enter the correct value.";
    END IF; 
    
    IF EXISTS(SELECT 1 FROM vw_dictionary_items
				   WHERE dictionaryid = indictionaryid and items =initems and pos=inpos)
     
      THEN SET errmsg = "ItemExists";
    END IF; 
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
            INSERT 
              INTO tbl_dictionary_items(dictionaryid,items,id,pos,
                         updatedby,updateddate,createdby,createddate)
			 VALUES (indictionaryid,initems,inid,inpos,
                     indictionaryowner,NOW(),indictionaryowner,NOW());	
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









