/*******************************************
Name          : sp_updateConceptCollection

Description   : updates concept collection details to
				tbl_conceptcollections table

Called By     : UI (DBConnectionManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 09/11/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_updateConceptCollection;
DELIMITER $$
CREATE PROCEDURE sp_updateConceptCollection
(
  IN   incollectionname VARCHAR(50),
  IN   indescription    TEXT,
  IN   inaccessibility  VARCHAR(50),
  IN   inuser           VARCHAR(50),
  IN   incollectionid   VARCHAR(50),
  OUT  errmsg           VARCHAR(255)
)
BEGIN
    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
    
    -- validating the input parameters
    IF (incollectionname IS NULL OR incollectionname = "")
      THEN SET errmsg = "Collection name cannot be empty.";
    END IF;

	IF (indescription IS NULL OR indescription = "")
      THEN SET errmsg = "Collection description cannot be empty.";
	END IF;

    IF (inaccessibility IS NULL OR inaccessibility = "")
      THEN SET errmsg = "Collection accessibility cannot be empty.";
    END IF;

    IF (inuser IS NULL OR inuser = "")
      THEN SET errmsg = "User name cannot be empty.";
    END IF;

    IF (incollectionid IS NULL OR incollectionid = "")
       THEN SET errmsg = "Collection id cannot be empty.";
	END IF;

    IF NOT EXISTS(SELECT 1 FROM tbl_conceptcollections WHERE id = incollectionid)
		THEN SET errmsg = "No such record exists.Please specify correct value";
    END IF;

    -- Updating the record
    IF errmsg IS NULL
      THEN SET errmsg = "";
      START TRANSACTION;
      UPDATE tbl_conceptcollections cc
		 SET cc.collectionname = IFNULL(incollectionname,cc.collectionname)
            ,cc.description = IFNULL(indescription,cc.description)
            ,cc.accessibility = IFNULL(inaccessibility,cc.accessibility)
            ,cc.updatedby     = inuser,
			 cc.updateddate   = NOW()
	   WHERE cc.id = incollectionid;
       IF errmsg = ""
          THEN COMMIT;
       ELSE
          ROLLBACK;
       END IF;
   END IF;
END$$
DELIMITER ;