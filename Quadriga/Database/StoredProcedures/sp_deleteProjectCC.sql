/*******************************************
Name          : sp_deleteProjectCC

Description   : Delete the Concept collection from
				tbl_project_conceptcollection table

Called By     : UI (DBConnectionProjectDictionary.java)

Create By     : Lohith Dwaraka

Modified Date : 07/11/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_deleteProjectCC;
DELIMITER $$
CREATE PROCEDURE sp_deleteProjectCC	
(
  IN  inuser varchar(200),
  IN  inccid varchar(200),
  IN  inprojectid varchar(200),
  OUT errmsg    VARCHAR(255)    
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
    IF(inccid IS NULL OR inccid = "")
      THEN SET errmsg = "Concept collection id cannot be empty.";
    END IF;
    
    IF(inprojectid IS NULL OR inprojectid = "")
      THEN SET errmsg = "Project id cannot be empty.";
    END IF;
    
     IF(inuser IS NULL OR inuser = "")
      THEN SET errmsg = "user name cannot be empty.";
    END IF;

  
    
    IF NOT EXISTS(SELECT 1 FROM tbl_project_conceptcollection    WHERE conceptcollectionid = inccid and projectid = inprojectid) 
      THEN SET errmsg = "Concept collection is not present in the project";
    END IF;
	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
			DELETE FROM
			tbl_project_conceptcollection WHERE projectid = inprojectid and conceptcollectionid = inccid;
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









