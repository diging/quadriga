
DROP PROCEDURE IF EXISTS sp_deleteProjectCollaborators;
DELIMITER $$
CREATE PROCEDURE sp_deleteProjectCollaborators
(
	IN  inprojectid			VARCHAR(50),
	IN 	incollaboratoruser  VARCHAR(100),
	OUT errmsg				VARCHAR(255)
)

BEGIN 

	 DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
	 SET errmsg = "SQL EXCEPTION has occured";

	IF (inprojectid IS NULL OR inprojectid="")
	THEN SET errmsg = "project id cannot be empty";
	END IF;

	IF (incollaboratoruser IS NULL OR incollaboratoruser="")
	THEN SET errmsg = "enter some value for collborator user";
	END IF;

	IF (errmsg IS NULL)
	THEN SET errmsg = " ";
			START TRANSACTION;
			DELETE FROM tbl_project_collaborator
			WHERE projectid = inprojectid AND 
				  collaboratoruser = incollaboratoruser;
		IF(errmsg = " ")
		THEN COMMIT;
		ELSE ROLLBACK;
		END IF;
	END IF;
END$$
DELIMITER ;
	