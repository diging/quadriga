/*******************************************
Name          : sp_getNetworkTopNodes

Description   : retrieves the network top nodes
				of a particular network

Called By     : UI (DBConnectionNetworkManager.java)

Create By     : Lohith Dwaraka

Modified Date : 08/08/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getNetworkTopNodes;

DELIMITER $$
CREATE PROCEDURE sp_getNetworkTopNodes
(
  IN  innetworkid  VARCHAR(20),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(innetworkid IS NULL OR innetworkid = "")
     THEN SET errmsg = "Network id cannot be empty.";
    END IF;


    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the network top node
	 select id,statementtype
       from tbl_network_statements
	   where networkid = innetworkid and istop=1 and isarchived=0;
	END IF;
END$$
DELIMITER ;