/*******************************************
Name          : sp_getConceptCollectionCollaborators

Description   : retrieves the users and their
                Collaborator role for a concept

Called By     : UI (DBConnectionManager.java)

Create By     : SatyaSwaroop Boddu

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getConceptCollectionItems;

DELIMITER $$
CREATE PROCEDURE sp_getConceptCollectionItems
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
    
    IF NOT EXISTS (SELECT 1 FROM vw_project
                     WHERE collectionname = incollectionname)
      THEN SET errmsg = "collection name is invalid.";
    END IF;

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
      -- retrieve the projectid id of the project 
      SELECT collectionid INTO varcollectionid FROM vw_conceptcollections
        WHERE collectionname = incollectionname; 
      
      -- retrieve the item details
      SELECT id, item
           AS item
        FROM vw_conceptcollections_items
	    WHERE collectionid = varcollectionid
      GROUP BY id;
     END IF;
END$$
DELIMITER ;