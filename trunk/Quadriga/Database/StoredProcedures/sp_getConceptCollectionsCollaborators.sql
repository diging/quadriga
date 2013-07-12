/*******************************************
Name          : sp_getConceptCollectionCollaborators

Description   : retrieves the users and their
                Collaborator role for a concept

Called By     : UI (DBConnectionManager.java)

Create By     : SatyaSwaroop Boddu

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getConceptCollectionCollaborators;

DELIMITER $$
CREATE PROCEDURE sp_getConceptCollectionCollaborators
(
  IN incollectionid  VARCHAR(100),
  OUT errmsg     VARCHAR(255)
)
BEGIN
    -- declare local variables
  --   DECLARE varcollectionname VARCHAR(5) ;

	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(incollectionid IS NULL OR incollectionid = "")
     THEN SET errmsg = "collection id cannot be empty.";
    END IF;
    
   -- IF NOT EXISTS (SELECT 1 FROM vw_conceptcollections_collaborator
   --                  WHERE collectionname = incollectionname)
   --   THEN SET errmsg = "collection name is invalid.";
   -- END IF;

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
      -- retrieve the collection id of the project 
    -- SELECT collectionname INTO varcollectionname FROM vw_conceptcollections
     --   WHERE id = incollectionid; 
      
      -- retrieve the collaborator details
      SELECT collectionid,collaboratoruser, 
         GROUP_CONCAT(collaboratorrole SEPARATOR ',')  AS 'Collaboratorrole'
        FROM vw_conceptcollections_collaborator
	    WHERE collectionid = incollectionid;
      
     END IF;
END$$
DELIMITER ;