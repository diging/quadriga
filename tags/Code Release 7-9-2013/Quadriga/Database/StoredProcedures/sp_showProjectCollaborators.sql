DROP PROCEDURE IF EXISTS sp_showProjectCollaborators;
DELIMITER $$
CREATE PROCEDURE sp_showProjectCollaborators
(
	IN inprojid		VARCHAR(50),
	OUT errmsg		VARCHAR(200)
)
BEGIN
 -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

	IF (errmsg IS NULL)
		THEN SET errmsg = "";
    END IF;

 -- validating the input variables
	IF(inprojid IS NULL OR inprojid = "")
		THEN SET errmsg = "projid cannot be empty";
	END IF;

	SELECT DISTINCT collaboratoruser FROM tbl_project_collaborator 
	WHERE projectid = inprojid;
END$$
DELIMITER ;


