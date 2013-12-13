package edu.asu.spring.quadriga.db.workbench;

import java.util.ArrayList;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyProjectManager {
	
	public abstract void transferProjectOwnerRequest(String projectId,
			String oldOwner, String newOwner, String collabRole)
			throws QuadrigaStorageException;

	public abstract void assignProjectOwnerEditor(String projectId, String owner)
			throws QuadrigaStorageException;

	public abstract void deleteProjectOwnerEditor(String projectId, String owner)
			throws QuadrigaStorageException;

	void addProjectRequest(IProject project, String userName)
			throws QuadrigaStorageException;

	void updateProjectRequest(String projID, String projName, String projDesc,
			String projAccess, String unixName, String userName)
			throws QuadrigaStorageException;

	void deleteProjectRequest(ArrayList<String> projectIdList)
			throws QuadrigaStorageException;

}
