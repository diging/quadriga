/*******************************************
Name          : sp_getEditorNetworkList

Description   : retrieves the network details
				of a particular editor for approval and reject

Called By     : UI (DBConnectionEditorManager.java)

Create By     : Lohith Dwaraka

Modified Date : 08/13/2013

********************************************/

DROP PROCEDURE IF EXISTS sp_getEditorNetworkList;

DELIMITER $$
CREATE PROCEDURE sp_getEditorNetworkList
(
  IN  inusername  VARCHAR(20),
 OUT errmsg    VARCHAR(255)
)
BEGIN

    -- the error handler for any sql exception
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
      SET errmsg = "SQL exception has occurred";

    -- check input variables
    IF(inusername IS NULL OR inusername = "")
     THEN SET errmsg = "User Name cannot be empty.";
    END IF;


    IF (errmsg IS NULL)
     THEN SET errmsg = "";
     -- retrieve the dictionary details
	 select networkid,workspaceid,networkname,networkowner,status 
	from tbl_networks where workspaceid IN (

	select distinct(workspaceid) 
	from tbl_workspace_collaborator where username=inusername 
	and collaboratorrole in ('wscollab_role2','wscollab_role1') 
	and workspaceid in 
	(select workspaceid from tbl_project_workspace where projectid in 
	(select distinct(projectid) from tbl_project_collaborator 
	where collaboratoruser = inusername and collaboratorrole IN ('collaborator_role4')))
	
	UNION DISTINCT
	
	select distinct(workspaceid) from tbl_project_workspace 
	where projectid in
	( select projectid from tbl_project_editor where owner =inusername)
	
	UNION DISTINCT
	
	select distinct(workspaceid) from tbl_workspace_editor where owner =inusername);
	
	END IF;
END$$
DELIMITER ;