/*******************************************
Name          : sp_getNetworkPreviousVersionDetails

Description   : retrieves the previous version network details
				

Called By     : UI (DBConnectionEditorManager.java)

Create By     : Lohith Dwaraka

Modified Date : 09/13/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getNetworkPreviousVersionDetails;

DELIMITER $$
CREATE PROCEDURE sp_getNetworkPreviousVersionDetails
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
     -- retrieve the dictionary details
	 select assigneduser,status,updateddate from tbl_network_assigned 
	 where isarchived='1' and networkid=innetworkid;
	
	END IF;
END$$
DELIMITER ;