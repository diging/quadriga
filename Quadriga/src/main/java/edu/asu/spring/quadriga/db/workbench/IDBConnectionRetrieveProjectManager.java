package edu.asu.spring.quadriga.db.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionRetrieveProjectManager {

	public abstract IProject getProjectDetails(String projectId)
			throws QuadrigaStorageException;

	List<IProject> getProjectList(String sUserName)
			throws QuadrigaStorageException;

	List<IProject> getProjectListAsWorkspaceOwner(String sUserName)
			throws QuadrigaStorageException;

	List<IProject> getProjectListAsWorkspaceCollaborator(String sUserName)
			throws QuadrigaStorageException;

	IProject getProjectDetailsByUnixName(String unixName)
			throws QuadrigaStorageException;

	List<IProject> getCollaboratorProjectList(String sUserName)
			throws QuadrigaStorageException;

	List<IProject> getProjectListByCollaboratorRole(String sUserName,
			String collaboratorRole) throws QuadrigaStorageException;

}
