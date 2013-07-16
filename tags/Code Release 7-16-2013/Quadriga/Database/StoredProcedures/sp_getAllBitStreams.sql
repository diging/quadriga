/*******************************************
Name          : sp_getAllBitStreams

Description   : Retrieves the details of all bitstreams
				associated with the workspace

Called By     : UI (DBConnectionDspaceManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 07/12/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getAllBitStreams;

DELIMITER $$
CREATE PROCEDURE sp_getAllBitStreams
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
			
			SELECT com.name as Community, col.name as Collection, i.name as Item, b.name as Bitstream, b.bitstreamid as Bitstreamid 
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
