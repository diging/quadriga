package edu.asu.spring.quadriga.dao.workspace;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceAccessDAO 
{

	/**
	 * This method verifies if the given user is owner of given workspace
	 * @param userName
	 * @param workspaceId
	 * @return TRUE - if the given user is the workspace owner.
	 *         FALSE - if the given user is not the workspace owner.
	 * @throws QuadrigaStorageException
	 */
	public abstract boolean chkWorkspaceOwner(String userName, String workspaceId)
			throws QuadrigaStorageException;

	/**
	 * This method verifies if the given user has editor role for the 
	 * given workspace.
	 * @param userName
	 * @param workspaceId
	 * @return TRUE - if the user has editor role.
	 *         FALSe - if the user has no editor role.
	 * @throws QuadrigaStorageException
	 */
	public abstract boolean chkWorkspaceOwnerEditorRole(String userName, String workspaceId)
			throws QuadrigaStorageException;

	/**
	 * This method verifies if the given user has editor role for the given
	 * workspace or the workspace associated project.
	 * @param userName
	 * @param workspaceId
	 * @return TRUE - if the user has editor role.
	 *         FALSE - if the user has no editor role.
	 * @throws QuadrigaStorageException
	 */
	public boolean chkWorkspaceProjectInheritOwnerEditorRole(String userName,
			String workspaceId) throws QuadrigaStorageException;

	/**
	 * This method checks if the workspace exists for the given workspaceid.
	 * @param workspaceId
	 * @return TRUE - if the workspace exists.
	 *         FALSE - if the workspace does not exists.
	 * @throws QuadrigaStorageException
	 */
	public abstract boolean chkWorkspaceExists(String workspaceId)
			throws QuadrigaStorageException;

	/**
	 * This method checks if any workspace is associated to given user
	 * @param userName
	 * @return TRUE - if the user has any workspace associated.
	 *         FALSE - if no workspace is associated with the given user.
	 * @throws QuadrigaStorageException
	 */
	public abstract boolean chkIsWorkspaceAssocaited(String userName)
			throws QuadrigaStorageException;

	/**
	 * This method checks if any workspace is associated to given collaborator.
	 * @param userName
	 * @param role
	 * @return TRUE - if the collaborator has any workspace associated.
	 *         FALSE - if the collaborator has no workspace associated.
	 * @throws QuadrigaStorageException
	 */
	public abstract boolean chkIsCollaboratorWorkspaceAssociated(String userName,
			String role) throws QuadrigaStorageException;
	

}
