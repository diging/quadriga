DROP PROCEDURE IF EXISTS sp_addUserProfile;
DELIMITER $$
CREATE PROCEDURE sp_addUserProfile
(
	IN inusername 	VARCHAR(50),
	IN inservicename	VARCHAR(50),
	IN inuri		VARCHAR(256),
	OUT errmsg		VARCHAR(100)
)

BEGIN

	 -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
	SET errmsg = "SQL exception has occurred";

	-- validating the input parameter
	IF(inusername IS NULL OR inusername=" ")
		THEN SET errmsg = "username cannot be empty";
	END IF;

	IF(SELECT 1 FROM tbl_quadriga_userprofile where username = inusername 
		and servicename=inservicename and uri=inuri)
		THEN SET errmsg = "service and uri already exists";
	END IF;

	IF(inservicename IS NULL OR inservicename=" ")
		THEN SET errmsg = "servicename cannot be empty";
	END IF;

	IF(inuri IS NULL OR inuri=" ")
		THEN SET errmsg = "uri cannot be empty";
	END IF;


	IF(errmsg IS NULL)
	THEN SET errmsg = " ";
	START TRANSACTION;
	INSERT INTO 
	tbl_quadriga_userprofile(username,servicename,uri,updatedby,
							 updateddate,createdby,createddate)
	VALUES(inusername,inservicename,inuri,inusername,NOW(),inusername,NOW());
	
	IF (errmsg=" ")
	THEN COMMIT;
	ELSE ROLLBACK;
	END IF;

	END IF;

END $$

DELIMITER ;
