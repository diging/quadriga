/*******************************************
Name          : sp_addNetworks_Appellation

Description   : adds the network appellation details to
				tbl_network_appellation_event table

Called By     : UI (DBConnectionNetworkManager.java)

Create By     : Lohith Dwaraka

Modified Date : 08/06/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_addNetworks_Appellation;
DELIMITER $$
CREATE PROCEDURE sp_addNetworks_Appellation
(
  IN  innetworkid    VARCHAR(100),
  IN  inappellationeventid    VARCHAR(50),
  IN  inowner	VARCHAR(50),
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
    
    IF(inappellationeventid IS NULL OR inappellationeventid = "")
	  THEN SET errmsg = "Appellation ID cannot be empty.";
    END IF;
    
    IF(inowner IS NULL OR inowner = "")
	  THEN SET errmsg = "Owner cannot be empty.";
    END IF;
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;

            INSERT 
              INTO tbl_network_appellation_event(networkid,appellationevent,
                         updatedby,updateddate,createdby,createddate)
			 VALUES (innetworkid,inappellationeventid,
                     inowner,NOW(),inowner,NOW());	
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









