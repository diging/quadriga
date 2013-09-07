/*******************************************
Name          : sp_addNetworks_Relation

Description   : adds the network relation details to
				tbl_network_relation_event table

Called By     : UI (DBConnectionNetworkManager.java)

Create By     : Lohith Dwaraka

Modified Date : 08/06/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_addNetworks_Relation;
DELIMITER $$
CREATE PROCEDURE sp_addNetworks_Relation
(
  IN  innetworkid    VARCHAR(100),
  IN  inrelationeventid    VARCHAR(50),
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
    
    IF(inrelationeventid IS NULL OR inrelationeventid = "")
	  THEN SET errmsg = "Relation ID cannot be empty.";
    END IF;
    
    IF(inowner IS NULL OR inowner = "")
	  THEN SET errmsg = "Owner cannot be empty.";
    END IF;
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;

            INSERT 
              INTO tbl_network_relation_event(networkid,relationeventid,
                         updatedby,updateddate,createdby,createddate)
			 VALUES (innetworkid,inrelationeventid,
                     inowner,NOW(),inowner,NOW());	
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









