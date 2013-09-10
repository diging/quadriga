
ALTER TABLE tbl_conceptcollections_collaborator
ADD FOREIGN KEY(collectionid) REFERENCES tbl_conceptcollections(id) ON DELETE CASCADE;

ALTER TABLE tbl_conceptcollections_collaborator
ADD FOREIGN KEY(collaboratoruser) REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;

ALTER TABLE tbl_conceptcollections_items
ADD FOREIGN KEY(id) REFERENCES tbl_conceptcollections(id) ON DELETE CASCADE ;

ALTER TABLE tbl_conceptcollections
ADD FOREIGN KEY(collectionowner) REFERENCES tbl_quadriga_user(username)  ON DELETE CASCADE;

ALTER TABLE tbl_dictionary_collaborator
ADD FOREIGN KEY(id) REFERENCES tbl_dictionary(id) ON DELETE CASCADE ;

ALTER TABLE tbl_dictionary_collaborator
ADD FOREIGN KEY(collaboratoruser)  REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;

ALTER TABLE tbl_dictionary_items
ADD FOREIGN KEY(id) REFERENCES tbl_dictionary(id) ON DELETE CASCADE ;

ALTER TABLE tbl_dictionary
ADD FOREIGN KEY(dictionaryowner) REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;

ALTER TABLE tbl_project_collaborator
ADD FOREIGN KEY(projectid) REFERENCES tbl_project(projectid) ON DELETE CASCADE ;

ALTER TABLE tbl_project_collaborator
ADD FOREIGN KEY(collaboratoruser)  REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;

ALTER TABLE tbl_project_workspace
ADD FOREIGN KEY(projectid) REFERENCES tbl_project(projectid) ON DELETE CASCADE ;

ALTER TABLE tbl_project_workspace
ADD FOREIGN KEY(workspaceid)REFERENCES tbl_workspace(workspaceid) ON DELETE CASCADE ;

ALTER TABLE tbl_project
ADD FOREIGN KEY(projectowner)REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;

ALTER TABLE tbl_quadriga_user_denied
ADD FOREIGN KEY(deniedby) REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;

ALTER TABLE tbl_workspace_collaborator
ADD FOREIGN KEY(username) REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;

ALTER TABLE tbl_workspace_collaborator
ADD FOREIGN KEY(workspaceid) REFERENCES tbl_workspace(workspaceid) ON DELETE CASCADE ;

ALTER TABLE tbl_workspace
ADD FOREIGN KEY(workspaceowner)  REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE; 

/* Begin - Foreign key dependencies for Dspace data */
ALTER TABLE tbl_workspace_dspace
ADD FOREIGN KEY(workspaceid) REFERENCES tbl_workspace(workspaceid) ON DELETE CASCADE ;

ALTER TABLE tbl_dspace_keys
ADD FOREIGN KEY(username) REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;
/* End - Foreign key dependencies for Dspace data */

/* Begin - Foreign key dependencies for user profile */
ALTER TABLE tbl_quadriga_userprofile
ADD FOREIGN KEY(username) REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;
/* End - Foreign key dependencies for user profile */



