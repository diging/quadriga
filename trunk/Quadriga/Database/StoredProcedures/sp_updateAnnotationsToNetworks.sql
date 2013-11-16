/*******************************************
Name          : sp_updateAnnotaionsToNetworks

Description   : adds the annotations to
				tbl_network_annotations table

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : SOWJNAYA AMBATI

Modified Date : 10/16/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_updateAnnotaionsToNetworks;
DELIMITER $$
CREATE PROCEDURE sp_updateAnnotaionsToNetworks	
(
  IN  inannotationtext    TEXT,
  IN  inannotationid	VARCHAR(50),
  OUT errmsg           VARCHAR(255)    
)
BEGIN
	DECLARE uniqueId  BIGINT;
    DECLARE annotationId VARCHAR(100);
    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
    SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
	
      
    IF (inannotaiontext IS NULL OR inannotaiontext = "")
	 THEN SET errmsg = "Annotation text cannot be empty";
	END IF;
	  
	IF (annotationId IS NULL OR annotationId = "")
	 THEN SET errmsg = "Object id cannot be empty";
	END IF;

	
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
            UPDATE 
               tbl_network_annotations set annotationtext = inannotationtext
			 WHERE annotationid =inannotationId;	
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









