DROP PROCEDURE IF EXISTS sp_addUserProfile;
DELIMITER $$
CREATE PROCEDURE sp_addUserProfile
(
	IN inusername 		VARCHAR(50),
	IN inserviceid		VARCHAR(50),
	IN inprofileid		VARCHAR(50),
	IN indescription	VARCHAR(256),
	OUT errmsg			VARCHAR(100)
)

BEGIN

	 -- the error handler for any sql exception
    -- DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
	-- SET errmsg = "SQL exception has occurred";

	-- validating the input parameter
	IF(inusername IS NULL OR inusername=" ")
		THEN SET errmsg = "username cannot be empty";
	END IF;

	IF(SELECT 1 FROM tbl_quadriga_userprofile where username = inusername 
		and profileid=inprofileid)
		THEN SET errmsg = "service and profile id already exists";
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
	INSERT INTO 
	tbl_quadriga_userprofile(username,serviceid,profileid,description,updatedby,
							 updateddate,createdby,createddate)
	VALUES(inusername,inserviceid,inprofileid,indescription,inusername,NOW(),inusername,NOW());
	
	IF (errmsg="no errors")
	THEN COMMIT;
	ELSE ROLLBACK;
	END IF;

	END IF;

END $$

DELIMITER ;
