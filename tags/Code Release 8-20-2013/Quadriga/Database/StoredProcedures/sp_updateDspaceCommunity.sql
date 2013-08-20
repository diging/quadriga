/*******************************************
Name          : sp_updateDspaceCommunity

Description   : Update an existing Dspace community data

Called By     : UI (DBConnectionDspaceManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 07/16/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_updateDspaceCommunity;

DELIMITER $$
CREATE PROCEDURE sp_updateDspaceCommunity
(
  IN  inCommunityid     	VARCHAR(20),
  IN  inName     			TEXT,
  IN  inShortDescription    TEXT,
  IN  inIntroductoryText    TEXT,
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
      THEN SET errorMessage = "community does not exist";
	ELSE
		START TRANSACTION;
		UPDATE tbl_dspace_community 
		SET	name = inName, shortDescription = inShortDescription, introductoryText = inIntroductoryText, handle = inHandle, updatedby = inUserName, updateddate = NOW()
		WHERE communityid = inCommunityid;
		COMMIT;
	END IF;

END$$
DELIMITER ;