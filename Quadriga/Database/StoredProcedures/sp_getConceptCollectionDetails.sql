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
  IN incollectionname  VARCHAR(20),
 
  OUT errmsg     VARCHAR(255)
)
BEGIN
    -- declare local variables
     DECLARE varcollectionid   INT DEFAULT 0;

	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(incollectionname IS NULL OR incollectionname = "")
     THEN SET errmsg = "collection name cannot be empty.";
    END IF;
   
    
    IF NOT EXISTS (SELECT 1 FROM vw_conceptcollections	
                     WHERE collectionname = incollectionname)
      THEN SET errmsg = "collection name is invalid.";
    END IF;
    
    

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
      -- retrieve the projectid id of the project 
      SELECT id INTO varcollectionid FROM vw_conceptcollections
        WHERE collectionname = incollectionname; 
      
      -- retrieve the item details
      SELECT incollectionname, item, description, pos, lemma
           AS item
        FROM vw_conceptcollections_items
	    WHERE id = varcollectionid;
      
     END IF;
END$$
DELIMITER ;