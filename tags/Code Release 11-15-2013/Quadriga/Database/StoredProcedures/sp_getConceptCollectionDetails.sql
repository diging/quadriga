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
  IN incollectionid  VARCHAR(100),
  IN inusername	     VARCHAR(40),	
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
   	
    IF(inusername IS NULL OR inusername = "")
     THEN SET errmsg = "username cannot be empty.";
    END IF;
   
	IF NOT EXISTS(SELECT 1 FROM vw_conceptcollections WHERE id = incollectionid)
      THEN SET errmsg = "No such conceptcollection"; 
    END IF;
    
    IF NOT EXISTS(SELECT 1 FROM vw_conceptcollections	
                     WHERE id = incollectionid and  collectionowner = inusername) AND NOT EXISTS(SELECT 1 FROM vw_conceptcollections_collaborator	WHERE collectionid = incollectionid and  collaboratoruser = inusername)
      THEN SET errmsg = "User dont have access to the collection"; 
    END IF;
    
    

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     
      -- retrieve the item details
      SELECT vw_conceptcollections_items.item, vw_conceptcollections_items.description, vw_conceptcollections_items.pos, vw_conceptcollections_items.lemma, vw_conceptcollections.id, vw_conceptcollections.collectionname, 
            vw_conceptcollections.description,vw_conceptcollections.collectionowner
        FROM vw_conceptcollections LEFT JOIN vw_conceptcollections_items
	    ON vw_conceptcollections_items.id = vw_conceptcollections.id WHERE vw_conceptcollections.id = incollectionid;
      
     END IF;
END$$
DELIMITER ;