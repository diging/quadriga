/*******************************************
Name          : sp_updateDspaceKeys

Description   : Update the user dspace keys in Quadriga database

Called By     : UI (DBConnectionDspaceManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 08/09/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_updateDspaceKeys;
DELIMITER $$
CREATE PROCEDURE sp_updateDspaceKeys
( 
	IN 	inpublicKey    	VARCHAR(100),
    IN 	inprivateKey   	VARCHAR(100),
	IN 	inusername 		VARCHAR(20),
	OUT errorMessage    VARCHAR(100)
)
BEGIN
     
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errorMessage = "OOPS! Seems the database is down. We have our best minds working on it...";

	IF NOT EXISTS(SELECT 1 FROM tbl_dspace_keys
		WHERE username = inusername)
			THEN SET errorMessage = "User does not have any key saved !";
	ELSE
			UPDATE tbl_dspace_keys 
			SET publickey = inpublicKey,
				privatekey = inprivateKey
			WHERE username = inusername;
	END IF;

END$$
DELIMITER ;