/*******************************************
Name          : sp_getUsers

Description   : Retrieves the details of all users
				with a particular role

Called By     : UI (DBConnectionManager.java)

Create By     : Ram Kumar Kumaresan

Modified Date : 08/06/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getUsers;
DELIMITER $$
CREATE PROCEDURE sp_getUsers
( 
   IN dbroleid    VARCHAR(50),
  OUT errmsg      VARCHAR(50)
)
BEGIN
     
    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQLException occurred";
	
 -- variables used in the procedure
    SET @sqlstmt = "";
    SET @dbrole = "";
	
    -- Retrieve all the active user details
    SET @sqlstmt = "SELECT fullname,username,email,quadrigarole            
				 FROM vw_quadriga_user
	             WHERE quadrigarole like CONCAT('%', ?, '%')";

    SET @dbrole = dbroleid;

	PREPARE squery FROM @sqlstmt;
    EXECUTE squery USING @dbrole;
    DEALLOCATE PREPARE squery;

    -- intialise variables
	SET @sqlstmt = "";
    SET @dbrole = "";

END$$
DELIMITER ;