/*******************************************
Name          : sp_deletewscollaborators

Description   : Delete the workspace collaborators

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 07/29/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_deletewscollaborators;
DELIMITER $$
CREATE PROCEDURE sp_deletewscollaborators
(
  IN inusername    TEXT,
  IN inworkspaceid VARCHAR(150),
  OUT errmsg        VARCHAR(255)
)
BEGIN
    -- Declare local varaibles
    DECLARE rowvalue   VARCHAR(50);
    DECLARE position   INT;

	-- the error handler for any sql exception
   DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
    SET errmsg = "SQLException occurred";

    -- deleting the temp table
    DROP TEMPORARY TABLE IF EXISTS temp_tbl_workspacecollaborators;

    -- validating the input parameters
    IF(inworkspaceid IS NULL OR inworkspaceid = "")
       THEN SET errmsg = "Workspaceid cannot be empty.";
    END IF;

   -- inserting the input into a temp table
    CREATE TEMPORARY TABLE temp_tbl_workspacecollaborators
    (
         username VARCHAR(50)
    );

    SET position = LOCATE(',',inusername);
	
    WHILE(position > 0) DO
        SET rowvalue = SUBSTRING_INDEX(inusername,',',1);
        INSERT INTO temp_tbl_workspacecollaborators(username) VALUES(rowvalue);
        SET inusername = SUBSTRING(inusername FROM position+1);
		SET position = LOCATE(',',inusername);
    END WHILE;

    -- inserting the row when the input has a single value
    INSERT INTO temp_tbl_workspacecollaborators(username) VALUES(inusername);

    -- Validating if one of the input values is wrong
    IF EXISTS(SELECT 1 FROM temp_tbl_workspacecollaborators tempws LEFT JOIN 
                   vw_quadriga_user quser ON tempws.username = quser.username
                 WHERE quser.username IS NULL)
    THEN SET errmsg = "One or more user's are invalid";
    END IF;

   IF EXISTS(SELECT 1 FROM temp_tbl_workspacecollaborators tempws LEFT JOIN 
                vw_workspace_collaborator wscollab ON tempws.username = wscollab.username
                 AND wscollab.workspaceid = inworkspaceid WHERE wscollab.username IS NULL)
   THEN SET errmsg = "One or more user's are not associated to the workspace.";
   END IF;

   SET SQL_SAFE_UPDATES=0;

   -- deleting the workspace associated collaborators
	IF(errmsg IS NULL)
      THEN SET errmsg = "";
	   START TRANSACTION;
       -- delete the selected workspace collaborators
       DELETE FROM tbl_workspace_collaborator WHERE workspaceid = inworkspaceid
       AND username IN (SELECT username FROM temp_tbl_workspacecollaborators);
	   IF(errmsg = "")
          THEN COMMIT;
       ELSE ROLLBACK;
       END IF;
    END IF;
	-- drop the temp table created
	DROP TEMPORARY TABLE IF EXISTS temp_tbl_workspacecollaborators;
	SET SQL_SAFE_UPDATES=1;    
END$$
DELIMITER ;