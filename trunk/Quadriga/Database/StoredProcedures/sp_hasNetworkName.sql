/*******************************************
Name          : sp_hasNetworkName

Description   : retrieves the network details
				of a particular User

Called By     : UI (DBConnectionNetworkManager.java)

Create By     : Lohith Dwaraka

Modified Date : 08/08/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_hasNetworkName;

DELIMITER $$
CREATE PROCEDURE sp_hasNetworkName
(
  IN  innetworkowner  VARCHAR(20),
  IN  innetworkname  VARCHAR(20),	
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
	 select 1
       from tbl_networks
	   where networkowner = innetworkowner and networkname =innetworkname;
	END IF;
END$$
DELIMITER ;