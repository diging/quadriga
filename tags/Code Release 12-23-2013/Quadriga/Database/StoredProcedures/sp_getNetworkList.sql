/*******************************************
Name          : sp_getNetworkList

Description   : retrieves the network details
				of a particular User

Called By     : UI (DBConnectionNetworkManager.java)

Create By     : Lohith Dwaraka

Modified Date : 08/08/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getNetworkList;

DELIMITER $$
CREATE PROCEDURE sp_getNetworkList
(
  IN  innetworkowner  VARCHAR(20),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(innetworkowner IS NULL OR innetworkowner = "")
     THEN SET errmsg = "Network owner name cannot be empty.";
    END IF;


    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the networks details
	 select networkid,workspaceid,networkname,status
       from tbl_networks
	   where networkowner = innetworkowner ;
	END IF;
END$$
DELIMITER ;