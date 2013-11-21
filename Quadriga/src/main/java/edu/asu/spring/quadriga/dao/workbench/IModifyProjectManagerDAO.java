package edu.asu.spring.quadriga.dao.workbench;

import java.util.ArrayList;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyProjectManagerDAO {

	public abstract void transferProjectOwnerRequest(String projectId,
			String oldOwner, String newOwner, String collabRole)
			throws QuadrigaStorageException;

	void deleteProjectRequest(ArrayList<String> projectIdList)
			throws QuadrigaStorageException;

	void updateProjectRequest(String projID, String projName, String projDesc,
			String projAccess, String unixName, String userName)
			throws QuadrigaStorageException;

	void addProjectRequest(IProject project, String userName)
			throws QuadrigaStorageException;

	void assignProjectOwnerEditor(String projectId, String owner)
			throws QuadrigaStorageException;

	void deleteProjectOwnerEditor(String projectId, String owner)
			throws QuadrigaStorageException;

}