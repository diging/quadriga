/*******************************************
Name          : sp_getNetworkAssignedtoOtherUser

Description   : retrieves the network details
				of a other editors

Called By     : UI (DBConnectionEditorManager.java)

Create By     : Lohith Dwaraka

Modified Date : 08/13/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getNetworkAssignedtoOtherUser;

DELIMITER $$
CREATE PROCEDURE sp_getNetworkAssignedtoOtherUser
(
  IN  inusername  VARCHAR(20),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(inusername IS NULL OR inusername = "")
     THEN SET errmsg = "User Name cannot be empty.";
    END IF;


    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the dictionary details
	 select networkid,workspaceid,networkname,networkowner,status 
	from tbl_networks where networkid IN (
	
	select networkid from tbl_network_assigned where assigneduser <>
	inusername
	) and status = 'ASSIGNED';
	
	END IF;
END$$
DELIMITER ;