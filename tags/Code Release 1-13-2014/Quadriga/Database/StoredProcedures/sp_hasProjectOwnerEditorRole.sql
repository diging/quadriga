/*******************************************
Name          : sp_hasProjectOwnerEditorRole

Description   : Check if owner of the project has
				editor roles

Called By     : UI DBConneciton

Create By     : Lohith Dwaraka

Modified Date : 08/14/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_hasProjectOwnerEditorRole;

DELIMITER $$
CREATE PROCEDURE sp_hasProjectOwnerEditorRole
(
  IN  inowner  VARCHAR(20),
  IN  inprojectid  VARCHAR(100),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(inprojectid IS NULL OR inprojectid = "")
     THEN SET errmsg = "Project id cannot be empty.";
    END IF;
    
    IF(inowner IS NULL OR inowner = "")
     THEN SET errmsg = "Owner name cannot be empty.";
    END IF;


    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the dictionary details
	 select 1
       from tbl_project_editor
	   where projectid = inprojectid and owner =inowner;
	END IF;
END$$
DELIMITER ;