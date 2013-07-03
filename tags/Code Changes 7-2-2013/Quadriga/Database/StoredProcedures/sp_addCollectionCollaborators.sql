DROP PROCEDURE IF EXISTS sp_addCollectionCollaborators;
DELIMITER $$
CREATE PROCEDURE sp_addCollectionCollaborators
(
	IN incollectionid 			INT,
	IN incollaboratoruser	VARCHAR(10),
	IN incollaboratorrole	VARCHAR(50),
	OUT errmsg				VARCHAR(200)
)

BEGIN

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

	IF EXISTS(SELECT 1 FROM vw_conceptcollections_collaborator 
				WHERE collectionid = incollectionid AND collaboratoruser = incollaboratoruser)
		THEN SET errmsg = "collaborator already exists";
	END IF;

	-- inserting record into the tbl_project_collaborator table
	IF(errmsg IS NULL)
	THEN SET errmsg = "no errors";
	START TRANSACTION;
	INSERT INTO
	tbl_conceptcollections_collaborator(collectionid,collaboratoruser,collaboratorrole,
							 updatedby,updateddate,createdby,createddate)
	VALUES(incollectionid,incollaboratoruser,incollaboratorrole,incollaboratoruser,
		   NOW(),incollaboratoruser, NOW());
		IF (errmsg = "no errors")
           THEN COMMIT;
         ELSE ROLLBACK;
		END IF;
	END IF;
END$$
DELIMITER ;

