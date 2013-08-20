DROP FUNCTION IF EXISTS fn_checkProjectOwner;
DELIMITER $$
CREATE FUNCTION fn_checkProjectOwner
(
  inusername    VARCHAR(50),
  inprojdbid   TEXT
)
RETURNS boolean
BEGIN
	-- Declare local varaibles
    DECLARE rowvalue   VARCHAR(50);
    DECLARE position   INT;
    
    -- deleting the temp table
    DROP TEMPORARY TABLE IF EXISTS temp_tbl_projectid;
    
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
    
    IF ((SELECT COUNT(DISTINCT projectowner) FROM vw_project WHERE projectid IN (SELECT projid FROM temp_tbl_projectid))>1)
    THEN RETURN FALSE;
    ELSE IF EXISTS (SELECT 1 FROM vw_project WHERE projectowner = inusername AND projectid IN(SELECT projid FROM temp_tbl_projectid))
          THEN RETURN TRUE;
         ELSE RETURN FALSE;
         END IF;
    END IF;
END$$
DELIMITER ;