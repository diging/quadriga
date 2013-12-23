/*******************************************
Name          : sp_addDictionaryDetails

Description   : adds the dictionary details to
				tbl_dictionary table

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_addDictionaryDetails;
DELIMITER $$
CREATE PROCEDURE sp_addDictionaryDetails
(
  IN  indictionaryname    VARCHAR(50),
  IN  indescription    TEXT,
  IN  inaccessibility  VARCHAR(50),
  IN  indictionaryowner   VARCHAR(50),
  OUT errmsg           VARCHAR(255)    
)
BEGIN

	DECLARE uniqueId  BIGINT;
    DECLARE dictionaryId VARCHAR(100);
    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
    IF(indictionaryname IS NULL OR indictionaryname = "")
	  THEN SET errmsg = "Dictionary name cannot be empty.";
    END IF;
	
    IF EXISTS(SELECT 1 FROM vw_dictionary
                WHERE dictionaryname = indictionaryname)
      THEN SET errmsg = "Dictionary name already exists.";
	END IF;



    IF(inaccessibility IS NULL)
       THEN SET errmsg = "accessibility cannot be empty";
    END IF;

    IF (indictionaryowner IS NULL OR indictionaryowner = "")
	 THEN SET errmsg = "dictionary owner cannot be empty";
	END IF;

    IF NOT EXISTS(SELECT 1 FROM vw_quadriga_user
				   WHERE username = indictionaryowner)
      THEN SET errmsg = "Invalid owner.Please enter the correct value.";
    END IF; 
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
         SET uniqueId = UUID_SHORT();
         SET dictionaryId = CONCAT('DICT_',CAST(uniqueId AS CHAR));
            INSERT 
              INTO tbl_dictionary(dictionaryid,dictionaryname,description,dictionaryowner,accessibility,
                         updatedby,updateddate,createdby,createddate)
			 VALUES (dictionaryId,indictionaryname,indescription,indictionaryowner,inaccessibility,
                     indictionaryowner,NOW(),indictionaryowner,NOW());	
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









