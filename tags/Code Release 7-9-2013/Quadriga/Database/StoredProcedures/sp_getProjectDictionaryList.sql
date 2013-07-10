/*******************************************
Name          : sp_getProjectDictionaryList

Description   : retrieves the dictionary details
				of a particular dictionary

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Lohith Dwaraka

Modified Date : 06/04/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getProjectDictionaryList;

DELIMITER $$
CREATE PROCEDURE sp_getProjectDictionaryList
(
  IN  inprojectid  VARCHAR(50),
  IN inuserid VARCHAR(50),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(inuserid IS NULL OR inuserid = "")
     THEN SET errmsg = "Project owner name cannot be empty.";
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM vw_project
                     WHERE projectowner = inuserid)
      THEN SET errmsg = "Project owner name is invalid.";
    END IF;

    IF NOT EXISTS(SELECT 1 FROM vw_quadriga_user
				   WHERE username = inuserid)
      THEN SET errmsg = "Invalid user.Please enter the correct value.";
    END IF; 
    
    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the dictionary details
	 SELECT dictionaryname,description,id,dictionaryowner,accessibility
       FROM vw_dictionary
	   WHERE id IN ( select dictionaryid from tbl_project_dictionary where projectid=inprojectid );
	END IF;
END$$
DELIMITER ;