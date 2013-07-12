/*******************************************
Name          : sp_addConceptCollections

Description   : adds the conceptcollection details to
				tbl_conceptcollections table

Called By     : UI (DBConnectionManager.java)

Create By     : SatyaSwaroop Boddu

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_addConceptCollections;
DELIMITER $$
CREATE PROCEDURE sp_addConceptCollections
(
  IN  incollectionname    VARCHAR(50),
  IN  indescription    TEXT,
  
  IN  inaccessibility  TINYINT,
  IN  incollectionowner   VARCHAR(50),
  OUT errmsg           VARCHAR(255)    
)
BEGIN

	 DECLARE uniqueId  BIGINT;
     DECLARE collectionId VARCHAR(100);
    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- validating the input variables
    IF(incollectionname IS NULL OR incollectionname = "")
	  THEN SET errmsg = "project name cannot be empty.";
    END IF;

    IF EXISTS(SELECT 1 FROM vw_conceptcollections
                WHERE collectionname = incollectionname)
      THEN SET errmsg = "collection name already exists.";
	END IF;

   


    IF(inaccessibility IS NULL)
       THEN SET errmsg = "accessibility cannot be empty";
    END IF;

    IF (incollectionowner IS NULL OR incollectionowner = "")
	 THEN SET errmsg = "concept collection owner cannot be empty";
	END IF;

    IF NOT EXISTS(SELECT 1 FROM vw_quadriga_user
				   WHERE username = incollectionowner)
      THEN SET errmsg = "Invalid owner.Please enter the correct value.";
    END IF; 

    -- Inserting the record into the tbl_project table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
         	SET uniqueId = UUID_SHORT();
            SET collectionId = CONCAT('CC_',CAST(uniqueId AS CHAR));
            INSERT 
              INTO tbl_conceptcollections(id, collectionname,description,collectionowner,accessibility,
                         updatedby,updateddate,createdby,createddate)
			 VALUES (collectionId, incollectionname,indescription,incollectionowner,inaccessibility,
                     incollectionowner,NOW(),incollectionowner,NOW());	
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









