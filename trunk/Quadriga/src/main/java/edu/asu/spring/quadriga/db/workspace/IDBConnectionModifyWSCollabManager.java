package edu.asu.spring.quadriga.db.workspace;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyWSCollabManager 
{

	/**
     * This methods adds a collaborator to a workspace.
	 * @param collaborator
	 * @param collabRoleList
	 * @param workspaceid
	 * @param userName
	 * @throws QuadrigaStorageException
	 */
	public abstract void addWorkspaceCollaborator(String collaborator, String collabRoleList,
			String workspaceid, String userName) throws QuadrigaStorageException;

	/**
	 * This method deletes the association of the give user as collaborator to workspace.
	 * @param collaborator
	 * @param workspaceid
	 * @throws QuadrigaStorageException
	 */
	public abstract void deleteWorkspaceCollaborator(String collaborator, String workspaceid)
			throws QuadrigaStorageException;

	/**
	 * This method updates the roles of the collaborator associated with the 
	 * workspace.
	 * @param workspaceId
	 * @param collabUser
	 * @param collaboratorRole
	 * @param userName
	 * @throws QuadrigaStorageException
	 */
	public abstract void updateWorkspaceCollaborator(String workspaceId,
			String collabUser, String collaboratorRole, String userName)
			throws QuadrigaStorageException;

}
