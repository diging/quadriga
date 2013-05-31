-- execute permission on tables
GRANT SELECT ON TABLE tbl_project TO dbadmin@localhost;
GRANT INSERT ON TABLE tbl_project TO dbadmin@localhost;
GRANT UPDATE ON TABLE tbl_project TO dbadmin@localhost;
GRANT DELETE ON TABLE tbl_project TO dbadmin@localhost;

GRANT SELECT ON TABLE tbl_project_collaborator TO dbadmin@localhost;
GRANT INSERT ON TABLE tbl_project_collaborator TO dbadmin@localhost;
GRANT UPDATE ON TABLE tbl_project_collaborator TO dbadmin@localhost;
GRANT DELETE ON TABLE tbl_project_collaborator TO dbadmin@localhost;

GRANT SELECT ON TABLE tbl_quadriga_user TO dbadmin@localhost;
GRANT INSERT ON TABLE tbl_quadriga_user TO dbadmin@localhost;
GRANT UPDATE ON TABLE tbl_quadriga_user TO dbadmin@localhost;
GRANT DELETE ON TABLE tbl_quadriga_user TO dbadmin@localhost;

GRANT SELECT ON TABLE tbl_quadriga_user_denied TO dbadmin@localhost;
GRANT INSERT ON TABLE tbl_quadriga_user_denied TO dbadmin@localhost;
GRANT UPDATE ON TABLE tbl_quadriga_user_denied TO dbadmin@localhost;
GRANT DELETE ON TABLE tbl_quadriga_user_denied TO dbadmin@localhost;

GRANT SELECT ON TABLE tbl_quadriga_user_requests TO dbadmin@localhost;
GRANT INSERT ON TABLE tbl_quadriga_user_requests TO dbadmin@localhost;
GRANT UPDATE ON TABLE tbl_quadriga_user_requests TO dbadmin@localhost;
GRANT DELETE ON TABLE tbl_quadriga_user_requests TO dbadmin@localhost;

-- execute permission on views
GRANT SELECT  ON vw_project TO dbadmin@localhost;
GRANT SELECT ON vw_project_collaborator TO dbadmin@localhost;
GRANT SELECT ON vw_quadriga_user TO dbadmin@localhost;
GRANT SELECT ON vw_quadriga_user_denied TO dbadmin@localhost;
GRANT SELECT ON vw_quadriga_user_requests TO dbadmin@localhost;

-- execute permissions on stored procedures
GRANT EXECUTE ON PROCEDURE sp_addProjectDetails TO dbadmin@localhost;
GRANT EXECUTE ON PROCEDURE sp_approveUserRequest TO dbadmin@localhost;
GRANT EXECUTE ON PROCEDURE sp_deactivateUser TO dbadmin@localhost;
GRANT EXECUTE ON PROCEDURE sp_denyUserRequest TO dbadmin@localhost;
GRANT EXECUTE ON PROCEDURE sp_getActiveUsers TO dbadmin@localhost;
GRANT EXECUTE ON PROCEDURE sp_getInActiveUsers TO dbadmin@localhost;
GRANT EXECUTE ON PROCEDURE sp_getProjectList TO dbadmin@localhost;
GRANT EXECUTE ON PROCEDURE sp_getUserDetails TO dbadmin@localhost;
GRANT EXECUTE ON PROCEDURE sp_updateProjectDetails TO dbadmin@localhost;
GRANT EXECUTE ON PROCEDURE sp_updateUserRoles TO dbadmin@localhost;

