/*******************************************
Name          : sp_getWorkspaceOwnerProjectList

Description   : Get the projects associated to workspace for which
                the user is owner

Called By     : UI

Create By     : Kiran Kumar Batna

Modified Date : 09/27/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getWorkspaceOwnerProjectList;
DELIMITER $$
CREATE PROCEDURE sp_getWorkspaceOwnerProjectList
(
    IN projowner VARCHAR(20),
	OUT errmsg  VARCHAR(100)
)
BEGIN
	-- the error handler for any sql exception
	   DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
          SET errmsg = 'SQL exception has occurred';
          
	IF(errmsg IS NULL)
    THEN
    SET errmsg = "";
    END IF;

    IF(projowner IS NULL OR projowner = " ")
    THEN 
    set errmsg = "project owner cannot be empty";
    END IF;
    
   IF NOT EXISTS(SELECT 1 FROM vw_quadriga_user
				   WHERE username = projowner)
   THEN SET errmsg = "Invalid owner.Please enter the correct value.";
   END IF; 
   
    -- fetch the results of the user and return
	    SELECT DISTINCT proj.projectname,proj.description,proj.unixname,proj.projectid,
            proj.projectowner,proj.accessibility
      FROM  vw_project proj
	  JOIN vw_project_workspace projws
        ON projws.projectid = proj.projectid
       JOIN vw_workspace ws
        ON projws.workspaceid = ws.workspaceid
      WHERE ws.workspaceowner = projowner;  
END$$
DELIMITER ;