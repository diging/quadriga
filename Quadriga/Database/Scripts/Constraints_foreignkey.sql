/***************************************************************
 * Foreign key dependencies
 */
ALTER TABLE tbl_project
  ADD CONSTRAINT fk_tbl_project_projectowner   FOREIGN KEY(projectowner)
     REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;
     
ALTER TABLE tbl_project_workspace
 ADD CONSTRAINT fk_project_workspace_projectid FOREIGN KEY(projectid)
  REFERENCES tbl_project(projectid) ON DELETE CASCADE;

ALTER TABLE tbl_project_workspace
 ADD CONSTRAINT fk_project_workspace_workspaceid FOREIGN KEY(workspaceid)
 REFERENCES tbl_workspace(workspaceid) ON DELETE CASCADE;
 
ALTER TABLE tbl_project_editor
 ADD CONSTRAINT fk_project_editor_projectid FOREIGN KEY(projectid)
 REFERENCES tbl_project(projectid) ON DELETE CASCADE;

ALTER TABLE tbl_project_editor
 ADD CONSTRAINT fk_project_editor_editor FOREIGN KEY(editor)
 REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;
 
ALTER TABLE tbl_project_dictionary
 ADD CONSTRAINT fk_project_dictionary_projectid FOREIGN KEY(projectid)
 REFERENCES tbl_project(projectid) ON DELETE CASCADE;

ALTER TABLE tbl_project_dictionary
 ADD CONSTRAINT fk_project_dictionary_dictionaryid FOREIGN KEY(dictionaryid)
 REFERENCES tbl_dictionary(dictionaryid) ON DELETE CASCADE;
 
ALTER TABLE tbl_project_conceptcollection
 ADD CONSTRAINT fk_project_cc_projectid FOREIGN KEY(projectid)
 REFERENCES tbl_project(projectid) ON DELETE CASCADE;

ALTER TABLE tbl_project_conceptcollection
 ADD CONSTRAINT fk_project_cc_conceptcollectionid FOREIGN KEY(conceptcollectionid)
 REFERENCES tbl_conceptcollection(conceptcollectionid) ON DELETE CASCADE;
 
ALTER TABLE tbl_project_collaborator
 ADD CONSTRAINT fk_project_collaborator_projectid FOREIGN KEY(projectid)
  REFERENCES tbl_project(projectid) ON DELETE CASCADE;
  
ALTER TABLE tbl_conceptcollection
 ADD CONSTRAINT fk_tbl_conceptcollection_owner FOREIGN KEY(collectionowner)
 REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;
 
ALTER TABLE tbl_conceptcollection_collaborator
 ADD CONSTRAINT fk_cc_collaborator_conceptcollectionid FOREIGN KEY(conceptcollectionid)
 REFERENCES tbl_conceptcollection(conceptcollectionid) ON DELETE CASCADE;

ALTER TABLE tbl_conceptcollection_collaborator
 ADD CONSTRAINT fk_cc_collaborator_collaboratoruser FOREIGN KEY(collaboratoruser)
 REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;
  
ALTER TABLE tbl_conceptcollection_items
 ADD CONSTRAINT fk_cc_items FOREIGN KEY(conceptcollectionid)
 REFERENCES tbl_conceptcollection(conceptcollectionid) ON DELETE CASCADE;
 
ALTER TABLE tbl_workspace
 ADD CONSTRAINT fk_tbl_workspace_workspaceowner FOREIGN KEY(workspaceowner)
 REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;
 
ALTER TABLE tbl_workspace_collaborator
 ADD CONSTRAINT fk_tbl_ws_collaborator_workspaceid FOREIGN KEY(workspaceid)
 REFERENCES tbl_workspace(workspaceid) ON DELETE CASCADE;

ALTER TABLE tbl_workspace_collaborator
 ADD CONSTRAINT fk_tbl_ws_collaborator_collaboratoruser FOREIGN KEY(collaboratoruser)
 REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;
 
ALTER TABLE tbl_workspace_conceptcollection
 ADD CONSTRAINT fk_tbl_ws_cc_workspaceid FOREIGN KEY(workspaceid)
 REFERENCES tbl_workspace(workspaceid) ON DELETE CASCADE;

ALTER TABLE tbl_workspace_conceptcollection
 ADD CONSTRAINT fk_ws_cc_conceptcollectionid FOREIGN KEY(conceptcollectionid)
 REFERENCES tbl_conceptcollection(conceptcollectionid) ON DELETE CASCADE;
 
ALTER TABLE tbl_workspace_dictionary
 ADD CONSTRAINT fk_tbl_ws_dictionary_workspaceid FOREIGN KEY(workspaceid)
 REFERENCES tbl_workspace(workspaceid) ON DELETE CASCADE;

ALTER TABLE tbl_workspace_dictionary
 ADD CONSTRAINT fk_tbl_ws_dictionary_dictionaryid FOREIGN KEY(dictionaryid)
 REFERENCES tbl_dictionary(dictionaryid) ON DELETE CASCADE;
 
ALTER TABLE tbl_workspace_dspace
 ADD CONSTRAINT fk_tbl_ws_dspace_workspaceid FOREIGN KEY(workspaceid)
 REFERENCES tbl_workspace(workspaceid) ON DELETE CASCADE;
 
ALTER TABLE tbl_workspace_editor
 ADD CONSTRAINT fk_tbl_workspace_editor_workspaceid FOREIGN KEY(workspaceid)
 REFERENCES tbl_workspace(workspaceid) ON DELETE CASCADE;

ALTER TABLE tbl_workspace_editor
 ADD CONSTRAINT fk_tbl_workspace_editor_editor FOREIGN KEY(editor)
 REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;

ALTER TABLE tbl_dictionary
 ADD CONSTRAINT fk_tbl_dictionary_owner FOREIGN KEY(dictionaryowner)
 REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;
 
ALTER TABLE tbl_dictionary_items
 ADD CONSTRAINT fk_tbl_dictionary_items_dictionaryid FOREIGN KEY(dictionaryid)
 REFERENCES tbl_dictionary(dictionaryid) ON DELETE CASCADE;
 
ALTER TABLE tbl_dictionary_collaborator
 ADD CONSTRAINT fk_tbl_dictionary_cc_dictionaryid FOREIGN KEY(dictionaryid)
 REFERENCES tbl_dictionary(dictionaryid) ON DELETE CASCADE;

ALTER TABLE tbl_dictionary_collaborator
 ADD CONSTRAINT fk_tbl_dict_cc_collaboratoruser FOREIGN KEY(collaboratoruser)
 REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;
 
ALTER TABLE tbl_dspace_keys
 ADD CONSTRAINT fk_tbl_dspace_keys FOREIGN KEY(username)
 REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;
 
ALTER TABLE tbl_networks
 ADD CONSTRAINT fk_tbl_networks_workspaceid FOREIGN KEY(workspaceid)
 REFERENCES tbl_workspace(workspaceid) ON DELETE CASCADE;

ALTER TABLE tbl_networks
 ADD CONSTRAINT fk_tbl_networks_networkowner FOREIGN KEY(networkowner)
 REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;
 
ALTER TABLE tbl_network_statements
 ADD CONSTRAINT fk_tbl_network_statements_networkid FOREIGN KEY(networkid)
 REFERENCES tbl_networks(networkid) ON DELETE CASCADE;
 
ALTER TABLE tbl_network_assigned
 ADD CONSTRAINT fk_tbl_network_assigned FOREIGN KEY(networkid)
 REFERENCES tbl_networks(networkid) ON DELETE CASCADE;

ALTER TABLE tbl_network_assigned
 ADD CONSTRAINT fk_tbl_network_assigneduser FOREIGN KEY(assigneduser)
 REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;
 
ALTER TABLE tbl_network_annotations
 ADD CONSTRAINT fk_tbl_network_annotations_networkid FOREIGN KEY(networkid)
 REFERENCES tbl_networks(networkid) ON DELETE CASCADE;

ALTER TABLE tbl_network_annotations
 ADD CONSTRAINT fk_tbl_network_annotations_username FOREIGN KEY(username)
 REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;
 
ALTER TABLE tbl_quadriga_userprofile
 ADD CONSTRAINT fk_tbl_quadriga_userprofile FOREIGN KEY(username)
 REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;
 
ALTER TABLE tbl_quadriga_user_role
 ADD CONSTRAINT fk_tbl_quadriga_user_role_username FOREIGN KEY(username)
 REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;
 
ALTER TABLE tbl_quadriga_user_denied
 ADD CONSTRAINT fk_tbl_quadriga_user_denied_deniedby FOREIGN KEY(deniedby)
 REFERENCES tbl_quadriga_user(username) ON DELETE CASCADE;
