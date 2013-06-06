/*******************************************
Name          : sp_getDictionaryName

Description   : retrieves the dictionary details
				of a particular dictionary

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getDictionaryName;

DELIMITER $$
CREATE PROCEDURE sp_getDictionaryName
(
  IN  indictionaryid  VARCHAR(20),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(indictionaryid IS NULL OR indictionaryid = "")
     THEN SET errmsg = "Dictionary owner name cannot be empty.";
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM vw_dictionary
                     WHERE dictionaryid = indictionaryid)
      THEN SET errmsg = "Dictionary id is invalid.";
    END IF;

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the dictionary details
	 SELECT dictionaryname
       FROM vw_dictionary
	   WHERE dictionaryid = indictionaryid;
	END IF;
END$$
DELIMITER ;