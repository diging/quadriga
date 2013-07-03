/*******************************************
Name          : sp_deactivateUser
`
Description   : Deactivate a given user account

Called By     : UI (DBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 05/22/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_deactivateUser;

DELIMITER $$
CREATE PROCEDURE sp_deactivateUser
( 	IN inUserName         VARCHAR(50),
	IN inDeactiveUserRole VARCHAR(50),
    IN inuser         VARCHAR(50),
	OUT errMsg            VARCHAR(50)
)
BEGIN
     
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errMsg = "SQLException occurred";

    IF(inUserName IS NULL OR inUserName = "")
     THEN SET errMsg = "UserName cannot be empty.";
    END IF;

	IF(inDeactiveUserRole IS NULL OR inDeactiveUserRole = "")
      THEN SET errMsg = "Deactive Role name is empty.";
	 END IF;

    IF(inuser IS NULL OR inuser = "")
       THEN SET errMsg = "Denied by user name is empty.";
	 END IF;

     IF NOT EXISTS(SELECT 1 FROM tbl_quadriga_user
                     WHERE username = inusername)
       THEN SET errMsg = "Denied by user name does not exist.";
     END IF;
   

    START TRANSACTION;

      UPDATE tbl_quadriga_user
	     SET quadrigarole =   if(length(quadrigarole)=0,inDeactiveUserRole,CONCAT(quadrigarole,CONCAT(',',inDeactiveUserRole)))
			,updatedby = inuser
            ,updateddate = NOW()
	   WHERE username = inusername;
     
     IF (errMsg IS NULL)
       THEN COMMIT;
	 ELSE ROLLBACK;
     END IF;

END$$
DELIMITER ;

