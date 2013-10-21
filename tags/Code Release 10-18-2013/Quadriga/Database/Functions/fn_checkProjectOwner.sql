DROP FUNCTION IF EXISTS fn_checkProjectOwner;
DELIMITER $$
CREATE FUNCTION fn_checkProjectOwner
(
  inusername    VARCHAR(50)
)
RETURNS boolean
BEGIN
    
    IF((SELECT COUNT(projectid) FROM tbl_project WHERE projectowner = inusername)>0)
    THEN RETURN true;
    ELSE RETURN false;
    END if;    
END$$
DELIMITER ;