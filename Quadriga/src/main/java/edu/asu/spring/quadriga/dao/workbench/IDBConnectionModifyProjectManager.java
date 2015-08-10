package edu.asu.spring.quadriga.dao.workbench;

import java.util.ArrayList;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyProjectManager 
{
	
	/**
	 * This method changes the owner of the project.
	 * The new user will become the owner of the project and existing owner will become collaborator with 'ADMIN' 
	 * role to the project.
	 * @param projectId
	 * @param oldOwner
	 * @param newOwner
	 * @param collabRole
	 * @throws QuadrigaStorageException
	 * @author kiran kumar batna
	 */
	public abstract void transferProjectOwnerRequest(String projectId,
			String oldOwner, String newOwner, String collabRole)
			throws QuadrigaStorageException;

	/**
	 * This method deletes the associated editor role of the project
	 * @param projectId
	 * @param owner
	 * @throws QuadrigaStorageException
	 * @author kiran kumar batna
	 */
	public abstract void deleteProjectOwnerEditor(String projectId, String owner)
			throws QuadrigaStorageException;


}
