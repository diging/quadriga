/*******************************************
Name          : sp_getAllBitStreamReferences

Description   : Retrieves the ids of community,
				collection, item, bitstream ids associated
				with a workspace

Called By     : UI (DBConnectionDspaceManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 07/17/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getAllBitStreamReferences;

DELIMITER $$
CREATE PROCEDURE sp_getAllBitStreamReferences
( 
   IN  inWorkspaceid     VARCHAR(50),
   IN  inUsername        VARCHAR(20),
   OUT errorMessage      VARCHAR(100)
)
BEGIN

	-- check if user has access to the project and workspace
	 IF EXISTS(SELECT 1 FROM tbl_project 
	 WHERE projectid = (SELECT projectid FROM tbl_project_workspace WHERE workspaceid = inWorkspaceid)
	 AND (projectowner = inUsername OR projectid in (SELECT projectid from tbl_project_collaborator where collaboratoruser = inUsername)))

		THEN 
			
			SELECT com.communityid as Communityid, col.collectionid as Collectionid, i.itemid as Itemid, b.bitstreamid as Bitstreamid
			FROM tbl_dspace_bitstream AS b, 
			tbl_dspace_community as com,
			tbl_dspace_collection as col,
			tbl_dspace_item as i
			WHERE b.bitstreamid in (
			SELECT bitstreamid FROM tbl_workspace_dspace WHERE workspaceid = inWorkspaceid)
			AND b.communityid = com.communityid
			AND b.collectionid = col.collectionid
			AND b.itemid = i.itemid;
	ELSE 
			SET errorMessage = "This action has been logged. Please don't try to hack into the system !!!";
	END IF;

END$$
DELIMITER ;
