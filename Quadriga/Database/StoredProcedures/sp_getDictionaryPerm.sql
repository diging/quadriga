/*******************************************
Name          : sp_getDictionaryPerm.sql

Description   : Check if the user has persmission to  the dictionary details
				of a particular dictionary

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 07/10/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getDictionaryPerm;

DELIMITER $$
CREATE PROCEDURE sp_getDictionaryPerm
(
  IN  indictionaryowner  VARCHAR(50),
  IN  indictionaryid  VARCHAR(100),
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
    
    IF(indictionaryid IS NULL OR indictionaryid = "")
     THEN SET errmsg = "Dictionary ID  cannot be empty.";
    END IF;


    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the dictionary details
	 SELECT dictionaryid
       FROM vw_dictionary
	   WHERE dictionaryowner = indictionaryowner and dictionaryid=indictionaryid;
	END IF;
END$$
DELIMITER ;