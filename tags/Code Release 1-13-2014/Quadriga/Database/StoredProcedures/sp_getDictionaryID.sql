/*******************************************
Name          : sp_getDictionaryID

Description   : retrieves the dictionary details
				of a particular dictionary

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getDictionaryID;

DELIMITER $$
CREATE PROCEDURE sp_getDictionaryID
(
  IN  inname  VARCHAR(100),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(inname IS NULL OR inname = "")
     THEN SET errmsg = "Dictionary name cannot be empty.";
    END IF;


    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the Dictionary details
	 SELECT dictionaryid
       FROM tbl_dictionary
	   WHERE dictionaryname = inname;
	END IF;
END$$
DELIMITER ;