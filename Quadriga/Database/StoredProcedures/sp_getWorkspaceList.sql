/*******************************************
Name          : sp_getWorkspaceList

Description   : Retrieves the workspace details.

Called By     : UI(DBConnectionProjectManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 06/24/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getWorkspaceList;

DELIMITER $$
CREATE PROCEDURE sp_getWorkspaceList
(
   IN inprojectid   VARCHAR(50),
   IN inarchive     INT,
   IN inactive      INT,
   OUT errmsg       VARCHAR(255)
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

       IF(inarchive IS NULL)
          THEN SET errmsg = "Archive parameter cannot be empty.";
       END IF;

       IF(inactive IS NULL)
         THEN SET errmsg = "Active parameter cannot be empty.";
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
             AND vsws.isarchived = inarchive
             AND vsws.isdeactivated = inactive
            WHERE vwprojws.projectid = inprojectid;
      END IF;
END $$
DELIMITER ;