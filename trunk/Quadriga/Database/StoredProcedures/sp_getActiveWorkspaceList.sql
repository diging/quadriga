/*******************************************
Name          : sp_getActiveWorkspaceList

Description   : Retrieves the active workspace details.

Called By     : UI(DBConnectionProjectManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 06/27/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getActiveWorkspaceList;

DELIMITER $$
CREATE PROCEDURE sp_getActiveWorkspaceList
(
  IN  inprojectid    VARCHAR(50),
  IN  inusername     VARCHAR(30),
  OUT errmsg         VARCHAR(255)
)
BEGIN
	  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
       SET errmsg = "SQL exception has occurred";

	  -- valdiate the input parameters
      IF (inprojectid IS NULL OR inprojectid = "")
       THEN SET errmsg = "Project id cannot be empty.";
      END IF;

      IF (inusername IS NULL OR inusername = "")
       THEN SET errmsg = "User name cannot be empty.";
      END IF;

      IF NOT EXISTS(SELECT 1 FROM vw_quadriga_user WHERE 
                     username = inusername)
      THEN SET errmsg = "Username is invalid";
      END IF;

      IF NOT EXISTS(SELECT 1 FROM vw_project
					  WHERE projectid = inprojectid)
        THEN SET errmsg = "Project id is invalid.";
      END IF;

      IF(errmsg IS NULL)
       THEN SET errmsg = "";
      END IF;

       -- fetch the workspace for which the user is owner or collaborator
       SELECT DISTINCT vsws.workspacename,
                 vsws.description,
                 vsws.workspaceid,
                 vsws.workspaceowner
            FROM vw_workspace vsws
            JOIN vw_project_workspace vwprojws
              ON vsws.workspaceid = vwprojws.workspaceid
             AND vsws.isarchived = 0
             AND vsws.isdeactivated = 0
            WHERE vwprojws.projectid = inprojectid
              AND vsws.workspaceowner = inusername;
END$$
DELIMITER ;