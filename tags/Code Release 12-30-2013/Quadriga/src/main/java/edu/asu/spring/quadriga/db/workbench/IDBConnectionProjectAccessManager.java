package edu.asu.spring.quadriga.db.workbench;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionProjectAccessManager {

	public abstract boolean chkProjectOwnerEditorRole(String userName, String projectId) throws QuadrigaStorageException;

	public abstract boolean chkDuplicateProjUnixName(String unixName, String projectId) throws QuadrigaStorageException;

	public abstract boolean chkProjectCollaborator(String userName, String collaboratorRole,
			String projectId) throws QuadrigaStorageException;

	public abstract boolean chkIsProjectAssociated(String userName) throws QuadrigaStorageException;

	public abstract boolean chkProjectOwner(String userName, String projectId) throws QuadrigaStorageException;

	public abstract boolean chkIsCollaboratorProjectAssociated(String userName,
			String collaboratorRole) throws QuadrigaStorageException;

}
