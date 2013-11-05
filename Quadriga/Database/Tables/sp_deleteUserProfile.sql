DROP PROCEDURE IF EXISTS sp_deleteUserProfile;
DELIMITER $$
CREATE PROCEDURE sp_deleteUserProfile
(
	IN inusername 		VARCHAR(50),
	IN inserviceid		VARCHAR(50),
	IN inprofileid		VARCHAR(255),
	OUT errmsg			VARCHAR(100)
)

BEGIN

	 -- the error handler for any sql exception
     DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
	 SET errmsg = "SQL exception has occurred";

	-- validating the input parameter
	IF(inusername IS NULL OR inusername=" ")
		THEN SET errmsg = "username cannot be empty";
	END IF;


	IF(inserviceid IS NULL OR inserviceid=" ")
		THEN SET errmsg = "service id cannot be empty";
	END IF;

	IF(inprofileid IS NULL OR inprofileid=" ")
		THEN SET errmsg = "profile id cannot be empty";
	END IF;

	IF(errmsg IS NULL)
	THEN SET errmsg = "no errors";
	START TRANSACTION;
	DELETE FROM tbl_quadriga_userprofile WHERE profileid = inprofileid;
	
	IF (errmsg="no errors")
	THEN COMMIT;
	ELSE ROLLBACK;
	END IF;

	END IF;

END $$

DELIMITER ;
