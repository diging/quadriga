/*******************************************
Name          : sp_addUserRequest

Description   : adds the user request to
				tbl_quadriga_user_requests table

Called By     : UI (DBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 05/30/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_addUserRequest;
DELIMITER $$
CREATE PROCEDURE sp_addUserRequest
(
  IN  inusername    VARCHAR(50),
  OUT errmsg           VARCHAR(255)    
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- validating the input variables
    IF(inusername IS NULL OR inusername = "")
	  THEN SET errmsg = "User Name cannot be empty.";
    END IF;

    IF EXISTS(SELECT 1 FROM tbl_quadriga_user_requests
                WHERE username = inusername)
      THEN SET errmsg = "User Request already exists.";
	END IF;


    -- Inserting the record into the tbl_quadriga_user_requests table
	START TRANSACTION;
		INSERT 
             INTO tbl_quadriga_user_requests(username,
                         updatedby,updateddate,createdby,createddate)
			 VALUES (inusername,
                     inusername,NOW(),inusername,NOW());	
		 IF (errmsg IS NULL)
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
END$$
DELIMITER ;