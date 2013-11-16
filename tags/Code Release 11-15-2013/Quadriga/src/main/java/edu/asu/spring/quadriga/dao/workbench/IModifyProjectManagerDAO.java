package edu.asu.spring.quadriga.dao.workbench;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyProjectManagerDAO {

	public abstract void transferProjectOwnerRequest(String projectId,
			String oldOwner, String newOwner, String collabRole)
			throws QuadrigaStorageException;

	public abstract QuadrigaUserDTO getProjectOwner(String userName)
			throws QuadrigaStorageException;

	void deleteProjectRequest(String projectIdList)
			throws QuadrigaStorageException;

	void updateProjectRequest(String projID, String projName, String projDesc,
			String projAccess, String unixName, String userName)
			throws QuadrigaStorageException;

	void addProjectRequest(IProject project, String userName)
			throws QuadrigaStorageException;

	String assignProjectOwnerEditor(String projectId, String owner)
			throws QuadrigaStorageException;

	String deleteProjectOwnerEditor(String projectId, String owner)
			throws QuadrigaStorageException;

}