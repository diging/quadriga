/*******************************************
Name          : sp_getDictionaryItems

Description   : retrieves the dictionary items

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/05/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getDictionaryItems;

DELIMITER $$
CREATE PROCEDURE sp_getDictionaryItems
(
  IN inid  VARCHAR(20),
  OUT errmsg     VARCHAR(255)
)
BEGIN

	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(inid IS NULL OR inid = "")
     THEN SET errmsg = "Dicitonary id cannot be empty.";
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM vw_dictionary
                     WHERE id = inid)
      THEN SET errmsg = "Dicitonary id is invalid.";
    END IF;

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
            
      -- retrieve the item details
      SELECT termid , term ,pos
        FROM vw_dictionary_items
	    WHERE id = inid
      ORDER BY term;
     END IF;
END$$
DELIMITER ;