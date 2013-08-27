/*******************************************
Name          : sp_doesWorkspaceInheritProjectOwnerEditorRole

Description   : Check if owner of the workspace has
				editor roles

Called By     : UI DBConneciton

Create By     : Lohith Dwaraka

Modified Date : 08/14/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_doesWorkspaceInheritProjectOwnerEditorRole;

DELIMITER $$
CREATE PROCEDURE sp_doesWorkspaceInheritProjectOwnerEditorRole
(
  IN  inowner  VARCHAR(20),
  IN  inworkspaceid  VARCHAR(100),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(inworkspaceid IS NULL OR inworkspaceid = "")
     THEN SET errmsg = "Project id cannot be empty.";
    END IF;
    
    IF(inowner IS NULL OR inowner = "")
     THEN SET errmsg = "Owner name cannot be empty.";
    END IF;


    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the workspace owner editor details
	select 1 from tbl_project_editor where  projectid in 
	 (select projectid from tbl_project_workspace where workspaceid = inworkspaceid );
	 
	END IF;
END$$
DELIMITER ;