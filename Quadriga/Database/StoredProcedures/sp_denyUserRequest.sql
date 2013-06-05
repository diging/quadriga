/*******************************************
Name          : sp_denyUserRequest

Description   : Used to deny the user request

Called By     : UI (DBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 05/24/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_denyUserRequest;

DELIMITER $$
CREATE PROCEDURE sp_denyUserRequest
( 	IN inusername VARCHAR(50),
	IN inadminid  VARCHAR(50),
	OUT errmsg    VARCHAR(50)
)
BEGIN
     
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQLException occurred";

      IF(inusername IS NULL OR inusername = "")
       THEN SET errMsg = "Error. Username is empty";
	 END IF;
	 
	 IF(inadminid IS NULL OR inadminid = "")
       THEN SET errMsg = "Error. Admin username is empty";
	 END IF;
	 
	 IF NOT EXISTS(SELECT 1 FROM tbl_quadriga_user_requests
                     WHERE username = inusername)
       THEN SET errMsg = "No open request found for the user";
     END IF;
	 
	-- insert into the denied log table
    START TRANSACTION;

	  -- insert into the denied table 
	  INSERT INTO tbl_quadriga_user_denied
            (fullname,username,passwd,email,deniedby,updatedby,updateddate,createdby,createddate)
		(SELECT fullname, username,passwd,email,inadminid,inadminid,NOW(),inadminid,NOW()
	       FROM tbl_quadriga_user_requests 
	      WHERE username = inusername);
	
	  -- delete the user request
	  DELETE FROM tbl_quadriga_user_requests WHERE username = inusername;
     
      IF(errmsg IS NULL)
         THEN COMMIT;
	  ELSE ROLLBACK;
     END IF;

END$$
DELIMITER ;
