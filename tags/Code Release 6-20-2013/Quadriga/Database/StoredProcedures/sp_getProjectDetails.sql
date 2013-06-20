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
  IN  projid 	  INT,
  OUT errmsg      VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
    
    IF (errmsg IS NULL)
     THEN SET errmsg = "";
    END IF;
    
    IF(projid IS NULL)
    THEN 
    SET errmsg = "project id cannot be empty";
    END IF;
    
   IF NOT EXISTS(SELECT 1 FROM tbl_project
				   WHERE projectid = projid)
   THEN SET errmsg = "Invalid id.Please enter the correct value.";
   END IF; 
     -- retrieve the project details
	 SELECT projectname,description,unixname,projectid,projectowner,accessibility
       FROM vw_project
	 WHERE projectid = projid;
	
END$$
DELIMITER ;

