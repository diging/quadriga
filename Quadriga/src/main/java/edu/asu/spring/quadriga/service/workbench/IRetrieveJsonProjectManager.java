package edu.asu.spring.quadriga.service.workbench;

import org.codehaus.jettison.json.JSONException;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IRetrieveJsonProjectManager {
	
	public abstract String getAllProjects(String sUserName)
			throws QuadrigaStorageException, JSONException;
	
	public abstract String getProjectList(String sUserName)
			throws QuadrigaStorageException, JSONException;
	
	public abstract String getProjectListAsWorkspaceCollaborator(String sUserName)
			throws QuadrigaStorageException ,JSONException;

	public abstract String getProjectListAsWorkspaceOwner(String sUserName)
			throws QuadrigaStorageException,JSONException;

	public abstract String getCollaboratorProjectList(String sUserName)
			throws QuadrigaStorageException,JSONException;
}
