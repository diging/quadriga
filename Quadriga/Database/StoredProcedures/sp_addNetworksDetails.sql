/*******************************************
Name          : sp_addNetworksDetails

Description   : adds the network details to
				tbl_networks table

Called By     : UI (DBConnectionNetworkManager.java)

Create By     : Lohith Dwaraka

Modified Date : 08/06/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_addNetworksDetails;
DELIMITER $$
CREATE PROCEDURE sp_addNetworksDetails
(
  IN  innetworkid    VARCHAR(100),
  IN  inworkspaceid    VARCHAR(150),
  IN  innetworkname	  VARCHAR(100),
  IN  innetworkowner    VARCHAR(50),
  IN  inaccessibility   TINYINT  ,
  IN  instatus		  VARCHAR(50) ,
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
    
    IF(innetworkname IS NULL OR innetworkname = "")
	  THEN SET errmsg = "Network name cannot be empty.";
    END IF;
    
    IF(innetworkowner IS NULL OR innetworkowner = "")
	  THEN SET errmsg = "Network name cannot be empty.";
    END IF;
    
    IF(instatus IS NULL OR instatus = "")
	  THEN SET errmsg = "Status cannot be empty.";
    END IF;

    IF(inaccessibility IS NULL)
       THEN SET errmsg = "accessibility cannot be empty";
    END IF;
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;

            INSERT 
              INTO tbl_networks(networkid,workspaceid,networkname,networkowner,accessibility,status,
                         updatedby,updateddate,createdby,createddate)
			 VALUES (innetworkid,inworkspaceid,innetworkname,innetworkowner,inaccessibility,instatus,
                     innetworkowner,NOW(),innetworkowner,NOW());	
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









