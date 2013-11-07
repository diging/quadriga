/*******************************************
Name          : sp_deleteQuadrigaUser

Description   : Delete quadriga user

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 11/07/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_deleteQuadrigaUser;
DELIMITER $$
CREATE PROCEDURE sp_deleteQuadrigaUser
(
  IN indeleteUser        VARCHAR(50),
  IN inadminUser         VARCHAR(50),
  IN inadminRole         VARCHAR(50),
  IN indeactivatedRole   VARCHAR(50),
  OUT  errmsg         VARCHAR(50)
)
BEGIN

	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

     -- variables used in the procedure
    SET @sqlstmt = "";
    SET @dbrole = "";
    SET @user = "";
 
    -- check if the inputs are empty
    IF (indeleteUser = "" OR indeleteUser IS NULL)
     THEN SET errmsg = "User to be deleted cannot be empty";
	END IF;

    IF (inadminUser = "" OR inadminUser IS NULL)
     THEN SET errmsg = "User name cannot be empty";
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM vw_quadriga_user WHERE username = indeleteUser)
     THEN SET errmsg = "User name is invalid";
    END IF;

    IF NOT EXISTS (SELECT 1 FROM vw_quadriga_user WHERE username = inadminUser)
     THEN SET errmsg = "Username is invalid";
    END IF;

    -- check if the usr is deactivated
    SET @sqlstmt = "IF NOT EXISTS (SELECT 1 FROM vw_quadriga_user WHERE username = ? 
                                   AND quadrigarole like CONCAT('%', ?, '%'))
                        THEN SET @errmsg = 'Only deactivated user can be deleted';
                     END IF;";

    SET @user  = indeleteUser;
    SET @dbrole = indeactivatedRole;

	PREPARE squery FROM @sqlstmt;
    EXECUTE squery USING @user,@dbrole;
    DEALLOCATE PREPARE squery;
    
	SET errmsg = @errmsg;

    -- intialise variables
	SET @sqlstmt = "";
    SET @dbrole = "";

     -- check if the user has admin role
    SET @sqlstmt = "IF NOT EXISTS (SELECT 1 FROM vw_quadriga_user WHERE username = ? 
                                   AND quadrigarole like CONCAT('%', ?, '%'))
                        THEN SET @errmsg = 'Only admin has permissions to delete users';
                     END IF;";

    SET @user  = inadminUser;
    SET @dbrole = inadminRole;

	PREPARE squery FROM @sqlstmt;
    EXECUTE squery USING @user,@dbrole;
    DEALLOCATE PREPARE squery;

	SET errmsg = @errmsg;

    -- intialise variables
	SET @sqlstmt = "";
    SET @dbrole = "";

    IF (errmsg IS NULL)
     THEN 
          SET errmsg = ""; 
		  START TRANSACTION;
          DELETE FROM tbl_quadriga_user 
            WHERE username = indeleteUser;
          IF (errmsg = "")
            THEN COMMIT;
          ELSE ROLLBACK;
          END IF;
    END IF;  
END$$
DELIMITER ;