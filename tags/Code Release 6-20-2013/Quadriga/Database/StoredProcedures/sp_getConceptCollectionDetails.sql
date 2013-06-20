/*******************************************
Name          : sp_getConceptCollectionDetails

Description   : retrieves the users and their
                Collaborator role for a concept

Called By     : UI (DBConnectionManager.java)

Create By     : SatyaSwaroop Boddu

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getConceptCollectionDetails;

DELIMITER $$
CREATE PROCEDURE sp_getConceptCollectionDetails
(
  IN incollectionid  VARCHAR(20),
 
  OUT errmsg     VARCHAR(255)
)
BEGIN
    
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(incollectionid IS NULL OR incollectionid = "")
     THEN SET errmsg = "collection id cannot be empty.";
    END IF;
   
    
    IF NOT EXISTS (SELECT 1 FROM vw_conceptcollections	
                     WHERE id = incollectionid)
      THEN SET errmsg = "collection id is invalid.";
    END IF;
    
    

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     
      -- retrieve the item details
      SELECT item, description, pos, lemma
           AS item
        FROM vw_conceptcollections_items
	    WHERE id = incollectionid;
      
     END IF;
END$$
DELIMITER ;