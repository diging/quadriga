DROP PROCEDURE IF EXISTS sp_deleteCollectionCollaborators;
DELIMITER $$
CREATE PROCEDURE sp_deleteCollectionCollaborators
(
	IN  incollectionid		VARCHAR(100),
	IN 	incollaboratoruser  VARCHAR(100),
	OUT errmsg				VARCHAR(255)
)

BEGIN 

	 DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
	 SET errmsg = "SQL EXCEPTION has occured";

	IF (incollectionid IS NULL OR incollectionid="")
	THEN SET errmsg = "conceptcollection id cannot be empty";
	END IF;

	IF (incollaboratoruser IS NULL OR incollaboratoruser="")
	THEN SET errmsg = "enter some value for collborator user";
	END IF;

	IF (errmsg IS NULL)
	THEN SET errmsg = "no errors";
			START TRANSACTION;
			DELETE FROM tbl_conceptcollections_collaborator
			WHERE collectionid = incollectionid AND 
				  collaboratoruser = incollaboratoruser;
		IF(errmsg = "no errors")
		THEN COMMIT;
		ELSE ROLLBACK;
		END IF;
	END IF;
END$$
DELIMITER ;
	