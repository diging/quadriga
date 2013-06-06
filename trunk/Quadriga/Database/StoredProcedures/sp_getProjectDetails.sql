/*******************************************
Name          : sp_getProjectDetails

Description   : retrieves the project details
				of a particular project

Called By     : UI (DBConnectionManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 05/30/2013

********************************************/

/*******************************************
Name          : sp_getProjectDetails

Description   : retrieves the project details
				of a particular project

Called By     : UI (DBConnectionManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 05/30/2013

********************************************/
use quadriga;
DROP PROCEDURE IF EXISTS sp_getProjectDetails;

DELIMITER $$
CREATE PROCEDURE sp_getProjectDetails
(
  IN  projid 	  VARCHAR(10),
  OUT errmsg      VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";
    
    IF (errmsg IS NULL)
     THEN SET errmsg = "";
    END IF;
    
    IF(projid IS NULL OR projid = " ")
    THEN 
    SET errmsg = "project id cannot be empty";
    END IF;
    
   IF NOT EXISTS(SELECT 1 FROM tbl_project
				   WHERE projectid = projid)
   THEN SET errmsg = "Invalid id.Please enter the correct value.";
   END IF; 
     -- retrieve the project details
	 SELECT projectname,description,projectid,projectowner,accessibility
       FROM vw_projectDetails
	 WHERE projectid = projid;
	
END$$
DELIMITER ;

