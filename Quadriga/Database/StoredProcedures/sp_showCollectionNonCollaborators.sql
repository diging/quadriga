DROP PROCEDURE IF EXISTS sp_showCollectionNonCollaborators;
DELIMITER $$
CREATE PROCEDURE sp_showCollectionNonCollaborators
(
	IN incollectionid		INT,
	OUT errmsg				VARCHAR(200)
)

BEGIN

 -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

	IF (errmsg IS NULL)
		THEN SET errmsg = "";
    END IF;

 -- validating the input variables
	 IF(incollectionid IS NULL)
		 THEN SET errmsg = "collectionid cannot be empty";
	 END IF;

	SELECT username,quadrigarole FROM tbl_quadriga_user WHERE
	username NOT IN (SELECT collaboratoruser FROM tbl_conceptcollections_collaborator
	WHERE collectionid = incollectionid 
	UNION
	SELECT collectionowner FROM tbl_conceptcollections WHERE id = incollectionid);
END$$
DELIMITER ;


