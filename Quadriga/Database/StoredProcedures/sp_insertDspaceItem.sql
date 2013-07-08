/*******************************************
Name          : sp_insertDspaceItem

Description   : Insert a new Dspace item data

Called By     : UI (DBConnectionDspaceManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 07/08/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_insertDspaceItem;

DELIMITER $$
CREATE PROCEDURE sp_insertDspaceItem
(
  IN  inCommunityid     	VARCHAR(20),
  IN  inCollectionid     	VARCHAR(20),
  IN  inItemid     			VARCHAR(20),
  IN  inName     			TEXT,
  IN  inHandle			    TEXT,
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
	ELSEIF (SELECT 1 FROM tbl_dspace_item
                WHERE itemid = inItemid)
      THEN SET errorMessage = "item already exists";
	ELSE 
		START TRANSACTION;
		INSERT INTO tbl_dspace_item VALUES(inCommunityid, inCollectionid, inItemid, inName, inHandle, inUserName, NOW(), inUserName, NOW());
		COMMIT;
	END IF;

END$$
DELIMITER ;