/*******************************************
Name          : fn_check_workbench_association

Description   : check if the user is accoiated to workbench

Called By     : UI

Create By     : Kiran Kumar Batna

Modified Date : 11/09/2013

********************************************/
DROP FUNCTION IF EXISTS fn_check_workbench_association;
DELIMITER $$
CREATE FUNCTION fn_check_workbench_association
(
  indeleteUser       VARCHAR(50)
)
RETURNS BOOLEAN
BEGIN
     -- check if the user is associated with projects
     IF EXISTS(SELECT 1 FROM vw_project WHERE projectowner = indeleteUser)
        THEN RETURN TRUE;
         ELSE IF EXISTS(SELECT 1 FROM vw_project_collaborator WHERE collaboratoruser = indeleteUser)
              THEN RETURN TRUE;
              ELSE IF EXISTS (SELECT 1 FROM vw_workspace WHERE workspaceowner = indeleteUser)
                    THEN RETURN TRUE;
                   ELSE IF EXISTS (SELECT 1 FROM vw_workspace_collaborator WHERE username = indeleteUser)
                        THEN RETURN TRUE;
						ELSE RETURN FALSE;
                        END IF;
                   END IF;
              END IF;
      END IF;
END$$
DELIMITER ;