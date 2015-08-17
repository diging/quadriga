package edu.asu.spring.quadriga.service.workspace;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyWSCollabManager {

	public abstract void addWorkspaceCollaborator(String collaborator, String collabRoleList,
			String workspaceid, String userName) throws QuadrigaStorageException;

	public abstract void deleteCollaborators(String collaborator, String workspaceid)
			throws QuadrigaStorageException;

	public abstract void updateCollaborators(String workspaceId,
			String collabUser, String collaboratorRole, String userName)
			throws QuadrigaStorageException;

}
