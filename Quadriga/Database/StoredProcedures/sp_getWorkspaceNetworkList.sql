/*******************************************
Name          : sp_getWorkspaceNetworkList

Description   : retrieves the network details
				of a particular workspace

Called By     : UI (DBConnectionNetworkManager.java)

Create By     : Lohith Dwaraka

Modified Date : 08/08/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getWorkspaceNetworkList;

DELIMITER $$
CREATE PROCEDURE sp_getWorkspaceNetworkList
(
  IN  inworkspaceid  VARCHAR(150),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(inworkspaceid IS NULL OR inworkspaceid = "")
     THEN SET errmsg = "Workspace id cannot be empty.";
    END IF;


    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the dictionary details
	 select networkid,networkname,networkowner,status
       from tbl_networks
	   where workspaceid = inworkspaceid;
	END IF;
END$$
DELIMITER ;