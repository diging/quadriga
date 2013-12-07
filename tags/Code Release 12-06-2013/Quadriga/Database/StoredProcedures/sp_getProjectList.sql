/*******************************************
Name          : sp_getProjectList

Description   : Get the projects associated to the user as owner

Called By     : UI

Create By     : Kiran Kumar Batna

Modified Date : 09/27/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getProjectList;
DELIMITER $$
CREATE PROCEDURE sp_getProjectList
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
    SELECT  projectname,description,unixname,projectid,
            projectowner,accessibility
      FROM  vw_project
      WHERE projectowner = projowner;
END$$
DELIMITER ;