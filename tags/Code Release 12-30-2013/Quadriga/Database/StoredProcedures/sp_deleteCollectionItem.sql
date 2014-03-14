/*******************************************
Name          : sp_deleteCollectionItem

Description   : Delete the collection items in
				tbl_conceptcollections_items table

Called By     : UI (DBConnectionCCManager.java)

Create By     : SatyaSwaroop Boddu

Modified Date : 06/13/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_deleteCollectionItem;

DELIMITER $$
CREATE PROCEDURE sp_deleteCollectionItem	
(
  IN  initemid	 			varchar(255),
  IN  incollectionid		VARCHAR(100),
  IN  inusername            VARCHAR(100),
  OUT errmsg           		VARCHAR(255)    
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
    IF(incollectionid IS NULL OR incollectionid = "")
      THEN SET errmsg = "dictionary id cannot be empty.";
    END IF;

    IF (initemid IS NULL OR initemid = "")
	 THEN SET errmsg = "Term cannot be empty";
	END IF;

     
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
         	  
    		IF NOT EXISTS(SELECT 1 FROM vw_conceptcollection_items WHERE item =initemid and conceptcollectionid = incollectionid)
     	 		THEN SET errmsg = "Item does not exists in this dictionary";
    		END IF;
    		
    		IF NOT EXISTS(SELECT 1 FROM vw_conceptcollection	
                     WHERE conceptcollectionid = incollectionid and  collectionowner = inusername) AND NOT EXISTS(SELECT 1 FROM vw_conceptcollection_collaborator	WHERE conceptcollectionid = incollectionid and  collaboratoruser = inusername)
      THEN SET errmsg = "User dont have access to the collection"; 
    END IF;
    
          	IF (errmsg = "")
				THEN DELETE FROM tbl_conceptcollection_items WHERE conceptcollectionid = incollectionid AND item = initemid;
			END IF;
		 	IF (errmsg = "")
          		 THEN COMMIT;
        	ELSE ROLLBACK;
       		END IF;
    END IF;
END$$
DELIMITER ;