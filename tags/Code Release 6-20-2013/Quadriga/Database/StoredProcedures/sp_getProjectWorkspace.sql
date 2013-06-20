/*******************************************
Name          : sp_getProjectWorkspace

Description   : retrieve the workspace details.

Called By     : UI (DBConnectionManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 05/30/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getProjectWorkspace;

DELIMITER $$
CREATE PROCEDURE sp_getProjectWorkspace
(
  IN inprojname   VARCHAR(50),
  OUT errmsg      VARCHAR(255)
)
BEGIN
    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    IF(inprojname IS NULL OR inprojname = "")
     THEN SET errmsg = "Project name cannot be empty.";
    END IF;

    IF NOT EXISTS(SELECT 1 FROM vw_project WHERE projectname = inprojname)
      THEN SET errmsg = "Project name is invalid";
	END IF;

    IF(errmsg IS NULL)
     THEN SET errmsg = "";
      SELECT vwwork.workspacename,vwwork.description
        FROM vw_workspace vwwork
	    JOIN vw_project_workspace vwprojwork
		  ON vwwork.id = vwprojwork.workspaceid
        JOIN vw_project vwproj
          ON vwproj.projectid = vwprojwork.projectid;
     END IF;
END$$
DELIMITER ;

