DROP PROCEDURE IF EXISTS sp_showCollectionCollaborators;
DELIMITER $$
CREATE PROCEDURE sp_showCollectionCollaborators
(
	IN incollectionid				INT,
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

	SELECT collaboratoruser,GROUP_CONCAT(collaboratorrole SEPARATOR ',') 
	FROM tbl_conceptcollections_collaborator 
	WHERE collectionid = incollectionid
	GROUP BY collaboratoruser;

END$$
DELIMITER ;


