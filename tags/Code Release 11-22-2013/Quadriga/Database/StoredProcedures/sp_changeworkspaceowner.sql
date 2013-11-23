/*******************************************
Name          : sp_changeworkspaceowner

Description   : Chagne the ownership of the workspace

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 08/01/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_changeworkspaceowner;
DELIMITER $$
CREATE PROCEDURE sp_changeworkspaceowner
(
  IN  inworkspaceid     VARCHAR(150),
  IN  inoldowner        VARCHAR(50),
  IN  innewowner        VARCHAR(50),
  IN  incollabrole      TEXT,
  OUT errmsg            VARCHAR(255)
)
BEGIN
	 -- declare local variables
    DECLARE rowvalue   VARCHAR(30);
    DECLARE position   INT;

    -- the error handler for any sql exception
     DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

   -- Validating the input parameters
   IF (inworkspaceid IS NULL OR inworkspaceid ="")
   THEN SET errmsg = "Workspace id cannot be empty.";
   END IF;

     IF (inoldowner IS NULL OR inoldowner = "")
   THEN SET errmsg = "Username cannot be empty.";
   END IF;

   IF (innewowner IS NULL OR innewowner = "")
   THEN SET errmsg = "Username cannot be empty.";
   END IF;

   IF (incollabrole IS NULL OR incollabrole = "")
   THEN SET errmsg = "Collaborator role cannot be empty.";
   END IF;

   IF NOT EXISTS(SELECT 1 FROM vw_workspace WHERE workspaceid = inworkspaceid)
   THEN SET errmsg = "Workspace id is invalid.";
   END IF;

   IF NOT EXISTS (SELECT 1 FROM vw_quadriga_user WHERE username = inoldowner)
   THEN SET errmsg = "User name is invalid.";
   END IF;

   IF NOT EXISTS (SELECT 1 FROM vw_quadriga_user WHERE username = innewowner)
   THEN SET errmsg = "Username is invalid.";
   END IF;

   IF NOT EXISTS(SELECT 1 FROM vw_workspace WHERE workspaceowner = inoldowner AND workspaceid = inworkspaceid)
   THEN SET errmsg = "User does not have privileges to transfer ownership. ";
   END IF;

   IF NOT EXISTS(SELECT 1 FROM vw_workspace_collaborator WHERE workspaceid = inworkspaceid AND username = innewowner)
   THEN SET errmsg = "Invalid user.";
   END IF;

   -- split the comma seperated string into a table
    DROP TEMPORARY TABLE IF EXISTS temp_tbl_collabrole;

	CREATE TEMPORARY TABLE temp_tbl_collabrole
    (
         role VARCHAR(30)
    );

    SET position = LOCATE(',',incollabrole);

    WHILE(position > 0) DO
        SET rowvalue = SUBSTRING_INDEX(incollabrole,',',1);
        INSERT INTO temp_tbl_collabrole(role) VALUES(rowvalue);
        SET incollabrole = SUBSTRING(incollabrole FROM position+1);
		SET position = LOCATE(',',incollabrole);
    END WHILE;

    INSERT INTO temp_tbl_collabrole(role) VALUES(incollabrole);

      -- disable safe update
   SET sql_safe_updates=0;
   
   IF (errmsg IS NULL)
   THEN SET errmsg = "";
   START TRANSACTION;
     -- delete from the collaborators list
     DELETE FROM tbl_workspace_collaborator WHERE workspaceid = inworkspaceid 
     AND username = innewowner;

     -- update the workspace owner
     UPDATE tbl_workspace
     SET workspaceowner = innewowner,
         updatedby = inoldowner,
         updateddate = NOW()
     WHERE workspaceid = inworkspaceid;

    -- Add the old owner as a collaborator
    INSERT INTO tbl_workspace_collaborator(workspaceid,username,collaboratorrole,updatedby,updateddate,
       createdby,createddate)
    SELECT inworkspaceid,inoldowner,role,inoldowner,NOW(),inoldowner,NOW() FROM temp_tbl_collabrole;

   IF (errmsg = "")
     THEN COMMIT;
   ELSE ROLLBACK;
  END IF;
  END IF;

  -- delete the temporary table
  DROP TEMPORARY TABLE IF EXISTS temp_tbl_collabrole;

  -- enable safe update
  SET sql_safe_updates=0;
END$$
DELIMITER ;