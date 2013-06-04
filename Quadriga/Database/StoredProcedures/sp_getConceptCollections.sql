/*******************************************
Name          : sp_getConceptCollections

Description   : retrieves the collection details
				of a particular collection

Called By     : UI (DBConnectionManager.java)

Create By     : SatyaSwaroop Boddu

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getConceptCollections;

DELIMITER $$
CREATE PROCEDURE sp_getConceptCollections
(
  IN  incollectionname  VARCHAR(20),
  OUT errmsg      VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(incollectionname IS NULL OR incollectionname = "")
     THEN SET errmsg = "Collection name cannot be empty.";
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM vw_conceptcollections
                     WHERE collectionname = incollectionname)
      THEN SET errmsg = "collection name is invalid.";
    END IF;

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the project details
	 SELECT collectionname,description,collectionid,collectionowner,accessibility
       FROM vw_conceptcollections
	   WHERE collectionname = incollectionname;
	END IF;
END$$
DELIMITER ;