package edu.asu.spring.quadriga.service.workspace;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface ICheckWSSecurity {

	public abstract boolean chkWorkspaceAccess(String userName, String projectId,
			String workspaceId) throws QuadrigaStorageException;

	public abstract boolean chkCreateWSAccess(String userName, String projectId)
			throws QuadrigaStorageException;

	public abstract boolean chkCollabWorkspaceAccess(String userName, String workspaceId,
			String collaboratorRole) throws QuadrigaStorageException;

	public abstract boolean chkModifyWorkspaceAccess(String userName, String workspaceId)
			throws QuadrigaStorageException;

	public abstract boolean checkWorkspaceOwner(String userName, String workspaceId)
			throws QuadrigaStorageException;

	public abstract boolean checkWorkspaceOwnerEditorAccess(String userName, String workspaceId)
			throws QuadrigaStorageException;

	public abstract boolean checkWorkspaceProjectInheritOwnerEditorAccess(String userName,
			String workspaceId) throws QuadrigaStorageException;

}
