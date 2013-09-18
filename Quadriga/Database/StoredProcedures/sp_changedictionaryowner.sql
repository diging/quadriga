/*******************************************
Name          : sp_changedictionaryowner

Description   : Chagne the ownership of the dictioanry

Called By     : UI

Create By     : Kiran Kumar Batna

Modified Date : 09/18/2013

********************************************/
DROP PROCEDURE IF EXISTS  sp_changedictionaryowner;
DELIMITER $$
CREATE PROCEDURE sp_changedictionaryowner
( 
  IN  indictionaryid     VARCHAR(100),
  IN  inoldowner         VARCHAR(50),
  IN  innewowner         VARCHAR(50),
  IN  incollabrole       TEXT,
  OUT errmsg             VARCHAR(255)
)
BEGIN
   	 -- declare local variables
    DECLARE rowvalue   VARCHAR(30);
    DECLARE position   INT;

    -- the error handler for any sql exception
     DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

        -- Validating the input parameters
   IF (indictionaryid IS NULL OR indictionaryid ="")
   THEN SET errmsg = "Dictionary id cannot be empty.";
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

   IF NOT EXISTS (SELECT 1 FROM vw_dictionary WHERE id = indictionaryid)
     THEN SET errmsg = "Dictionary id is invalid";
   END IF;

   IF NOT EXISTS (SELECT 1 FROM vw_quadriga_user WHERE username = inoldowner)
   THEN SET errmsg = "User name is invalid.";
   END IF;

   IF NOT EXISTS (SELECT 1 FROM vw_quadriga_user WHERE username = innewowner)
   THEN SET errmsg = "Username is invalid.";
   END IF;

   IF NOT EXISTS (SELECT 1 FROM vw_dictionary WHERE dictionaryowner = inoldowner
                  AND id = indictionaryid)
   THEN SET errmsg = "User does not have privileges to transfer ownership.";
   END IF;

   IF NOT EXISTS (SELECT 1 FROM vw_dictionary_collaborator 
				   WHERE collaboratoruser = innewowner
                 AND dictionaryid = indictionaryid)
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

   IF errmsg IS NULL
     THEN SET errmsg = "";
     START TRANSACTION;
     -- delete the new user as a dictionary collaborator
     DELETE FROM tbl_dictionary_collaborator WHERE collaboratoruser = innewowner
        AND dictionaryid = indictionaryid;

     	 -- assign new owner to the project
     UPDATE tbl_dictionary
       SET dictionaryowner = innewowner
		  ,updatedby = inoldowner
          ,updateddate = NOW()
      WHERE id = indictionaryid;

	 -- insert the old owner as collaborator to the concept collection
    INSERT INTO tbl_dictionary_collaborator(id,collaboratoruser,
      collaboratorrole,updatedby,updateddate,createdby,createddate)
    SELECT indictionaryid,inoldowner,role,inoldowner,NOW(),inoldowner,NOW() 
      FROM temp_tbl_collabrole;
	IF errmsg = ""
      THEN COMMIT;
    ELSE 
      ROLLBACK;
    END IF;
  END IF;
    -- enable safe update
  SET sql_safe_updates=1;
  DROP TEMPORARY TABLE IF EXISTS temp_tbl_collabrole;
END$$
DELIMITER ;