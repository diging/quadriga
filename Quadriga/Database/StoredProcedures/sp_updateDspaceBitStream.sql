/*******************************************
Name          : sp_updateDspaceBitStream

Description   : Update an existing Dspace BitStream data

Called By     : UI (DBConnectionDspaceManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 07/16/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_updateDspaceBitStream;

DELIMITER $$
CREATE PROCEDURE sp_updateDspaceBitStream
(
  IN  inCommunityid     	VARCHAR(20),
  IN  inCollectionid     	VARCHAR(20),
  IN  inItemid     			VARCHAR(20),
  IN  inBitstreamid 		VARCHAR(20),
  IN  inName     			TEXT,
  IN  inSize			    TEXT,
  IN  inMimeType  			TEXT,
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
	ELSEIF NOT EXISTS(SELECT 1 FROM tbl_dspace_item
				WHERE itemid = inItemid)
	  THEN SET errorMessage = "item data is not present";
	ELSEIF NOT EXISTS(SELECT 1 FROM tbl_dspace_bitstream
                WHERE bitstreamid = inBitstreamid)
      THEN SET errorMessage = "bitstream data is not present";
	ELSE 
		START TRANSACTION;
		UPDATE tbl_dspace_bitstream 
		SET communityid = inCommunityid, collectionid = inCollectionid, itemid = inItemid, name = inName, size = inSize, mimeType = inMimeType, updatedby = inUserName, updateddate = NOW()
		WHERE bitstreamid = inBitstreamid;
		COMMIT;
	END IF;

END$$
DELIMITER ;