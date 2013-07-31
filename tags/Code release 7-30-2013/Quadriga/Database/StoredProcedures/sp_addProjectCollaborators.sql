DROP PROCEDURE IF EXISTS sp_addProjectCollaborators;
DELIMITER $$
CREATE PROCEDURE sp_addProjectCollaborators
(
	IN inprojid 			VARCHAR(50),
	IN incollaboratoruser	VARCHAR(10),
	IN incollaboratorrole	TEXT,
    IN inuser               VARCHAr(10),
	OUT errmsg				VARCHAR(200)
)

BEGIN

	 -- declare local variables
    DECLARE rowvalue   VARCHAR(30);
    DECLARE position   INT;

	 -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

	-- validating the input variables
	IF(inprojid IS NULL OR inprojid = "")
	 THEN SET errmsg = "project id cannot be empty";
	END IF;
      
	IF(incollaboratoruser IS NULL OR incollaboratoruser = "")
		THEN SET errmsg = "collaborator user cannot be empty";
	END IF;

	IF(incollaboratorrole IS NULL OR incollaboratorrole = "")
		THEN SET errmsg = "collaborator role cannot be empty";
	END IF;

	IF EXISTS(SELECT 1 FROM vw_project_collaborator 
				WHERE projectid = inprojid AND collaboratoruser = incollaboratoruser)
		THEN SET errmsg = "collaborator already exists";
	END IF;

    -- split the comma seperated string into a table
    DROP TEMPORARY TABLE IF EXISTS temp_tbl_collabrole;

	CREATE TEMPORARY TABLE temp_tbl_collabrole
    (
         role VARCHAR(30)
    );

    SET position = LOCATE(',',incollaboratorrole);

    WHILE(position > 0) DO
        SET rowvalue = SUBSTRING_INDEX(incollaboratorrole,',',1);
        INSERT INTO temp_tbl_collabrole(role) VALUES(rowvalue);
        SET incollaboratorrole = SUBSTRING(incollaboratorrole FROM position+1);
		SET position = LOCATE(',',incollaboratorrole);
    END WHILE;

    INSERT INTO temp_tbl_collabrole(role) VALUES(incollaboratorrole);

	-- inserting record into the tbl_project_collaborator table
	IF(errmsg IS NULL)
	THEN SET errmsg = "";
	START TRANSACTION;
	INSERT INTO
	tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,
							 updatedby,updateddate,createdby,createddate)
	SELECT inprojid,incollaboratoruser,role,inuser,NOW(),inuser,NOW() 
	  FROM temp_tbl_collabrole;
         
		IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
		END IF;
	END IF;
END$$
DELIMITER ;