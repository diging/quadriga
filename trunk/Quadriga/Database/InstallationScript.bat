@ECHO OFF

REM Setting the input variables for the batch file
SET MYSQLLocation=%1
SET Server_Address=%2
SET Server_Port=%3
SET User=%4
SET Database=%5

ECHO %MYSQLLocation% --host=%Server_Address% --port=%Server_Port% --user=%User%

REM %MYSQLLocation% --host=%Server_Address% --port=%Server_Port% --user=%User% < .\Scripts\db_quadriga_schema.sql

REM %MYSQLLocation% --user=%User% --password < .\Scripts\db_quadriga_schema.sql

ECHO Creating Database
%MYSQLLocation% --user=%User% --password < .\Scripts\db_quadriga_schema.sql

REM ECHO Show database
REM %MYSQLLocation% --user=%User% --password < .\Scripts\db_show_database_quadriga.sql
REM PAUSE 

REM ECHO Using the Database
REM %MYSQLLocation% --user=%User% --password  < .\Scripts\db_use_quadriga.sql

REM PAUSE

ECHO Creating tables
%MYSQLLocation% --user=%User% --password %Database%  < .\Tables\tbl_project.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\Tables\tbl_project_collaborator.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\Tables\tbl_project_workspace.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\Tables\tbl_quadriga_user.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\Tables\tbl_quadriga_user_denied.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\Tables\tbl_quadriga_user_requests.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\Tables\tbl_workspace.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\Tables\tbl_workspace_collaborator.sql

ECHO Creating View
%MYSQLLocation% --user=%User% --password %Database%  < .\Views\vw_project.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\Views\vw_project_collaborator.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\Views\vw_project_workspace.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\Views\vw_quadriga_user.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\Views\vw_quadriga_user_denied.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\Views\vw_quadriga_user_requests.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\Views\vw_workspace.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\Views\vw_workspace_collaborator.sql

REM ECHO Showing tables
REM %MYSQLLocation% --user=%User% --password %Database%  < .\Scripts\db_show_tables_quadriga.sql

REM PAUSE

ECHO Creating Stored Procedures
%MYSQLLocation% --user=%User% --password %Database%  < .\StoredProcedures\sp_addProjectDetails.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\StoredProcedures\sp_addUserRequest.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\StoredProcedures\sp_approveUserRequest.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\StoredProcedures\sp_deactivateUser.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\StoredProcedures\sp_denyUserRequest.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\StoredProcedures\sp_getActiveUsers.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\StoredProcedures\sp_getInActiveUsers.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\StoredProcedures\sp_getProjectCollaborators.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\StoredProcedures\sp_getProjectDetails.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\StoredProcedures\sp_getProjectList.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\StoredProcedures\sp_getProjectWorkspace.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\StoredProcedures\sp_getUserDetails.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\StoredProcedures\sp_getUserRequests.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\StoredProcedures\sp_updateProjectDetails.sql
%MYSQLLocation% --user=%User% --password %Database%  < .\StoredProcedures\sp_updateUserRoles.sql

REM ECHO Show Procedures
REM %MYSQLLocation% --user=%User% --password %Database%  < .\Scripts\db_show_sp_quadriga.sql






