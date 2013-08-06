/*******************************************
Name          : sp_validatecollectionid

Description   : retrieves the users and their
                Collaborator role for a concept

Called By     : UI (DBConnectionManager.java)

Create By     : SatyaSwaroop Boddu

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_validatecollectionid;

DELIMITER $$
CREATE PROCEDURE sp_validatecollectionid
(
 
  IN incollectionname VARCHAR(50),
 
  OUT errmsg     VARCHAR(255)
)
BEGIN
   

	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(incollectionname IS NULL OR incollectionname = "")
     THEN SET errmsg = "collection id cannot be empty.";
    END IF;
   
    
    
    IF EXISTS (SELECT 1 FROM vw_conceptcollections	
                     WHERE collectionname = incollectionname)
      THEN SET errmsg = "collection id is invalid.";
    END IF;
    

    
END$$
DELIMITER ;