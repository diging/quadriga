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
  OUT errmsg        VARCHAR(255)
)
BEGIN
       
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

      -- checking if the project name already exists
      IF EXISTS (SELECT 1 FROM tbl_workspace WHERE workspacename = inname)
       THEN SET errmsg = 'Workspace name already exists.';
      END IF;

      -- insert the record into the workspace table
      IF (errmsg IS NULL)
       THEN SET errmsg = "";
       START TRANSACTION;
         INSERT INTO tbl_workspace(workspacename,description,workspaceid,workspaceowner,
                isarchived,isdeactivated,updatedby,updateddate,createdby,createddate)
         VALUES(inname,indescription,UUID_SHORT(),inowner,0,0,inowner,NOW(),inowner,NOW());

       IF(errmsg = "")
        THEN COMMIT;
       ELSE 
         ROLLBACK;
       END IF;
	END IF;
END$$
DELIMITER ;