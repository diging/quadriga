DROP PROCEDURE IF EXISTS sp_getProjectCollaborators;

DELIMITER $$
CREATE PROCEDURE sp_getProjectCollaborators
(
 -- IN inprojname  VARCHAR(20),
  OUT errmsg     VARCHAR(255)
)
BEGIN
    -- declare local variables
 /*   DECLARE projid   INT DEFAULT 0;

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
      -- retrieve the projectid id of the project 
      SELECT projectid INTO projid FROM vw_project
        WHERE projectname = inprojname; */
      
      -- retrieve the collaborator details
      SELECT collaboratoruser,collaboratorrole
        FROM vw_project_collaborator
	 --  WHERE projectid = projid;
    -- END IF;
END$$
DELIMITER ;