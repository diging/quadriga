/*******************************************
Name          : sp_getProjectDetails

Description   : retrieves the project details
				of a particular project

Called By     : UI (DBConnectionManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 05/30/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getProjectDetails;

DELIMITER $$
CREATE PROCEDURE sp_getProjectDetails
(
  IN inprojname   VARCHAR(50),
  OUT errmsg      VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(inprojname IS NULL OR inprojname = "")
     THEN SET errmsg = "Project name cannot be empty.";
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM vw_project
                     WHERE projectname = inprojname)
      THEN SET errmsg = "Project name is invalid.";
    END IF;

    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the project details
	 SELECT projectname,description,projectid,projectowner,accessibility
       FROM vw_project WHERE projectname = inprojname;
	END IF;
END$$
DELIMITER ;