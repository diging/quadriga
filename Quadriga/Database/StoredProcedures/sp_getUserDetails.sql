/*******************************************
Name          : sp_getUserDetails

Description   : Retrieves the details of a user
				supplied by UI

Called By     : UI (DBConnectionManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 05/21/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_getUserDetails;
DELIMITER $$
CREATE PROCEDURE sp_getUserDetails
( IN inusername   VARCHAR(30),
  OUT errmsg      VARCHAR(50)
)
BEGIN
     
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
     SET errmsg = 'SQL exception has occurred';
    
    IF(errmsg IS NULL)
    THEN
    SET errmsg = "";
    -- fetch the results of the user and return
    SELECT  fullname,
		    username,
            email,
            quadrigarole            
      FROM vw_quadriga_user
	 WHERE username = inusername ;  
     END IF;
    
END$$
DELIMITER ;
