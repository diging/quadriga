/*******************************************
Name          : sp_updateDictionary

Description   : updates dictionary details to tbl_dictionary table

Called By     : UI (DBConnectionManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 09/16/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_updateDictionary;
DELIMITER $$
CREATE PROCEDURE sp_updateDictionary
(
  IN   indictname       VARCHAR(50),
  IN   indescription    TEXT,
  IN   inaccessibility  VARCHAR(50),
  IN   inuser           VARCHAR(50),
  IN   indictionaryid   VARCHAR(50),
  OUT  errmsg           VARCHAR(255)
)
BEGIN
    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

	-- validate the input parameters
    IF (indictname IS NULL OR indictname = "")
     THEN SET errmsg = "Dictionary name cannot be empty.";
    END IF;

    IF (indescription IS NULL OR indescription = "")
     THEN SET errmsg = "Dictionary description cannot be empty.";
	END IF;

    IF (inaccessibility IS NULL OR inaccessibility = "")
      THEN SET errmsg = "Dictionary accessibility cannot be empty.";
    END IF;

    IF (inuser IS NULL OR inuser = "")
      THEN SET errmsg = "User name cannot be empty.";
    END IF;
    
	IF (indictionaryid IS NULL OR indictionaryid = "")
	   THEN SET errmsg = "Dictionaryid cannot be empty";
	END IF;

    IF NOT EXISTS (SELECT 1 FROM vw_quadriga_user WHERE username = inuser)
      THEN SET errmsg = "User name is invalid.Please enter a correct username.";
    END IF;

	IF NOT EXISTS (SELECT 1 FROM vw_dictionary WHERE id = indictionaryid)
      THEN SET errmsg = "Invalid dictionary id.";
	END IF;

    -- updating the dictionary
    IF errmsg IS NULL
      THEN SET errmsg = "";
      START TRANSACTION;
         UPDATE tbl_dictionary dict
            SET dict.dictionaryname = indictname
			   ,dict.description = indescription
               ,dict.accessibility = inaccessibility
               ,dict.updatedby = inuser
               ,dict.updateddate = NOW()
		  WHERE dict.id = indictionaryid;
	   IF errmsg = ""
          THEN COMMIT;
       ELSE ROLLBACK;
       END IF;
     END IF;
END$$
DELIMITER ;