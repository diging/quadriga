/*******************************************
Name          : sp_approveUserRequest

Description   : Used to approve the user request

Called By     : UI (DBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 05/24/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_approveUserRequest;

DELIMITER $$
CREATE PROCEDURE sp_approveUserRequest
( 	IN inusername VARCHAR(50),
	IN userroles  VARCHAR(50),
    IN user       VARCHAR(20),
	OUT errmsg    VARCHAR(50)
)
BEGIN
     
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQLException occurred";

    START TRANSACTION;

	-- insert into the user table
	INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate) 
	(SELECT fullname, username,passwd,email,userroles,user,NOW(),user,NOW()
	   FROM tbl_quadriga_user_requests 
	  WHERE username = inusername);
	
	-- delete the user request
	DELETE FROM tbl_quadriga_user_requests WHERE username = inusername;

    IF (errmsg IS NULL)
      THEN COMMIT;
    ELSE ROLLBACK;
    END IF;

END
$$
DELIMITER ;