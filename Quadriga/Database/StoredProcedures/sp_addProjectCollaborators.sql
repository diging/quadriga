DROP PROCEDURE IF EXISTS sp_addProjectCollaborators;
DELIMITER $$
CREATE PROCEDURE sp_addProjectCollaborators
(
	IN inprojid	INT,
	IN incollaboratoruser	VARCHAR(10),
	IN incollaboratorrole	VARCHAR(50),
	OUT errmsg	VARCHAR(200)
)

BEGIN

	 -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

	-- validating the input variables
	IF(inprojid IS NULL OR inprojid = "")
		THEN SET errmsg = "projid cannot be empty";
	END IF;

	IF(incollaboratoruser IS NULL OR incollaboratoruser = "")
		THEN SET errmsg = "collaborator user cannot be empty";
	END IF;

	IF(incollaboratorrole IS NULL OR incollaboratorrole = "")
		THEN SET errmsg = "collaborator role cannot be empty";
	END IF;

	IF EXISTS(SELECT 1 FROM vw_project_collaborator 
				WHERE projectid = inprojid AND collaboratoruser = incollaboratoruser
				AND collaboratorrole = incollaboratorrole)
		THEN SET errmsg = "collaborator already exists";
	END IF;

	-- inserting record into the tbl_project_collaborator table
	IF(errmsg IS NULL)
	THEN SET errmsg = "no errors";
	START TRANSACTION;
	INSERT INTO
	tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,
							 updatedby,updateddate,createdby,createddate)
	VALUES(inprojid,incollaboratoruser,incollaboratorrole,incollaboratoruser,
		   NOW(),incollaboratoruser, NOW());
		IF (errmsg = "no errors")
           THEN COMMIT;
         ELSE ROLLBACK;
		END IF;
	END IF;
END$$
DELIMITER ;

