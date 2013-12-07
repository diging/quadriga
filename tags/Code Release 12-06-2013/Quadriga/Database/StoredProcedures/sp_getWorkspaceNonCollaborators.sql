/*******************************************
Name          : sp_getWorkspaceNonCollaborators

Description   : retrieves the active users who are not 
				collaborators to the workspace

Called By     : UI (DBConnectionManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 07/19/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getWorkspaceNonCollaborators;
DELIMITER $$
CREATE PROCEDURE sp_getWorkspaceNonCollaborators
(
	IN inworkspaceid	VARCHAR(150),
	OUT errmsg		    VARCHAR(200)
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

     -- retrieve the users who are not collaborators
  	SELECT username FROM vw_quadriga_user WHERE
	username NOT IN (SELECT username FROM vw_workspace_collaborator
	WHERE workspaceid = inworkspaceid
	UNION
	SELECT workspaceowner FROM vw_workspace WHERE workspaceid = inworkspaceid);
     END IF;
END$$
DELIMITER ;