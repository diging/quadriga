package edu.asu.spring.quadriga.service.workbench;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyProjectManager {

	public abstract void deleteProjectRequest(String projectIdList)
			throws QuadrigaStorageException;

	/**
	 * This method updates a project into the database.
	 * @param project
	 * @param userName
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 *//*
	public abstract void updateProjectRequest(IProject project, String userName)
			throws QuadrigaStorageException;*/

	public abstract void transferProjectOwnerRequest(String projectId,
			String oldOwner, String newOwner, String collabRole)
			throws QuadrigaStorageException;

	public abstract String assignEditorToOwner(String projectId, String owner) throws QuadrigaStorageException;

	public abstract String deleteEditorToOwner(String projectId, String owner)
			throws QuadrigaStorageException;

	void updateProjectRequest(String projID, String projName, String projDesc,
			String projAccess, String unixName, String userName)
			throws QuadrigaStorageException;

	void addProjectRequest(IProject project, String userName)
			throws QuadrigaStorageException;

}
