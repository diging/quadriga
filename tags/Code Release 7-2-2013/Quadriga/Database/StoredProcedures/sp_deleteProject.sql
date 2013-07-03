/*******************************************
Name          : sp_deleteProject

Description   : Delete the project from 
                tbl_project and its references

Called By     : UI (DBConnectionDictionaryManager.java)

Create By     : Kiran Kumar Batna

Modified Date : 06/12/2013

********************************************/
DROP PROCEDURE IF EXISTS sp_deleteProject;

DELIMITER $$
CREATE PROCEDURE sp_deleteProject
(
  IN  inprojdbid     TEXT,
  OUT errmsg         VARCHAR(255)   
)
BEGIN

    -- Declare local varaibles
    DECLARE rowvalue   VARCHAR(50);
    DECLARE position   INT;

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- deleting the temp table
    DROP TEMPORARY TABLE IF EXISTS temp_tbl_projectid;

    -- validating the input parameters
    IF (inprojdbid IS NULL OR inprojdbid = "")
    THEN SET errmsg = "Project internal id cannot be null.";
	END IF;

    -- inserting the input into a temp table
    CREATE TEMPORARY TABLE temp_tbl_projectid
    (
         projid VARCHAR(50)
    );

    SET position = LOCATE(',',inprojdbid);

    WHILE(position > 0) DO
        SET rowvalue = SUBSTRING_INDEX(inprojdbid,',',1);
        INSERT INTO temp_tbl_projectid(projid) VALUES(rowvalue);
        SET inprojdbid = SUBSTRING(inprojdbid FROM position+1);
		SET position = LOCATE(',',inprojdbid);
    END WHILE;

    -- inserting the row when the input has a single value
    INSERT INTO temp_tbl_projectid(projid) VALUES(inprojdbid);

    -- Validating if one of the input values is wrong
    IF EXISTS(SELECT 1 FROM temp_tbl_projectid tempproj LEFT JOIN 
                   vw_project proj ON tempproj.projid = proj.projectid
                 WHERE proj.projectid IS NULL)
    THEN SET errmsg = "One or more project internal id is invalid";
    END IF;

    -- deleting the values from the tables

    SET SQL_SAFE_UPDATES=0;

    IF (errmsg IS NULL)
    THEN SET errmsg = "";
        
		 START TRANSACTION;
       
         -- deleting the records from the tbl_project_workspace
         DELETE FROM tbl_project_workspace
         WHERE projectid IN (SELECT projid FROM temp_tbl_projectid);

         -- deleting the records from the tbl_project_collaborator
		 DELETE FROM tbl_project_collaborator
          WHERE projectid IN (SELECT projid FROM temp_tbl_projectid);

         --  deleting the records from tbl_project
         DELETE FROM tbl_project
          WHERE projectid IN (SELECT projid FROM temp_tbl_projectid);

        IF (errmsg = "")
        THEN COMMIT;
        ELSE ROLLBACK;
        END IF;
    END IF;
      -- drop the temp table created
       DROP TEMPORARY TABLE IF EXISTS temp_tbl_projectid;
       SET SQL_SAFE_UPDATES=1;
END$$
DELIMITER ;
