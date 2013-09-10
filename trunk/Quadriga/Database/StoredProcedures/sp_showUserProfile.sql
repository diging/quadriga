DROP PROCEDURE IF EXISTS sp_showUserProfile;
DELIMITER $$

CREATE PROCEDURE sp_showUserProfile
(
	IN inusername	VARCHAR(50),
	OUT errmsg		VARCHAR(50)		
)

BEGIN

	 -- the error handler for any sql exception
   -- DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
	-- SET errmsg = "SQL exception has occurred";

	IF(inusername IS NULL OR inusername=" ")
		THEN SET errmsg = "username cannot be empty";
	END IF;

	IF(errmsg IS NULL)
	THEN SET errmsg = " ";
	SELECT u.fullname, u.email, p.username, p.servicename, p.uri FROM tbl_quadriga_user as u, tbl_quadriga_userprofile as p
	WHERE u.username = inusername
	AND u.username = p.username;
	END IF;

END $$
DELIMITER ;