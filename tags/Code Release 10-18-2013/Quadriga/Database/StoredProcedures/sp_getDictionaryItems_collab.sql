/*******************************************
Name          : sp_getDictionaryItems_collab

Description   : retrieves the dictionary items

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/05/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getDictionaryItems_collab;

DELIMITER $$
CREATE PROCEDURE sp_getDictionaryItems_collab
(
  IN inid  VARCHAR(100),
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
                     WHERE id IN( select id from tbl_dictionary where id=inid 
    ))
      THEN SET errmsg = "Dicitonary id is invalid.";
    END IF;

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
            
      -- retrieve the item details
      SELECT termid , term ,pos
        FROM vw_dictionary_items
	    WHERE id IN( select id from tbl_dictionary where id=inid 
    )
      ORDER BY term;
     END IF;
END$$
DELIMITER ;