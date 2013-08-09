/*******************************************
Name          : sp_addDspaceKeys

Description   : Add the user dspace keys to Quadriga database

Called By     : UI (DBConnectionDspaceManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 08/08/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_addDspaceKeys;
DELIMITER $$
CREATE PROCEDURE sp_addDspaceKeys
( 
	IN publicKey    VARCHAR(100),
    IN privateKey   VARCHAR(100),
	IN username 	VARCHAR(20),
	OUT errmsg      VARCHAR(100)
)
BEGIN
     
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "OOPS! Seems the database is down. We have our best minds working on it...";

    INSERT INTO tbl_quadriga_dspace_keys values
	(username,publicKey,privateKey);

END$$
DELIMITER ;