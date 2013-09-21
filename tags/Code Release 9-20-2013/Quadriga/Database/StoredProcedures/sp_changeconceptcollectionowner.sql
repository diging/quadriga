/*******************************************
Name          : sp_changeconceptcollectionowner

Description   : Chagne the ownership of the concept collection

Called By     : UI

Create By     : Kiran Kumar Batna

Modified Date : 09/05/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_changeconceptcollectionowner;
DELIMITER $$
CREATE PROCEDURE sp_changeconceptcollectionowner
(
  IN  incollectionid     VARCHAR(100),
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
   IF (incollectionid IS NULL OR incollectionid ="")
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

   IF NOT EXISTS (SELECT 1 FROM vw_conceptcollections WHERE id = incollectionid)
   THEN SET errmsg = "Collection id is invalid.";
   END IF;

   IF NOT EXISTS (SELECT 1 FROM vw_quadriga_user WHERE username = inoldowner)
   THEN SET errmsg = "User name is invalid.";
   END IF;

   IF NOT EXISTS (SELECT 1 FROM vw_quadriga_user WHERE username = innewowner)
   THEN SET errmsg = "Username is invalid.";
   END IF;

   IF NOT EXISTS (SELECT 1 FROM vw_conceptcollections WHERE collectionowner = inoldowner
                  AND id = incollectionid)
   THEN SET errmsg = "User does not have privileges to transfer ownership.";
   END IF;

   IF NOT EXISTS (SELECT 1 FROM vw_conceptcollections_collaborator 
				   WHERE collaboratoruser = innewowner
                 AND collectionid = incollectionid)
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
     -- delete the new user as a collection collaborator
     DELETE FROM tbl_conceptcollections_collaborator WHERE collaboratoruser = innewowner
        AND collectionid = incollectionid;

	 -- assign new owner to the project
     UPDATE tbl_conceptcollections
       SET collectionowner = innewowner
		  ,updatedby = inoldowner
          ,updateddate = NOW()
      WHERE id = incollectionid;

    -- insert the old owner as collaborator to the concept collection
    INSERT INTO tbl_conceptcollections_collaborator(collectionid,collaboratoruser,
      collaboratorrole,updatedby,updateddate,createdby,createddate)
    SELECT incollectionid,inoldowner,role,inoldowner,NOW(),inoldowner,NOW() 
      FROM temp_tbl_collabrole;
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