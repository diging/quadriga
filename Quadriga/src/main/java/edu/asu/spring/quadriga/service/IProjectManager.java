package edu.asu.spring.quadriga.service;

import java.sql.SQLException;
import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.implementation.Project;

/**
 * Javadoc missing
 * 
 */
public interface IProjectManager {
	
	public abstract List<IProject> getProjectsOfUser(String sUserId) throws SQLException;
	
	public abstract String updateProjectDetails(Project existingProject);
	
	public abstract int deleteProject(String projectId);
	
	/**
	 * Calls DBconnection manager to add the given project
	 * @param newProject -- Project class object
	 * @return Error message on any error else a blank string
	 * @author Kiran Kumar Batna
	 */
	public abstract String addNewProject(IProject newProject);
	
	public abstract IProject getProject(String id);

	public abstract int addCollaborators(ICollaborator collaborator);
	
	public abstract IProject showNonExistingCollaborator(String projectid);
	
	public abstract IProject showExistingCollaborator(String projectid);


}
