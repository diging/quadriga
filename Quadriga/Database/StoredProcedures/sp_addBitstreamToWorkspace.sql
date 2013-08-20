/*******************************************
Name          : sp_addBitstreamToWorkspace

Description   : Add a bitstream data to workspace

Called By     : UI (DBConnectionDspaceManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 07/09/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_addBitstreamToWorkspace;

DELIMITER $$
CREATE PROCEDURE sp_addBitstreamToWorkspace
(
  IN  inWorkspaceid     	VARCHAR(50),
  IN  inCommunityid 		VARCHAR(20),
  IN  inCollectionid 		VARCHAR(20),
  IN  inItemid		 		VARCHAR(20),
  IN  inBitstreamid 		VARCHAR(20),
  IN  inUserName			VARCHAR(50),
  OUT errorMessage			VARCHAR(50)
)
BEGIN
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errorMessage = "SQL exception has occurred";
	
    -- check if the workspace exists
	 IF EXISTS(SELECT 1 FROM tbl_workspace WHERE workspaceid = inWorkspaceid)
		THEN 		
					START TRANSACTION;
					INSERT INTO tbl_workspace_dspace VALUES(inWorkspaceid, inCommunityid, inCollectionid, inItemid, inBitstreamid, inUserName, NOW());
					COMMIT;
	ELSE 
			SET errorMessage = "This action has been logged. Please don't try to hack into the system !!!";
	END IF;

END$$
DELIMITER ;