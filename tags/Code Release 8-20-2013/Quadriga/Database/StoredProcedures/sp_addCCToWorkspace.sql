/*******************************************
Name          : sp_addCCToWorkspace

Description   : adds the Concept collection  to
				project in tbl_workspace_conceptcollection table

Called By     : UI 

Create By     : Lohith Dwaraka

Modified Date : 07/11/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_addCCToWorkspace;
DELIMITER $$
CREATE PROCEDURE sp_addCCToWorkspace
(
	IN  inworkspaceid    VARCHAR(50),  
	IN  inccid    VARCHAR(50),
  	IN  inuserid    VARCHAR(50) ,
  	OUT errmsg           VARCHAR(255)    
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
	
    -- validating the input variables
    IF(inworkspaceid IS NULL OR inworkspaceid = "")
	  THEN SET errmsg = "Workspace ID cannot be empty.";
    END IF;
    
     IF(inccid IS NULL OR inccid = "")
	  THEN SET errmsg = "Concept collection ID cannot be empty.";
    END IF;
    
     IF(inuserid IS NULL OR inuserid = "")
	  THEN SET errmsg = "User ID cannot be empty.";
    END IF;
	
    IF EXISTS(SELECT 1 FROM tbl_workspace_conceptcollection
				   WHERE workspaceid = inworkspaceid and conceptcollectionid=inccid)
      THEN SET errmsg = "CCExists";
    END IF; 
    
    IF NOT EXISTS(SELECT 1 FROM vw_workspace
				   WHERE workspaceowner = inuserid and workspaceid=inworkspaceid)
      THEN SET errmsg = "Project ID doesn't belong to user";
    END IF; 
    
    IF NOT EXISTS(SELECT 1 FROM vw_conceptcollections
				   WHERE collectionowner = inuserid and id=inccid)
      THEN SET errmsg = "Concept collection ID doesn't belong to user";
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
              INTO tbl_workspace_conceptcollection(workspaceid,conceptcollectionid,
                         updatedby,updateddate,createdby,createddate)
			 VALUES (inworkspaceid,inccid,
                     inuserid,NOW(),inuserid,NOW());	
		 IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
         END IF;
    END IF;
END$$
DELIMITER ;









