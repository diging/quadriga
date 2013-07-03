/*******************************************
Name          : sp_updateUserRoles
`
Description   : Change the roles for a given user account

Called By     : UI (DBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 05/22/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_updateUserRoles;

DELIMITER $$
CREATE PROCEDURE sp_updateUserRoles
( 	IN inUserName     VARCHAR(50),
	IN inNewUserRoles VARCHAR(50),
	IN inuser         VARCHAR(20),
	OUT errMsg        VARCHAR(50)
)
BEGIN
     
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errMsg = "SQLException occurred";

    IF(inUserName IS NULL OR inUserName = "")
      THEN SET errMsg = "username cannot be empty.";
    END IF;

    IF NOT EXISTS(SELECT 1 FROM tbl_quadriga_user
                    WHERE username = inUserName)
     THEN SET errMsg = "user does not exists.";
    END IF;

    IF(inNewUserRoles IS NULL OR inNewUserRoles = "" )
      THEN SET errMsg = "User associated roles cannot be empty";
    END IF;
    
    IF(inuser IS NULL OR inuser = "")
      THEN SET errMsg = "user cannot be empty";
    END IF;

    IF NOT EXISTS(SELECT 1 FROM tbl_quadriga_user 
                    WHERE username = inUserName)
	 THEN SET errMsg = "user entered is invalid.";
	END IF;

    START TRANSACTION;

       UPDATE tbl_quadriga_user
	      SET quadrigarole =  inNewUserRoles
		     ,updatedby = inuser
             ,updateddate = NOW()
	    WHERE username = inusername;
   
    IF (errmsg IS NULL)
     THEN COMMIT;
    ELSE ROLLBACK;
    END IF;

END$$
DELIMITER ;

