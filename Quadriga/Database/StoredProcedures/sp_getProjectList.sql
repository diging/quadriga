DROP PROCEDURE IF EXISTS sp_getProjectList;
DELIMITER $$
CREATE PROCEDURE sp_getProjectList
(
  OUT errmsg  VARCHAR(100)
)
BEGIN
	-- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = 'SQL exception has occurred';

	IF(errmsg IS NULL)
    THEN
    SET errmsg = "";
    -- fetch the results of the user and return
    SELECT  projectname,description,projectid,
            id,projectowner,accessibility
      FROM  vw_project; 
     END IF;
END$$
DELIMITER ;