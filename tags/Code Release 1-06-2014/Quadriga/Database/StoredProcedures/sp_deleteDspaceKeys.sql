/*******************************************
Name          : sp_deleteDspaceKeys

Description   : Delete the user dspace keys from Quadriga database

Called By     : UI (DBConnectionDspaceManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 08/21/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_deleteDspaceKeys;
DELIMITER $$
CREATE PROCEDURE sp_deleteDspaceKeys
( 
	IN inUsername 	VARCHAR(20),
	OUT errmsg      VARCHAR(100)
)
BEGIN
     
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "OOPS! Seems the database is down. We have our best minds working on it...";

    DELETE FROM tbl_dspace_keys WHERE username = inUsername;

END$$
DELIMITER ;