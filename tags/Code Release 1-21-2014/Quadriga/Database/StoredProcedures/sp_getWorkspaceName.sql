/*******************************************
Name          : sp_getWorkspaceName

Description   : retrieves the workspace name
				of a particular workspace id

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getWorkspaceName;

DELIMITER $$
CREATE PROCEDURE sp_getWorkspaceName
(
  IN  inid  VARCHAR(150),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(inid IS NULL OR inid = "")
     THEN SET errmsg = "Workspace id cannot be empty.";
    END IF;
    

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the Workspace name
	 SELECT workspacename
       FROM tbl_workspace
	   WHERE workspaceid = inid;
	END IF;
END$$
DELIMITER ;