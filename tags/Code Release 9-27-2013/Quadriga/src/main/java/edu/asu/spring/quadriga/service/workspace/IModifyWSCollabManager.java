package edu.asu.spring.quadriga.service.workspace;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyWSCollabManager {

	public abstract void addWorkspaceCollaborator(String collaborator, String collabRoleList,
			String workspaceid, String userName) throws QuadrigaStorageException;

	public abstract void deleteWorkspaceCollaborator(String collaborator, String workspaceid)
			throws QuadrigaStorageException;

	public abstract void updateWorkspaceCollaborator(String workspaceId,
			String collabUser, String collaboratorRole, String userName)
			throws QuadrigaStorageException;

}