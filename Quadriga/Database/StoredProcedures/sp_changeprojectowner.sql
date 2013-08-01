/*******************************************
Name          : sp_changeprojectowner

Description   : Chagne the ownership of the project

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 06/12/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_changeprojectowner;
DELIMITER $$
CREATE PROCEDURE sp_changeprojectowner
(
  IN  inprojectid       VARCHAR(100),
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
   IF (inprojectid IS NULL OR inprojectid ="")
   THEN SET errmsg = "Project id cannot be empty.";
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

   IF NOT EXISTS (SELECT 1 FROM vw_project WHERE projectid = inprojectid)
   THEN SET errmsg = "Project id is invalid.";
   END IF;

   IF NOT EXISTS (SELECT 1 FROM vw_quadriga_user WHERE username = inoldowner)
   THEN SET errmsg = "User name is invalid.";
   END IF;

   IF NOT EXISTS (SELECT 1 FROM vw_quadriga_user WHERE username = innewowner)
   THEN SET errmsg = "Username is invalid.";
   END IF;

   IF NOT EXISTS (SELECT 1 FROM vw_project WHERE projectowner = inoldowner
                  AND projectid = inprojectid)
   THEN SET errmsg = "User does not have privileges to transfer ownership.";
   END IF;

   IF NOT EXISTS (SELECT 1 FROM vw_project_collaborator WHERE collaboratoruser = innewowner
                 AND projectid = inprojectid)
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
      -- delete the new user as a project collaborator
      DELETE FROM tbl_project_collaborator WHERE collaboratoruser = innewowner;
   
      -- Assign the new owner to the project
      UPDATE tbl_project 
      SET projectowner = innewowner
         ,updatedby = inoldowner
         ,updateddate = NOW()
      WHERE projectid = inprojectid;

      -- Assign the old owner as collaborator to the project
      INSERT INTO tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,updatedby,updateddate,
       createdby,createddate)
     SELECT inprojectid,inoldowner,role,inoldowner,NOW(),inoldowner,NOW() FROM temp_tbl_collabrole;

   IF (errmsg = "")
   THEN COMMIT;
   ELSE ROLLBACK;
   END IF;
  END IF;
  -- enable safe update
  SET sql_safe_updates=1;
  DROP TEMPORARY TABLE IF EXISTS temp_tbl_collabrole;
END$$
DELIMITER ;
