/*******************************************
Name          : sp_getInActiveUsers

Description   : Retrieves the details of all Inactive users
				in the Quad DB

Called By     : UI (DBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 05/22/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getInActiveUsers;
DELIMITER $$
CREATE PROCEDURE sp_getInActiveUsers
( 
   IN dbroleid    VARCHAR(50),
  OUT errmsg      VARCHAR(50)
)
BEGIN
     
    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQLException occurred";

	 -- variables used in the procedure
     SET @stmt = "";
	 SET @roleid = "";    

    -- Retrieve all the inactive user details
    SET @stmt = "SELECT fullname,username,email,quadrigarole            
				 FROM vw_quadriga_user
	             WHERE quadrigarole like CONCAT('%', ?, '%')";
    SET @roleid = dbroleid;
	PREPARE sqlquery FROM @stmt;
    EXECUTE sqlquery USING @roleid;
    DEALLOCATE PREPARE sqlquery;
    SET @stmt = "";
	SET @roleid = "";
END$$
DELIMITER ;
