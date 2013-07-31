/*******************************************
Name          : sp_getDictionaryOwner

Description   : retrieves the dictionary owner
				of a particular dictionary

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getDictionaryOwner;

DELIMITER $$
CREATE PROCEDURE sp_getDictionaryOwner
(
  IN  inid  VARCHAR(100),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(inid IS NULL OR inid = "")
     THEN SET errmsg = "Dictionary owner name cannot be empty.";
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM vw_dictionary
                     WHERE id = inid)
      THEN SET errmsg = "Dictionary id is invalid.";
    END IF;

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the dictionary details
	 select dictionaryowner from tbl_dictionary where id= inid;
	END IF;
END$$
DELIMITER ;