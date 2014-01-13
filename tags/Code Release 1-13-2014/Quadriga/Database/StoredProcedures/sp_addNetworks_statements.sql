/*******************************************
Name          : sp_addNetworks_statements

Description   : adds the network relation details to
				tbl_network_relation_event table

Called By     : UI (DBConnectionNetworkManager.java)

Create By     : Lohith Dwaraka

Modified Date : 08/06/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_addNetworks_statements;
DELIMITER $$
CREATE PROCEDURE sp_addNetworks_statements
(
  IN  innetworkid    VARCHAR(100),
  IN  inid    VARCHAR(50),
  IN  instatementtype    VARCHAR(50),
  IN  inistop    VARCHAR(10),
  IN  inisarchived    VARCHAR(10),
  IN  inowner	VARCHAR(50),
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
    
    IF(inid IS NULL OR inid = "")
	  THEN SET errmsg = "ID cannot be empty.";
    END IF;
    
    IF(instatementtype IS NULL OR instatementtype = "")
	  THEN SET errmsg = "Statement type cannot be empty.";
    END IF;
    
    IF(inistop IS NULL OR inistop = "")
	  THEN SET errmsg = "isTop cannot be empty.";
    END IF;
    
    IF(inowner IS NULL OR inowner = "")
	  THEN SET errmsg = "Owner cannot be empty.";
    END IF;
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;

            INSERT 
              INTO tbl_network_statements(networkid,statementid,statementtype,istop,isarchived,
                         updatedby,updateddate,createdby,createddate)
			 VALUES (innetworkid,inid,instatementtype,inistop,inisarchived,
                     inowner,NOW(),inowner,NOW());	
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









