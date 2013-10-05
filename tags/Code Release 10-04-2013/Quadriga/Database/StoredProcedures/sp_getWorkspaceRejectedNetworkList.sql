/*******************************************
Name          : sp_getWorkspaceRejectedNetworkList

Description   : retrieves the rejected network details
				of a particular workspace

Called By     : UI (sp_getWorkspaceRejectedNetworkList.java)

Create By     : Lohith Dwaraka

Modified Date : 08/08/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getWorkspaceRejectedNetworkList;

DELIMITER $$
CREATE PROCEDURE sp_getWorkspaceRejectedNetworkList
(
  IN  inworkspaceid  VARCHAR(20),
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
	   where workspaceid = inworkspaceid and status = 'REJECTED';
	END IF;
END$$
DELIMITER ;