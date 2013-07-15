/*******************************************
Name          : sp_addCollectionItems

Description   : adds the dictionary items details to
				tbl_conceptcollections_items table

Called By     : UI (DBConnectionCCManager.java)

Create By     : SatyaSwaroop Boddu

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_addCollectionItems;
DELIMITER $$
CREATE PROCEDURE sp_addCollectionItems	
(
  IN  	initem    VARCHAR(255),
  IN  	 inlemma   VARCHAR(255),
  IN  	 inpos VARCHAR(255),
  IN 	 indescription TEXT,
  IN 	inid VARCHAR(100),
  IN    inusername VARCHAR(100),
  OUT 	errmsg           VARCHAR(255)    
)
BEGIN
	
    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
    IF(inid IS NULL OR inid = "")
      THEN SET errmsg = "id cannot be empty.";
    END IF;

    IF (initem IS NULL OR initem = "")
	 THEN SET errmsg = "Items cannot be empty";
	END IF;
	  
	IF (inlemma IS NULL OR inlemma = "")
	 THEN SET errmsg = "lemma cannot be empty";
	END IF;
	
	IF (inpos IS NULL OR inpos = "")
	 THEN SET errmsg = "pos cannot be empty";
	END IF;

    
    
  
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
        
           IF EXISTS(SELECT 1 FROM vw_conceptcollections_items
				   WHERE id = inid and item =initem)
      		THEN SET errmsg = "ItemExists";
    		END IF;
    		IF NOT EXISTS(SELECT 1 FROM vw_conceptcollections	
                     WHERE id = inid and  collectionowner = inusername) AND NOT EXISTS(SELECT 1 FROM vw_conceptcollections_collaborator	WHERE collectionid = inid and  collaboratoruser = inusername)
      THEN SET errmsg = "User dont have access to the collection"; 
    END IF;
    
    		IF (errmsg = "")
            THEN INSERT 
              INTO tbl_conceptcollections_items(id, item, lemma, pos, description,
                         updateddate,createddate)
			 VALUES (inid, initem, inlemma, inpos, indescription
                    ,NOW(),NOW());
           END IF;          
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









