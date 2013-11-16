/*******************************************
Name          : sp_getProjectCollaborators

Description   : retrieves the users and their
                Collaborator role for a project

Called By     : UI (DBConnectionManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 05/30/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getProjectCollaborators;

DELIMITER $$
CREATE PROCEDURE sp_getProjectCollaborators
(
  IN inprojid  VARCHAR(50),
  OUT errmsg   VARCHAR(255)
)
BEGIN
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
 IF(inprojid IS NULL OR inprojid = "")
    THEN SET errmsg = "Project id cannot be empty.";
  END IF;
   
   IF NOT EXISTS (SELECT 1 FROM vw_project
                   WHERE projectid = inprojid)
      THEN SET errmsg = "Project id is invalid.";
    END IF;
   

    IF (errmsg IS NULL)
     THEN SET errmsg = "";

     -- retrieve the collaborator details
      SELECT collaboratoruser, 
         GROUP_CONCAT(collaboratorrole SEPARATOR ',')  AS 'Collaboratorrole'
        FROM vw_project_collaborator
	    WHERE projectid = inprojid
      GROUP BY collaboratoruser;
     END IF;
END$$
DELIMITER ;

