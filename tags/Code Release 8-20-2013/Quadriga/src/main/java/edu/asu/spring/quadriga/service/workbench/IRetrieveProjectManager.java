package edu.asu.spring.quadriga.service.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IRetrieveProjectManager {

	public abstract IProject getProjectDetails(String projectId)
			throws QuadrigaStorageException;

	public abstract List<IProject> getProjectList(String sUserName)
			throws QuadrigaStorageException;
	
	public abstract List<ICollaborator> getCollaboratingUsers(String projectId) throws QuadrigaStorageException;

}
