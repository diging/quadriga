/******************************************************
Name          : sp_deleteBitstreamFromWorkspace

Description   : Remove a bitstream from workspace

Called By     : UI (DBConnectionDspaceManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 07/15/2013

******************************************************/

DROP PROCEDURE IF EXISTS sp_deleteBitstreamFromWorkspace;

DELIMITER $$
CREATE PROCEDURE sp_deleteBitstreamFromWorkspace
(
  IN  inWorkspaceid     	VARCHAR(150),
  IN  inBitstreamid 		VARCHAR(20),
  IN  inUserName			VARCHAR(50),
  OUT errorMessage			VARCHAR(100)
)
BEGIN
	-- check if the workspace exists
	 IF EXISTS(SELECT 1 FROM tbl_workspace WHERE workspaceid = inWorkspaceid)
		THEN 	
				START TRANSACTION;
				DELETE FROM tbl_workspace_dspace WHERE workspaceid = inWorkspaceid
				AND bitstreamid = inBitstreamid;
				COMMIT;
	ELSE 
			SET errorMessage = "This action has been logged. Please don't try to hack into the system !!!";
	END IF;

END$$
DELIMITER ;