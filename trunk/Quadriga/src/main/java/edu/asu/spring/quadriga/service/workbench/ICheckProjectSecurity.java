package edu.asu.spring.quadriga.service.workbench;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface ICheckProjectSecurity {

	public abstract boolean checkProjectAccess(String userName,String projectId)
			throws QuadrigaStorageException;

	public abstract boolean checkProjectOwner(String userName,String projectId)
			throws QuadrigaStorageException;

	public abstract boolean checkQudrigaAdmin(String userName)
			throws QuadrigaStorageException;

	public abstract boolean checkCollabProjectAccess(String userName, String projectId,
			String collaboratorRole) throws QuadrigaStorageException;

	public abstract boolean checkProjectOwnerEditorAccess(String userName, String projectId)
			throws QuadrigaStorageException;

}
