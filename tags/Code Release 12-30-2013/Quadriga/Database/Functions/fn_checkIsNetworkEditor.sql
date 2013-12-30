/*******************************************
Name          : fn_checkIsNetworkEditor

Description   : Check if the user is editor to particular network

Called By     : UI

Create By     : Kiran Kumar Batna

Modified Date : 10/30/2013

********************************************/
DROP FUNCTION IF EXISTS fn_checkIsNetworkEditor;

DELIMITER $$
CREATE FUNCTION fn_checkIsNetworkEditor
(
  innetworkid  VARCHAR(100),
  inuser       VARCHAR(50)
)
RETURNS boolean
BEGIN
    IF EXISTS(SELECT 1 FROM vw_networks WHERE networkid = innetworkid)
      THEN 
		   IF EXISTS (SELECT 1 FROM vw_workspace_editor vwws
                          JOIN vw_networks vwnw
                          ON vwws.workspaceid = vwnw.workspaceid
                          AND vwws.user = inuser
                          AND vwnw.networkid = innetworkid
                     )
             THEN RETURN TRUE;
             ELSE 
                IF EXISTS(SELECT 1 FROM vw_project_editor vwproj
                           JOIN vw_project_workspace vwpws
                             ON vwproj.projectid = vwpws.projectid
						   JOIN vw_workspace_editor vwws
                             ON vwpws.workspaceid = vwws.workspaceid
	                       JOIN vw_networks vwnw
                             ON vwnw.workspaceid = vwws.workspaceid
                            AND vwproj.user = inuser
                            AND vwnw.networkid = innetworkid
                         )
				 THEN RETURN TRUE;
                   ELSE RETURN FALSE;
                END IF;
           END IF;
      ELSE
         RETURN FALSE;
     END IF;
END$$
DELIMITER ;