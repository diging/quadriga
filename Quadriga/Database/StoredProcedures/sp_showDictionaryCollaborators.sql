DROP PROCEDURE IF EXISTS sp_showDictionaryCollaborators;
DELIMITER $$
CREATE PROCEDURE sp_showDictionaryCollaborators
(
	IN indictionaryid		VARCHAR(100),
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
	IF(indictionaryid IS NULL)
		THEN SET errmsg = "dictionaryid cannot be empty";
	END IF;

	SELECT DISTINCT collaboratoruser FROM tbl_dictionary_collaborator 
	WHERE id = indictionaryid;
END$$
DELIMITER ;


