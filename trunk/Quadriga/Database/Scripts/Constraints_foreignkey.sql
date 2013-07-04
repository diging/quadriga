
ALTER TABLE tbl_conceptcollections_collaborator
ADD FOREIGN KEY(collectionid) REFERENCES tbl_conceptcollections(id);

ALTER TABLE tbl_conceptcollections_collaborator
ADD FOREIGN KEY(collaboratoruser) REFERENCES tbl_quadriga_user(username);

ALTER TABLE tbl_conceptcollections_items
ADD FOREIGN KEY(id) REFERENCES tbl_conceptcollections(id);

ALTER TABLE tbl_conceptcollections
ADD FOREIGN KEY(collectionowner) REFERENCES tbl_quadriga_user(username);

ALTER TABLE tbl_dictionary_collaborator
ADD FOREIGN KEY(id) REFERENCES tbl_dictionary(id);

ALTER TABLE tbl_dictionary_collaborator
ADD FOREIGN KEY(collaboratoruser)  REFERENCES tbl_quadriga_user(username);

ALTER TABLE tbl_dictionary_items
ADD FOREIGN KEY(id) REFERENCES tbl_dictionary(id);

ALTER TABLE tbl_dictionary
ADD FOREIGN KEY(dictionaryowner) REFERENCES tbl_quadriga_user(username);

ALTER TABLE tbl_project_collaborator
ADD FOREIGN KEY(projectid) REFERENCES tbl_project(projectid);

ALTER TABLE tbl_project_collaborator
ADD FOREIGN KEY(collaboratoruser)  REFERENCES tbl_quadriga_user(username);

ALTER TABLE tbl_project_workspace
ADD FOREIGN KEY(projectid) REFERENCES tbl_project(projectid);

ALTER TABLE tbl_project_workspace
ADD FOREIGN KEY(workspaceid)REFERENCES tbl_workspace(workspaceid);

ALTER TABLE tbl_project
ADD FOREIGN KEY(projectowner)REFERENCES tbl_quadriga_user(username);

ALTER TABLE tbl_quadriga_user_denied
ADD FOREIGN KEY(deniedby) REFERENCES tbl_quadriga_user(username);

ALTER TABLE tbl_workspace_collaborator
ADD FOREIGN KEY(username) REFERENCES tbl_quadriga_user(username);

ALTER TABLE tbl_workspace_collaborator
ADD FOREIGN KEY(id) REFERENCES tbl_workspace(workspaceid);

ALTER TABLE tbl_workspace
ADD FOREIGN KEY(workspaceowner)  REFERENCES tbl_quadriga_user(username); 

/* Begin - Foreign key dependencies for Dspace data */
ALTER TABLE tbl_dspace_collection
ADD FOREIGN KEY(communityid) REFERENCES tbl_dspace_community(communityid);

ALTER TABLE tbl_dspace_item
ADD FOREIGN KEY(communityid) REFERENCES tbl_dspace_community(communityid);
ALTER TABLE tbl_dspace_item
ADD FOREIGN KEY(collectionid) REFERENCES tbl_dspace_collection(collectionid);

ALTER TABLE tbl_dspace_bitstream
ADD FOREIGN KEY(communityid) REFERENCES tbl_dspace_community(communityid);
ALTER TABLE tbl_dspace_bitstream
ADD FOREIGN KEY(collectionid) REFERENCES tbl_dspace_collection(collectionid);
ALTER TABLE tbl_dspace_bitstream
ADD FOREIGN KEY(itemid) REFERENCES tbl_dspace_item(itemid);
/* End - Foreign key dependencies for Dspace data */