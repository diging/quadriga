DROP FUNCTION IF EXISTS fn_checkWorkspaceOwner;
DELIMITER $$
CREATE FUNCTION fn_checkWorkspaceOwner
(
  inusername      VARCHAR(50),
  inworkspaceid   TEXT
)
RETURNS boolean
BEGIN
	-- Declare local varaibles
    DECLARE rowvalue   VARCHAR(50);
    DECLARE position   INT;
    
    -- deleting the temp table
    DROP TEMPORARY TABLE IF EXISTS temp_tbl_workspaceid;
    
        -- inserting the input into a temp table
    CREATE TEMPORARY TABLE temp_tbl_workspaceid
    (
         workspaceid VARCHAR(50)
    );

    SET position = LOCATE(',',inworkspaceid);

    WHILE(position > 0) DO
        SET rowvalue = SUBSTRING_INDEX(inworkspaceid,',',1);
        INSERT INTO temp_tbl_workspaceid(workspaceid) VALUES(rowvalue);
        SET inworkspaceid = SUBSTRING(inworkspaceid FROM position+1);
		SET position = LOCATE(',',inworkspaceid);
    END WHILE;

    -- inserting the row when the input has a single value
    INSERT INTO temp_tbl_workspaceid(projid) VALUES(inworkspaceid);
    
    IF ((SELECT COUNT(DISTINCT workspaceowner) FROM vw_workspace WHERE workspaceid IN (SELECT workspaceid FROM temp_tbl_workspaceid))>1)
    THEN RETURN FALSE;
    ELSE IF EXISTS (SELECT 1 FROM vw_workspace WHERE workspaceowner = inusername AND workspaceid IN(SELECT workspaceid FROM temp_tbl_workspaceid))
          THEN RETURN TRUE;
         ELSE RETURN FALSE;
         END IF;
    END IF;
END$$
DELIMITER ;