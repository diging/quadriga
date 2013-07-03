/*******************************************
Name          : sp_getDeactivatedWorkspaceList

Description   : Retrieves the deactivated workspace details.

Called By     : UI(DBConnectionProjectManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 06/27/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getDeactivatedWorkspaceList;

DELIMITER $$
CREATE PROCEDURE sp_getDeactivatedWorkspaceList
(
  IN  inprojectid    VARCHAR(50),
  OUT errmsg         VARCHAR(255)
)
BEGIN
	  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
       SET errmsg = "SQL exception has occurred";

	  -- valdiate the input parameters
      IF (inprojectid IS NULL OR inprojectid = "")
       THEN SET errmsg = "Project id cannot be empty.";
      END IF;

      IF NOT EXISTS(SELECT 1 FROM vw_project
					  WHERE projectid = inprojectid)
        THEN SET errmsg = "Project id is invalid.";
      END IF;

      IF (errmsg IS NULL)
        THEN SET errmsg = "";
          SELECT vsws.workspacename,
                 vsws.description,
                 vsws.workspaceid,
                 vsws.workspaceowner
            FROM vw_workspace vsws
            JOIN vw_project_workspace vwprojws
              ON vsws.workspaceid = vwprojws.workspaceid
             AND vsws.isdeactivated = 1
            WHERE vwprojws.projectid = inprojectid;
      END IF;
END$$
DELIMITER ;