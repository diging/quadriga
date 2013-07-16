/*******************************************
Name          : sp_getWorkspaceCCList

Description   : retrieves the concept collection
				of a particular workspace

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getWorkspaceCCList;

DELIMITER $$
CREATE PROCEDURE sp_getWorkspaceCCList
(
  IN  inworkspaceid  VARCHAR(50),
  IN inuserid VARCHAR(50),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(inuserid IS NULL OR inuserid = "")
     THEN SET errmsg = "Project owner name cannot be empty.";
    END IF;
    
    IF(inworkspaceid IS NULL OR inworkspaceid = "")
     THEN SET errmsg = "workspace id cannot be empty.";
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM vw_workspace
                     WHERE workspaceowner = inuserid)
      THEN SET errmsg = "Project owner name is invalid.";
    END IF;

    IF NOT EXISTS(SELECT 1 FROM vw_quadriga_user
				   WHERE username = inuserid)
      THEN SET errmsg = "Invalid user.Please enter the correct value.";
    END IF; 
    
    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the dictionary details
	 SELECT collectionname,description,id,collectionowner
       FROM vw_conceptcollections
	   WHERE id IN ( select conceptcollectionid from tbl_workspace_conceptcollection where workspaceid=inworkspaceid );
	END IF;
END$$
DELIMITER ;