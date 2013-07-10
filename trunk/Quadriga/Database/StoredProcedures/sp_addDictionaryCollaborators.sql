DROP PROCEDURE IF EXISTS sp_addDictionaryCollaborators;
DELIMITER $$
CREATE PROCEDURE sp_addDictionaryCollaborators
(
	IN indictionaryid 		VARCHAR(200),
	IN incollaboratoruser	VARCHAR(200),
	IN incollaboratorrole	VARCHAR(50),
	IN inuser               VARCHAR(10),
	OUT errmsg				VARCHAR(200)
)

BEGIN

	DECLARE rowvalue   VARCHAR(30);
    DECLARE position   INT;

	 -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

	-- validating the input variables
	
	IF(incollaboratoruser IS NULL OR incollaboratoruser = "")
		THEN SET errmsg = "collaborator user cannot be empty";
	END IF;

	IF(incollaboratorrole IS NULL OR incollaboratorrole = "")
		THEN SET errmsg = "collaborator role cannot be empty";
	END IF;

	IF EXISTS(SELECT 1 FROM vw_dictionary_collaborator 
				WHERE dictionaryid = indictionaryid AND collaboratoruser = incollaboratoruser)
		THEN SET errmsg = "collaborator already exists";
	END IF;

	DROP TEMPORARY TABLE IF EXISTS temp_tbl_role;
	
	CREATE TEMPORARY TABLE temp_tbl_role
	(
		role 		VARCHAR(200)
	);

	SET position = LOCATE(',',incollaboratorrole);

	WHILE (position > 0)DO
	SET rowvalue = SUBSTRING_INDEX(incollaboratorrole,',',1);
	INSERT INTO temp_tbl_role(role) values (rowvalue);
	SET incollaboratorrole = SUBSTRING(incollaboratorrole FROM position+1);
	SET position = LOCATE(',',incollaboratorrole);
	END WHILE;

	INSERT INTO temp_tbl_role(role) values (incollaboratorrole);

	-- inserting record into the tbl_project_collaborator table
	IF(errmsg IS NULL)
	THEN SET errmsg = "";
	START TRANSACTION;
	INSERT INTO
	tbl_dictionary_collaborator(id,collaboratoruser,collaboratorrole,
							 updatedby,updateddate,createdby,createddate)
	SELECT indictionaryid,incollaboratoruser,role,inuser,NOW(),inuser,NOW()
	FROM temp_tbl_role;

		IF (errmsg = "")
           THEN COMMIT;
         ELSE ROLLBACK;
		END IF;
	END IF;

	DROP TEMPORARY TABLE IF EXISTS temp_tbl_role;

END$$
DELIMITER ;

