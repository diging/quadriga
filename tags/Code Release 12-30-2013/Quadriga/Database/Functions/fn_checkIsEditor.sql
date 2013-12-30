/*******************************************
Name          : fn_checkIsEditor

Description   : Check if the user is editor

Called By     : UI

Create By     : Kiran Kumar Batna

Modified Date : 10/30/2013

********************************************/
DROP FUNCTION IF EXISTS fn_checkIsEditor;
DELIMITER $$
CREATE FUNCTION fn_checkIsEditor(inuser VARCHAR(50))
RETURNS boolean
BEGIN
	-- check if the user is asscoiated as a project editor
	IF EXISTS(SELECT 1 FROM vw_project_editor WHERE user = inuser) 
     THEN RETURN TRUE;
    ELSE 
		 -- check if the user is associated as a workspace editor
         IF EXISTS(SELECT 1 FROM vw_workspace_editor WHERE user = inuser)
		   THEN RETURN TRUE;
         ELSE RETURN FALSE;
         END IF;
    END IF;
END$$
DELIMITER ;