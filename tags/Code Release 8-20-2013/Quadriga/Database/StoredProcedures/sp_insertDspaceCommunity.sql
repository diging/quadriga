/*******************************************
Name          : sp_insertDspaceCommunity

Description   : Insert a new Dspace community data

Called By     : UI (DBConnectionDspaceManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 07/08/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_insertDspaceCommunity;

DELIMITER $$
CREATE PROCEDURE sp_insertDspaceCommunity
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
		
	IF EXISTS(SELECT 1 FROM tbl_dspace_community
                WHERE communityid = inCommunityid)
      THEN SET errorMessage = "community already exists";
	ELSE
		START TRANSACTION;
		INSERT INTO tbl_dspace_community VALUES(inCommunityid, inName, inShortDescription, inIntroductoryText, inHandle, inUserName, NOW(), inUserName, NOW());
		COMMIT;
	END IF;

END$$
DELIMITER ;