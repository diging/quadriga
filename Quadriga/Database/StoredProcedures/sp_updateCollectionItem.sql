/*******************************************
Name          : sp_updateCollectionItem

Description   : Update the collection items details to
				tbl_conceptcollections_items table from the rest

Called By     : UI (DBConnectionCCManager.java)

Create By     : SatyaSwaroop Boddu

Modified Date : 06/13/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_updateCollectionItem;
DELIMITER $$
CREATE PROCEDURE sp_updateCollectionItem	
(
  IN  inconceptname    VARCHAR(255),
  IN  inlemma    VARCHAR(200),
  IN  indescription   TEXT,
  IN  inpos    VARCHAR(50),
  IN  incollectionId VARCHAR(100),
  IN  inusername  VARCHAR(100),
  OUT errmsg           VARCHAR(255)    
)
BEGIN

	
    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
    IF(inconceptname IS NULL OR inconceptname = "")
      THEN SET errmsg = "dictionaryid cannot be empty.";
    END IF;

    IF (inlemma IS NULL OR inlemma = "")
	 THEN SET errmsg = "Items cannot be empty";
	END IF;
	
	IF (indescription IS NULL OR indescription = "")
	 THEN SET errmsg = "id cannot be empty";
	END IF;
	
	IF (inpos IS NULL OR inpos = "")
	 THEN SET errmsg = "id cannot be empty";
	END IF;
	IF (incollectionId IS NULL OR incollectionId = "")
	 THEN SET errmsg = "id cannot be empty";
	END IF;
    
   
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
      START TRANSACTION;
       
    		IF NOT EXISTS(SELECT 1 FROM vw_conceptcollections_items WHERE item =inconceptname and id = incollectionId)
     	 		THEN SET errmsg = "Item does not exists in this dictionary";
    		END IF;
    		IF NOT EXISTS(SELECT 1 FROM vw_conceptcollections	
                     WHERE id = incollectionid and  collectionowner = inusername) AND NOT EXISTS(SELECT 1 FROM vw_conceptcollections_collaborator	WHERE collectionid = incollectionid and  collaboratoruser = inusername)
      THEN SET errmsg = "User dont have access to the collection"; 
    END IF;
    	IF (errmsg = "")	
         	THEN UPDATE  tbl_conceptcollections_items SET lemma=inlemma, pos=inpos, description=indescription  WHERE id=incollectionId and item =inconceptname;
         END IF;	
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;