package edu.asu.spring.quadriga.service;

import java.sql.SQLException;
import java.util.List;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.implementation.Project;

public interface IProjectManager {
	
	public abstract List<IProject> getProjectsOfUser(String sUserId) throws SQLException;
	
	public abstract String updateProjectDetails(Project existingProject);
	
	public abstract int deleteProject(String projectId);
	
	public abstract int addNewProject(Project newProject);
	
	public abstract IProject getProject(String id);

	


}
