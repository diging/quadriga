package edu.asu.spring.quadriga.db.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionRetrieveProjectManager {

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;
	
	public abstract IProject getProjectDetails(String projectId)
			throws QuadrigaStorageException;

	public abstract List<IProject> getProjectList(String sUserName,String spName)
			throws QuadrigaStorageException;

	public abstract int setupTestEnvironment(String sQuery) throws QuadrigaStorageException;

	public abstract List<IProject> getCollaboratorProjectList(String sUserName, String collaboratorRole)
			throws QuadrigaStorageException;


}
