/*******************************************
Name          : sp_addDictionaryToProject

Description   : adds the dictionary  to
				project in tbl_projectdictionary table

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_addDictionaryToProject;
DELIMITER $$
CREATE PROCEDURE sp_addDictionaryToProject
(
	IN  inprojectid    VARCHAR(50),  
	IN  indictionaryid    VARCHAR(100),
  	IN  inuserid    VARCHAR(50) ,
  	OUT errmsg           VARCHAR(255)    
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
    IF(inprojectid IS NULL OR inprojectid = "")
	  THEN SET errmsg = "Project ID cannot be empty.";
    END IF;
    
     IF(indictionaryid IS NULL OR indictionaryid = "")
	  THEN SET errmsg = "Dictionary ID cannot be empty.";
    END IF;
    
     IF(inuserid IS NULL OR inuserid = "")
	  THEN SET errmsg = "User ID cannot be empty.";
    END IF;
	
    IF EXISTS(SELECT 1 FROM tbl_project_dictionary
				   WHERE projectid = inprojectid and dictionaryid=indictionaryid)
      THEN SET errmsg = "DictionaryExists";
    END IF; 
    
    IF NOT EXISTS(SELECT 1 FROM vw_project
				   WHERE projectowner = inuserid and projectid=inprojectid)
      THEN SET errmsg = "Project ID doesn't belong to user";
    END IF; 
    
    IF NOT EXISTS(SELECT 1 FROM vw_dictionary
				   WHERE dictionaryowner = inuserid and dictionaryid=indictionaryid)
      THEN SET errmsg = "Dictionary ID doesn't belong to user";
    END IF; 
    
    IF NOT EXISTS(SELECT 1 FROM vw_quadriga_user
				   WHERE username = inuserid)
      THEN SET errmsg = "Invalid user.Please enter the correct value.";
    END IF; 

	
    -- Inserting the record into the tbl_dictionary table
    IF(errmsg IS NULL)
      THEN SET errmsg = "";
         START TRANSACTION;
            INSERT 
              INTO tbl_project_dictionary(projectid,dictionaryid,
                         updatedby,updateddate,createdby,createddate)
			 VALUES (inprojectid,indictionaryid,
                     inuserid,NOW(),inuserid,NOW());	
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









