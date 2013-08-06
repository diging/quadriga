package edu.asu.spring.quadriga.db.workbench;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyProjectManager {
	
	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

	public abstract String deleteProjectRequest(String projectIdList)
			throws QuadrigaStorageException;

	public abstract String updateProjectRequest(IProject project, String userName)
			throws QuadrigaStorageException;

	public abstract String addProjectRequest(IProject project)
			throws QuadrigaStorageException;

	public abstract void transferProjectOwnerRequest(String projectId,
			String oldOwner, String newOwner, String collabRole)
			throws QuadrigaStorageException;

}
