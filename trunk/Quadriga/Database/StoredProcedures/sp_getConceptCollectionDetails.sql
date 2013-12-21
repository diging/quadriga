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
   
	IF NOT EXISTS(SELECT 1 FROM vw_conceptcollection WHERE conceptcollectionid = incollectionid)
      THEN SET errmsg = "No such conceptcollection"; 
    END IF;
    
    IF NOT EXISTS(SELECT 1 FROM vw_conceptcollection	
                     WHERE conceptcollectionid = incollectionid and  collectionowner = inusername) AND NOT EXISTS(SELECT 1 FROM vw_conceptcollection_collaborator	WHERE conceptcollectionid = incollectionid and  collaboratoruser = inusername)
      THEN SET errmsg = "User dont have access to the collection"; 
    END IF;
    
    

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     
      -- retrieve the item details
      SELECT vw_conceptcollection_items.item, vw_conceptcollection_items.description, vw_conceptcollection_items.pos, vw_conceptcollection_items.lemma, vw_conceptcollection.conceptcollectionid, vw_conceptcollection.collectionname, 
            vw_conceptcollection.description,vw_conceptcollection.collectionowner
        FROM vw_conceptcollection LEFT JOIN vw_conceptcollection_items
	    ON vw_conceptcollection_items.conceptcollectionid = vw_conceptcollection.conceptcollectionid WHERE vw_conceptcollection.conceptcollectionid = incollectionid;
      
     END IF;
END$$
DELIMITER ;