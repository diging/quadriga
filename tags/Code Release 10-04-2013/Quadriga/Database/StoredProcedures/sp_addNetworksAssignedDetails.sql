/*******************************************
Name          : sp_addNetworksAssignedDetails

Description   : adds the assigned user for network  to
				tbl_network_assigned table

Called By     : UI (DBConnectionEditorManager.java)

Create By     : Lohith Dwaraka

Modified Date : 08/27/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_addNetworksAssignedDetails;
DELIMITER $$
CREATE PROCEDURE sp_addNetworksAssignedDetails
(
  IN  innetworkid    VARCHAR(100),
  IN  inassigneduser    VARCHAR(50),
  OUT errmsg           VARCHAR(255)    
)
BEGIN

	DECLARE uniqueId  BIGINT;
    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
    IF(innetworkid IS NULL OR innetworkid = "")
	  THEN SET errmsg = "Network id cannot be empty.";
    END IF;
    
    IF(inassigneduser IS NULL OR inassigneduser = "")
	  THEN SET errmsg = "User name can't be empty.";
    END IF;
    

    
    
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
			
            INSERT 
              INTO tbl_network_assigned(networkid, assigneduser,status,
                         updatedby,updateddate,createdby,createddate,isarchived)
			 VALUES (innetworkid,inassigneduser,'PENDING',
                     inassigneduser,NOW(),inassigneduser,NOW(),'0');	
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









