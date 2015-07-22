package edu.asu.spring.quadriga.accesschecks;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectSecurityChecker {

	public abstract boolean checkProjectAccess(String userName,String projectId)
			throws QuadrigaStorageException;

	public abstract boolean isProjectOwner(String userName,String projectId)
			throws QuadrigaStorageException;

	public abstract boolean checkQudrigaAdmin(String userName)
			throws QuadrigaStorageException;

	public abstract boolean checkCollabProjectAccess(String userName, String projectId,
			String collaboratorRole) throws QuadrigaStorageException;

	public abstract boolean checkProjectOwnerEditorAccess(String userName, String projectId)
			throws QuadrigaStorageException;

	public abstract boolean isCollaborator(String userName, String collaboratorRole,String projectId)
			throws QuadrigaStorageException;

	public abstract boolean ownsAtLeastOneProject(String userName) throws QuadrigaStorageException;

	public abstract boolean collaboratesOnAtLeastOneProject(String userName,
			String collaboratorRole) throws QuadrigaStorageException;
	
	public abstract boolean chkDuplicateProjUnixName(String unixName, String projectId) 
			throws QuadrigaStorageException;


}
