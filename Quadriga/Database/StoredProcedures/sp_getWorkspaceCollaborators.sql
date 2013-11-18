/*******************************************
Name          : sp_getWorkspaceCollaborators

Description   : retrieves the users and their
                Collaborator role for a workspace

Called By     : UI (DBConnectionManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 07/19/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getWorkspaceCollaborators;


DROP PROCEDURE IF EXISTS sp_getWorkspaceCollaborators;

DELIMITER $$
CREATE PROCEDURE sp_getWorkspaceCollaborators
(
  IN inworkspaceid  VARCHAR(150),
  OUT errmsg        VARCHAR(255)
)
BEGIN
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
     SET errmsg = "SQL exception has occurred";

    -- check input variables
 IF(inworkspaceid IS NULL OR inworkspaceid = "")
    THEN SET errmsg = "Workspace id cannot be empty.";
  END IF;
   
   IF NOT EXISTS (SELECT 1 FROM vw_workspace
                   WHERE workspaceid = inworkspaceid)
      THEN SET errmsg = "Workspace id is invalid.";
    END IF;
   

    IF (errmsg IS NULL)
     THEN SET errmsg = "";

     -- retrieve the collaborator details
      SELECT username, 
         GROUP_CONCAT(collaboratorrole SEPARATOR ',')  AS 'Collaboratorrole'
        FROM vw_workspace_collaborator
	    WHERE workspaceid = inworkspaceid
      GROUP BY username;
     END IF;
END$$
DELIMITER ;