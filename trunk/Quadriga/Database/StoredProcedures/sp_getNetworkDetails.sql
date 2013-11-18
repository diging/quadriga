/*******************************************
Name          : sp_getNetworkDetails

Description   : retrieves the network status
				of a particular network

Called By     : UI (DBConnectionNetworkManager.java)

Create By     : Lohith Dwaraka

Modified Date : 08/08/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getNetworkDetails;

DELIMITER $$
CREATE PROCEDURE sp_getNetworkDetails
(
  IN  innetworkid  VARCHAR(150),	
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
     -- retrieve the dictionary details
	 select networkid,workspaceid,networkname,status,networkowner
       from tbl_networks
	   where networkid =innetworkid;
	END IF;
END$$
DELIMITER ;