package edu.asu.spring.quadriga.service.workbench;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyProjectManager {

	public abstract void deleteProjectRequest(String projectIdList)
			throws QuadrigaStorageException;

	public abstract void updateProjectRequest(IProject project, String userName)
			throws QuadrigaStorageException;

	public abstract void addProjectRequest(IProject project)
			throws QuadrigaStorageException;

	public abstract void transferProjectOwnerRequest(String projectId,
			String oldOwner, String newOwner, String collabRole)
			throws QuadrigaStorageException;

	public abstract String assignEditorToOwner(String projectId, String owner) throws QuadrigaStorageException;

	public abstract String deleteEditorToOwner(String projectId, String owner)
			throws QuadrigaStorageException;

}
