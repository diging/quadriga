package edu.asu.spring.quadriga.db.workbench;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyProjectManager {
	
	public abstract void deleteProjectRequest(String projectIdList)
			throws QuadrigaStorageException;

	public abstract void updateProjectRequest(IProject project, String userName)
			throws QuadrigaStorageException;

	/**
	 *  This method inserts a record for new project
	 *  @param  project
	 *  @return String error message on error else a blank string
	 *  @exception QuadrigaStorageException
	 *  @author Kiran Kumar Batna 
	 */
	public abstract void addProjectRequest(IProject project)
			throws QuadrigaStorageException;

	public abstract void transferProjectOwnerRequest(String projectId,
			String oldOwner, String newOwner, String collabRole)
			throws QuadrigaStorageException;

	public abstract String assignProjectOwnerEditor(String projectId, String owner)
			throws QuadrigaStorageException;

	public abstract String deleteProjectOwnerEditor(String projectId, String owner)
			throws QuadrigaStorageException;

}
