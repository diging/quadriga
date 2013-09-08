

DROP PROCEDURE IF EXISTS sp_deleteCollectionCollaborators;
DELIMITER $$
CREATE PROCEDURE sp_deleteCollectionCollaborators
(
	IN  incollectionid		VARCHAR(100),
	IN 	incollaboratoruser  TEXT,
	OUT errmsg				VARCHAR(255)
)

BEGIN 

	-- Declare local varaibles
    DECLARE rowvalue   VARCHAR(50);
    DECLARE position   INT;

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
	SET errmsg = "SQL EXCEPTION has occured";


    -- deleting the temp table
    DROP TEMPORARY TABLE IF EXISTS temp_tbl_collaboratoruser;

	IF (incollectionid IS NULL OR incollectionid="")
	THEN SET errmsg = "conceptcollection id cannot be empty";
	END IF;

	IF (incollaboratoruser IS NULL OR incollaboratoruser="")
	THEN SET errmsg = "enter some value for collborator user";
	END IF;

    -- inserting the input into a temp table
    CREATE TEMPORARY TABLE temp_tbl_collaboratoruser
    (
         user VARCHAR(100)
    );

 SET position = LOCATE(',',incollaboratoruser);
	
    WHILE(position > 0) DO
        SET rowvalue = SUBSTRING_INDEX(incollaboratoruser,',',1);
        INSERT INTO temp_tbl_collaboratoruser(user) VALUES(rowvalue);
        SET incollaboratoruser = SUBSTRING(incollaboratoruser FROM position+1);
		SET position = LOCATE(',',incollaboratoruser);
    END WHILE;

    -- inserting the row when the input has a single value
    INSERT INTO temp_tbl_collaboratoruser(user) VALUES(incollaboratoruser);

    -- Validating if one of the input values is wrong
    IF EXISTS(SELECT 1 FROM temp_tbl_collaboratoruser tempcs LEFT JOIN 
                   vw_quadriga_user vu ON tempcs.user = vu.username
			   WHERE tempcs.user IS NULL)
    THEN SET errmsg = "One or more user is invalid";
    END IF;

    IF EXISTS(SELECT 1 FROM temp_tbl_collaboratoruser tempcs LEFT JOIN
                  vw_conceptcollections_collaborator vwcc ON tempcs.user= vwcc.collaboratoruser
				WHERE tempcs.user IS NULL AND vwcc.collectionid = incollectionid
                  )
	THEN SET errmsg = "One or more user is not collaborator to the concept collection.";
    END IF;

	IF (errmsg IS NULL)
	THEN SET errmsg = "";
			START TRANSACTION;
			DELETE FROM tbl_conceptcollections_collaborator
			WHERE collectionid = incollectionid AND 
				  collaboratoruser IN (SELECT user FROM temp_tbl_collaboratoruser);
		IF(errmsg = "")
		THEN COMMIT;
		ELSE ROLLBACK;
		END IF;
	END IF;
END$$
DELIMITER ;