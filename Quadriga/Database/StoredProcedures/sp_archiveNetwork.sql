/*******************************************
Name          : sp_archiveNetwork

Description   : Archive the network

Called By     : UI (DBConnectionNetworkManager.java)

Create By     : Lohith Dwaraka

Modified Date : 09/11/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_archiveNetwork;
DELIMITER $$
CREATE PROCEDURE sp_archiveNetwork	
(
  IN  innetworkid    VARCHAR(100),
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
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
      	 START TRANSACTION;

			UPDATE 
			tbl_network_statements SET isarchived=2 WHERE networkid =innetworkid and isarchived =1;

			UPDATE 
			tbl_network_statements SET isarchived=1 WHERE networkid =innetworkid and isarchived =0;
			
			UPDATE 
			tbl_network_assigned SET isarchived=2 WHERE networkid =innetworkid and isarchived =1;
			
			UPDATE 
			tbl_network_assigned SET isarchived=1 WHERE networkid =innetworkid and isarchived =0;
			
			

		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









