/*******************************************
Name          : sp_getDspaceKeys

Description   : Get the user dspace keys from Quadriga database

Called By     : UI (DBConnectionDspaceManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 08/08/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getDspaceKeys;
DELIMITER $$
CREATE PROCEDURE sp_getDspaceKeys
( 
	IN inusername 	VARCHAR(20),
	OUT errmsg      VARCHAR(100)
)
BEGIN
     
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "OOPS! Seems the database is down. We have our best minds working on it...";

    SELECT publicKey,privateKey FROM tbl_dspace_keys
	WHERE username = inusername;

END$$
DELIMITER ;