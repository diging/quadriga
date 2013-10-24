package edu.asu.spring.quadriga.db.workspace;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionWSAccessManager 
{

	public abstract boolean chkWorkspaceOwner(String userName, String workspaceId)
			throws QuadrigaStorageException;

	public abstract boolean chkWorkspaceOwnerEditorRole(String userName, String workspaceId)
			throws QuadrigaStorageException;

	public boolean chkWorkspaceProjectInheritOwnerEditorRole(String userName,
			String workspaceId) throws QuadrigaStorageException;

	public abstract boolean chkWorkspaceExists(String workspaceId)
			throws QuadrigaStorageException;

	public abstract boolean chkIsWorkspaceAssocaited(String userName)
			throws QuadrigaStorageException;

	public abstract boolean chkIsCollaboratorWorkspaceAssociated(String userName,
			String role) throws QuadrigaStorageException;
	

}
