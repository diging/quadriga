/*******************************************
Name          : sp_getUserRequests

Description   : Used to get all the open user requests

Called By     : UI (DBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 05/24/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getUserRequests;

DELIMITER $$
CREATE PROCEDURE sp_getUserRequests
(	
  OUT errmsg      VARCHAR(50)
)
BEGIN
     
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQLException occurred";

    IF(errmsg IS NULL)
     THEN SELECT fullname, username, email
	        FROM tbl_quadriga_user_requests;    
	END IF;
END$$
DELIMITER ;