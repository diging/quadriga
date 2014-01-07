/*******************************************
Name          : sp_getDictionaryDetails

Description   : retrieves the users and their
                Collaborator role for a dictionary

Called By     : UI (DBConnectionManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 09/17/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getDictionaryDetails;
DELIMITER $$
CREATE PROCEDURE sp_getDictionaryDetails
(
   IN indictionaryid  VARCHAR(100),
  OUT errmsg     VARCHAR(255)
)
BEGIN
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(indictionaryid IS NULL OR indictionaryid = "")
     THEN SET errmsg = "Dictionary id cannot be empty.";
    END IF;
   	
    IF NOT EXISTS (SELECT 1 FROM vw_dictionary WHERE id = indictionaryid)
      THEN SET errmsg = "Invalid Dictionary id.";
    END IF;

    IF errmsg IS NULL
      THEN SET errmsg = "";
      SELECT dictionaryname,description,dictionaryowner,id,accessibility
        FROM vw_dictionary WHERE dictionaryid = indictionaryid ;
	END IF;
END$$
DELIMITER ;