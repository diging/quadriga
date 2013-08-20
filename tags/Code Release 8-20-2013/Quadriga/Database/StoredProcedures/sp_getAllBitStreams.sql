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

	-- check if the workspace exists
	 IF EXISTS(SELECT 1 FROM tbl_workspace WHERE workspaceid = inWorkspaceid)
	 
		THEN 
			
			SELECT communityid as Communityid, collectionid as Collectionid, itemid as Itemid, bitstreamid as Bitstreamid
			FROM tbl_workspace_dspace WHERE workspaceid = inWorkspaceid;
	
	ELSE 
			SET errorMessage = "No such Workspace exists. This action has been logged. Please don't try to hack into the system !!!";
	END IF;

END$$
DELIMITER ;
