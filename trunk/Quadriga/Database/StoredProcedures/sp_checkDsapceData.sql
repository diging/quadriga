/*******************************************
Name          : sp_checkDsapceData

Description   : Check whether the community, collection and
				item data are already existing in Quadriga

Called By     : UI (DBConnectionDspaceManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 07/08/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_checkDsapceData;

DELIMITER $$
CREATE PROCEDURE sp_checkDsapceData
(
  IN  inCommunityid     VARCHAR(20),
  IN  inCollectionid     VARCHAR(20),
  IN  inItemid     VARCHAR(20),
  OUT dataStatus         VARCHAR(255)   
)
BEGIN

	IF EXISTS(SELECT 1 FROM tbl_dspace_community
                WHERE communityid = inCommunityid)
      THEN 
		SET dataStatus = "community exists";

		IF EXISTS(SELECT 1 FROM tbl_dspace_collection
                WHERE collectionid = inCollectionid)
			THEN 
				SET dataStatus = "collection exists";
				
				IF EXISTS(SELECT 1 FROM tbl_dspace_item
						WHERE itemid = inItemid)
					THEN 
						SET dataStatus = "item exists";
				END IF;

		END IF;
	
	END IF;

END$$
DELIMITER ;