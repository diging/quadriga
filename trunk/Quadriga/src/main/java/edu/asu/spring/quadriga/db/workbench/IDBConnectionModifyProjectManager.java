package edu.asu.spring.quadriga.db.workbench;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyProjectManager {

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
