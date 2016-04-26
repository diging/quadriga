package edu.asu.spring.quadriga.accesschecks;

import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWSSecurityChecker {

	public abstract boolean hasAccessToWorkspace(String userName, String projectId,
			String workspaceId) throws QuadrigaStorageException;

	public abstract boolean hasPermissionToCreateWS(String userName, String projectId)
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

	public abstract boolean checkIsWorkspaceExists(String workspaceId)
			throws QuadrigaStorageException;

	public abstract boolean checkIsWorkspaceAssociated(String userName)
			throws QuadrigaStorageException;

	public abstract boolean chkIsCollaboratorWorkspaceAssociated(String userName,
			String role) throws QuadrigaStorageException,QuadrigaAccessException;

}
