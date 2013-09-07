/*******************************************
Name          : sp_updateNetworkStatus

Description   : Update the Network status to approved or reject

Called By     : UI (DBConnectionEditignManager.java)

Create By     : Lohith Dwaraka

Modified Date : 08/30/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_updateNetworkStatus;
DELIMITER $$
CREATE PROCEDURE sp_updateNetworkStatus	
(
  IN  innetworkid    VARCHAR(100),
  IN  instatus    VARCHAR(50),
  OUT errmsg           VARCHAR(255)    
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
    IF(innetworkid IS NULL OR innetworkid = "")
      THEN SET errmsg = "Network id cannot be empty.";
    END IF;

    IF (instatus IS NULL OR instatus = "")
	 THEN SET errmsg = "Status cannot be empty";
	END IF;
	
    
    IF NOT EXISTS(SELECT 1 FROM tbl_networks
				   WHERE networkid = innetworkid)
     
      THEN SET errmsg = "Network not present";
    END IF; 
	
    -- Updating the record into the tbl_networks table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
			UPDATE 
			tbl_networks SET status= instatus WHERE networkid=innetworkid;
			UPDATE
			tbl_network_assigned SET status= instatus WHERE networkid=innetworkid;
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









