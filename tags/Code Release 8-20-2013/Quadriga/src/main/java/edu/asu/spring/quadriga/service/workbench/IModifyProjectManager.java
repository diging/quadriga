package edu.asu.spring.quadriga.service.workbench;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyProjectManager {

	public abstract String deleteProjectRequest(String projectIdList)
			throws QuadrigaStorageException;

	public abstract String updateProjectRequest(IProject project, String userName)
			throws QuadrigaStorageException;

	public abstract String addProjectRequest(IProject project)
			throws QuadrigaStorageException;

	public abstract void transferProjectOwnerRequest(String projectId,
			String oldOwner, String newOwner, String collabRole)
			throws QuadrigaStorageException;

	public abstract String assignEditorToOwner(String projectId, String owner) throws QuadrigaStorageException;

}