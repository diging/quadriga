/*******************************************
Name          : sp_archiveNetworkStatement

Description   : Archive the network statement

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 09/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_archiveNetworkStatement;
DELIMITER $$
CREATE PROCEDURE sp_archiveNetworkStatement	
(
  IN  innetworkid    VARCHAR(100),
  IN  instatementid    VARCHAR(300),
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

    IF (instatementid IS NULL OR instatementid = "")
	 THEN SET errmsg = "Statement ids cannot be empty";
	END IF;
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
      	 START TRANSACTION;
      	 IF EXISTS(SELECT 1 FROM tbl_network_statements
				   WHERE statementid= instatementid and networkid =innetworkid and isarchived =1)
		 THEN 
         
			UPDATE 
			tbl_network_statements SET isarchived=2 WHERE statementid=instatementid and networkid =innetworkid and isarchived =1;
		 END IF;
		 IF EXISTS(SELECT 1 FROM tbl_network_statements
				   WHERE statementid= instatementid and networkid =innetworkid and isarchived =0)
		 THEN
			UPDATE 
			tbl_network_statements SET isarchived=1 WHERE statementid=instatementid and networkid =innetworkid and isarchived =0;
		 END IF;
		 
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









