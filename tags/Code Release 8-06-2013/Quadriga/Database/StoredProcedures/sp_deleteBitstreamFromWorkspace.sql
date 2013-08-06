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
  IN  inWorkspaceid     	VARCHAR(50),
  IN  inBitstreamid 		VARCHAR(20),
  IN  inUserName			VARCHAR(50),
  OUT errorMessage			VARCHAR(100)
)
BEGIN
	-- the error handler for any sql exception
--    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
  --    SET errorMessage = "SQL exception has occurred";

-- check if user has access to the project and workspace
	 IF EXISTS(SELECT 1 FROM tbl_project 
	 WHERE projectid = (SELECT projectid FROM tbl_project_workspace WHERE workspaceid = inWorkspaceid)
	 AND (projectowner = inUsername OR projectid in (SELECT projectid from tbl_project_collaborator where collaboratoruser = inUsername)))

		THEN 
			
			IF NOT EXISTS(SELECT 1 FROM tbl_workspace
                WHERE workspaceid = inWorkspaceid)
			THEN SET errorMessage = "workspace is not present";
				ELSEIF NOT EXISTS(SELECT 1 FROM tbl_dspace_bitstream
                WHERE bitstreamid  = inBitstreamid)
				THEN SET errorMessage = "bitstream is not present";
			ELSE 
				START TRANSACTION;
				DELETE FROM tbl_workspace_dspace WHERE workspaceid = inWorkspaceid
				AND bitstreamid = inBitstreamid;
				COMMIT;
			END IF;
	ELSE 
			SET errorMessage = "This action has been logged. Please don't try to hack into the system !!!";
	END IF;

END$$
DELIMITER ;