package edu.asu.spring.quadriga.dao.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IRetrieveProjectManagerDAO {

	public abstract IProject getProjectDetails(String projectId)
			throws QuadrigaStorageException;

	List<IProject> getProjectList(String sUserName)
			throws QuadrigaStorageException;

	List<IProject> getCollaboratorProjectList(String sUserName)
			throws QuadrigaStorageException;

	List<IProject> getProjectListAsWorkspaceOwner(String sUserName)
			throws QuadrigaStorageException;

	List<IProject> getProjectListAsWorkspaceCollaborator(String sUserName)
			throws QuadrigaStorageException;

}