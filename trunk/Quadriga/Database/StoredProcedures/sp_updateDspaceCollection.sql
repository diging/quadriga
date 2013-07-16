/*******************************************
Name          : sp_updateDspaceCollection

Description   : Update an existing Dspace collection data

Called By     : UI (DBConnectionDspaceManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 07/16/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_updateDspaceCollection;

DELIMITER $$
CREATE PROCEDURE sp_updateDspaceCollection
(
  IN  inCommunityid     	VARCHAR(20),
  IN  inCollectionid     	VARCHAR(20),
  IN  inName     			TEXT,
  IN  inShortDescription    TEXT,
  IN  inEntityReference    TEXT,
  IN  inHandle    			TEXT, 
  IN  inUserName			VARCHAR(50),
  OUT errorMessage			VARCHAR(50)
)
BEGIN
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errorMessage = "SQL exception has occurred";
		
	IF NOT EXISTS(SELECT 1 FROM tbl_dspace_community
                WHERE communityid = inCommunityid)
      THEN SET errorMessage = "community data is not present";
	ELSEIF NOT EXISTS(SELECT 1 FROM tbl_dspace_collection
                WHERE collectionid = inCollectionid)
      THEN SET errorMessage = "collection data is not present";
	ELSE 
		START TRANSACTION;
		UPDATE tbl_dspace_collection 
		SET  communityid = inCommunityid, name =inName, shortDescription = inShortDescription, entityReference = inEntityReference, handle = inHandle, updatedby = inUserName, updateddate = NOW()
		WHERE collectionid = inCollectionid;
		COMMIT;
	END IF;

END$$
DELIMITER ;