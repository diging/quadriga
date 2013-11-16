/*******************************************
Name          : sp_getAnnotations

Description   : get the annotations from
				tbl_network_annotations table

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : SOWJNAYA AMBATI

Modified Date : 10/16/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getAnnotations;
DELIMITER $$
CREATE PROCEDURE sp_getAnnotations	
(
  IN inuserid VARCHAR(100),
  IN  inid    VARCHAR(300),
  IN inobjecttype VARCHAR(50),
  OUT errmsg           VARCHAR(255)    
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
	
	IF (inuserid IS NULL OR inuserid = "")
	 THEN SET errmsg = "User id cannot be empty";
	END IF;
	
	  
	IF (inid IS NULL OR inid = "")
	 THEN SET errmsg = "Object id cannot be empty";
	END IF;

	IF (inobjecttype IS NULL OR inobjecttype = "")
	 THEN SET errmsg = "Object Type cannot be empty";
	END IF;
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
        SELECT annotationtext,annotationid FROM tbl_network_annotations where id = inid and userid = inuserid
         and objecttype = inobjecttype;
    END IF;
END$$
DELIMITER ;









