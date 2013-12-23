/*******************************************
Name          : sp_getConceptCollectionID

Description   : retrieves the CC details
				of a particular CC

Called By     : UI (DBConnectionCCManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getConceptCollectionID;

DELIMITER $$
CREATE PROCEDURE sp_getConceptCollectionID
(
  IN  inname  VARCHAR(100),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(inname IS NULL OR inname = "")
     THEN SET errmsg = "COncept collection name cannot be empty.";
    END IF;


    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the cc details
	 SELECT conceptcollectionid
       FROM tbl_conceptcollection
	   WHERE collectionname = inname;
	END IF;
END$$
DELIMITER ;