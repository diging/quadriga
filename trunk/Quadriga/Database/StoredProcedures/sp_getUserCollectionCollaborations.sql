/*******************************************
Name          : sp_getUserCollectionCollaborations

Description   : retrieves the users and their
                Collaborator role for a concept

Called By     : UI (DBConnectionManager.java)

Create By     : SatyaSwaroop Boddu

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getUserCollectionCollaborations;

DELIMITER $$
CREATE PROCEDURE sp_getUserCollectionCollaborations
(
  IN inusername  VARCHAR(20),
  OUT errmsg     VARCHAR(255)
)
BEGIN
    

	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(inusername IS NULL OR inusername = "")
     THEN SET errmsg = "user name cannot be empty.";
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM vw_conceptcollections_collaborator
                     WHERE collaboratoruser = inusername)
      THEN SET errmsg = "user name is invalid.";
    END IF;

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     
      -- retrieve the collaboration details
     
      select collectionname, description, collectionid from vw_conceptcollections where collectionid IN (select collectionid from vw_conceptcollections_collaborator where collaboratoruser = inusername);
     END IF;
     
END$$
DELIMITER ;