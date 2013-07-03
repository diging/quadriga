/*******************************************
Name          : sp_createworkspace

Description   : Stores the workspace details.

Called By     : UI(DBConnectionProjectManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 06/20/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_createworkspace;

DELIMITER $$
CREATE PROCEDURE sp_createworkspace
(
  IN inname         VARCHAR(50),
  IN indescription  VARCHAR(100),
  IN inowner        VARCHAR(20),
  IN inprojectid    VARCHAR(50),
  OUT errmsg        VARCHAR(255)
)
BEGIN
       
       -- Declare local variables
       DECLARE uniqueId  BIGINT;
       DECLARE workspaceId VARCHAR(50);

	   DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
        SET errmsg = "SQL exception has occurred";

      -- validating the input parameters
       IF (inname IS NULL OR inname = "")
	   THEN SET errmsg = "Workspace name cannot be empty.";
      END IF;

      IF (indescription IS NULL OR indescription = "")
        THEN SET errmsg = "Workspace description cannot be empty.";
      END IF;

	  IF (inowner IS NULL OR inowner = "")
       THEN SET errmsg = "Workspace owner cannot be empty.";
	  END IF;

      IF (inprojectid IS NULL)
        THEN SET errmsg = "Project for workspace cannnot be empty.";
      END IF;

      -- checking if the workspace name already exists
      IF EXISTS (SELECT 1 FROM vw_workspace WHERE workspacename = inname)
       THEN SET errmsg = "Workspace name already exists.";
      END IF;

      -- checking if the project id is valid
      IF NOT EXISTS(SELECT 1 FROM vw_project WHERE projectid = inprojectid)
        THEN SET errmsg = "Project mapping is invalid.";
      END IF;

      -- validating the owner
      IF NOT EXISTS(SELECT 1 FROM vw_quadriga_user WHERE username = inowner)
         THEN SET errmsg = "Invalid User.";
      END IF;

     
      -- insert the record into the workspace table
      IF (errmsg IS NULL)
       THEN SET errmsg = "";
       START TRANSACTION;
		 SET uniqueId = UUID_SHORT();
		 SET workspaceId = CONCAT('WS_',CAST(uniqueId AS CHAR));
         -- insert into the workspace table
         INSERT INTO tbl_workspace(workspacename,description,workspaceid,workspaceowner,
                isarchived,isdeactivated,updatedby,updateddate,createdby,createddate)
         VALUES(inname,indescription,workspaceId,inowner,0,0,inowner,NOW(),inowner,NOW());

         -- insert into the project workspace mapping table
         INSERT INTO tbl_project_workspace(projectid,workspaceid,updatedby,updateddate,createdby,createddate)
         VALUES(inprojectid,workspaceId,inowner,NOW(),inowner,NOW());

       IF(errmsg = "")
        THEN COMMIT;
       ELSE 
         ROLLBACK;
       END IF;
	END IF;
END$$
DELIMITER ;