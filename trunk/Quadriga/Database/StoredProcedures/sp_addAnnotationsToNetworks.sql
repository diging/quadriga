/*******************************************
Name          : sp_addAnnotaionsToNetworks

Description   : adds the annotations to
				tbl_network_annotations table

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : SOWJNAYA AMBATI

Modified Date : 10/16/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_addAnnotaionsToNetworks;
DELIMITER $$
CREATE PROCEDURE sp_addAnnotaionsToNetworks	
(
  IN  innetworkid VARCHAR(200),
  IN  innodename    VARCHAR(300),
  IN  inannotaiontext    TEXT,
  IN inuserid VARCHAR(50) ,
  OUT errmsg           VARCHAR(255)    
)
BEGIN
	DECLARE uniqueId  BIGINT;
    DECLARE annotationId VARCHAR(100);
    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
	IF (innetworkid IS NULL OR innetworkid = "")
	 THEN SET errmsg = "Network ID cannot be empty";
	END IF;
      
    IF (inannotaiontext IS NULL OR inannotaiontext = "")
	 THEN SET errmsg = "Annotation text cannot be empty";
	END IF;
	
	
	IF (inuserid IS NULL OR inuserid = "")
	 THEN SET errmsg = "User id cannot be empty";
	END IF;
	
	  
	IF (innodename IS NULL OR innodename = "")
	 THEN SET errmsg = "Node name cannot be empty";
	END IF;

	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
         SET uniqueId = UUID_SHORT();
         SET annotationId = CONCAT('ANNOT_',CAST(uniqueId AS CHAR));
            INSERT 
              INTO tbl_network_annotations(networkid,nodename,annotationtext,annotationid,userid,
                         updatedby,updateddate,createdby,createddate)
			 VALUES (innetworkid,innodename,inannotaiontext,annotationId,inuserid,
                     indictionaryowner,NOW(),indictionaryowner,NOW());	
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









