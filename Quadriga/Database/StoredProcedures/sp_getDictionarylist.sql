/*******************************************
Name          : sp_getDictionarylist

Description   : retrieves the dictionary details
				of a particular dictionary

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getDictionarylist;

DELIMITER $$
CREATE PROCEDURE sp_getDictionarylist
(
  IN  indictionaryowner  VARCHAR(20),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(indictionaryowner IS NULL OR indictionaryowner = "")
     THEN SET errmsg = "Dictionary owner name cannot be empty.";
    END IF;


    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the dictionary details
	 SELECT dictionaryname,description,dictionaryid,dictionaryowner,accessibility
       FROM vw_dictionary
	   WHERE dictionaryowner = indictionaryowner;
	END IF;
END$$
DELIMITER ;