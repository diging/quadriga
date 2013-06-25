/*******************************************
Name          : sp_archiveworkspace

Description   : Archive the workspace

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 06/20/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_archiveworkspace;

DELIMITER $$
CREATE PROCEDURE sp_archiveworkspace
(
  IN inworkspaceid   TEXT,
  IN inarchive       TINYINT,
  IN inuser          VARCHAR(50),
  OUT errmsg         VARCHAR(255)
)
BEGIN

    -- Declare local varaibles
    DECLARE rowvalue   BIGINT;
    DECLARE position   INT;

	-- the error handler for any sql exception
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
   SET errmsg = "SQLException occurred";

    -- deleting the temp table
    DROP TEMPORARY TABLE IF EXISTS temp_tbl_workspaceid;

    -- validating the input parameters
    IF(inworkspaceid IS NULL OR inworkspaceid = "")
       THEN SET errmsg = "Workspaceid cannot be empty.";
    END IF;

    IF(inarchive IS NULL)
       THEN SET errmsg = "Archive parameter cannot be empty.";
    END IF;

    IF NOT EXISTS(SELECT 1 FROM vw_quadriga_user WHERE username = inuser)
      THEN SET errmsg = "Invalid User.";
    END IF;

    -- inserting the input into a temp table
    CREATE TEMPORARY TABLE temp_tbl_workspaceid
    (
         workspaceid BIGINT
    );

    SET position = LOCATE(',',inworkspaceid);
	
    WHILE(position > 0) DO
        SET rowvalue = SUBSTRING_INDEX(inworkspaceid,',',1);
        INSERT INTO temp_tbl_workspaceid(workspaceid) VALUES(rowvalue);
        SET inworkspaceid = SUBSTRING(inworkspaceid FROM position+1);
		SET position = LOCATE(',',inworkspaceid);
    END WHILE;

    -- inserting the row when the input has a single value
    INSERT INTO temp_tbl_workspaceid(workspaceid) VALUES(inworkspaceid);
    
    -- Validating if one of the input values is wrong
    IF EXISTS(SELECT 1 FROM temp_tbl_workspaceid tempws LEFT JOIN 
                   vw_workspace ws ON tempws.workspaceid = ws.workspaceid
                 WHERE ws.workspaceid IS NULL)
    THEN SET errmsg = "One or more project internal id is invalid";
    END IF;

	SET SQL_SAFE_UPDATES=0;

    IF(errmsg IS NULL)
      THEN SET errmsg = "";
	   START TRANSACTION;
          
          UPDATE tbl_workspace
             SET  isarchived = inarchive,
                  updatedby = inuser,
                  updateddate = NOW()
           WHERE workspaceid IN (SELECT workspaceid FROM temp_tbl_workspaceid);
	   IF(errmsg = "")
          THEN COMMIT;
       ELSE ROLLBACK;
       END IF;
     END IF;
		-- drop the temp table created
       DROP TEMPORARY TABLE IF EXISTS temp_tbl_workspaceid;
       SET SQL_SAFE_UPDATES=1;
END$$
DELIMITER ;